package scrawler.actors.indexer

import java.net.URL
import javax.inject._

import akka.{Done, NotUsed}
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import akka.stream.scaladsl.{BroadcastHub, Flow, Keep, MergeHub, Sink}
import akka.util.Timeout
import play.api.libs.concurrent.InjectedActorSupport
import play.api.libs.json.JsValue
import play.api.mvc.RequestHeader
import scrawler.actors.crawler.CrawlerActor
import scrawler.actors.indexer.SiteIndexingActor.{FutureURLCommand, IndexChildURL, IndexURL, IndexingCompleteCommand}
import scrawler.actors.sitemap.SiteMapActor
import scrawler.util.URLSupport
import scala.concurrent.duration._

import scala.collection.mutable
import scala.concurrent.Future


object SiteIndexingActor
{
  trait Factory {
    def apply(url: URL): Actor
  }

//  def props(url: URL): Props = {
//    Props[SiteIndexingActor](new SiteIndexingActor(url))
//  }


  case class IndexURL(url: URL)
  case class IndexChildURL(url: URL)
  case class FutureURLCommand(url: URL)
  case class IndexingCompleteCommand()
}


class SiteIndexingActor @Inject()(val url: URL)
extends Actor
          with InjectedActorSupport
          with ActorLogging
{
  import akka.pattern.{ask, pipe}


  private val domain: URL = new URL(s"${url.getProtocol}://${url.getHost}:${url.getPort}")

  private val visited = new mutable.HashSet[URL]


  override def receive: Receive = LoggingReceive {
    case IndexURL(toIndex) => indexURL(toIndex)
    case IndexChildURL(toIndex) => indexChildURL(toIndex)
//    case url: FutureURLCommand => futureIndexURL(url)
    case IndexingCompleteCommand => finishCrawling()
  }


  private def indexURL(toIndex: URL): Unit =
  {
    println(s"About to index ${toIndex.getHost}")
    if (toIndex == null)
    {
      throw new IllegalArgumentException()
    }


    val (hubSink, hubSource) = MergeHub.source[JsValue](perProducerBufferSize = 16)
                                       .toMat(BroadcastHub.sink(bufferSize = 256))(Keep.both)
                                       .run()

    val siteMapper: ActorRef = context.actorOf(Props[SiteMapActor](new SiteMapActor()))
    val crawler: ActorRef = context.actorOf(Props[CrawlerActor](new CrawlerActor(siteMapper)))

    crawler ! CrawlerActor.CrawlURL(toIndex)

  }


  private def indexChildURL(url: URL): Unit =
  {
    if (visited.contains(url))
    {
      return
    }

    visited.add(url)

    if (url.getHost.equals(domain.getHost))
    {
      sender() ! CrawlerActor.CrawlURL(url)
    }
  }


  private def toSiteMapFutureFlow(request: RequestHeader): Future[Flow[JsValue, JsValue, NotUsed]] =
  {
    // Use guice assisted injection to instantiate and configure the child actor.
    implicit val timeout = Timeout(1.second) // the first run in dev can take a while :-(

    val baseUrl = "http://wiprodigital.com"

    indexer ! SiteIndexingActor.InitializeDomainCommand(baseUrl)

    val future: Future[Any] = indexer ? SiteIndexingActor.FutureURLCommand(baseUrl)
    val futureFlow: Future[Flow[JsValue, JsValue, NotUsed]] = future.mapTo[Flow[JsValue, JsValue, NotUsed]]
    futureFlow
  }


  //  private def futureIndexURL(message: FutureURLCommand): Unit =
//  {
//    checkDomain()
//
//    val url = message.url
//
//    if (!visited.contains(url)) {
//      visited.add(url)
//
//      if (url.getHost.equals(domain.get.getHost)) {
//        crawler ! CrawlURLCommand(url)
//      }
//      else {
//        println(s"$url is not part of this domain!")
//      }
//    }
//
//
//    pipe()
//  }


//  val (crawlerSink, crawlerSource) = MergeHub.source[JsValue](perProducerBufferSize = 16)
//    .toMat(BroadcastHub.sink(bufferSize = 256))(Keep.both)
//    .run()
//
//
//  private val commandSink: Sink[JsValue, Future[Done]] = Sink.foreach
//  {
//    json => {
//      // When the user types in a stock in the upper right corner, this is triggered,
//    }
//  }



//  private def future(): Unit = {
//    // Put the source and sink together to make a flow of hub source as output (aggregating all
//    // stocks as JSON to the browser) and the actor as the sink (receiving any JSON messages
//    // from the browse), using a coupled sink and source.
//    Flow.fromSinkAndSourceCoupled(commandSink, crawlerSource).watchTermination(){ (_, termination) =>
//      // When the flow shuts down, make sure this actor also stops.
//      termination.foreach(_ => context.stop(self))
//      NotUsed
//                                                                          }
//
//  }


  private def finishCrawling(): Unit =
  {
    println("I'm done")
    context.parent ! true
  }
}