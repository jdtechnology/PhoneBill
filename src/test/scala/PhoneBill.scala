package main.test.scala.PhoneBill

import main.scala.PhoneBill.PhoneBill
import org.scalatest._

class PhoneBill extends WordSpec with Matchers {
  val logs = """00:01:07,406-234-090
                00:05:01,701-080-080
                00:05:00,406-234-090"""
  val logsWithDuplicates = """00:01:07,406-234-090
                00:05:01,701-080-080
                00:05:00,406-234-090
                00:01:07,407-234-090
                00:05:01,709-080-080
                00:05:00,407-234-090"""
  val splitLogs = Map(406234090 -> 367, 701080080 -> 301)
  val splitLogsDuplicateTime = Map(701080080 -> 301, 709080080 -> 301, 407234090 -> 367, 406234090 -> 367)
  val logsStringArray: Array[String] = logs.split("\n").map(_.trim)
  val logsWithDuplicatesStringArray: Array[String] = logsWithDuplicates.split("\n").map(_.trim)

  "splitlogs" should {
    "return a Map[Int, Int] when passed a valid parameter" in {
      PhoneBill.splitLogsToMapOfIntInt(logs) shouldBe Map(406234090 -> 367, 701080080 -> 301)
    }
    "handle duplicates in the same way and return a Map[Int, Int]" in {
      PhoneBill.splitLogsToMapOfIntInt(logsWithDuplicates) shouldBe Map(701080080 -> 301, 709080080 -> 301, 407234090 -> 367, 406234090 -> 367)
    }
  }
  "findMaxMin" should {
    "return a Tuple2 of (Int, Int) with max dur and min number value when passed valid parameter" in {
      PhoneBill.findMaxMinTimeNumValue(splitLogs) shouldBe (406234090, 367)
    }
    "handle the same way given two identical duration values returning the one with the lowest number numerical value" in {
      PhoneBill.findMaxMinTimeNumValue(splitLogsDuplicateTime) shouldBe (406234090, 367)
    }
  }
  "total" should {
    "return 0 if the log given is the log to exclude (max)" in {
      PhoneBill.total((406234090, 367), (406234090, 367)) shouldBe 0
    }
    "return an Integer of cost for given log, over 5 minutes = 150 * started minutes (301 starts 6th minute) (if non-identical params)" in {
      PhoneBill.total((701080080, 301), (406234090, 367)) shouldBe 900
    }
    "return an Integer of cost, given a time of exactly 5 minutes (if non-identical params)" in {
      PhoneBill.total((406234090, 300), (406234090, 367)) shouldBe 750
    }
    "return an Integer of cost, given a time of exactly 6 minutes (handle the same as above)" in {
      PhoneBill.total((406234090, 360), (406234090, 367)) shouldBe 900
    }
  }
  "stringToTuples" should {
    "return a representation of the logs as a Seq[Tuple2(String, Int)] where the Int is Duration in seconds (given valid params)" in {
      PhoneBill.stringToTuples(logsStringArray) shouldBe Seq(("406-234-090", 67), ("701-080-080", 301), ("406-234-090", 300))
    }
    "handle the same given a longer Array with duplicates having their own Tuple2" in {
      PhoneBill.stringToTuples(logsWithDuplicatesStringArray) shouldBe Seq(("406-234-090", 67), ("701-080-080", 301), ("406-234-090", 300), ("407-234-090", 67), ("709-080-080", 301), ("407-234-090", 300))
    }
  }
  "numberToInt" should {
    "return an Integer from a string removing any dashes (given valid params)" in {
      PhoneBill.numberToInt("701-080-080") shouldBe 701080080
    }
    "also remove spaces for good measure" in {
      PhoneBill.numberToInt("701-08 0-080") shouldBe 701080080
    }
  }
  "toSecs" should {
    "return an Integer after converting a time string in format hh:mm:ss to seconds (given valid params)" in  {
      PhoneBill.toSecs("00:01:07") shouldBe 67
    }
    "also another test with hours for good measure" in {
      PhoneBill.toSecs("2:05:00") shouldBe 7500
    }
  }
  "maxMins" should {
    "return a list (of Tuple2(Int, Int) of the logs with maximum durations (given valid params)" in {
      PhoneBill.maxMins(splitLogs.toList) shouldBe List((406234090, 367))
    }
    "also add more wih identical durations" in {
      PhoneBill.maxMins(splitLogsDuplicateTime.toList) shouldBe List((407234090, 367), (406234090, 367))
    }
  }
}
