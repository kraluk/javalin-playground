package io.kraluk.javalin.playground.hello

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

internal class HelloFacadeTest {

  private val facade: HelloFacade = HelloFacade()

  @Test
  internal fun `should say hello`() {
    // Given
    val name = "Luke"

    // When
    val result = facade.sayHello(name)

    // Then
    result `should be equal to` "Hello LUKE!"
  }
}
