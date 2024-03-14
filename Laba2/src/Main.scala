import scala.io.Source

/**
 * Объект, содержащий точку входа в приложение.
 */
object Main {
  /**
   * Точка входа в приложение.
   *
   * @param args Аргументы командной строки.
   */
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      System.err.println(s"Введите имя файла")
      System.exit(0)
    }
    try {
      val file = Source.fromFile(args(0))
      val lines = file.getLines().toList
      file.close()
      if (lines.isEmpty) System.err.println("Файл пуст")
      else {
        lines.zipWithIndex.foreach { case (line, index) =>
          val splitLine = line.split("\\s+")
          if (splitLine.isEmpty) {
            println(s"Line $index: Only spaces in string")
          } else {
            val maxWord = splitLine.maxBy(_.length)
            println(s"Line $index: ${if (maxWord.isEmpty) "\u001b[31;0mline is empty\u001b[0;0m" else maxWord}")
          }
        }
      }
    } catch {
      case e: Exception =>
        System.err.println(s"Не удалось открыть файл: ${e.getMessage}")
    }
  }
}