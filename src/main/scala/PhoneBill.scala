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
    println("The total cost is: " + solution(log))
  }

  def solution(s: String): Int = {
    val logsMap = splitLogsToMapOfIntInt(s)
    val numberToExclude = findMaxMinTimeNumValue(logsMap)
    logsMap.map(x => total(x, numberToExclude)).sum //Totalling the costs
  }

  def splitLogsToMapOfIntInt(s: String): Map[Int, Int] = {
    val logsArray = s.split("\n").map(_.trim)
    val logsMap = stringToTuples(logsArray).groupBy(_._1).mapValues(_.map(_._2))
    logsMap.map(x => numberToInt(x._1) -> x._2.sum)
  }
  def findMaxMinTimeNumValue(logs: Map[Int, Int]): (Int, Int) = {
    maxMins(logs.toList).toMap.minBy(_._1)
  }
  def total(log: (Int, Int), max: (Int, Int)): Int = {
    if(max == log) return 0

    val secs = log._2
    if(secs < 300) { 3 * secs }
    else if(secs % 60 == 0) { (secs / 60) * 150 }
    else { ((secs / 60) + 1) * 150 }
  }
  //Turn data into a more manageable form
  def stringToTuples(logs: Array[String]): Seq[(String, Int)] = {
    for(log <- logs) yield {
      val left = log.split(",")(1).trim
      val right = durToSecs(log.split(",")(0).trim)
      (left, right)
    }
  }
  //Turn the phone number into a string for comparison purposes
  def numberToInt(noString: String): Int = {
    noString.replaceAll("[\\s\\-]", "").toInt
  }
  //Convert the full time string given into seconds as integers
  def durToSecs(tString: String): Int = {
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
}
