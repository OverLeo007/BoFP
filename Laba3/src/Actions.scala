/**
 * Представляет различные действия, которые могут быть выполнены над строкой.
 */
sealed trait Action

object Actions {
  /** Очистка строки. */
  case object CLEAR extends Action
  /** Удаление всех вхождений из строки. */
  case object DELETE extends Action
  /** Заменяет все вхождения в строке на другие символы. */
  case object REPLACE extends Action
  /** Добавляет символ в начало строки. */
  case object ADD extends Action
}
