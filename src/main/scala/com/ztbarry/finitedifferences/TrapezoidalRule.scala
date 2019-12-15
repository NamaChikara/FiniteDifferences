package com.ztbarry.finitedifferences

class TrapezoidalRule(val fun: Double => Double, val a: Double, val b: Double)
  extends Difference1D {

  def numericalMethod(meshValues: Vector[Double]): Double = {
    val trapezoidMidpoints = meshValues
      .sliding(2)
      .map(_.sum / 2)
    val trapezoidWiths: Double = (b - a) / (meshValues.length - 1)

    trapezoidMidpoints.sum * trapezoidWiths
  }

}
