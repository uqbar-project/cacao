package org.uqbar.cacao.dimensions.mutable

import java.lang.Math.max
import java.lang.Math.min

import scala.Double.NaN

import org.uqbar.math.vectors.Vector

trait Scalable {

	def size: Vector

	def scaleBy(x: Double = 1, y: Double = 1): Unit

	def scaleBy(scaleRatios: Vector): Unit = scaleBy(scaleRatios.x, scaleRatios.y)
	def scaleBy(ratio: Double): Unit = scaleBy(ratio, ratio)

	def scaleTo(targetSize: Vector): Unit = scaleTo(targetSize.x, targetSize.y)
	def scaleTo(width: Double = NaN, height: Double = NaN): Unit = {
		require(!width.isNaN || !height.isNaN, "At least one argument must be supplied.")
		require(size.x != 0 || width == 0)
		require(size.y != 0 || height == 0)
		if (size.x == 0 || size.y == 0) scaleBy(0)
		else if (width.isNaN) scaleBy(height / size.y)
		else if (height.isNaN) scaleBy(width / size.x)
		else scaleBy(width / size.x, height / size.y)
	}

	def scaleHorizontallyTo(width: Double): Unit = {
		require(size.x != 0 || width == 0)

		if (size.x == 0) scaleBy(x = 0) else scaleBy(x = width / size.x)
	}
	def scaleVerticallyTo(height: Double): Unit = {
		require(size.y != 0 || height == 0)

		if (size.y == 0) scaleBy(y = 0) else scaleBy(y = height / size.y)
	}

	def scaleToFit(size: Vector): Unit = scaleToFit(size.x, size.y)
	def scaleToFit(width: Double, height: Double): Unit = {
		require(size.x != 0 || width == 0)
		require(size.y != 0 || height == 0)

		if (size.x * size.y == 0) scaleBy(0) else scaleBy(min(width / size.x, height / size.y))
	}

	def scaleToCover(size: Vector): Unit = scaleToCover(size.x, size.y)
	def scaleToCover(width: Double, height: Double): Unit = {
		require(size.x != 0 || width == 0)
		require(size.y != 0 || height == 0)

		if (size.x * size.y == 0 || width * height == 0) scaleBy(0) else scaleBy(max(width / size.x, height / size.y))
	}
}