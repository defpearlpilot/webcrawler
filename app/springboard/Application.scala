package springboard

import java.net.URL

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import scrawler.actors.indexer.SiteIndexingActor
import scrawler.actors.indexer.SiteIndexingActor.IndexURL
import scrawler.util.URLSupport

import scala.concurrent.duration._

object Application {
  implicit val timeout: Timeout = 15.minute
  val baseUrl = "http://wiprodigital.com"


  def  main(args: Array[String]) {
    val actorSystem = ActorSystem()

    val url = new URL(baseUrl)
    val indexer = actorSystem.actorOf(Props[SiteIndexingActor](new SiteIndexingActor(url)))

    URLSupport.toURL(baseUrl).foreach(url => indexer ! IndexURL(url))
  }
}
