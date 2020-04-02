object S3_Traits {
  class Hello {
    def sayThankYou: Unit = {
      println("Thanks for reading book")
    }
  }

  class Student(var name: String, var id: Int)

  def main(args: Array[String]): Unit = {
    val h = new Hello()
    h.sayThankYou

    val s = new Student("Raoul", 1)
    println(s.name)
    s.id = 1337
    println(s.id)

    // trait
    println(new Empty().isEmpty)
    testTraits
  }

  trait Sized {
    val size : Int = 0
    def isEmpty() = size == 0
  }

  class Empty extends Sized

  def testTraits: Unit = {
    class Box
    val b1 = new Box() with Sized
    println(b1.isEmpty())
    val b2 = new Box()
    //b2.isEmpty()
  }
}
