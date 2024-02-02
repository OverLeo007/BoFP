import scala.io.Source

object Main {
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
          println(s"Line $index: ${line.split("\\s+").maxBy(_.length)}")
        }
      }
    } catch {
      case e: Exception =>
        System.err.println(s"Не удалось открыть файл: ${e.getMessage}")
    }
  }
}
