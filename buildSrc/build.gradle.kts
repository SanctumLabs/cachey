import java.net.URI

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral {
        content {
            includeGroup("io.gitlab.arturbosch.detekt")
        }
    }
    maven {
        url = URI.create("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.21")
}
