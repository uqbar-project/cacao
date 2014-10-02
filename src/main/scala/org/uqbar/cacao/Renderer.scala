package org.uqbar.cacao

import org.uqbar.math.vectors._

trait Renderer {
	protected var _camera: Camera = Camera()
	def camera = _camera
	def camera_=(camera: Camera) = _camera = camera

	protected var _color: Color = Color.Black
	def color = _color
	def color_=(color: Color) = _color = color

	protected var _font: Font = _
	def font = _font
	def font_=(font: Font) = _font = font

	def draw(image: Image)(position: Vector = Origin)
	def draw(string: String)(position: Vector)
	def draw(shapes: Shape*)
	def fill(shapes: Shape*)
	def clear(from: Vector, size: Vector)

	def beforeRendering
	def afterRendering
}