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

package com.ACSlit.pro.templates.impl.composeActivity

import com.ACSlit.pro.templates.Language.Kotlin
import com.ACSlit.pro.templates.ProjectVersionData
import com.ACSlit.pro.templates.base.composeDependencies
import com.ACSlit.pro.templates.base.modules.android.defaultAppModule
import com.ACSlit.pro.templates.base.util.AndroidModuleResManager.ResourceType.VALUES
import com.ACSlit.pro.templates.impl.R
import com.ACSlit.pro.templates.impl.base.createRecipe
import com.ACSlit.pro.templates.impl.base.writeMainActivity
import com.ACSlit.pro.templates.impl.baseProjectImpl
import com.ACSlit.pro.templates.projectLanguageParameter

private const val composeKotlinVersion = "2.0.21"

private fun composeLanguageParameter() = projectLanguageParameter {
  default = Kotlin
  filter = { it == Kotlin }
}

// Compose template is available only in Kotlin
fun composeActivityProject() =
    baseProjectImpl(
        language = composeLanguageParameter(),
        projectVersionData = ProjectVersionData(kotlin = composeKotlinVersion),
    ) {
      templateName = R.string.template_compose
      thumb = R.drawable.compose_empty_activity

      defaultAppModule(addAndroidX = false) {
        isComposeModule = true

        recipe = createRecipe {
          require(data.language == Kotlin) { "Compose activity requires Kotlin language" }

          composeDependencies()

          res { writeXmlResource("themes", VALUES, source = ::composeThemesXml) }

          sources {
            writeMainActivity(this, ktSrc = ::composeActivitySrc, javaSrc = { "" })
            writeKtSrc("${data.packageName}.ui.theme", "Color", source = ::themeColorSrc)
            writeKtSrc("${data.packageName}.ui.theme", "Theme", source = ::themeThemeSrc)
            writeKtSrc("${data.packageName}.ui.theme", "Type", source = ::themeTypeSrc)
          }
        }
      }
    }
