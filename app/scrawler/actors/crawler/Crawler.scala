package scrawler.actors.crawler

import java.net.URL

import akka.actor.Actor
import com.google.inject.Inject
import org.jsoup.Jsoup
import scrawler.actors.indexer.IndexURLCommand
import scrawler.actors.parser.PageParser
import scrawler.util.URLSupport

import scala.collection.JavaConverters._


class Crawler @Inject()(parser: PageParser) extends Actor {

  def receive = {
    case crawlCommand: CrawlURLCommand => traverseURL(crawlCommand.url)
  }


  private def traverseURL(url: URL): Unit =
  {
    getAllPageLinks(url).foreach(childUrl =>
                                 {
                                   sender ! IndexURLCommand(childUrl)
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