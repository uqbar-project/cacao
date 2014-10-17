package org.uqbar.cacao.dimensions.immutable

import java.lang.Math.max
import java.lang.Math.min

import scala.Double.NaN

import org.uqbar.math.vectors.Vector

trait Scalable[T <: Scalable[T]] { self: T =>

	def size: Vector

	def scaledBy(x: Double = 1, y: Double = 1): T

	def scaledBy(scaleRatios: Vector): T = scaledBy(scaleRatios.x, scaleRatios.y)
	def scaledBy(ratio: Double): T = scaledBy(ratio, ratio)

	def scaledTo(targetSize: Vector): T = scaledTo(targetSize.x, targetSize.y)
	def scaledTo(width: Double = NaN, height: Double = NaN): T = {
		require(!width.isNaN || !height.isNaN, "At least one argument must be supplied.")
		require(size.x != 0 || width == 0)
		require(size.y != 0 || height == 0)

		if (size.x == 0 || size.y == 0) scaledBy(0)
		else if (width.isNaN) scaledBy(height / size.y)
		else if (height.isNaN) scaledBy(width / size.x)
		else scaledBy(width / size.x, height / size.y)
	}

	def scaledHorizontallyTo(width: Double): T = {
		require(size.x != 0 || width == 0)

		if (size.x == 0) scaledBy(x = 0) else scaledBy(x = width / size.x)
	}
	def scaledVerticallyTo(height: Double): T = {
		require(size.y != 0 || height == 0)

		if (size.y == 0) scaledBy(y = 0) else scaledBy(y = height / size.y)
	}

	def scaledToFit(size: Vector): T = scaledToFit(size.x, size.y)
	def scaledToFit(width: Double, height: Double): T = {
		require(size.x != 0 || width == 0)
		require(size.y != 0 || height == 0)
		if (size.x * size.y == 0) scaledBy(0) else scaledBy(min(width / size.x, height / size.y))
	}

	def scaledToCover(size: Vector): T = scaledToCover(size.x, size.y)
	def scaledToCover(width: Double, height: Double): T = {
		require(size.x != 0 || width == 0)
		require(size.y != 0 || height == 0)

		if (size.x * size.y == 0 || width * height == 0) scaledBy(0) else scaledBy(max(width / size.x, height / size.y))
	}
}