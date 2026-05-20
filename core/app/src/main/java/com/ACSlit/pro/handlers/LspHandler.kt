package com.ACSlit.pro.handlers

// import com.ACSlit.pro.lsp.clang.ClangLanguageServer || planned for v..03
import android.content.Context
import com.ACSlit.pro.lsp.api.ILanguageClient
import com.ACSlit.pro.lsp.api.ILanguageServerRegistry
import com.ACSlit.pro.lsp.java.JavaLanguageServer
import com.ACSlit.pro.lsp.clang.ClangLanguageServer
import com.ACSlit.pro.lsp.kotlin.KotlinLanguageServer
import com.ACSlit.pro.lsp.xml.XMLLanguageServer

/** @author Akash Yadav */
object LspHandler {

  fun registerLanguageServers(context: Context) {
    ILanguageServerRegistry.getDefault().apply {
      getServer(JavaLanguageServer.SERVER_ID) ?: register(JavaLanguageServer())
      getServer(KotlinLanguageServer.SERVER_ID) ?: register(KotlinLanguageServer(context))
      getServer(ClangLanguageServer.SERVER_ID) ?: register(ClangLanguageServer(context))
      getServer(XMLLanguageServer.SERVER_ID) ?: register(XMLLanguageServer())
    }
  }

  fun connectClient(client: ILanguageClient) {
    ILanguageServerRegistry.getDefault().connectClient(client)
  }

  fun destroyLanguageServers(isConfigurationChange: Boolean) {
    if (isConfigurationChange) {
      return
    }
    ILanguageServerRegistry.getDefault().destroy()
  }
}
