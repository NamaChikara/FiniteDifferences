package org.ztbarry.finitedifferences

import scala.math.log10

trait Difference1D extends Mesh1D {

  def fun: Double => Double

  def meshIntegral(n: Int): Option[Double]

  private val log2 = (x: Double) => log10(x)/log10(2.0)

  def getMeshValues(n: Int): Vector[Double] = {
    mesh(n).map(fun)
  }

  def approxConvergenceOrder(n: Int): Option[Double] = {

    val nApprox = meshIntegral(n)
    val n2Approx = meshIntegral(n * 2)
    val n4Approx = meshIntegral(n * 4)

    if (nApprox.isEmpty | n2Approx.isEmpty | n4Approx.isEmpty) {
      None
    } else {
      Some(log2((nApprox.get - n2Approx.get) / (n2Approx.get - n4Approx.get)))
    }

  }
}