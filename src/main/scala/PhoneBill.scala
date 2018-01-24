import scala.collection.immutable

object PhoneBill {
  def main(args: Array[String]): Unit = {
    val log = """00:01:07,406-234-090
                00:05:01,701-080-080
                00:05:00,406-234-090"""
    val log2 = """00:01:07,406-234-090
                00:05:01,701-080-080
                00:05:00,406-234-090
                00:01:07,407-234-090
                00:05:01,701-080-080
                00:05:00,407-234-090"""
    println("The total cost is: " + solution(log))
  }

  def stringToTuples(logs: Array[String]): Seq[(String, Int)] = {
    for(log <- logs) yield {
      val left = log.split(",")(1).trim
      val right = toSecs(log.split(",")(0).trim)
      (left, right)
    }
  }

  def solution(s: String): Int = {
    val logs: Array[String] = s.split("\n").map(_.trim)
    val logsMap = stringToTuples(logs) groupBy (_._1) mapValues (_ map (_._2))
    val mapNoMax: Map[Int, Int] = logsMap.map(x => (numberToInt(x._1) -> x._2.sum))
    val maxVal: (Int, Int) = mapNoMax.maxBy(_._2)
    println(maxVal)
    mapNoMax.map(x => costMap(x, maxVal)).sum
  }
  def numberToInt(noString: String): Int = {
    noString.replaceAll("[\\s\\-]", "").toInt
  }
  def toSecs(tString: String): Int = {
    val timeVal: Array[Int] = tString.split(":").map(_.toInt)
    val secs = (timeVal(0) * 60 * 60) + (timeVal(1) * 60) + timeVal(2)
    secs
  }
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
