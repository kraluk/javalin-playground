package io.kraluk.javalin.playground.configuration

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.kraluk.javalin.playground.hello.rest.HelloController
import org.koin.core.component.KoinComponent

internal class Router(
  private val helloController: HelloController,
) : KoinComponent {

  fun register(app: Javalin): Javalin =
    app
      .routes {
        path("hello") {
          path("{name}") {
            get { helloController.hello(it) }
          }
        }
      }
}
