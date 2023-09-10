rootProject.name = "javalin-playground"

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      plugin("kotlin.jvm", "org.jetbrains.kotlin.jvm").version("1.9.10")

      plugin("ktlint", "org.jlleitschuh.gradle.ktlint").version("11.5.1")
      plugin("detekt", "io.gitlab.arturbosch.detekt").version("1.23.1")
      plugin("versions", "com.github.ben-manes.versions").version("0.48.0")

      version("javalin", "5.6.2")
      version("koin", "3.4.3")
      version("logback", "1.4.11")
      version("slf4j", "2.0.9")
      version("jackson", "2.15.2")
    }
    create("testLibs") {
      version("junit", "5.10.0")
      version("kluent", "1.73")
    }
    create("toolLibs") {
      version("ktlint", "0.50.0")
    }
  }
}
