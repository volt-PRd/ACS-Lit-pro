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

package com.ACSlit.pro.lsp.models

import com.ACSlit.pro.lsp.CancellableRequestParams
import com.ACSlit.pro.models.Location
import com.ACSlit.pro.models.Position
import com.ACSlit.pro.progress.ICancelChecker
import java.nio.file.Path

/** @author Akash Yadav */
data class ReferenceParams(
    var file: Path,
    var position: Position,
    var includeDeclaration: Boolean,
    override val cancelChecker: ICancelChecker,
) : CancellableRequestParams

data class ReferenceResult(var locations: List<Location>)
