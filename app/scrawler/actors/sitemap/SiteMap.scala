package scrawler.actors.sitemap

import java.net.URL

case class SiteMapRoot(url: URL)

case class SiteMapEdge(url: URL, child: URL)


class SiteMap(private val url: URL)
{

}
