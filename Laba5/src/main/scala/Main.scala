package ru.paskal.laba5

import tree.{MapNode, Node, Tree}

import org.json4s.{DefaultFormats, JObject}
import org.json4s.jackson.JsonMethods.parse

import java.io.{FileNotFoundException, NotSerializableException}
import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    var jsonObjects: Option[List[MapNode]] = None
    var tree: Option[Tree] = None

    var continue = true
    while (continue) {
      println("1. Загрузить JSON из файла")
      if (jsonObjects.isDefined) {
        println("2. Создать дерево по полю")
        println("4. Выполнить линейный поиск")
      } else {
        println("(недоступно) 2. Создать дерево по полю")
        println("(недоступно) 4. Выполнить линейный поиск")
      }
      if (tree.isDefined) {
        println("3. Выполнить поиск по дереву")
        println("5. Сохранить индекс в файл")
      } else {
        println("(недоступно) 3. Выполнить поиск по дереву")
        println("(недоступно) 5. Сохранить индекс в файл")
      }
      println("6. Загрузить индекс из файла")
      println("7. Выход")

      var choice: Int = -1

      try {
        choice = scala.io.StdIn.readInt()
      } catch {
        case e: NumberFormatException =>
      }
      choice match {
        case 1 =>
          println("Введите путь до файла:")
          val filePath = scala.io.StdIn.readLine()
          jsonObjects = Some(readJsonFile(filePath))
        case 2 if jsonObjects.isDefined =>
          println("Введите поле:")
          val field = scala.io.StdIn.readLine()
          tree = Some(Tree.makeFrom(jsonObjects.get, field))
          println(s"Высота: ${tree.get.height}")
        case 3 if tree.isDefined =>
          println("Введите значение поля:")
          val value = scala.io.StdIn.readLine()
          val result = tree.get.find(value)
          println(s"Результат: $result")
          println(s"Сравнений: ${tree.get.comparisonCount}")
        case 4 if jsonObjects.isDefined =>
          println("Введите поле:")
          val field = scala.io.StdIn.readLine()
          println("Введите значение поля:")
          val value = scala.io.StdIn.readLine()
          val noTreeSearch = new NoTree(jsonObjects.get, field)
          val result = noTreeSearch.find(value)
          println(s"Результат: $result")
          println(s"Сравнений: ${noTreeSearch.comparisonCount}")
        case 5 if tree.isDefined =>
          println("Введите путь до файла:")
          val filePath = scala.io.StdIn.readLine()
          Tree.saveIndex(tree.get, filePath)
        case 6 =>
          println("Введите путь до файла:")
          val filePath = scala.io.StdIn.readLine()
          try {
            tree = Some(Tree.loadIndex(filePath))
            println("Загружено дерево")
            println(s"Индекс построен по ${tree.get.key}")
            println(s"Высота дерева: ${tree.get.height}")
          } catch {
            case e: NotSerializableException => println(e)
            case e: FileNotFoundException => println(e)
          }
        case 7 =>
          continue = false
        case _ =>
          println("Некорректный ввод")
      }
    }
  }

  def readJsonFile(filePath: String): List[MapNode] = {
    val source = Source.fromFile(filePath)
    val jsonString = try {
      source.mkString
    } finally {
      source.close()
    }

    implicit val formats: DefaultFormats.type = DefaultFormats
    val json = parse(jsonString)

    val objects: List[JObject] = json.extract[List[JObject]]

    objects.map { obj =>
      val dataMap = obj.values
      MapNode(dataMap)
    }
  }
}