import scala.collection.immutable

object PhoneBill {
  def main(args: Array[String]): Unit = {
    val log = """00:01:07,406-234-090
                00:05:01,701-080-080
                00:05:00,406-234-090"""
    solution(log)
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
//    println(logs.toList)
    val logsMap = stringToTuples(logs) groupBy (_._1) mapValues (_ map (_._2))
    val mapNoMax: Map[String, Int] = logsMap.map(x => (x._1 -> x._2.sum))
    println(mapNoMax)
    val maxVal: (String, Int) = mapNoMax.maxBy(_._2)
    val totCost: Int = mapNoMax.map(x => costMap(x, maxVal)).sum
    println(totCost)

    val durs: Array[String] = logs.map(_.split(",")(0).trim)
    val uniNo: Array[String] = logs.map(_.split(",")(1).trim)
    //println(uniNo.groupBy(identity).mapValues(_.size) + "lllll")
    println(uniNo.groupBy(identity).mapValues(_.size) + "lllll")
    val maxKey = uniNo.maxBy(_(1))  // (a,100)

    val costTotal = logs.map(x => cost(x, maxKey)).sum
    println(costTotal)

    2
  }
  def toSecs(tString: String): Int = {
    val timeVal: Array[Int] = tString.split(":").map(_.toInt)
    val secs = (timeVal(0) * 60 * 60) + (timeVal(1) * 60) + timeVal(2)
    secs
  }
  def costMap(log: (String, Int), max: (String, Int)): Int = {
      if(max == log) return 0
      else {
        val secs = log._2
        if(secs < (5 * 60 * 60)) 3 * secs
        else (secs / 60) * 150
      }
  }
  def cost(log: String, max: String): Int = {
    if(max == log.split(",")(1).trim) return 0
    else {
      val dur = log.split(",")(0).trim
      val timeVal: Array[Int] = dur.split(":").map(_.toInt)
      val secs = (timeVal(0) * 60 * 60) + (timeVal(1) * 60) + timeVal(2)
      if(secs < (5 * 60)) 3 * secs
      else if(secs == 50 * 60) (secs / 60) * 150
      else ((secs / 60) + 1) * 150
    }
  }
}
