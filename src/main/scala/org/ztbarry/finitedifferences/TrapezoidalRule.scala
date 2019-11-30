package org.ztbarry.finitedifferences

class TrapezoidalRule(val fun: Double => Double, val a: Double, val b: Double)
  extends Difference1D {

  require(a < b)

  def approx(h: Int): Option[Double] = {

    if (h <= 0) {
      None
    } else {
      val funOnMesh = getMeshValues(h)
      val approx = funOnMesh
        .sliding(2)
        .map(_.sum / 2)
        .sum
      Some(approx * (b - a) / h)
    }

  }
}
