package scrawler.actors.indexer

import java.net.URL

abstract class IndexCommand

case class InitializeDomainCommand(domain: String) extends IndexCommand
case class IndexURLCommand(url: URL) extends IndexCommand
case class IndexingCompleteCommand() extends IndexCommand