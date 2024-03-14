package ru.paskal.laba4

import java.util.Optional

class FlatProcessLog(
                  val inpStr: String,
                  val actionName: String,
                  val arg1: Option[Char],
                  val arg2: Option[Char],
                  val resStr: String,
                  val index: Integer,
                  val startStr: String
                ) {


  private def canEqual(other: Any): Boolean = other.isInstanceOf[FlatProcessLog]




  override def toString = s"Log(index=$index, inpStr=$inpStr, resStr=$resStr, actionName=$actionName, arg1=$arg1, arg2=$arg2, startStr=$startStr)"

  override def equals(other: Any): Boolean = other match
    case that: FlatProcessLog =>
      that.canEqual(this) &&
        inpStr == that.inpStr &&
        actionName == that.actionName &&
        arg1 == that.arg1 &&
        arg2 == that.arg2 &&
        resStr == that.resStr &&
        index == that.index &&
        startStr == that.startStr
    case _ => false
  
  override def hashCode(): Int =
    val state = Seq(inpStr, actionName, arg1, arg2, resStr, index, startStr)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
}
