package org.scrawler.actors.crawler

import java.net.URL

abstract class CrawlerMessage

case class CrawlURLCommand(url: URL)