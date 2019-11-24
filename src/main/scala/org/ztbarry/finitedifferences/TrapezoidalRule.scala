package org.ztbarry.finitedifferences

class TrapezoidalRule(val fun: Double => Double, val a: Double, val b: Double)
  extends Difference1D with Mesh1D {

  require(a < b)

  def approx(n: Int): Option[Double] = {

    if (n <= 0) {
      None
    } else {
      val funOnMesh = getMeshValues(mesh(n))
      val approx = funOnMesh
        .sliding(2)
        .map(_.sum / 2)
        .sum
      Some(approx)
    }

  }
}
