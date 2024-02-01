import scala.annotation.tailrec

/**
 * Объект, содержащий утилитарные методы для работы с числами.
 */
object Utils {

  /**
   * Выполняет итерацию от начального значения до конечного с заданным шагом и применяет к каждому значению
   * указанное тело.
   *
   * @param start Начальное значение.
   * @param stop  Конечное значение.
   * @param step  Шаг итерации.
   * @param body  Функция, применяемая к каждому значению.
   */
  def forIFin(
               start: BigDecimal,
               stop: BigDecimal,
               step: BigDecimal,
               body: BigDecimal => Unit
             ): Unit = {
    @tailrec
    def iteration(i: BigDecimal): Unit = {
      body(i)
      if (i < stop) iteration(i + step)
    }
    iteration(start)
  }

  /**
   * Вычисляет факториал числа n.
   *
   * @param n Число, для которого вычисляется факториал.
   * @return Факториал числа n.
   */
  def factorial(n: BigDecimal): BigDecimal = {
    if (n <= 1) 1 else n * factorial(n - 1)
  }
}
