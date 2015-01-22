package org.uqbar.cacao.dimensions.mutable

import java.lang.Math.max
import java.lang.Math.min

import scala.Double.NaN

import org.uqbar.math.spaces.R2._

trait Scalable {

	def size: Vector

	def scaleBy(x: Double = 1, y: Double = 1): Unit

	def scaleBy(scaleRatios: Vector): Unit = scaleBy(scaleRatios(X), scaleRatios(Y))
	def scaleBy(ratio: Double): Unit = scaleBy(ratio, ratio)

	def scaleTo(targetSize: Vector): Unit = scaleTo(targetSize(X), targetSize(Y))
	def scaleTo(width: Double = NaN, height: Double = NaN): Unit = {
		require(!width.isNaN || !height.isNaN, "At least one argument must be supplied.")
		require(size(X) != 0 || width == 0)
		require(size(Y) != 0 || height == 0)
		if (size(X) == 0 || size(Y) == 0) scaleBy(0)
		else if (width.isNaN) scaleBy(height / size(Y))
		else if (height.isNaN) scaleBy(width / size(X))
		else scaleBy(width / size(X), height / size(Y))
	}

	def scaleHorizontallyTo(width: Double): Unit = {
		require(size(X) != 0 || width == 0)

		if (size(X) == 0) scaleBy(x = 0) else scaleBy(x = width / size(X))
	}
	def scaleVerticallyTo(height: Double): Unit = {
		require(size(Y) != 0 || height == 0)

		if (size(Y) == 0) scaleBy(y = 0) else scaleBy(y = height / size(Y))
	}

	def scaleToFit(size: Vector): Unit = scaleToFit(size(X), size(Y))
	def scaleToFit(width: Double, height: Double): Unit = {
		require(size(X) != 0 || width == 0)
		require(size(Y) != 0 || height == 0)

		if (size(X) * size(Y) == 0) scaleBy(0) else scaleBy(min(width / size(X), height / size(Y)))
	}

	def scaleToCover(size: Vector): Unit = scaleToCover(size(X), size(Y))
	def scaleToCover(width: Double, height: Double): Unit = {
		require(size(X) != 0 || width == 0)
		require(size(Y) != 0 || height == 0)

		if (size(X) * size(Y) == 0 || width * height == 0) scaleBy(0) else scaleBy(max(width / size(X), height / size(Y)))
	}
}