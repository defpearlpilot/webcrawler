package scrawler.util

import java.net.URL

object URLSupport {
  def toURL(url: String): Option[URL] = {
    try {
      Some(new URL(url))
    }
    catch {
      case _: Throwable => None
    }
  }
}
