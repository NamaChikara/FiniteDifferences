package com.ztbarry.finitedifferences

class SimpsonsRule(val fun: Double => Double, val a: Double, val b: Double)
  extends Difference1D {

  def numericalMethod(meshValues: Vector[Double]): Double = {
    // need to add a check to make sure the number of partitions is greater than 1 (require n > 1).
    val simpsonsSegments = meshValues
      .sliding(3, 2)
      .map(x => (x(0) + 4 * x(1) + x(2)) / 3)
    val simpsonsWiths: Double = (b - a) / (meshValues.length - 1)

    simpsonsSegments.sum * simpsonsWiths
  }

}
