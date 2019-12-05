package com.ztbarry.finitedifferences

class TrapezoidalRule(val fun: Double => Double, val a: Double, val b: Double)
  extends Difference1D {

  def meshIntegral(n: Int): Either[String, Double] = {

    for {
      meshValues <- getMeshValues(n)
    } yield {
      meshValues
        .sliding(2)
        .map(_.sum / 2)
        .sum
    }

  }
}
