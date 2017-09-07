package springboard.fpscala.ds

import scala.annotation.tailrec

sealed trait List[+T]

case object Nil extends List[Nothing]
case class Cons[+A](h: A, t: List[A]) extends List[A]

object List
{
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x,xs) => x + sum(xs)
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] = {
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  }

  def tail[A](ls: List[A]): List[A] = ls match {
    case Nil => Nil
    case Cons(_, Nil) => Nil
    case Cons(_, xs) => xs
  }

  def setHead[A](h: A, ls: List[A]): List[A] = ls match {
    case Nil => Nil
    case Cons(_, xs) => Cons(h, xs)
  }

  def drop[A](c: Int, ls: List[A]): List[A] = {
    @tailrec
    def _drop(_c: Int, _ls: List[A]): List[A] = _c match {
      case _ if c == _c => _ls
      case _ => _drop(_c + 1, tail(_ls))
    }

    _drop(0, ls)
  }


  def dropWhile[A](ls: List[A], p: A => Boolean): List[A] = {
    @tailrec
    def _drop(_ls: List[A]): List[A] = _ls match {
      case Nil => Nil
      case Cons(h, t) if p(h) => _drop(t)
      case _ => _ls
    }

    _drop(ls)
  }


  def init[A](ls: List[A]): List[A] = {
    @tailrec
    def _init(_ls: List[A]): List[A] = _ls match {
      case Nil => Nil
      case Cons(h, Cons(t, Nil)) if
    }

    _init
  }


  def main(args: Array[String]): Unit = {
    val one = Cons(5, Nil)
    val two = Cons(10, one)

//    println(tail(empty))
//    println(tail(one))
//    println(tail(two))
//
//    println(setHead(20, Nil))
//    println(setHead(20, one))
//    println(setHead(20, two))

    println(drop(1, two))
    println(drop(2, two))

    println(dropWhile(two, (i: Int) => i > 5))
  }
}