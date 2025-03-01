plugins {
  kotlin("jvm") version "2.1.10"
  kotlin("plugin.power-assert") version "2.1.10"
  id("com.diffplug.spotless") version "7.0.2"
}

group = "de.kfabi"

version = "1.0.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
  implementation("org.mongodb:mongodb-driver-core:5.3.1")
  testImplementation(kotlin("test"))
}

spotless {
  kotlin { ktfmt().googleStyle() }

  kotlinGradle { ktfmt().googleStyle() }
}

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain(21) }
