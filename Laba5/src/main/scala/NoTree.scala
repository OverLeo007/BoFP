package ru.paskal.laba5

import tree.MapNode

class NoTree(
              mapNodes: List[MapNode],
              keyToIndex: String
              ) {
  var comparisonCount: Integer = 0
  
  def find(value: Any): List[MapNode] = {
    comparisonCount = 0
    var res = List.empty[MapNode]
    for (node <- mapNodes) {
      if (node.contains(keyToIndex)) {
        comparisonCount += 1
        if (node.get(keyToIndex) == value) {
          res = res.appended(node)
          return res
        }
      }
    }
    res
  }
}
