package org.uqbar._cacao

import org.uqbar.math.vectors._

trait RendererDef extends ShapeDef with ColorDef with ImageDef with FontDef with RenderSettingDef {

	def Renderer: RendererCompanion

	trait RendererCompanion {
		def apply: Renderer
	}

	trait Renderer {
		def cropped(from: Vector = (0, 0))(size: Vector): Renderer
		def scaled(ratio: Vector): Renderer
		def translated(translation: Vector): Renderer

		def draw(image: Image)(position: Vector = Origin): Renderer
		def draw(string: String)(position: Vector): Renderer
		def draw(shape: Shape): Renderer
		def fill(shape: Shape): Renderer
		def clear(from: Vector, size: Vector): Renderer

		def set(setting: RenderSetting): Renderer

		def apply(task: Renderer => Unit): Unit = apply()(task)
		def apply(settings: RenderSetting*)(task: Renderer => Unit): Unit = task((this /: settings){ (r, s) => r set s })
	}
}