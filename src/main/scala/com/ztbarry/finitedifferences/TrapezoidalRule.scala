package com.ztbarry.finitedifferences

class TrapezoidalRule(val fun: Double => Double, val a: Double, val b: Double)
  extends Difference1D {

  def numericalMethod(meshValues: Vector[Double]): Double = {
    meshValues
      .sliding(2)
      .map(_.sum / 2)
      .sum
  }

}
