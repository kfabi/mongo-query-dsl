plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.power.assert)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.mongodb.driver.bom))
    implementation(libs.mongodb.driver.core)
}

kotlin { jvmToolchain(21) }

tasks.test {
    useJUnitPlatform()
}