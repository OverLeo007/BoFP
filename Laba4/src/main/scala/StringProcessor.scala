package ru.paskal.laba4

import scala.collection.mutable.ArrayBuffer
import com.github.tototoshi.csv._

/**
 * Класс, предоставляющий операции над строками.
 * Операции включают в себя очистку, удаление, замену и добавление символов в строку.
 */
class StringProcessor(
                     val processList: ArrayBuffer[FlatProcessLog] = ArrayBuffer.empty[FlatProcessLog]
                     ) {

//  private val processList =

  def getFlattenLogList: List[FlatProcessLog] = {
    processList.toList
  }

    def getLogList: List[ProcessLog] = {
      StringProcessor.convertToProcessLog(processList.toList)
    }


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


  def process(action: Action, str: String, charArgs: Char*): String = {
    process(action, str, 0, str, charArgs *)
  }

  /**
   * Выполняет указанное действие над строкой.
   *
   * @param action   Действие, которое нужно выполнить.
   * @param str      Исходная строка.
   * @param charArgs Аргументы для действия (например, символы для удаления, замены или добавления).
   * @return Модифицированная строка в соответствии с указанным действием.
   * @throws IllegalArgumentException Если действие некорректно или аргументы некорректны.
   */
  def process(action: Action, str: String, index: Integer, startStr: String, charArgs: Char*): String = {

    def innerProcessor(): String = {
      action match
        case Actions.CLEAR => clearing(str)
        case Actions.DELETE => if (charArgs.length != 1) failure(Actions.DELETE, charArgs) else deleting(str, charArgs.head)
        case Actions.REPLACE => if (charArgs.length != 2) failure(Actions.REPLACE, charArgs) else replacing(str, charArgs.head, charArgs(1))
        case Actions.ADD => if (charArgs.length != 1) failure(Actions.ADD, charArgs) else adding(str, charArgs.head)
        case null => throw new IllegalArgumentException("Действие некорректно \\ Аргументы некорректны")
    }

    val result = innerProcessor()

    val curLog = FlatProcessLog(
      inpStr = str,
      actionName = action.name,
      arg1 = if (charArgs.nonEmpty) Option(charArgs.head) else None,
      arg2 = if (charArgs.length > 1) Option(charArgs(1)) else None,
      resStr = result,
      index = index,
      startStr = startStr
    )
    this.processList.addOne(curLog)
    result
  }

  /**
   * Выполняет последовательность действий над строкой.
   *
   * @param actionsArgs Список кортежей, каждый содержащий действие и аргументы для этого действия.
   * @param str         Исходная строка.
   * @return Модифицированная строка после применения всех действий.
   */
  def processAll(actionsArgs: List[(Action, List[Char])], str: String): String = {
    actionsArgs.zipWithIndex.foldLeft(List(str)) { case (strList, ((action, charArgs), index)) =>
      strList.map { currentStr =>
        process(action, currentStr, index, str, charArgs *)
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
   * @param args   аргументы, при которых невозможно действие.
   * @throws IllegalArgumentException ошибка, показывающе какое действие 
   *                                  и какой набор аргументов невозможно использовать.            
   */
  private def failure(action: Action, args: Seq[Char]): Nothing = {
    throw new IllegalArgumentException("Действие " + action +
      " не может быть выполнено с аргументами: List(" + args.mkString(", ") + ")")
  }
}


object StringProcessor {

  private val actionMap: Map[String, Action] = Map(
    Actions.CLEAR.name -> Actions.CLEAR,
    Actions.DELETE.name -> Actions.DELETE,
    Actions.REPLACE.name -> Actions.REPLACE,
    Actions.ADD.name -> Actions.ADD
  )

  def writeToCSV(filename: String, logs: List[FlatProcessLog]): Unit = {
    println("Сохраняем историю в csv...")
    val writer = CSVWriter.open(filename)
    val headers = Seq("inpStr", "actionName", "arg1", "arg2", "resStr", "index", "startStr")
    writer.writeRow(headers)

    logs.foreach { log =>
      val row = Seq(
        log.inpStr,
        log.actionName,
        log.arg1.getOrElse("").toString,
        log.arg2.getOrElse("").toString,
        log.resStr,
        log.index.toString,
        log.startStr
      )
      writer.writeRow(row)
    }

    writer.close()

  }

  def readFromCSV(filename: String): StringProcessor = {
    println("Загружаем историю из csv...")
    val reader = CSVReader.open(filename)
    val logs = reader.allWithHeaders().map { row =>
      FlatProcessLog(
        row("inpStr"),
        row("actionName"),
        if (row("arg1").isEmpty) None else Some(row("arg1").charAt(0)), // Deserialize Option[Char]
        if (row("arg2").isEmpty) None else Some(row("arg2").charAt(0)), // Deserialize Option[Char]
        row("resStr"),
        row("index").toInt,
        row("startStr")
      )
    }
    reader.close()
    StringProcessor(ArrayBuffer.from(logs))
  }

  private def convertToProcessLog(flatLogs: List[FlatProcessLog]): List[ProcessLog] = {
    val seqList = flatLogs.filter(log => log.index == 0).map(log => ArrayBuffer(log))
    val indexGroups = flatLogs.groupBy(log => log.index).map((k, v) => (k, v)).toList.sortBy(elem => elem._1)
    indexGroups.foreach((k, v) => {
      if (k > 0) {

        v.foreach(cur => {
          val prev = indexGroups.flatMap((k, v) => v).filter(
            logg => logg.resStr.equals(cur.inpStr) && (logg.index == cur.index - 1)
          ).head

          seqList.find(
            list => list.last.equals(prev)
          ).getOrElse(throw new RuntimeException("Ошибка конвертации")).addOne(cur)
        })
      }
    })
    seqList.map(list => {
      val actions = list.map(
        log =>
        (
          actionMap.getOrElse(log.actionName, throw new RuntimeException("Ошибка преобразования при конвертации")),
          log.arg1.toList ++ log.arg2.toList
        )
      ).toList
      ProcessLog(list.head.inpStr, actions, list.last.resStr)
    })
  }
}