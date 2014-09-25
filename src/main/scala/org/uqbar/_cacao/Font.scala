package org.uqbar._cacao

import java.lang.Math._
import org.uqbar.math.vectors._

trait FontDef extends RenderSettingDef {
	def Font: FontCompanion

	trait FontCompanion {
		def apply(name: Symbol, size: Int, modifiers: FontModifier*): Font
	}

	trait Font extends RenderSetting {
		def sizeOf(target: Char): Vector
		def sizeOf(target: String): Vector = target.foldLeft(Origin){ (acum, char) =>
			val size = sizeOf(char)
			(acum.x + size.x, max(acum.y, size.y))
		}
	}

	trait FontModifier
	object FontModifier {
		object Bold extends FontModifier
		object Italic extends FontModifier
	}
}