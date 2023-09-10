package io.kraluk.javalin.playground

import org.amshove.kluent.`should not be null`
import org.junit.jupiter.api.Test

internal class PlaygroundApplicationTest {

  @Test
  internal fun `should not be null`() {
    // Given
    val application = PlaygroundApplication()

    // When
    val created = application.create()

    // Then
    created.`should not be null`()
  }
}
