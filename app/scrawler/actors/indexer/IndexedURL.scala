package scrawler.actors.indexer

import java.net.URL

case class IndexedURL(url: URL, parent: URL)

object IndexedURL
{
  import play.api.libs.json._ // Combinator syntax

  implicit val indexedURL: Writes[IndexedURL] = new Writes[IndexedURL] {
    override def writes(index: IndexedURL): JsValue = Json.obj(
      "type" -> "url",
      "url" -> index.url.toString,
      "from" -> index.parent.toString
    )
  }

}
