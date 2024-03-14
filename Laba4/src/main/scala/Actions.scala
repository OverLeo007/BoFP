package ru.paskal.laba4


/**
 * Представляет различные действия, которые могут быть выполнены над строкой.
 */
sealed trait Action {
  val name: String
}

object Actions {
  /** Очистка строки. */
  case object CLEAR extends Action {
    val name = "clear"
  }
  /** Удаление всех вхождений из строки. */
  case object DELETE extends Action {
    val name = "delete"
  }
  /** Заменяет все вхождения в строке на другие символы. */
  case object REPLACE extends Action {
    val name = "replace"
  }
  /** Добавляет символ в начало строки. */
  case object ADD extends Action {
    val name = "add"
  }
}
