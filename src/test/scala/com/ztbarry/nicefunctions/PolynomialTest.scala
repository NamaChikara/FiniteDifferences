package com.ztbarry.nicefunctions
// https://docs.scala-lang.org/getting-started/intellij-track/testing-scala-in-intellij-with-scalatest.html
//
class PolynomialTest extends org.scalatest.FunSuite {

  test("Constant polynomial evaluation.") {
    val g = new Polynomial(Vector[Double](1.0))
    assert(g.eval(1.0) === 1.0)
  }

  test("Quadratic polynomial evaluation.") {
    val g = new Polynomial(Vector[Double](0.0, 0.0, 2.0))
    assert(g.eval(3.0) == 18.0)
  }

  test("toString formatted correctly.") {
    val g = new Polynomial(Vector[Double](1.0, 2.0))
    assert(g.toString == "1.0x^0 + 2.0x^1")
  }

  test("Return polynomial function definition.") {
    val g = new Polynomial(Vector[Double](1.0, 2.0))
    val fun: Double => Double = g.fun
    assert(fun(2.0) == 5.0)
  }

  test("Evaluate multiple inputs") {
    val g = new Polynomial(Vector[Double](1.0, 0.0, 0.0, 1.0))
    assert(g.eval(Vector[Double](1.0, 2.0, 1.0)) == Vector[Double](2.0, 9.0, 2.0))
  }

  test("Multiply by a constant") {
    val g = new Polynomial(Vector[Double](1.0, 2.0))
    val h = g * 2.0
    assert(h.toString == "2.0x^0 + 4.0x^1")
  }

}
