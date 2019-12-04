package com.ztbarry.finitedifferences

class TrapezoidalRule(val fun: Double => Double, val a: Double, val b: Double)
  extends Difference1D {

  def meshIntegral(n: Int): Option[Double] = {

    if (n <= 0) {
      None
    } else {
      val funOnMesh = getMeshValues(n)
      val approx = funOnMesh
        .sliding(2)
        .map(_.sum / 2)
        .sum
      Some(approx * (b - a) / n)
    }

  }
}
