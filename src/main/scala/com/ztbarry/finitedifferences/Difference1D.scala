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
      nApprox  <- approxIntegral(n * 2)
      n2Approx <- approxIntegral(n * 2)
      n4Approx <- approxIntegral(n * 4)
    } yield {
      val numeratorDiff = nApprox - n2Approx
      val denominatorDiff = n2Approx - n4Approx
      if (numeratorDiff == 0) {
        Left(f"Grid approximation of interval identical for n = $n and n = ${n * 2}.")
      } else if (denominatorDiff == 0) {
        Left(f"Grid approximation of interval identical for n = ${n * 2} and n = ${n * 4}.")
      } else {
        val ratio = numeratorDiff / denominatorDiff
        if (ratio < 0) {
          Left(f"Ratio of errors is less than 0.")
        } else {
          Right(log2(ratio))
        }
      }
    }
  }
}