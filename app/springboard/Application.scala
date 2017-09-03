package springboard

import java.net.URL
import java.util.concurrent.ExecutorService

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, RunnableGraph, Sink, Source}
import akka.util.Timeout
import akka.stream.OverflowStrategy.dropHead

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

case class Message(message: String)

object Application {
  implicit val timeout: Timeout = 15.minute
  val baseUrl = "http://wiprodigital.com"


  def  main(args: Array[String]) {
    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val ec = actorSystem.dispatchers.defaultGlobalDispatcher

    val url = new URL(baseUrl)

    val sink = Sink.foreach[Message](m1 => {
      println(m1)
      m1
    })
    val queue = Source.queue[Message](100, dropHead).to(sink).run()


    queue.offer(Message("hello"))
    queue.offer(Message("this"))
    queue.offer(Message("is"))
    queue.offer(Message("a"))
    queue.offer(Message("message"))
    queue.complete()

    queue.watchCompletion().onComplete(f => {
      println(f)
      actorSystem.terminate()
    })

    //    val flow: RunnableGraph[Future[Message]] = q1.toMat(sink)(Keep.right)
    //    val eventualMessage = flow.run()

    //    val indexer = actorSystem.actorOf(Props[SiteIndexingActor](new SiteIndexingActor(url)))
    //
    //    URLSupport.toURL(baseUrl).foreach(url => indexer ! IndexURL(url))
  }
}
