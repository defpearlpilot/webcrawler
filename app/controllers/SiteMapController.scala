package controllers

import javax.inject._

import akka.actor.{ActorSystem, Props}
import play.api.mvc._
import scrawler.actors.crawler.Crawler
import scrawler.actors.indexer.{IndexURLCommand, InitializeDomainCommand, SiteIndex}
import scrawler.actors.parser.CannedParser
import scrawler.util.URLSupport

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class SiteMapController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def crawl = Action
  {
    val baseUrl = "http://wiprodigital.com"
    val actorSystem = ActorSystem()

    val crawler = actorSystem.actorOf(Props[Crawler](new Crawler(new CannedParser())))
    val index = actorSystem.actorOf(Props[SiteIndex](new SiteIndex(crawler = crawler)))

    index ! InitializeDomainCommand(baseUrl)

    URLSupport.toURL(baseUrl).foreach(url => index ! IndexURLCommand(url))

    Ok(views.html.crawl())
  }

}
