package springboard

import java.net.URL

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.util.Timeout
import akka.stream.OverflowStrategy.dropHead
import akka.stream.scaladsl.RunnableGraph

import scala.concurrent.Future
import scala.concurrent.duration._

case class Message(message: String)

object Application {
  implicit val timeout: Timeout = 15.minute
  val baseUrl = "http://wiprodigital.com"


  def  main(args: Array[String]) {
    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val url = new URL(baseUrl)

    val q1 = Source.queue[Message](100, dropHead)

    val queue = Source.queue[Message](100, dropHead).to(Sink.foreach(println)).run()


    val sink = Sink.fold[Message, Message](Message("hello"))((m1, m2) => m2)

    queue.offer(Message("this"))
    queue.offer(Message("is"))
    queue.offer(Message("a"))
    queue.offer(Message("message"))
    queue.complete()

    val flow: RunnableGraph[Future[Message]] = q1.toMat(sink)(Keep.right)
    val eventualMessage = flow.run()
    println(eventualMessage)
    //    val indexer = actorSystem.actorOf(Props[SiteIndexingActor](new SiteIndexingActor(url)))
//
//    URLSupport.toURL(baseUrl).foreach(url => indexer ! IndexURL(url))
  }
}
