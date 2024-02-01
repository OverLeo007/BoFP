import Utils.factorial

import scala.io.StdIn.readLine

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
    val fx = (x: BigDecimal) => math.sin(x.toDouble) / x
    val taylorSeq = (x: BigDecimal, n: Int) => (BigDecimal(-1).pow(n) * x.pow(2 * n)) / factorial(2 * n + 1)

    println("Введите через пробел: x_начальное, x_конечное, dx - шаг, e - точность")
    val numbersStr = readLine().replace(",", ".").split("\\s+")

    if (numbersStr.length == 4) {
      try {
        val numbers = numbersStr.map(BigDecimal.apply)
        val taylor = new Taylor(
          xS = numbers(0),
          xF = numbers(1),
          dx = numbers(2),
          e = numbers(3),
          func = fx,
          taylorSumBody = taylorSeq
        )
        taylor.run()
      } catch {
        case e: NumberFormatException =>
          System.err.println("Ошибка при преобразовании строки в BigDecimal. Некорректный формат числа.")
        case e: IllegalArgumentException =>
          System.err.println("Ошибка в значениях: " + e.getMessage)
      }
    } else {
      System.err.println("Ошибка: ввод должен содержать ровно 4 числа.")
    }
  }
}