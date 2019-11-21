package org.ztbarry.nicefunctions

class PolynomialTest extends org.scalatest.FunSuite {

  test("Creation via array") {
    val g = new Polynomial(Vector[Double](1.0))
    assert(g.eval(1.0) === 1.0)
  }

}
