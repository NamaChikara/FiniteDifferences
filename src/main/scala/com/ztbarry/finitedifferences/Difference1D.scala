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

  private def getDifferenceRatio(num: Double, den: Double, n: Int): Either[String, Double] = {
    if (num == 0) {
      Left(f"Grid approximation of integral identical for n = $n and n = ${n * 2}.")
    } else if (den == 0) {
      Left(f"Grid approximation of integral identical for n = ${n * 2} and n = ${n * 4}.")
    } else {
      val ratio = num / den
      if (ratio < 0) {
        Left(f"Ratio of errors is less than 0.")
      } else {
        Right(log2(ratio))
      }
    }
  }
  // http://www.csc.kth.se/utbildning/kth/kurser/DN2255/ndiff13/ConvRate.pdf
  def approxConvergenceOrder(n: Int): Either[String, Double] = {
    val differences = for {
      nApprox  <- approxIntegral(n)
      n2Approx <- approxIntegral(n * 2)
      n4Approx <- approxIntegral(n * 4)
    } yield {
      Vector(nApprox - n2Approx, n2Approx - n4Approx)
    }

    differences.fold(
      l => Left(l),
      r => getDifferenceRatio(r(0), r(1), n)
    )
  }

  def findConvergenceOrder(nStart: Int = 10, cutoff: Double = 0.01, maxIterations: Int = 10): Either[String, Double] = {
    if (maxIterations < 1) {
      Left("maxIterations must be at least 1.")
    } else {
      val percentChange = for {
        nApprox  <- approxConvergenceOrder(nStart)
        n2Approx <- approxConvergenceOrder(nStart * 2)
      } yield {
        (nApprox - n2Approx) / nApprox
      }

      percentChange.fold(
        l => Left(l),
        r => {
          if (r < cutoff) {
            approxConvergenceOrder(nStart * 2)
          } else {
            findConvergenceOrder(nStart * 2, maxIterations = maxIterations - 1)
          }
        }
      )
    }
  }
}
