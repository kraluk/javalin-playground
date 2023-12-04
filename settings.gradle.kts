rootProject.name = "javalin-playground"

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      plugin("kotlin.jvm", "org.jetbrains.kotlin.jvm").version("1.9.21")

      plugin("ktlint", "org.jlleitschuh.gradle.ktlint").version("12.0.2")
      plugin("detekt", "io.gitlab.arturbosch.detekt").version("1.23.4")
      plugin("versions", "com.github.ben-manes.versions").version("0.50.0")

      version("javalin", "5.6.3")
      version("koin", "3.5.0")
      version("logback", "1.4.14")
      version("slf4j", "2.0.9")
      version("jackson", "2.16.0")
    }
    create("testLibs") {
      version("junit", "5.10.1")
      version("kluent", "1.73")
    }
    create("toolLibs") {
      version("ktlint", "0.50.0")
    }
  }
}
