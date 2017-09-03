package springboard

import java.net.URL
import java.util.concurrent.ExecutorService

import akka.actor.{Actor, ActorSystem, Props}
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, RunnableGraph, Sink, Source, SourceQueue}
import akka.util.Timeout
import akka.stream.OverflowStrategy.fail
import play.api.Logger
import play.api.libs.EventSource

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._

case class Tick()

class TickActor(queue: SourceQueue[String]) extends Actor {
  def receive = {
    case Tick => {
      println("ticking")
      queue.offer("tack")
    }
  }
}




object Application2 {
  implicit val timeout: Timeout = 15.minute
  val baseUrl = "http://wiprodigital.com"

  def peekMatValue[T, M](src: Source[T, M]): (Source[T, M], Future[M]) = {
    val p = Promise[M]
    val s = src.mapMaterializedValue(
      { m =>
        p.trySuccess(m)
        m
      })
    (s, p.future)
  }

  def main(args: Array[String]) {
    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val ec = actorSystem.dispatchers.defaultGlobalDispatcher

    val (queueSource, futureQueue) = peekMatValue(Source.queue[String](10, fail))

    futureQueue.map
    { queue =>

      val tickActor = actorSystem.actorOf(Props(new TickActor(queue)))
      val tickSchedule =
        actorSystem.scheduler.schedule(0 milliseconds,
                                       1 second,
                                       tickActor,
                                       Tick)

      queue.watchCompletion().map
      { done =>
        println("Client disconnected")
        tickSchedule.cancel
        println("Scheduler canceled")
      }
    }

    queueSource.map
    {
      m => println(m)
    }
  }

}
