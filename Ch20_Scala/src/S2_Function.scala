object S2_Function {
  def isJavaMentioned(tweet: String) : Boolean = tweet.contains("Java")
  def isShortTweet(tweet: String) : Boolean = tweet.length < 20

  def main(args: Array[String]): Unit = {
    val tweets = List(
      "I love the new features in Java 8",
      "How's it going?",
      "An SQL query walks into a bar, see tow tables and say 'Can I join you?'"
    )

    firstClassFunction(tweets)
    anonymousFunction(tweets)
    testClosure
    testCurrying
  }

  def firstClassFunction(tweets: List[String]) = {
    tweets.filter(isJavaMentioned).foreach(println)
    tweets.filter(isShortTweet).foreach(println)
  }

  def anonymousFunction(tweets: List[String]): Unit = {
    val isLongTweet : String => Boolean =
      (tweet: String) => tweet.length > 60
    tweets.filter(isLongTweet).foreach(println)

    println(isLongTweet.apply("1234567890123456789012345678901234567890123456789012345678901234567890"))
    println(isLongTweet("asdf"))
  }

  def testClosure: Unit = {
    var count = 0
    val inc = () => count += 1
    inc()
    println(count)
    inc()
    println(count)
  }

  def multiply(x:Int, y:Int) = x * y
  def multiplyCurry(x: Int)(y: Int) = x * y

  def testCurrying: Unit = {
    val r = multiply(2, 10)
    println(r)

    val rCurry = multiplyCurry(2)(10)
    println(rCurry)

    val multiplyByTwo : Int => Int = multiplyCurry(2)
    val r2 = multiplyByTwo(10)
    println(r2)
  }
}
