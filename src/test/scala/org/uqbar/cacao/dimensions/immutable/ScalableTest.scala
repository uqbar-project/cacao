package org.uqbar.cacao.dimensions.immutable

import org.scalatest.BeforeAndAfter
import org.scalatest.FreeSpec
import org.scalatest.Matchers
import org.scalatest.matchers.MatchResult
import org.scalatest.matchers.Matcher
import org.uqbar.math.spaces.R2._

class ScalableTest extends FreeSpec with Matchers with BeforeAndAfter {

	val s = S(12, 10)
	val z = S(0, 0)

	"A Scalable" - {

		"scaled by" - {
			"a single ratio" - {
				"in (1, ∞] should be proportionally larger" in { s scaledBy 2 should haveSize (s.size * 2) }
				"= 1 should be the same size" in { s scaledBy 1 should haveSameSizeAs (s) }
				"in (0, 1) should be proportionally smaller" in { s scaledBy 0.5 should haveSize (s.size / 2) }
				"= 0 should have size zero" in { s scaledBy 0 should haveSize (Origin) }
				"in (-1, 0) should be proportionally smaller and flipped" in { s scaledBy -0.5 should haveSize (-s.size / 2) }
				"= -1 should be flipped" in { s scaledBy -1 should haveSize (-s.size) }
				"in [-∞,-1) should be proportionally larger and flipped" in { s scaledBy -1 should haveSize (-s.size) }
			}

			"a non-zero single ratio should be able to get scaled back to it's original size" in {
				s scaledBy 2 scaledBy 0.5 should haveSameSizeAs (s)
				s scaledBy 0.5 scaledBy 2 should haveSameSizeAs (s)
				s scaledBy -1 scaledBy -1 should haveSameSizeAs (s)
			}

			"independent x and y ratios" - {
				val ratios = List(Double.MinValue, -2, -1, -0.5, -Double.MinPositiveValue, 0, Double.MinPositiveValue, 0.5, 1, 2, Double.MaxValue)

				"should have expected size" in {
					for (n <- ratios) {
						s scaledBy (n, n) should haveSameSizeAs (s scaledBy n)
						s scaledBy (x = n) should haveSameSizeAs (s scaledBy (n, 1))
						s scaledBy (y = n) should haveSameSizeAs (s scaledBy (1, n))
						s scaledBy (x = n) scaledBy (y = n) should haveSameSizeAs (s scaledBy (n, n))
						s scaledBy (x = n) scaledBy (y = n) should haveSameSizeAs (s scaledBy n)
					}
				}

				"should maintain it's size if it was zero" in {
					for (n <- ratios) {
						z scaledBy (n, n) should haveSize (Origin)
						z scaledBy (x = n) should haveSize (Origin)
						z scaledBy (y = n) should haveSize (Origin)
					}
				}
			}
		}

		"scaled to" - {

			"some size should be of that size" in {
				for (size @ (width, height) <- Seq((24, 5), (24, 20), (1, 0), (0, 1), (0, 0))) {
					s scaledTo (width, height) should haveSize (size)
					s scaledTo size should haveSize (size)
				}
			}

			"some width should be proportionally scaled to have that width" in {
				s scaledTo (width = 24) should haveSize (24, 20)
			}

			"some height should be proportionally scaled to have that height" in {
				s scaledTo (height = 20) should haveSize (24, 20)
			}

			"size zero should have that size even if it's original size was zero" in {
				z scaledTo (0, 0) should haveSize (Origin)
				z scaledTo Origin should haveSize (Origin)
			}

			"a non-zero size should fail if it's original size was zero" in {
				an[IllegalArgumentException] should be thrownBy s.scaledTo()
				an[IllegalArgumentException] should be thrownBy z.scaledTo(1, 1)
			}
		}

		"scaled horizontally to" - {

			"some width should be unproportionally scaled to have that width" in {
				s scaledHorizontallyTo 24 should haveSize (24, 10)
				s scaledHorizontallyTo 0 should haveSize (0, 10)
			}

			"width zero should have size zero even if it's original size was zero" in {
				z scaledHorizontallyTo 0 should haveSize (Origin)
			}

			"a non-zero width should fail if it's original width was zero" in {
				an[IllegalArgumentException] should be thrownBy z.scaledHorizontallyTo(1)
				an[IllegalArgumentException] should be thrownBy s.scaledHorizontallyTo(0).scaledHorizontallyTo(1)
			}
		}

		"scaled vertically to" - {

			"some height should be unproportionally scaled to have that height" in {
				s scaledVerticallyTo 20 should haveSize (12, 20)
				s scaledVerticallyTo 0 should haveSize (12, 0)
			}

			"height zero should have size zero even if it's original size was zero" in {
				z scaledVerticallyTo 0 should haveSize (Origin)
			}

			"a non-zero height should fail if it's original height was zero" in {
				an[IllegalArgumentException] should be thrownBy z.scaledVerticallyTo(1)
				an[IllegalArgumentException] should be thrownBy s.scaledVerticallyTo(0).scaledVerticallyTo(1)
			}
		}

		"scaled to fit" - {

			"some size should be proportionally scaled to the largest possible size whitin it" in {
				for ((size @ (width, height), expected) <- Map((24, 100) -> (24, 20), (120, 20) -> (24, 20), (6, 10) -> (6, 5), (12, 5) -> (6, 5), (12, 0) -> (0, 0), (0, 10) -> (0, 0), (0, 0) -> (0, 0))) {
					s scaledToFit (width, height) should haveSize (expected)
					s scaledToFit size should haveSize (expected)
				}
			}

			"size zero should have size zero even if it's original size was zero" in {
				z scaledToFit (0, 0) should haveSize (Origin)
				z scaledToFit (Origin) should haveSize (Origin)
			}

			"a non-zero size should fail if it's original size was zero" in {
				for (size @ (width, height) <- Seq((0, 1), (1, 0), (1, 1))) {
					an[IllegalArgumentException] should be thrownBy z.scaledToFit(width, height)
					an[IllegalArgumentException] should be thrownBy z.scaledToFit(size)
				}
			}
		}

		"scaled to cover" - {
			"some size should be proportionally scaled to the smallest possible size around it" in {
				for ((size @ (width, height), expected) <- Map((24, 100) -> (120, 100), (120, 20) -> (120, 100), (6, 10) -> (12, 10), (12, 5) -> (12, 10), (6, 5) -> (6, 5), (24, 0) -> (0, 0), (0, 20) -> (0, 0), (0, 0) -> (0, 0))) {
					s scaledToCover (width, height) should haveSize (expected)
					s scaledToCover (size) should haveSize (expected)
				}
			}

			"size zero should have size zero even if it's original size was zero" in {
				z scaledToCover (0, 0) should haveSize (Origin)
				z scaledToCover (Origin) should haveSize (Origin)
			}

			"a non-zero size should fail if it's original size was zero" in {
				for (size @ (width, height) <- Seq((0, 1), (1, 0), (1, 1))) {
					an[IllegalArgumentException] should be thrownBy z.scaledToCover(width, height)
					an[IllegalArgumentException] should be thrownBy z.scaledToCover(size)
				}
			}
		}
	}
}

//▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
// TEST MODEL
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄

case class S(x: Double, y: Double) extends Scalable[S] {
	def size = (x, y)

	def scaledBy(x: Double, y: Double) = copy(x * this.x, y * this.y)
}

//▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
// MATCHERS
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄

case class haveSameSizeAs[T <: Scalable[T]](other: Scalable[T]) extends Matcher[Scalable[T]] {
	def apply(target: Scalable[T]) = MatchResult(
		target.size == other.size,
		s"$target size was ${target.size} but should have been ${other.size}",
		s"$target size was ${other.size} but it shouldn't have been"
	)
}

case class haveSize(size: Vector) extends Matcher[Scalable[_]] {
	def apply(target: Scalable[_]) = MatchResult(
		target.size == size,
		s"$target size was ${target.size} but should have been $size",
		s"$target size was $size but it shouldn't have been"
	)
}