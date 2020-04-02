import scala.io.Source

object S1_HelloScala {
  def main(args: Array[String]) {
    helloBottles()
    helloBottles2()
    testCollection()
    testTuple()
  }

  def helloBottles(): Unit = {
    var n : Int = 2
    while ( n <= 6 ) {
      println(s"Hello ${n} bottles of bear")
      n += 1
    }
  }

  def helloBottles2(): Unit = {
    println(2 to 6)
    2 to 6 foreach { n => println(s"Hello ${n} bottles of bear")}
  }

  def testCollection(): Unit = {
    val authorsToAge = Map("Raoul" -> 23, "Mario" -> 40, "Alan" -> 53)
    println(authorsToAge)

    val authors = List("Raoul", "Mario", "Alan")
    println(authors)
    val numbers = Set(1, 1, 2, 3, 5, 8)
    println(numbers)

    val newNumbers = numbers + 9
    println(newNumbers)
    println(numbers)

    val fileLines = Source.fromFile("/etc/hosts").getLines().toList
    val linesLongUpper = fileLines.filter(l => l.length() > 30)
      .map(l => l.toUpperCase)
    println(linesLongUpper)

    val linesLongUpper2 = fileLines filter (_.length() > 30) map(_.toUpperCase)
    println(linesLongUpper2)

    val linesLongUpper3 = fileLines.par filter (_.length > 30) map(_.toUpperCase)
    println(linesLongUpper3)
  }

  def testTuple(): Unit = {
    val raoul = ("Raoul", "1234")
    println(raoul)
    val book = (2018, "Modern Java In Action", "Manning")
    println(book)
    println(book._2)
  }
}
