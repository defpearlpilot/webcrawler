package scrawler.actors.indexer

import java.net.URL
import javax.inject._

import akka.{Done, NotUsed}
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import akka.stream.scaladsl.{BroadcastHub, Flow, Keep, MergeHub, Sink}
import play.api.libs.concurrent.InjectedActorSupport
import play.api.libs.json.JsValue
import scrawler.actors.crawler.CrawlURLCommand
import scrawler.actors.indexer.SiteIndexingActor.{FutureURLCommand, IndexURLCommand, IndexingCompleteCommand, InitializeDomainCommand}
import scrawler.util.URLSupport

import scala.collection.mutable
import scala.concurrent.Future


object SiteIndexingActor
{
  case class InitializeDomainCommand(domain: String)
  case class IndexURLCommand(url: URL)
  case class FutureURLCommand(url: URL)
  case class IndexingCompleteCommand()
}

class SiteIndexingActor @Inject()(@Named("crawler") crawler: ActorRef)
  extends Actor
          with InjectedActorSupport
          with ActorLogging
{
  import akka.pattern.{ask, pipe}


  private var domain: Option[URL] = Option.empty
  private val visited = new mutable.HashSet[URL]


  override def receive: Receive = LoggingReceive {
    case domain: InitializeDomainCommand => initializeDomain(domain)
    case url: IndexURLCommand => indexURL(url)
    case url: FutureURLCommand => futureIndexURL(url)
    case IndexingCompleteCommand => finishCrawling()
  }


  private def indexURL(message: IndexURLCommand): Unit =
  {
    checkDomain()

    val url = message.url

    if (!visited.contains(url))
    {
      visited.add(url)

      if (url.getHost.equals(domain.get.getHost))
      {
        crawler ! CrawlURLCommand(url)
      }
      else
      {
        println(s"$url is not part of this domain!")
      }
    }
  }


  private def checkDomain(): Unit =
  {
    if (domain.isEmpty)
    {
      throw new IllegalStateException("Attempting to index a url without having defined the domain")
    }
  }

  private def futureIndexURL(message: FutureURLCommand): Unit =
  {
    checkDomain()

    val url = message.url

    if (!visited.contains(url)) {
      visited.add(url)

      if (url.getHost.equals(domain.get.getHost)) {
        crawler ! CrawlURLCommand(url)
      }
      else {
        println(s"$url is not part of this domain!")
      }
    }


    pipe()
  }


  val (crawlerSink, crawlerSource) = MergeHub.source[JsValue](perProducerBufferSize = 16)
    .toMat(BroadcastHub.sink(bufferSize = 256))(Keep.both)
    .run()


  private val commandSink: Sink[JsValue, Future[Done]] = Sink.foreach
  {
    json => {
      // When the user types in a stock in the upper right corner, this is triggered,
      val symbol = (json \ "symbol").as[StockSymbol]
      addStocks(Set(symbol))
    }
  }



  private def future(): Unit = {
    // Put the source and sink together to make a flow of hub source as output (aggregating all
    // stocks as JSON to the browser) and the actor as the sink (receiving any JSON messages
    // from the browse), using a coupled sink and source.
    Flow.fromSinkAndSourceCoupled(commandSink, crawlerSource).watchTermination(){ (_, termination) =>
      // When the flow shuts down, make sure this actor also stops.
      termination.foreach(_ => context.stop(self))
      NotUsed
                                                                          }

  }


  private def initializeDomain(message: InitializeDomainCommand): Unit =
  {
    domain match
    {
      case Some(_) => throw new IllegalStateException("Attempting to initialize domain twice")
      case None =>
        domain = URLSupport.toURL(message.domain)
        if (domain.isEmpty) {
          throw new IllegalArgumentException("Attempting to set domain with invalid URL")
        }
    }

    println(s"Initialized domain to: ${domain.get}")
  }


  private def finishCrawling(): Unit =
  {
    println("I'm done")
    context.parent ! true
  }
}