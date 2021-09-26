package sbuilds

import scala.io.Source._
import net.liftweb.json._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import java.io.FileNotFoundException

case class UserLog(
  ts:  Long,
  pt:  Int,
  si:  String,
  uu:  String,
  bg:  String,
  sha: String,
  nm:  String,
  ph:  String,
  dp:  Int)

object LogReader {
  implicit val formats = DefaultFormats
  def main(args: Array[String]) {
    var rowNo = 0
    try {
      val lines = fromFile("src/main/resources/log.json").getLines.toList
      if (lines.length==0) 
        println("Log file is empty")
      else{
        var validLines = new ListBuffer[UserLog]
        var uniqueExtns = new ListBuffer[String]
        var extnMap = Map.empty[String, Int]
        for (i <- 0 to lines.length - 1) {
          rowNo = i + 1
          val logLine = parse(lines(i)).extract[UserLog]
          validLines += logLine
          val extn = if (logLine.nm.split('.').length > 1) logLine.nm.split('.')(1) else "NO_EXTN"
          if (!uniqueExtns.contains(extn)) {
            uniqueExtns += extn
            extnMap += (extn -> 1)
          } else
            extnMap(extn) = extnMap(extn) + 1
        }
        extnMap foreach (ext => println(ext._1 + ":" + ext._2))
      }
    } catch {
      case fileNotFound: FileNotFoundException => println("Input log file not found")
      case mappingEx: MappingException         => println("Error parsing record in row num:" + rowNo)
    }
  }
}