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

package com.ACSlit.pro.templates.impl.base

import com.ACSlit.pro.templates.ModuleTemplateData
import com.ACSlit.pro.templates.ModuleTemplateRecipeResult
import com.ACSlit.pro.templates.ProjectTemplateData
import com.ACSlit.pro.templates.ProjectTemplateRecipeResult
import com.ACSlit.pro.templates.base.ModuleTemplateBuilder
import com.ACSlit.pro.templates.base.ProjectTemplateBuilder

data class ProjectTemplateRecipeResultImpl(override val data: ProjectTemplateData) :
    ProjectTemplateRecipeResult

data class ModuleTemplateRecipeResultImpl(override val data: ModuleTemplateData) :
    ModuleTemplateRecipeResult

internal fun ProjectTemplateBuilder.recipeResult(): ProjectTemplateRecipeResult {
  return ProjectTemplateRecipeResultImpl(data)
}

internal fun ModuleTemplateBuilder.recipeResult(): ModuleTemplateRecipeResult {
  return ModuleTemplateRecipeResultImpl(data)
}
