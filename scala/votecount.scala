package votecount


trait VoteCount {
  def tally(lines: Iterator[String]): Map[Char, Int]
}

trait AlgebirdMonoid extends VoteCount {
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  val weights = Range(6,0,-1)

  def tally(lines: Iterator[String]) = Monoid.sum(lines.map { _.zip(weights).toMap })
}

trait Vars extends VoteCount {
  def tally(lines: Iterator[String]) = {
    var a,b,c,d,e,f = 0

    lines.foreach { l =>
      var w = 6
      l.foreach { v =>
        v match {
          case 'a' => a += w
          case 'b' => b += w
          case 'c' => c += w
          case 'd' => d += w
          case 'e' => e += w
          case 'f' => f += w
          case _ => ()
        }
        w -= 1
      }
    }

    Map('a' -> a, 'b' -> b, 'c' -> c, 'd' -> d, 'e' -> e, 'f' -> f)
  }
}

class Runner extends App {
  self: VoteCount =>

  val lines = io.Source fromFile args.head getLines

  tally(lines).map(println)
}

object RunAlgebird extends Runner with AlgebirdMonoid
object RunVars extends Runner with Vars
