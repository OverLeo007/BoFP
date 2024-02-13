import scala.collection.immutable.HashMap

/**
 * Класс, предоставляющий операции над строками.
 * Операции включают в себя очистку, удаление, замену и добавление символов в строку.
 */
class StringProcessor {

  /**
   * Очищает строку, удаляя все символы из неё.
   *
   * @param str Исходная строка.
   * @return Пустая строка.
   */
  private def clearing(str: String): String = ""

  /**
   * Удаляет все вхождения указанного символа из строки.
   *
   * @param str     Исходная строка.
   * @param delChar Символ, который нужно удалить.
   * @return Строка без указанного символа.
   */
  private def deleting(str: String, delChar: Char): String = str.filterNot(_ == delChar)

  /**
   * Заменяет все вхождения одного символа на другой символ в строке.
   *
   * @param str     Исходная строка.
   * @param oldChar Символ, который нужно заменить.
   * @param newChar Символ, на который нужно заменить.
   * @return Строка с заменёнными символами.
   */
  private def replacing(str: String, oldChar: Char, newChar: Char): String = str.replace(oldChar, newChar)

  /**
   * Добавляет указанный символ в начало строки.
   *
   * @param str       Исходная строка.
   * @param toAddChar Символ, который нужно добавить.
   * @return Строка с добавленным символом в начале.
   */
  private def adding(str: String, toAddChar: Char): String = toAddChar + str

  /**
   * Выполняет указанное действие над строкой.
   *
   * @param action   Действие, которое нужно выполнить.
   * @param str      Исходная строка.
   * @param charArgs Аргументы для действия (например, символы для удаления, замены или добавления).
   * @return Модифицированная строка в соответствии с указанным действием.
   * @throws IllegalArgumentException Если действие некорректно или аргументы некорректны.
   */
  def process(action: Action, str: String, charArgs: Char*): String = {

    action match
      case Actions.CLEAR => clearing(str)
      case Actions.DELETE => if (charArgs.length != 1) failure(Actions.DELETE, charArgs) else deleting(str, charArgs.head)
      case Actions.REPLACE => if (charArgs.length != 2) failure(Actions.REPLACE, charArgs) else replacing(str, charArgs.head, charArgs(1))
      case Actions.ADD => if (charArgs.length != 1) failure(Actions.ADD, charArgs) else adding(str, charArgs.head)
      case null => throw new IllegalArgumentException("Действие некорректно \\ Аргументы некорректны")
  }

  /**
   * Выполняет последовательность действий над строкой.
   *
   * @param actionsArgs Список кортежей, каждый содержащий действие и аргументы для этого действия.
   * @param str         Исходная строка.
   * @return Модифицированная строка после применения всех действий.
   */
  def processAll(actionsArgs: List[(Action, List[Char])], str: String): String = {
    actionsArgs.foldLeft(List(str)) {case (strList, (action, charArgs)) =>
      strList.map { currentStr =>
        process(action, currentStr, charArgs: _*)
      }
    }.head
  }

  /**
   * Выполняет удаление символов первой строки из второй строки.
   *
   * @param str1 Строка, символы которой будут удалены.
   * @param str2 Строка символы из которой будут удалены.
   * @return Модифицированная строка после применения всех действий.
   */
  def deleteAll(str1: String, str2: String): String = processAll(str1.map(chr => (Actions.DELETE, List(chr))).toList, str2)


  /**
   * Выбрасывает исключение при некорректном кол-ве параметров для определеннго действия.
   * 
   * @param action действие, выполнение которого невозможно.
   * @param args аргументы, при которых невозможно действие.
   * @throws IllegalArgumentException ошибка, показывающе какое действие 
   *                                  и какой набор аргументов невозможно использовать.            
   */
  private def failure(action: Action, args: Seq[Char]): Nothing = {
    throw new IllegalArgumentException("Действие " + action +
      " не может быть выполнено с аргументами: List(" + args.mkString(", ") + ")")
  }
}
