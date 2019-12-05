package com.ztbarry.finitedifferences

import org.scalatest.FunSuite

class Mesh1DTest extends FunSuite {

  test("access val a") {
    val myMesh = new { val a = 1.0; val b = 2.0 } with Mesh1D {}
    assert(myMesh.a == 1.0)
  }

  test("access val b") {
    val myMesh = new { val a = 1.0; val b = 2.0 } with Mesh1D {}
    assert(myMesh.b == 2.0)
  }

  test("create mesh with valid input n") {
    val myMesh = new { val a = 1.0; val b = 2.0 } with Mesh1D {}
    assert(myMesh.mesh(4) == Right(Vector[Double](1.00, 1.25, 1.50, 1.75, 2.00)))
  }

  test("create mesh with invalid input n") {
    val myMesh = new { val a = 1.0; val b = 2.0 } with Mesh1D {}
    assert(myMesh.mesh(0) == Left("n must not be equal to 0"))
  }

}
