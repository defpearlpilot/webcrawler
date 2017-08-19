package controllers

import javax.inject._

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.scaladsl.Flow
import akka.util.Timeout
import play.api.Logger
import play.api.libs.concurrent.InjectedActorSupport
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import scrawler.actors.indexer.SiteIndexingActor
import scrawler.actors.indexer.SiteIndexingActor.IndexURL
import scrawler.util.URLSupport

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class SiteMapController @Inject()(cc: ControllerComponents)
                                 (implicit ec: ExecutionContext)
  extends AbstractController(cc)
          with RequestValidator
          with InjectedActorSupport
{

  override def logger: Logger = Logger("SiteMapController")

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action
  {
    Ok(views.html.index("Your new application is ready."))
  }

  def crawl = Action
  {
    val actorSystem = ActorSystem()
    val maybeURL = URLSupport.toURL("http://wiprodigital.com")

    maybeURL.foreach(url => {
      val indexer: ActorRef = actorSystem.actorOf(Props[SiteIndexingActor](new SiteIndexingActor(url)))
      indexer ! IndexURL(url)
    })

    Ok(views.html.crawl())
  }


//  def ws: WebSocket = WebSocket.acceptOrResult[JsValue, JsValue] {
//      case rh if validate(rh) =>
//        toWebSocketFutureFlow(rh)
//          .map { flow => Right(flow) }
//          .recover {
//                     case e: Exception =>
//                       logger.error("Cannot create websocket", e)
//                       val jsError = Json.obj("error" -> "Cannot create websocket")
//                       val result = InternalServerError(jsError)
//                       Left(result)
//                   }
//
//      case rejected =>
//        logger.error(s"Request ${rejected} failed same origin check")
//        Future.successful {
//                            Left(Forbidden("forbidden"))
//                          }
//
//    }
//
//

}


trait RequestValidator extends SameOriginCheck {
  def logger: Logger

  def validate(rh: RequestHeader): Boolean = {
    isSameOrigin(rh)
  }
}


trait SameOriginCheck {
  def logger: Logger

  def isSameOrigin(rh: RequestHeader): Boolean = {
    rh.headers.get("Origin") match {

      case Some(originValue) if originMatches(originValue) => {
        logger.debug(s"originCheck: originValue = $originValue")
        true
      }

      case Some(badOrigin) => {
        logger.error(s"originCheck: rejecting request because Origin header value ${badOrigin} is not in the same origin")
        false
      }

      case None =>
        logger.error("originCheck: rejecting request because no Origin header found")
        false
    }
  }

  def originMatches(origin: String): Boolean = {
    origin.contains("localhost:9000") || origin.contains("localhost:19001")
  }

}