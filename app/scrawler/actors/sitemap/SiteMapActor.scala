package scrawler.actors.sitemap

import java.net.URL

import akka.NotUsed
import akka.actor.{Actor, Props}
import akka.stream.scaladsl.Sink
import play.api.libs.json.JsValue
import scrawler.actors.sitemap.SiteMapActor.UpdateSiteMap


object SiteMapActor
{
  trait Factory
  {
    def apply(): Actor
  }

  def props: Props = Props[SiteMapActor]

  /* update the siteMap*/
  case class UpdateSiteMap(pcMap: Map[URL, Seq[URL]])
}

class SiteMapActor(val sink: Sink[JsValue, NotUsed]) extends Actor
{

  def receive =
  {
    case UpdateSiteMap(map) => doSomething(map)
  }


  def doSomething(map: Map[URL, Seq[URL]]): Unit =
  {

  }
}
