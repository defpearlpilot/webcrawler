package scrawler.actors.sitemap

import java.net.URL

import akka.NotUsed
import akka.actor.{Actor, Props}
import akka.event.LoggingReceive
import akka.stream.{KillSwitches, UniqueKillSwitch}
import akka.stream.OverflowStrategy.dropHead
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source, SourceQueueWithComplete}
import play.api.libs.json.JsValue
import scrawler.actors.sitemap.SiteMapActor.{Subscribe, UpdateSiteMap}

import scala.concurrent.duration._
import scala.concurrent.Future


object SiteMapActor
{
  trait Factory
  {
    def apply(): Actor
  }

  def props: Props = Props[SiteMapActor]

  /* update the siteMap*/
  case class UpdateSiteMap(pcMap: Map[URL, Seq[URL]])
  case class Subscribe(url: URL)
}


class SiteMapActor(private val siteMapSink: Sink[JsValue, NotUsed]) extends Actor
{


  def receive = LoggingReceive
  {
    case UpdateSiteMap(map) => processSiteMapUpdate(map)
//    case Subscribe(url) => subscribe(url)
  }


  def processSiteMapUpdate(map: Map[URL, Seq[URL]]): Unit =
  {
  }


//  def subscribe(url: URL): Future[Flow[JsValue, JsValue, NotUsed]] =
//  {
//    val killswitchFlow: Flow[JsValue, JsValue, UniqueKillSwitch] = {
//      Flow.apply[JsValue]
//      .joinMat(KillSwitches.singleBidi[JsValue, JsValue])(Keep.right)
//      .backpressureTimeout(1.seconds)
//    }
//
//    val value: Source[SiteMapEdge, SourceQueueWithComplete[SiteMapEdge]] = Source.queue[SiteMapEdge](100, dropHead)
//
//    siteMapSource.
//    // Set up a complete runnable graph from the stock source to the hub's sink
//    val graph: RunnableGraph[UniqueKillSwitch] = {
//    siteMapSource.viaMat(killswitchFlow)(Keep.right)
//    .to(siteMapSink)
//    .named(s"stock-${stock.symbol}-$id")
//  }
//
//    // Start it up!
//    val killSwitch = graph.run()
//
//  }
}
