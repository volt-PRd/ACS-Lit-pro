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

package com.ACSlit.pro.uidesigner.utils

import android.view.View
import android.view.ViewGroup
import com.ACSlit.pro.inflater.INamespace
import com.ACSlit.pro.inflater.IView
import com.ACSlit.pro.inflater.internal.LayoutFile
import com.ACSlit.pro.inflater.internal.NamespaceImpl
import com.ACSlit.pro.uidesigner.models.UiAttribute
import com.ACSlit.pro.uidesigner.models.UiView
import com.ACSlit.pro.uidesigner.models.UiViewGroup

/**
 * Creates layout inflater components for UI Designer.
 *
 * @author Akash Yadav
 */
open class UiInflaterComponentFactory : com.ACSlit.pro.inflater.IComponentFactory {

  override fun createView(
      file: LayoutFile,
      name: String,
      view: View,
  ): com.ACSlit.pro.inflater.IView {
    if (view is ViewGroup) {
      return UiViewGroup(file, name, view)
    }
    return UiView(file, name, view)
  }

  override fun createAttr(
      view: IView,
      namespace: INamespace?,
      name: String,
      value: String,
  ): com.ACSlit.pro.inflater.IAttribute {
    return UiAttribute(namespace = namespace as NamespaceImpl?, name = name, value = value).apply {
      isRequired = UiAttribute.isRequired(view, this)
    }
  }
}
