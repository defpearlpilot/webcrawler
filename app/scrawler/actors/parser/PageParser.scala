package scrawler.actors.parser

import java.net.URL

import org.jsoup.select.Elements

trait PageParser
{
  def fetchElements(url: URL, element: String): Elements
}
