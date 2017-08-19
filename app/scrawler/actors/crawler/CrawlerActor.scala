package scrawler.actors.crawler

import java.net.URL

import akka.actor.{Actor, ActorRef}
import com.google.inject.Inject
import scrawler.actors.crawler.CrawlerActor.CrawlURL
import scrawler.actors.indexer.SiteIndexingActor
import scrawler.actors.parser.PageParser
import scrawler.util.URLSupport

import scala.collection.JavaConverters._

object CrawlerActor
{
  trait Factory {
    def apply(): Actor
  }

//  def props: Props = Props[CrawlerActor]

  case class CrawlURL(url: URL)
}

class CrawlerActor(siteMapActor: ActorRef) extends Actor {

  @Inject
  val parser: PageParser = null


  def receive = {
    case CrawlURL(url) => traverseURL(url)
  }


  private def traverseURL(url: URL): Unit =
  {
    println(s"Will traverse url $url")
    getAllPageLinks(url).foreach(childUrl =>
                                 {
                                   sender ! SiteIndexingActor.IndexURL(childUrl)
                                 })
  }

  private def getAllPageLinks(url: URL): List[URL] = {
    val linkElements = parser.fetchElements(url, "a[href]")
    val rawLinks = (for (link <- linkElements.iterator().asScala) yield
      {
        link.attr("href")
      }).toSeq.distinct

    rawLinks.flatMap(URLSupport.toURL).toList
  }
}