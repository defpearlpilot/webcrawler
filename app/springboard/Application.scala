package springboard

import java.net.URL

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.Timeout
import akka.stream.OverflowStrategy.dropHead

import scala.concurrent.duration._

case class Message(message: String)

object Application {
  implicit val timeout: Timeout = 15.minute
  val baseUrl = "http://wiprodigital.com"


  def  main(args: Array[String]) {
    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val url = new URL(baseUrl)

    val queue = Source.queue[Message](100, dropHead).to(Sink.foreach(println)).run()


    queue.offer(Message("this"))
    queue.offer(Message("is"))
    queue.offer(Message("a"))
    queue.offer(Message("message"))

    //    val indexer = actorSystem.actorOf(Props[SiteIndexingActor](new SiteIndexingActor(url)))
//
//    URLSupport.toURL(baseUrl).foreach(url => indexer ! IndexURL(url))
  }
}
