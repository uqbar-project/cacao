package org.uqbar.cacao

import org.uqbar.math.spaces.R2._

trait Image {
	def size: Vector

	def apply(position: Vector): Color

	def writeTo(fileName: String)

	def scaled(ratio: Vector): Image
	def rotated(radians: Double): Image
	def cropped(from: Vector = (0, 0))(size: Vector): Image
	def repeated(repetitions: Vector): Image
}