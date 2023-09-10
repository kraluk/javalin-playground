import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
  idea
  jacoco
  application

  alias(libs.plugins.kotlin.jvm)

  alias(libs.plugins.ktlint)
  alias(libs.plugins.detekt)
  alias(libs.plugins.versions)
}

repositories {
  mavenCentral()
}

val integrationTestImplementation: Configuration = configurations.create("integrationTestImplementation")
  .extendsFrom(configurations.testImplementation.get())
val integrationTestRuntimeOnly: Configuration = configurations.create("integrationTestRuntimeOnly")
  .extendsFrom(configurations.testRuntimeOnly.get())

dependencies {
  implementation(kotlin("reflect"))

  implementation("io.javalin:javalin:${libs.versions.javalin.get()}")

  implementation("io.insert-koin:koin-core:${libs.versions.koin.get()}")
  implementation("io.insert-koin:koin-core-jvm:${libs.versions.koin.get()}")

  implementation("com.fasterxml.jackson.core:jackson-databind:${libs.versions.jackson.get()}")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${libs.versions.jackson.get()}")

  implementation("org.slf4j:slf4j-api:${libs.versions.slf4j.get()}")
  implementation("ch.qos.logback:logback-classic:${libs.versions.logback.get()}")

  testImplementation(kotlin("test"))
  testImplementation("org.junit.jupiter:junit-jupiter-engine:${testLibs.versions.junit.get()}")
  testImplementation("org.amshove.kluent:kluent:${testLibs.versions.kluent.get()}")

  integrationTestImplementation("io.javalin:javalin-testtools:${libs.versions.javalin.get()}")
}

application {
  mainClass.set("io.kraluk.javalin.playground.PlaygroundApplication")
}

tasks.test {
  useJUnitPlatform()
  defaultCharacterEncoding = "UTF-8"

  testLogging {
    events(
      TestLogEvent.STARTED,
      TestLogEvent.PASSED,
      TestLogEvent.FAILED,
      TestLogEvent.SKIPPED,
    )
    exceptionFormat = TestExceptionFormat.FULL
    showExceptions = true
    showCauses = true
    showStackTraces = true
    showStandardStreams = true
  }

  maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2)
    .takeIf { it > 0 }
    ?: 1
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-java-parameters", "-Xjsr305=strict")
    jvmTarget = JavaVersion.VERSION_17.majorVersion
  }
}

sourceSets {
  create("integrationTest") {
    compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
    runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
  }
}

val integrationTest = tasks.register<Test>("integrationTest") {
  description = "Runs integration tests."
  group = "verification"
  useJUnitPlatform()
  defaultCharacterEncoding = "UTF-8"

  testLogging {
    events(
      TestLogEvent.STARTED,
      TestLogEvent.PASSED,
      TestLogEvent.FAILED,
      TestLogEvent.SKIPPED,
    )
    exceptionFormat = TestExceptionFormat.FULL
    showExceptions = true
    showCauses = true
    showStackTraces = true
    showStandardStreams = true
  }

  testClassesDirs = sourceSets["integrationTest"].output.classesDirs
  classpath = sourceSets["integrationTest"].runtimeClasspath
  shouldRunAfter(tasks.test)

  finalizedBy(tasks.jacocoTestReport)
}

// customize if needed: https://docs.gradle.org/current/userguide/jacoco_plugin.html
// reports are in build/reports/jacoco as index.html
tasks.jacocoTestReport {
  dependsOn(integrationTest) // all tests are required to run before generating the report
}

tasks.check { dependsOn(integrationTest) }

tasks.distTar {
  enabled = false
}

ktlint {
  version.set(toolLibs.versions.ktlint.get())
  android.set(false)
  verbose.set(true)
  outputToConsole.set(true)
  coloredOutput.set(true)

  filter {
    exclude { entry ->
      entry.file.toString().contains("generated")
    }
  }
  reporters {
    reporter(ReporterType.HTML)
  }
}

detekt {
  buildUponDefaultConfig = true
  allRules = false
  config.setFrom("$projectDir/detekt-config.yml")
}

tasks.withType<Detekt>().configureEach {
  reports {
    html.required.set(true)
  }
}

project.afterEvaluate { // https://github.com/detekt/detekt/issues/6198
  configurations["detekt"].resolutionStrategy.eachDependency {
    if (requested.group == "org.jetbrains.kotlin") {
      useVersion("1.9.0")
    }
  }
}
