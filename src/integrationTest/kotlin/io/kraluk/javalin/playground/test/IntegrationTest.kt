package io.kraluk.javalin.playground.test

import io.javalin.Javalin
import io.kraluk.javalin.playground.PlaygroundApplication
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class IntegrationTest {

  protected lateinit var application: Javalin

  @BeforeAll
  internal fun setupUp() {
    application = PlaygroundApplication().create()
  }

  @AfterAll
  internal fun cleanUp() {
    application.close()
  }
}
