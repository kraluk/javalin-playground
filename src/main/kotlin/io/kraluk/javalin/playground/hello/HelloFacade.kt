package io.kraluk.javalin.playground.hello

import io.kraluk.javalin.playground.shared.logger

class HelloFacade {

  fun sayHello(name: String): String =
    name
      .uppercase()
      .let { "Hello $it!" }
      .also { log.info("Returning '{}'", it) }

  companion object {
    private val log by logger()
  }
}
