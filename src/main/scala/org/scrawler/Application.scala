package org.scrawler

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import org.scrawler.actors.crawler.Crawler
import org.scrawler.actors.indexer.{IndexURLCommand, InitializeDomainCommand, SiteIndex}
import org.scrawler.util.URLSupport

import scala.concurrent.duration._

object Application {
  implicit val timeout: Timeout = 15.minute
  val baseUrl = "http://wiprodigital.com"


  def main(args: Array[String]) {

    val actorSystem = ActorSystem()

    val crawler = actorSystem.actorOf(Props[Crawler](new Crawler()))
    val index = actorSystem.actorOf(Props[SiteIndex](new SiteIndex(crawler = crawler)))

    index ! InitializeDomainCommand(baseUrl)

    URLSupport.toURL(baseUrl).foreach(url => index ! IndexURLCommand(url))
  }
}
