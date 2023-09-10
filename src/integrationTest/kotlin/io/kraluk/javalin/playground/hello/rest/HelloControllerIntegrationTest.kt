package io.kraluk.javalin.playground.hello.rest

import io.javalin.testtools.JavalinTest
import io.kraluk.javalin.playground.test.IntegrationTest
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should not be null`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
internal class HelloControllerIntegrationTest : IntegrationTest() {

  @Test
  internal fun `should say hello to the given person`() =
    JavalinTest.test(application) { _, client ->
      val response = client.get("/hello/james")

      response.code `should be equal to` 200
      response.headers `should contain` ("Content-Type" to "text/plain")
      response.body.`should not be null`().string() `should be equal to` "Hello JAMES!"
    }
}
