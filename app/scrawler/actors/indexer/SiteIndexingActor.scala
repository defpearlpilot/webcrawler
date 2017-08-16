package scrawler.actors.indexer

import java.net.URL
import javax.inject._

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import play.api.libs.concurrent.InjectedActorSupport
import scrawler.actors.crawler.CrawlURLCommand
import scrawler.actors.indexer.SiteIndexingActor.{FutureURLCommand, IndexURLCommand, IndexingCompleteCommand, InitializeDomainCommand}
import scrawler.util.URLSupport

import scala.collection.mutable


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
    if (domain.isEmpty)
    {
      throw new IllegalStateException("Attempting to index a url without having defined the domain")
    }

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


  private def futureIndexURL(message: FutureURLCommand): Unit =
  {
    if (domain.isEmpty) {
      throw new IllegalStateException("Attempting to index a url without having defined the domain")
    }

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