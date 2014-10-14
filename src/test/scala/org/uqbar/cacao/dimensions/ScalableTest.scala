package org.uqbar.cacao.dimensions

import org.scalatest.FreeSpec
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfter
import org.uqbar.math.vectors._

class ScalableTest extends FreeSpec with Matchers with BeforeAndAfter {

	var s: S = _

	before {
		s = S(12, 10)
	}

	"A Scalable" - {
		"scaled by" - {
			"a ratio in (1, ∞] should grow proportionally" in { s.scaledBy(2).size should be (s.size * 2) }
			"a ratio = 1 should stay the same" in { s.scaledBy(1).size should be (s.size) }
			"a ratio in (0, 1) should shrink proportionally" in { s.scaledBy(0.5).size should be (s.size / 2) }
			"a ratio = 0 should shrink to size zero" in { s.scaledBy(0).size should be (Origin) }
			"a ratio in (-1, 0) should shrink proportionally and change it's size's sign" in { s.scaledBy(-0.5).size should be (-s.size / 2) }
			"a ratio = -1 should change it's size's sign" in { s.scaledBy(-1).size should be (-s.size) }
			"a ratio in [-∞,-1) should grow proportionally and change it's size's sign" in { s.scaledBy(-1).size should be (-s.size) }
		}

		"scaledBy" in {
			val ratios = List(Double.MinValue, -2, -1, -0.5, -Double.MinPositiveValue, 0, Double.MinPositiveValue, 0.5, 1, 2, Double.MaxValue)

			for (n <- ratios) {
				s.scaledBy(n, n).size should be (s.scaledBy(n).size)
				s.scaledBy(x = n).size should be (s.scaledBy(n, 1).size)
				s.scaledBy(y = n).size should be (s.scaledBy(1, n).size)
				s.scaledBy(x = n).scaledBy(y = n).size should be (s.scaledBy(n, n).size)
				s.scaledBy(x = n).scaledBy(y = n).size should be (s.scaledBy(n).size)

				S(0, 0).scaledBy(n, n).size should be (Origin)
				S(0, 0).scaledBy(x = n).size should be (Origin)
				S(0, 0).scaledBy(y = n).size should be (Origin)
			}
		}

		"scaledTo" in {
			s.scaledTo(24, 5).size should be((24, 5): Vector)
			s.scaledTo((24, 5): Vector).size should be((24, 5): Vector)
			s.scaledTo(24, 20).size should be((24, 20): Vector)
			s.scaledTo((24, 20): Vector).size should be((24, 20): Vector)
			s.scaledTo(1, 0).size should be((1, 0): Vector)
			s.scaledTo((1, 0): Vector).size should be((1, 0): Vector)
			s.scaledTo(0, 1).size should be((0, 1): Vector)
			s.scaledTo((0, 1): Vector).size should be((0, 1): Vector)
			s.scaledTo(0, 0).size should be(Origin)
			s.scaledTo(Origin).size should be(Origin)
			s.scaledTo(width = 24).size should be((24, 20): Vector)
			s.scaledTo(height = 20).size should be((24, 20): Vector)
			an[IllegalArgumentException] should be thrownBy s.scaledTo()

			S(0, 0).scaledTo(0, 0).size should be(Origin)
			S(0, 0).scaledTo(Origin).size should be(Origin)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledTo(1, 1)
		}

		"scaledHorizontallyTo" in {
			s.scaledHorizontallyTo(24).size should be((24, 10): Vector)
			s.scaledHorizontallyTo(0).size should be((0, 10): Vector)
			S(0, 0).scaledHorizontallyTo(0).size should be(Origin)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledHorizontallyTo(1)
		}

		"scaledVerticallyTo" in {
			s.scaledVerticallyTo(20).size should be((12, 20): Vector)
			s.scaledVerticallyTo(0).size should be((12, 0): Vector)
			S(0, 0).scaledVerticallyTo(0).size should be(Origin)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledVerticallyTo(1)
		}

		"scaledToFit" in {
			s.scaledToFit(24, 100).size should be((24, 20): Vector)
			s.scaledToFit((24, 100): Vector).size should be((24, 20): Vector)
			s.scaledToFit(120, 20).size should be((24, 20): Vector)
			s.scaledToFit((120, 20): Vector).size should be((24, 20): Vector)
			s.scaledToFit(6, 10).size should be((6, 5): Vector)
			s.scaledToFit((6, 10): Vector).size should be((6, 5): Vector)
			s.scaledToFit(12, 5).size should be((6, 5): Vector)
			s.scaledToFit((12, 5): Vector).size should be((6, 5): Vector)
			s.scaledToFit(12, 0).size should be(Origin)
			s.scaledToFit((12, 0): Vector).size should be(Origin)
			s.scaledToFit(0, 10).size should be(Origin)
			s.scaledToFit((0, 10): Vector).size should be(Origin)
			s.scaledToFit(0, 0).size should be(Origin)
			s.scaledToFit(Origin).size should be(Origin)
			S(0, 0).scaledToFit(0, 0).size should be(Origin)
			S(0, 0).scaledToFit(Origin).size should be(Origin)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToFit(0, 1)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToFit((0, 1): Vector)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToFit(1, 0)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToFit((1, 0): Vector)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToFit(1, 1)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToFit((1, 1): Vector)
		}

		"scaledToCover" in {
			s.scaledToCover(24, 100).size should be((120, 100): Vector)
			s.scaledToCover((24, 100): Vector).size should be((120, 100): Vector)
			s.scaledToCover(120, 20).size should be((120, 100): Vector)
			s.scaledToCover((120, 20): Vector).size should be((120, 100): Vector)
			s.scaledToCover(6, 10).size should be((12, 10): Vector)
			s.scaledToCover((6, 10): Vector).size should be((12, 10): Vector)
			s.scaledToCover(12, 5).size should be((12, 10): Vector)
			s.scaledToCover((12, 5): Vector).size should be((12, 10): Vector)
			s.scaledToCover(6, 5).size should be((6, 5): Vector)
			s.scaledToCover((6, 5): Vector).size should be((6, 5): Vector)
			s.scaledToCover(24, 0).size should be(Origin)
			s.scaledToCover((24, 0): Vector).size should be(Origin)
			s.scaledToCover(0, 20).size should be(Origin)
			s.scaledToCover((0, 20): Vector).size should be(Origin)
			s.scaledToCover(0, 0).size should be(Origin)
			s.scaledToCover(Origin).size should be(Origin)
			S(0, 0).scaledToCover(0, 0).size should be(Origin)
			S(0, 0).scaledToCover(Origin).size should be(Origin)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToCover(0, 1)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToCover((0, 1): Vector)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToCover(1, 0)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToCover((1, 0): Vector)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToCover(1, 1)
			an[IllegalArgumentException] should be thrownBy S(0, 0).scaledToCover((1, 1): Vector)
		}
	}
}

case class S(var x: Double, var y: Double) extends Scalable[S] {
	def size = (x, y)

	def scaledBy(x: Double, y: Double) = S(x * this.x, y * this.y)

	def scaleBy(x: Double, y: Double) {
		this.x *= x
		this.y *= y
	}
}