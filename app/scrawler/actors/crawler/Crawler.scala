package scrawler.actors.crawler

import java.net.URL

import akka.actor.Actor
import com.google.inject.Inject
import scrawler.actors.indexer.SiteIndexingActor
import scrawler.actors.parser.PageParser
import scrawler.util.URLSupport

import scala.collection.JavaConverters._


class Crawler extends Actor {

  @Inject
  val parser: PageParser = null

  def receive = {
    case crawlCommand: CrawlURLCommand => traverseURL(crawlCommand.url)
  }


  private def traverseURL(url: URL): Unit =
  {
    println(s"Will traverse url $url")
    getAllPageLinks(url).foreach(childUrl =>
                                 {
                                   sender ! SiteIndexingActor.IndexURLCommand(childUrl)
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