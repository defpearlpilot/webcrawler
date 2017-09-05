package springboard

import scala.annotation.tailrec

object Fibonacci
{
  def fib(n: Int): Int = {
    def _fib(n: Int): Int = {
      n match {
        case 0 => 0
        case 1 => 1
        case 2 => 1
        case _ => _fib(n - 1) + _fib(n - 2)
      }
    }

    _fib(n)
  }

  def tfib(n: Int): Int = {
    @tailrec
    def _tfib(_n: Int, prev: Int, cur: Int): Int = {
      n match {
        case 0 => prev
        case _ => _tfib(_n - 1, cur, cur + prev)
      }
    }

    _tfib(n, 0, 1)
  }


  def main(args: Array[String]): Unit =
  {
    println(fib(0))
    println(fib(1))
    println(fib(2))
    println(fib(3))
    println(fib(4))
    println(fib(5))
  }

}
