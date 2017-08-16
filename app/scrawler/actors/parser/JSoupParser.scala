package scrawler.actors.parser

import java.net.URL

import org.jsoup.Jsoup
import org.jsoup.select.Elements

class JSoupParser extends PageParser
{
  override def fetchElements(url: URL, element: String): Elements =
  {
    Jsoup.connect(url.toString).timeout(0).get().select(element)
  }
}
