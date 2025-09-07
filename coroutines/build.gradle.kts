plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.power.assert)
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":core"))
    implementation(platform(libs.mongodb.driver.bom))
    api(libs.mongodb.driver.kotlin.coroutine)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

kotlin { jvmToolchain(21) }

tasks.test {
    useJUnitPlatform()
}