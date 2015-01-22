package org.uqbar.cacao.dimensions.immutable

import java.lang.Math.max
import java.lang.Math.min

import scala.Double.NaN

import org.uqbar.math.spaces.R2._

trait Scalable[T <: Scalable[T]] { self: T =>

	def size: Vector

	def scaledBy(x: Double = 1, y: Double = 1): T

	def scaledBy(scaleRatios: Vector): T = scaledBy(scaleRatios(X), scaleRatios(Y))
	def scaledBy(ratio: Double): T = scaledBy(ratio, ratio)

	def scaledTo(targetSize: Vector): T = scaledTo(targetSize(X), targetSize(Y))
	def scaledTo(width: Double = NaN, height: Double = NaN): T = {
		require(!width.isNaN || !height.isNaN, "At least one argument must be supplied.")
		require(size(X) != 0 || width == 0)
		require(size(Y) != 0 || height == 0)

		if (size(X) == 0 || size(Y) == 0) scaledBy(0)
		else if (width.isNaN) scaledBy(height / size(Y))
		else if (height.isNaN) scaledBy(width / size(X))
		else scaledBy(width / size(X), height / size(Y))
	}

	def scaledHorizontallyTo(width: Double): T = {
		require(size(X) != 0 || width == 0)

		if (size(X) == 0) scaledBy(x = 0) else scaledBy(x = width / size(X))
	}
	def scaledVerticallyTo(height: Double): T = {
		require(size(Y) != 0 || height == 0)

		if (size(Y) == 0) scaledBy(y = 0) else scaledBy(y = height / size(Y))
	}

	def scaledToFit(size: Vector): T = scaledToFit(size(X), size(Y))
	def scaledToFit(width: Double, height: Double): T = {
		require(size(X) != 0 || width == 0)
		require(size(Y) != 0 || height == 0)
		if (size(X) * size(Y) == 0) scaledBy(0) else scaledBy(min(width / size(X), height / size(Y)))
	}

	def scaledToCover(size: Vector): T = scaledToCover(size(X), size(Y))
	def scaledToCover(width: Double, height: Double): T = {
		require(size(X) != 0 || width == 0)
		require(size(Y) != 0 || height == 0)

		if (size(X) * size(Y) == 0 || width * height == 0) scaledBy(0) else scaledBy(max(width / size(X), height / size(Y)))
	}
}