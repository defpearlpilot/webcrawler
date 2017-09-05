package springboard

object Partial
{

  def partial1[A, B, C](a: A, f: (A, B) => C): B => C = {
    (b: B) => {
      f(a, b)
    }
  }

  def curry[A, B, C](f: (A, B) => C): A => (B => C) = {
    (a: A) => {
      partial1(a, f)
    }
  }

  def uncurry[A, B, C](f: A => B => C): (A, B) => C = {
    (a: A, b: B) => {
      f(a)(b)
    }
  }

  def nested(a: Int): Int => Int = {
    (b: Int) => {
      a + b
    }
  }

  def compose[A, B, C](f: B => C, g: A => B): A => C = {
    (a: A) => {
      f(g(a))
    }
  }

  def main(args: Array[String]): Unit = {

    val plus5 = partial1[Int, Int, Int](5, (s: Int, o: Int) => s + o)

    println(plus5(5))
    println(plus5(10))

    val curried = curry[Int, Int, Int]((s: Int, o: Int) => s * o)
    val mul3 = curried(3)

    println(mul3(5))

    val add = uncurry[Int, Int, Int](nested)
    println(add(1, 2))

    val plus2Doubled = compose[Int, Int, Int]((b: Int) => b * 2, (a: Int) => a + 2)
    println(plus2Doubled(3))
  }
}
