import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport


class Module extends AbstractModule with AkkaGuiceSupport {
  import scrawler.actors.crawler.CrawlerActor
  import scrawler.actors.indexer.SiteIndexingActor
  import scrawler.actors.parser.{JSoupParser, PageParser}
  import scrawler.actors.sitemap.SiteMapActor

  override def configure(): Unit = {
    bind(classOf[PageParser]).to(classOf[JSoupParser])

    bindActor[SiteIndexingActor]("indexer")
    bindActorFactory[CrawlerActor, CrawlerActor.Factory]
    bindActorFactory[SiteMapActor, SiteMapActor.Factory]
  }

}
