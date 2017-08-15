package org.scrawler.actors.indexer

import java.net.URL

import akka.actor.{Actor, ActorRef}
import org.scrawler.actors.crawler.CrawlURLCommand
import org.scrawler.util.URLSupport

import scala.collection.mutable

class SiteIndex(val crawler: ActorRef) extends Actor {

  private var domain: Option[URL] = None

  private val visited = new mutable.HashSet[URL]


  def receive = {
    case domain: InitializeDomainCommand => initializeDomain(domain)
    case url: IndexURLCommand => indexURL(url)
    case IndexingCompleteCommand => finishCrawling()
  }


  def indexURL(message: IndexURLCommand): Unit =
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


  def initializeDomain(message: InitializeDomainCommand): Unit =
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


  def finishCrawling(): Unit =
  {
    println("I'm done")
    context.parent ! true
  }
}