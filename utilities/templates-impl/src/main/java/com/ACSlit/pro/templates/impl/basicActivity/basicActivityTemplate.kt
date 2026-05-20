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

package com.ACSlit.pro.templates.impl.basicActivity

import com.ACSlit.pro.templates.ProjectTemplate
import com.ACSlit.pro.templates.base.AndroidModuleTemplateBuilder
import com.ACSlit.pro.templates.base.modules.android.defaultAppModule
import com.ACSlit.pro.templates.base.util.AndroidModuleResManager.ResourceType.LAYOUT
import com.ACSlit.pro.templates.base.util.SourceWriter
import com.ACSlit.pro.templates.impl.R
import com.ACSlit.pro.templates.impl.base.createRecipe
import com.ACSlit.pro.templates.impl.base.emptyThemesAndColors
import com.ACSlit.pro.templates.impl.base.writeMainActivity
import com.ACSlit.pro.templates.impl.baseProjectImpl

fun basicActivityProject(): ProjectTemplate {
  return baseProjectImpl {
    templateName = R.string.template_basic
    thumb = R.drawable.basic_activity
    defaultAppModule {
      recipe = createRecipe {
        sources { writeBasicActivitySrc(this) }

        res {
          writeBasicActivityLayout()
          emptyThemesAndColors()
        }
      }
    }
  }
}

private fun AndroidModuleTemplateBuilder.writeBasicActivitySrc(writer: SourceWriter) {
  writeMainActivity(writer = writer, ktSrc = ::basicActivitySrcKt, javaSrc = ::basicActivitySrcJava)
}

internal fun AndroidModuleTemplateBuilder.writeBasicActivityLayout() {
  res.apply {
    writeXmlResource("activity_main", LAYOUT, source = ::basicActivityLayout)
    writeXmlResource("content_main", LAYOUT, source = ::basicActivityContent)
  }
}
