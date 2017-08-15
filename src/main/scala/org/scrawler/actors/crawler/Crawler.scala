package org.scrawler.actors.crawler

import java.net.URL

import akka.actor.Actor
import org.jsoup.Jsoup
import org.scrawler.actors.indexer.IndexURLCommand
import org.scrawler.util.URLSupport

import scala.collection.JavaConverters._


class Crawler() extends Actor {

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
    val linkElements = Jsoup.connect(url.toString).timeout(0).get().select("a[href]")
    val rawLinks = (for (link <- linkElements.iterator().asScala) yield
      {
        link.attr("href")
      }).toSeq.distinct

    rawLinks.flatMap(URLSupport.toURL).toList
  }
}