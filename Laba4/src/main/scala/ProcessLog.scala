package ru.paskal.laba4

class ProcessLog(
                       val inpStr: String,
                       val actions: List[(Action, List[Char])],
                       val resStr: String
                       ) {
  override def toString: String = {
    val actionsStr = actions.map((action, args) => s"${action.name}(${args.mkString(", ")})").mkString(", ")
    s"ProcessLog(input=$inpStr, action(s)=[$actionsStr], result=$resStr)"
  }
}
