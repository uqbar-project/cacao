package org.uqbar.cacao.dimensions

import org.uqbar.math.vectors.Vector
import Double.NaN
import Math.min
import Math.max
import java.security.InvalidParameterException

trait Scalable[T <: Scalable[T]] { self: T =>

	def size: Vector

	//*********************************************************************************************
	// MUTABLE
	//*********************************************************************************************

	def scaleBy(x: Double = 1, y: Double = 1)

	def scaleBy(scaleRatios: Vector): Unit = scaleBy(scaleRatios.x, scaleRatios.y)
	def scaleBy(ratio: Double): Unit = scaleBy(ratio, ratio)

	def scaleTo(targetSize: Vector): Unit = scaleTo(targetSize.x, targetSize.y)
	def scaleTo(width: Double = NaN, height: Double = NaN): Unit =
		if (width.isNaN && height.isNaN) throw new InvalidParameterException
		else if (width.isNaN) scaleBy(height / size.y)
		else if (height.isNaN) scaleBy(width / size.x)
		else scaleBy(width / size.x, height / size.y)

	def scaleHorizontallyTo(width: Double): Unit = scaleBy(width / size.x)
	def scaleVerticallyTo(height: Double): Unit = scaleBy(height / size.y)

	def scaleToFit(size: Vector): Unit = scaleToFit(size.x, size.y)
	def scaleToFit(width: Double, height: Double): Unit = scaleBy(min(width / size.x, height / size.y))

	def scaleToCover(size: Vector): Unit = scaleToCover(size.x, size.y)
	def scaleToCover(width: Double, height: Double): Unit = scaleBy(max(width / size.x, height / size.y))

	//*********************************************************************************************
	// IMMUTABLE
	//*********************************************************************************************

	// If x == 0 || y == 0 should raise error
	def scaledBy(x: Double = 1, y: Double = 1): T

	def scaledBy(scaleRatios: Vector): T = scaledBy(scaleRatios.x, scaleRatios.y)
	def scaledBy(ratio: Double): T = scaledBy(ratio, ratio)

	def scaledTo(targetSize: Vector): T = scaledTo(targetSize.x, targetSize.y)
	def scaledTo(width: Double = NaN, height: Double = NaN): T =
		if (width.isNaN && height.isNaN) throw new InvalidParameterException
		else if (width.isNaN) scaledBy(height / size.y)
		else if (height.isNaN) scaledBy(width / size.x)
		else scaledBy(width / size.x, height / size.y)

	def scaledHorizontallyTo(width: Double): T = scaledBy(x = width / size.x)
	def scaledVerticallyTo(height: Double): T = scaledBy(y = height / size.y)

	def scaledToFit(size: Vector): T = scaledToFit(size.x, size.y)
	def scaledToFit(width: Double, height: Double): T = if (width == 0 || height == 0)
		throw new InvalidParameterException
	else scaledBy(min(width / size.x, height / size.y))

	def scaledToCover(size: Vector): T = scaledToCover(size.x, size.y)
	def scaledToCover(width: Double, height: Double): T = if (width == 0 || height == 0)
		throw new InvalidParameterException
	else scaledBy(max(width / size.x, height / size.y))
}