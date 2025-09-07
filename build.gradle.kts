plugins {
  alias(libs.plugins.kotlin)
  alias(libs.plugins.spotless)
}

group = "de.kfabi"

version = "1.0.0-SNAPSHOT"

repositories { mavenCentral() }

spotless {
  kotlin { ktfmt().googleStyle() }

  kotlinGradle { ktfmt().googleStyle() }
}
