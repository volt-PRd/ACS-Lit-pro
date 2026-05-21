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

package com.ACSlit.pro.templates.impl

import com.ACSlit.pro.templates.BooleanParameter
import com.ACSlit.pro.templates.EnumParameter
import com.ACSlit.pro.templates.Language
import com.ACSlit.pro.templates.ProjectTemplate
import com.ACSlit.pro.templates.ProjectVersionData
import com.ACSlit.pro.templates.Sdk
import com.ACSlit.pro.templates.StringParameter
import com.ACSlit.pro.templates.base.AndroidModuleTemplateBuilder
import com.ACSlit.pro.templates.base.ProjectTemplateBuilder
import com.ACSlit.pro.templates.base.baseProject
import com.ACSlit.pro.templates.impl.base.createRecipe
import com.ACSlit.pro.templates.minSdkParameter
import com.ACSlit.pro.templates.packageNameParameter
import com.ACSlit.pro.templates.projectLanguageParameter
import com.ACSlit.pro.templates.projectNameParameter
import com.ACSlit.pro.templates.useKtsParameter

/** Indents the given string for the given [indentation level][level]. */
fun String.indentToLevel(level: Int): String {
  val lines = split(Regex("[\r\n]"))
  return StringBuilder()
      .apply {
        for (line in lines) {
          append(line)
          append(" ".repeat(level * 4))
        }
      }
      .toString()
}

@Suppress("UnusedReceiverParameter")
internal fun AndroidModuleTemplateBuilder.templateAsset(name: String, path: String): String {
  return "templates/${name}/${path}"
}

internal inline fun baseProjectImpl(
    projectName: StringParameter = projectNameParameter(),
    packageName: StringParameter = packageNameParameter(),
    useKts: BooleanParameter = useKtsParameter(),
    minSdk: EnumParameter<Sdk> = minSdkParameter(),
    language: EnumParameter<Language> = projectLanguageParameter(),
    projectVersionData: ProjectVersionData = ProjectVersionData(),
    crossinline block: ProjectTemplateBuilder.() -> Unit,
): ProjectTemplate =
    baseProject(
        projectName = projectName,
        packageName = packageName,
        useKts = useKts,
        minSdk = minSdk,
        language = language,
        projectVersionData = projectVersionData,
    ) {
      block()

      // make sure we return a proper result
      if (!isRecipeSet) {
        recipe = createRecipe {}
      }
    }
