/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.ACSlit.pro.treesitter.api

import com.ACSlit.pro.treesitter.TSInputEdit
import com.ACSlit.pro.treesitter.TSLanguage
import com.ACSlit.pro.treesitter.TSLookaheadIterator
import com.ACSlit.pro.treesitter.TSNode
import com.ACSlit.pro.treesitter.TSParser
import com.ACSlit.pro.treesitter.TSPoint
import com.ACSlit.pro.treesitter.TSQuery
import com.ACSlit.pro.treesitter.TSQueryCapture
import com.ACSlit.pro.treesitter.TSQueryCursor
import com.ACSlit.pro.treesitter.TSQueryMatch
import com.ACSlit.pro.treesitter.TSQueryPredicateStep
import com.ACSlit.pro.treesitter.TSRange
import com.ACSlit.pro.treesitter.TSTree
import com.ACSlit.pro.treesitter.TSTreeCursor
import com.ACSlit.pro.treesitter.TSTreeCursorNode
import com.ACSlit.pro.treesitter.string.SynchronizedUTF16String
import com.ACSlit.pro.treesitter.string.UTF16String
import com.ACSlit.pro.treesitter.util.TSObjectFactory

/**
 * [TSObjectFactory] implementation for AndroidIDE.
 *
 * @author Akash Yadav
 */
class TreeSitterObjectFactory : TSObjectFactory {

  override fun createInputEdit(
      startByte: Int,
      oldEndByte: Int,
      newEndByte: Int,
      startPoint: TSPoint,
      oldEndPoint: TSPoint,
      newEndPoint: TSPoint,
  ): TSInputEdit {
    return TreeSitterInputEdit.obtain(
        startByte,
        oldEndByte,
        newEndByte,
        startPoint,
        oldEndPoint,
        newEndPoint,
    )
  }

  override fun createParser(parserPointer: Long): TSParser {
    return TreeSitterParser.obtain(parserPointer)
  }

  override fun createQuery(queryPointer: Long): TSQuery {
    return TreeSitterQuery.obtain(queryPointer)
  }

  override fun createQueryCursor(pointer: Long): TSQueryCursor {
    return TreeSitterQueryCursor.obtain(pointer)
  }

  override fun createPoint(row: Int, column: Int): TSPoint {
    return TreeSitterPoint.obtain(row, column)
  }

  override fun createRange(
      startByte: Int,
      endByte: Int,
      startPoint: TSPoint,
      endPoint: TSPoint?,
  ): TSRange {
    return TreeSitterRange.obtain(startByte, endByte, startPoint, endPoint)
  }

  override fun createRangeArr(size: Int): Array<TSRange?> {
    return arrayOfNulls(size)
  }

  override fun createTree(pointer: Long): TSTree {
    return TreeSitterTree.obtain(pointer)
  }

  override fun createTreeCursor(pointer: Long): TSTreeCursor {
    return TreeSitterTreeCursor.obtain(pointer)
  }

  override fun createNode(
      context0: Int,
      context1: Int,
      context2: Int,
      context3: Int,
      id: Long,
      treePointer: Long,
  ): TSNode {
    return TreeSitterNode.obtain(context0, context1, context2, context3, id, treePointer)
  }

  override fun createQueryCapture(node: TSNode?, index: Int): TSQueryCapture {
    return TreeSitterQueryCapture.obtain(node, index)
  }

  override fun createQueryMatch(
      id: Int,
      patternIndex: Int,
      captures: Array<TSQueryCapture?>?,
  ): TSQueryMatch {
    return TreeSitterQueryMatch.obtain(id, patternIndex, captures)
  }

  override fun createQueryPredicateStep(type: Int, valueId: Int): TSQueryPredicateStep {
    return TreeSitterQueryPredicateStep.obtain(type, valueId)
  }

  override fun createQueryPredicateStepArr(size: Int): Array<TSQueryPredicateStep?> {
    return arrayOfNulls(size)
  }

  override fun createTreeCursorNode(
      type: String?,
      name: String?,
      startByte: Int,
      endByte: Int,
  ): TSTreeCursorNode {
    return TreeSitterTreeCursorNode.obtain(type, name, startByte, endByte)
  }

  override fun createLookaheadIterator(pointer: Long): TSLookaheadIterator {
    return TreeSitterLookaheadIterator.obtain(pointer)
  }

  override fun createLanguage(name: String?, pointers: LongArray?): TSLanguage {
    return TreeSitterNativeLanguage.obtain(name, pointers)
  }

  override fun createString(pointer: Long, isSynchronized: Boolean): UTF16String {
    return if (isSynchronized) SynchronizedUTF16String(pointer) else UTF16String(pointer)
  }
}
