import com.twitter.algebird._
import com.twitter.algebird.Operators._

object VoteCount extends App {
  val weights = Range(6,0,-1)
  val lines = io.Source.stdin.getLines

  val tally = monoidTally(lines)

  tally.map(println)

  def monoidTally(lines: Iterator[String]) = Monoid.sum(lines.map { l => Map(l.zip(weights): _*) })

  def varTally(lines: Iterator[String]) = {
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
