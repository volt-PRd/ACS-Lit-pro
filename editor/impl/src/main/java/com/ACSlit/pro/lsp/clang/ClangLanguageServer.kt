/*
 *  This file is part of AndroidCodeStudio.
 *
 *  AndroidCodeStudio is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidCodeStudio is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidCodeStudio.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.ACSlit.pro.lsp.clang

import android.content.Context
import com.ACSlit.pro.lsp.api.ILanguageClient
import com.ACSlit.pro.lsp.api.ILanguageServer
import com.ACSlit.pro.lsp.api.IServerSettings
import com.ACSlit.pro.lsp.models.CodeFormatResult
import com.ACSlit.pro.lsp.models.CompletionParams
import com.ACSlit.pro.lsp.models.CompletionResult
import com.ACSlit.pro.lsp.models.DefinitionParams
import com.ACSlit.pro.lsp.models.DefinitionResult
import com.ACSlit.pro.lsp.models.DiagnosticResult
import com.ACSlit.pro.lsp.models.ExpandSelectionParams
import com.ACSlit.pro.lsp.models.FormatCodeParams
import com.ACSlit.pro.lsp.models.ReferenceParams
import com.ACSlit.pro.lsp.models.ReferenceResult
import com.ACSlit.pro.lsp.models.SignatureHelp
import com.ACSlit.pro.lsp.models.SignatureHelpParams
import com.ACSlit.pro.lsp.models.LSPFailure
import com.ACSlit.pro.models.Range
import com.ACSlit.pro.projects.IWorkspace
import java.nio.file.Path

/**
 * Stub implementation of ClangLanguageServer.
 * TODO: Implement C/C++ language server support in a future version.
 *
 * @author ACS Lit Pro
 */
class ClangLanguageServer(private val context: Context) : ILanguageServer {

  override val serverId: String = SERVER_ID

  override var client: ILanguageClient? = null
    private set

  companion object {
    const val SERVER_ID = "ide.lsp.clang"
  }

  override fun shutdown() {
    // TODO: Implement cleanup
  }

  override fun connectClient(client: ILanguageClient?) {
    this.client = client
  }

  override fun applySettings(settings: IServerSettings?) {
    // TODO: Apply settings
  }

  override fun setupWorkspace(workspace: IWorkspace) {
    // TODO: Setup workspace for C/C++ projects
  }

  override fun complete(params: CompletionParams?): CompletionResult {
    return CompletionResult.EMPTY
  }

  override suspend fun findReferences(params: ReferenceParams): ReferenceResult {
    return ReferenceResult(emptyList())
  }

  override suspend fun findDefinition(params: DefinitionParams): DefinitionResult {
    return DefinitionResult(emptyList())
  }

  override suspend fun expandSelection(params: ExpandSelectionParams): Range {
    return params.selection
  }

  override suspend fun signatureHelp(params: SignatureHelpParams): SignatureHelp {
    return SignatureHelp(emptyList(), -1, -1)
  }

  override suspend fun analyze(file: Path): DiagnosticResult {
    return DiagnosticResult.NO_UPDATE
  }

  override fun formatCode(params: FormatCodeParams?): CodeFormatResult {
    return CodeFormatResult(false, mutableListOf())
  }
}
