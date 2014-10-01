package org.uqbar.cacao

import org.uqbar.math.vectors._

//trait RenderSetting

trait Renderer {
	var color: Color = _
	var font: Font = _

	def cropped(from: Vector = (0, 0))(size: Vector): Renderer
	def scaled(ratio: Vector): Renderer
	def translated(translation: Vector): Renderer

	def draw(image: Image)(position: Vector = Origin)
	def draw(string: String)(position: Vector)
	def draw(shapes: Shape*)
	def fill(shapes: Shape*)
	def clear(from: Vector, size: Vector)

	def beforeRendering
	def afterRendering
}