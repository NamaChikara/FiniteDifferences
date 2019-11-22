package org.ztbarry.finitedifferences

class TrapezoidalRule(val fun: Double => Double, val a: Double, val b: Double)
  extends Difference1D with Mesh1D {

  require(a < b)

  def approx(n: Int): Vector[Double] = {

    getMeshValues(mesh(n))

  }
}
