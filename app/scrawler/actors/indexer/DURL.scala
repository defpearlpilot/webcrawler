package scrawler.actors.indexer

import java.net.URL

import akka.NotUsed
import akka.stream.scaladsl.Source

class DURL(val url: URL)
{
  private val source: Source[IndexedURL, NotUsed] = {
    Source.single(IndexedURL(url, url))
  }
}
