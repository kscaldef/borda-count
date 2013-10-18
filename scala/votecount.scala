package votecount


trait VoteCount {
  def tally(lines: Iterator[String]): Map[Char, Int]
  val weights = Range(6,0,-1)
}

trait AlgebirdMonoid extends VoteCount {
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  def tally(lines: Iterator[String]) = Monoid.sum(lines.map { _.zip(weights).toMap })
}

trait ScalazMonoid extends VoteCount {
  import scalaz.std.anyVal._  // Monoid instance for Ints
  import scalaz.std.map._     // Monoid instance for Maps
  import scalaz.std.stream._ // Foldable instance for Streams
  import scalaz.syntax.traverse._  // .foldMap method

  def tally(lines: Iterator[String]) = lines.toStream.foldMap(_.zip(weights).toMap)
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
object RunScalaz extends Runner with ScalazMonoid
object RunVars extends Runner with Vars

object RunBenchmark extends App {

  def source = io.Source.fromFile("../votes20.txt").getLines

  def run(nv: (String, VoteCount)) = nv match { case (n: String, v: VoteCount) =>
    val start = new java.util.Date().getTime
    v.tally(source)
    val end = new java.util.Date().getTime
    println("%s: %s ms".format(n, end - start))
  }

  Seq(
    ("Vars", new Vars { }),
    ("AlgebirdMonoid", new AlgebirdMonoid { })
    // ("ScalazMonoid", new ScalazMonoid { })   -- stack overflow
  ).map(run)

}
