package io.kraluk.javalin.playground.hello

import io.kraluk.javalin.playground.hello.rest.HelloController
import org.koin.dsl.module

internal object HelloModule {

  internal val module = module {
    single { HelloController(get()) }
    single { HelloFacade() }
  }
}
