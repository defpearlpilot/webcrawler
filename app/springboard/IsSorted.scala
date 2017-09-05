package springboard

import scala.annotation.tailrec

object IsSorted
{
  def isAscending(first: Int, second: Int): Boolean = first <= second
  def isDescending(first: Int, second: Int): Boolean = first >= second

  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean =
  {
    @tailrec
    def _isSorted(as: Array[A], index: Int): Boolean = {
      if (index >= as.length - 1) {
        true
      } else if (!ordered(as(index), as(index+1))) {
        false
      } else {
        _isSorted(as, index + 1)
      }
    }

    _isSorted(as, 0)
  }

  def isSorted2[A](as: Array[A], ordered: (A, A) => Boolean): Boolean =
  {
    @tailrec
    def _isSorted(as: Array[A], index: Int): Boolean = {
      index match
      {
        case _ if index >= as.length - 1 => true
        case _ if !ordered(as(index), as(index + 1)) => false
        case _ => _isSorted(as, index + 1)
      }
    }

    _isSorted(as, 0)
  }


  def main(as: Array[String]): Unit =
  {

//    println(isSorted[Int](Array(), isAscending))
//    println(isSorted[Int](Array(1), isAscending))
    println(isSorted[Int](Array(1,2), isAscending))
    println(isSorted[Int](Array(2,1), isAscending))
    println(isSorted[Int](Array(1,2,4), isAscending))

    println(isSorted2[Int](Array(1,2,4,5,7,8), isAscending))

  }
}
