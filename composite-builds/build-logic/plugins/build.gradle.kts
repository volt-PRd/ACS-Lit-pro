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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

repositories {
  google()
  gradlePluginPortal()
  mavenCentral()
}

tasks.withType(KotlinCompile::class.java) {
  compilerOptions {
    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
  }
}

dependencies {
  implementation(projects.buildLogic.common)
  implementation(projects.buildLogic.desugaring)
  implementation(projects.buildLogic.propertiesParser)

  implementation("com.android.tools.build:gradle:${libs.versions.agp.asProvider().get()}")

  implementation(libs.common.jkotlin)
  implementation(libs.common.antlr4)
  implementation(libs.google.gson)
  implementation(libs.google.java.format)
}

gradlePlugin {
  plugins {
    create("com.ACSlit.pro.build") {
      id = "com.ACSlit.pro.build"
      implementationClass = "com.ACSlit.pro.plugins.AndroidIDEPlugin"
    }
    create("com.ACSlit.pro.core-app") {
      id = "com.ACSlit.pro.core-app"
      implementationClass = "com.ACSlit.pro.plugins.AndroidIDECoreAppPlugin"
    }
    create("com.ACSlit.pro.build.propsparser") {
      id = "com.ACSlit.pro.build.propsparser"
      implementationClass = "com.ACSlit.pro.plugins.PropertiesParserPlugin"
    }
    create("com.ACSlit.pro.build.lexergenerator") {
      id = "com.ACSlit.pro.build.lexergenerator"
      implementationClass = "com.ACSlit.pro.plugins.LexerGeneratorPlugin"
    }
  }
}
