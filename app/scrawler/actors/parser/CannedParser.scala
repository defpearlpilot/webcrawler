package scrawler.actors.parser

import java.net.URL

import org.jsoup.nodes.{Attributes, Element}
import org.jsoup.parser.Tag
import org.jsoup.select.Elements

import scala.collection.mutable

class CannedParser extends PageParser
{
  val map = new mutable.HashMap[String, Elements]()
  map.put("http://first.com", new Elements(anchor("http://first.com/page1"),
                                           anchor("http://first.com/page2")))
  map.put("http://first.com/page1", new Elements(anchor("http://first.com")))
  map.put("http://first.com/page2", new Elements(anchor("http://first.com")))

  def anchor(ref: String): Element = {
    val attributes = new Attributes()
    attributes.put("href", ref)

    val anchor = Tag.valueOf("a")
    new Element(anchor, "http://blah.com", attributes)
  }



  override def fetchElements(url: URL, element: String): Elements =
  {
    map(url.toString)
  }
}
