import Utils.{factorial, forIFin}

import scala.annotation.tailrec
import scala.math.pow

/**
 * Класс для вычисления ряда Тейлора.
 *
 * @param xS           Начальное значение x.
 * @param xF           Конечное значение x.
 * @param dx           Шаг изменения x.
 * @param e            Ошибка.
 * @param func         Функция, для которой вычисляется ряд Тейлора.
 * @param taylorSumBody Функция, вычисляющая сумму ряда Тейлора.
 */
class Taylor(
              xS: BigDecimal = -0.9,
              xF: BigDecimal = -0.4,
              dx: BigDecimal = 0.1,
              e: BigDecimal = 0.00000001,
              func: BigDecimal => BigDecimal,
              taylorSumBody: (BigDecimal, Int) => BigDecimal
            ) {

  // Проверяем адекватность параметров
  if (dx <= 0 || e <= 0 || xS >= xF) {
    throw new IllegalArgumentException("Некорректные параметры")
  }

  // Проверяем достижимость xF от xS с заданным шагом
  if ((xF - xS) % dx != 0) {
    throw new IllegalArgumentException("Невозможно достичь xF от xS с заданным шагом dx.")
  }

  /**
   * Запускает вычисления ряда Тейлора и выводит результаты.
   */
  def run(): Unit = {
    println("╔" + "═" * 12 + "╤" + "═" * 22 + "╤" + "═" * 22 + "╤" + "═" * 7 + "╗")
    println(f"║ X          │ f(x)                 │ Taylor(x)            │ TI    ║")
    forIFin(xS, xF, dx, printTaylorResult)
    println("╚" + "═" * 12 + "╧" + "═" * 22 + "╧" + "═" * 22 + "╧" + "═" * 7 + "╝")
  }

  /**
   * Выводит результаты вычисления ряда Тейлора для заданного x.
   *
   * @param x Значение x.
   */
  private def printTaylorResult(x: BigDecimal): Unit = {

    /**
     * Вычисляет ряд Тейлора для заданного x.
     *
     * @return Пара значений: сумма ряда Тейлора и количество членов ряда.
     */
    def taylorFunc(): (BigDecimal, Int) = {
      val terms = LazyList.from(0).map {
        n => taylorSumBody(x, n)
      }.takeWhile(_.abs >= e)
      (terms.sum, terms.size)
    }

    val funcRes = taylorFunc()
    val taylorFuncResult = funcRes._1.setScale(15 min e.scale, BigDecimal.RoundingMode.HALF_UP)
    val termsCount = funcRes._2

    val builtItFuncResult = func(x).setScale(15 min e.scale, BigDecimal.RoundingMode.HALF_UP)
    println("╟" + "─" * 12 + "┼" + "─" * 22 + "┼" + "─" * 22 + "┼" + "─" * 7 + "╢")
    println(f"║ $x%-10s │ $builtItFuncResult%-20s │ $taylorFuncResult%-20s │ $termsCount%-5s ║")

  }
}