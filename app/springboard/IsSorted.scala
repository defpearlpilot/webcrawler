package springboard

import scala.annotation.tailrec

object IsSorted
{
  def isAscending(first: Int, second: Int): Boolean = first <= second

  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean =
  {
    @tailrec
    def _isSorted(as: Array[A], i1: Int, i2: Int): Boolean = {
      if (i2 == as.length - 1) {
        ordered(as(i1), as(i2))
      } else if (ordered(as(i1), as(i2))) {
        _isSorted(as, i1 + 1, i2 + 1)
      } else {
        false
      }
    }

    if (as.length <= 1)
      return true

    _isSorted(as, 0, 1)
  }

  def main(as: Array[String]): Unit =
  {

//    println(isSorted[Int](Array(), isAscending))
//    println(isSorted[Int](Array(1), isAscending))
    println(isSorted[Int](Array(1,2), isAscending))
    println(isSorted[Int](Array(2,1), isAscending))
    println(isSorted[Int](Array(1,2,4), isAscending))
  }
}
