package io.kraluk.javalin.playground.hello.rest

import io.javalin.http.Context
import io.kraluk.javalin.playground.hello.HelloFacade
import io.kraluk.javalin.playground.shared.logger

class HelloController(private val facade: HelloFacade) {

  fun hello(context: Context) =
    context.pathParam("name")
      .also { log.info("Saying hello to '{}'", it) }
      .let { facade.sayHello(name = it) }
      .apply { context.result(this) }

  companion object {
    private val log by logger()
  }
}
