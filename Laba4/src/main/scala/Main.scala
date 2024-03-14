package ru.paskal.laba4

import java.nio.file.Paths
import scala.util.Random.shuffle


/**
 * Объект, содержащий точку входа в приложение.
 */
object Main {

  val fileName = "files\\log.csv"

  /**
   * Точка входа в приложение.
   *
   * @param args Аргументы командной строки.
   */
  def main(args: Array[String]): Unit = {
    testProcess()
  }

  private def testProcess(): Unit = {
    val processor = new StringProcessor

    // Пример использования метода process
    val str = "Hello, world!"
    val clearedStr = processor.process(Actions.CLEAR, str)
    println("Cleared string: " + clearedStr) // Ожидаемый результат: ""

    val deletedStr = processor.process(Actions.DELETE, str, 'o')
    println("Deleted string: " + deletedStr) // Ожидаемый результат: "Hell, wrld!"

    val replacedStr = processor.process(Actions.REPLACE, str, 'l', 'z')
    println("Replaced string: " + replacedStr) // Ожидаемый результат: "Hezzo, worzd!"

    val addedStr = processor.process(Actions.ADD, str, '!')
    println("Added string: " + addedStr) // Ожидаемый результат: "!Hello, world!"

    // Пример использования метода processAll
    val actions = List[(Action, List[Char])](
      (Actions.DELETE, List('o')),
      (Actions.ADD, List('!')),
      (Actions.REPLACE, List('l', 'z')),
      (Actions.ADD, List('a'))
    )
    val processedStr = processor.processAll(actions, str)
    println("Processed string: " + processedStr) // Ожидаемый результат: "!Hezz, wrzd!"
    val actions2 = List[(Action, List[Char])](
      (Actions.ADD, List('o')),
      (Actions.DELETE, List('!')),
      (Actions.REPLACE, List('l', 'a'))
    )
    val processedStr2 = processor.processAll(actions2, str)
    println("Processed string: " + processedStr2) // Ожидаемый результат: "!Hezz, wrzd!"

    // Пример использования метода deleteAll
    val str1 = "abracadabra"
    val str2 = "bebrazium"
    val deletedAllStr = processor.deleteAll(str1, str2)
    println("Deleted all characters of str1 from str2: " + deletedAllStr) // Ожидаемый результат: "ezium"

    StringProcessor.writeToCSV(fileName, processor.getFlattenLogList)
    println(processor.getLogList.mkString("\n"))
    println("")
    val fromCsv = StringProcessor.readFromCSV(fileName)
    println(fromCsv.getLogList.mkString("\n"))
  }
}