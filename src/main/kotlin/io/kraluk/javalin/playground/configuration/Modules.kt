package io.kraluk.javalin.playground.configuration

import io.kraluk.javalin.playground.PlaygroundApplication
import io.kraluk.javalin.playground.hello.HelloModule
import org.koin.dsl.module

internal object Modules {

  private val configuration = module {
    single { PlaygroundApplication() }
    single { Router(get()) }
  }

  internal val applicationModules = listOf(
    configuration,
    HelloModule.module,
  )
}
