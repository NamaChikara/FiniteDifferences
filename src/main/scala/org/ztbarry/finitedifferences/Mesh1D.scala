package org.ztbarry.finitedifferences

trait Mesh1D {

  val a: Double
  val b: Double

  def mesh(n: Int): Vector[Double] = {

    for (i <- Vector.range(0, n + 1)) yield {
      a + i * (b - a) / n
    }

  }

}