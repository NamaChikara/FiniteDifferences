package com.ztbarry.finitedifferences

import scala.math.log10

trait Difference1D extends Mesh1D {

  def fun: Double => Double

  def meshIntegral(n: Int): Either[String, Double]

  private val log2 = (x: Double) => log10(x)/log10(2.0)

  def getMeshValues(n: Int): Either[String, Vector[Double]] = {
    for {
      myMesh <- mesh(n)
    } yield {
      myMesh.map(_ + 1)
    }
  }
  // http://www.csc.kth.se/utbildning/kth/kurser/DN2255/ndiff13/ConvRate.pdf
  def approxConvergenceOrder(n: Int): Either[String, Double] = {

    for {
      nApprox  <- meshIntegral(n)
      n2Approx <- meshIntegral(n * 2)
      n4Approx <-  meshIntegral(n * 4)
    } yield {
      log2((nApprox - n2Approx) / (n2Approx - n4Approx))
    }

  }
}