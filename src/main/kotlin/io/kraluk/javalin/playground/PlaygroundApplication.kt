package io.kraluk.javalin.playground

import com.fasterxml.jackson.databind.SerializationFeature
import io.javalin.Javalin
import io.javalin.json.JavalinJackson
import io.kraluk.javalin.playground.configuration.Modules
import io.kraluk.javalin.playground.configuration.Router
import io.kraluk.javalin.playground.shared.logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun main() {
  PlaygroundApplication()
    .create()
    .start(7070)
}

class PlaygroundApplication : KoinComponent {

  private val router: Router by inject()

  fun create(): Javalin {
    startKoin { loadKoinModules(Modules.applicationModules) }.also { log.info("Starting Koin...") }

    return Javalin
      .create {
        it.showJavalinBanner = false

        it.plugins.enableRouteOverview("/debug")
        // it.plugins.enableDevLogging()

        it.jsonMapper(jsonMapper())
      }
      .events {
        it.serverStarting { }
        it.serverStopping { stopKoin().also { log.info("Stopping Koin...") } }
      }
      .apply { router.register(this) }
  }

  private fun jsonMapper(): JavalinJackson =
    JavalinJackson()
      .updateMapper {
        it.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
      }

  companion object {
    private val log by logger()
  }
}
