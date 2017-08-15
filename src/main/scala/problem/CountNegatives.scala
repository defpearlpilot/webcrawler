package problem


object CountNegatives
{
  def main(args: Array[String]) {
    println("Hello")

    val matrix = Array(
      Array(-5, -4, -2, 0, 3, 8),
      Array(-3, -1, 0, 1, 5, 10),
      Array(-2, 0, 4, 7, 10, 11),
      Array(1, 2, 5, 12, 15, 17)
    )

    val arr = matrix(0)
    binarySearch(arr, 50, 0, arr.length -1)
  }


  def countNegatives(array: Array[Array[Int]]): Int = {
    0
  }


  def binarySearch(array: Array[Int], toFind: Int, _start: Int, _end: Int): Int = {
    var start = _start
    var end = _end
    var mid = (end - start) / 2

    var num = array(mid)
    while (num != toFind || mid == start || mid == end)
    {
      val range = nextBounds(toFind, num, start, mid, end)

      start = range._1
      end = range._2
      mid = start + (end - start) / 2
      num = array(mid)
    }

    mid
  }

  def nextBounds(toFind: Int, found: Int, start: Int, mid: Int, end: Int): (Int, Int) = {
    if (found < toFind) { (mid, end) } else { (start, mid) }
  }
}
