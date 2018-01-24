package main.scala.PhoneBill

object PhoneBill {
  def main(args: Array[String]): Unit = {
    //Test data (as given)
    val log = """00:01:07,406-234-090
                00:05:01,701-080-080
                00:05:00,406-234-090"""
    //test data (modified to test for multi maximums)
    val log2 = """00:01:07,406-234-090
                00:05:01,701-080-080
                00:05:00,406-234-090
                00:01:07,407-234-090
                00:05:01,709-080-080
                00:05:00,407-234-090"""
    println("The total cost is: " + solution(log2))
  }

  def solution(s: String): Int = {
    val logs: Array[String] = s.split("\n").map(_.trim) //Split into array of individual logs
    val logsMap = stringToTuples(logs).groupBy(_._1).mapValues(_.map(_._2)) //Convering our logs into a map of tuples
    val logsInSecs: Map[Int, Int] = logsMap.map(x => (numberToInt(x._1) -> x._2.sum)) //Converting all of our phone nos to ints and summing totals of seq(durations in seconds)
    val maxTimes: Map[Int, Int] = maxMins(logsInSecs.toList).toMap //Finding all of the maxtimes (maxBy returns only 1)
    val maxTime = maxTimes.minBy(_._1) //Finding the the max duration with the number with he lowest numerical value
    logsInSecs.map(x => costMap(x, maxTime)).sum //Totalling the costs
  }
  //Turn data into a more manageable form
  def stringToTuples(logs: Array[String]): Seq[(String, Int)] = {
    for(log <- logs) yield {
      val left = log.split(",")(1).trim
      val right = toSecs(log.split(",")(0).trim)
      (left, right)
    }
  }
  //Turn the phone number into a string for compatison purposes
  def numberToInt(noString: String): Int = {
    noString.replaceAll("[\\s\\-]", "").toInt
  }
  //Convert the full time string given into seconds as integers
  def toSecs(tString: String): Int = {
    val timeVal: Array[Int] = tString.split(":").map(_.toInt)
    val secs = (timeVal(0) * 60 * 60) + (timeVal(1) * 60) + timeVal(2)
    secs
  }
  //Return the maximum minutes (given maxBy only returns one!)
  @annotation.tailrec
  def maxMins(logss: List[(Int, Int)], count: Int = 0): List[(Int, Int)] = {
    val logs = logss.sortBy(_._2)
    if(logss.size == (count)) logs
    else if(logs.head._2 < logs.last._2) maxMins(logs.drop(1), count + 1)
    else return logs
  }
  //Calculate the cost of the log given
  def costMap(log: (Int, Int), max: (Int, Int)): Int = {
      if(max == log) return 0
      else {
        val secs = log._2
        if(secs < 5 * 60) 3 * secs
        else if(secs == 5 * 60) (secs / 60) * 150
        else ((secs / 60) + 1) * 150
      }
  }
}
