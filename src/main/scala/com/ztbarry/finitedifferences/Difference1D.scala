package com.ztbarry.finitedifferences

import scala.math.log10

trait Difference1D extends Mesh1D {

  def fun: Double => Double

  def numericalMethod(meshValues: Vector[Double]): Double

  private val log2 = (x: Double) => log10(x)/log10(2.0)

  private def getMeshValues(n: Int): Either[String, Vector[Double]] = {
    for {
      myMesh <- mesh(n)
    } yield {
      myMesh.map(fun)
    }
  }

  def approxIntegral(n: Int): Either[String, Double] = {
    for {
      meshValues <- getMeshValues(n)
    } yield {
      numericalMethod(meshValues)
    }
  }
  // http://www.csc.kth.se/utbildning/kth/kurser/DN2255/ndiff13/ConvRate.pdf
  def approxConvergenceOrder(n: Int): Either[String, Double] = {

    for {
      nApprox  <- approxIntegral(n)
      n2Approx <- approxIntegral(n * 2)
      n4Approx <- approxIntegral(n * 4)
    } yield {
      log2((nApprox - n2Approx) / (n2Approx - n4Approx))
    }

  }
}