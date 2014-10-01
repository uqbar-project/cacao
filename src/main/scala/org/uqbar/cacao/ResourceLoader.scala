package org.uqbar.cacao

trait ResourceLoader {
	def font(name: Symbol, size: Int, modifiers: FontModifier*): Font
	def image(fileName: String): Image
}