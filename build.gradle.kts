import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.net.URI

buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath(Plugins.Jetbrains.gradle)
        classpath(Plugins.Jetbrains.allOpen)
        classpath(Plugins.Jetbrains.noargs)
        classpath(Plugins.Jacoco.core)
    }
}

plugins {
    detekt
    githooks
}

repositories {
    mavenCentral()
    maven {
        url = URI.create("https://jitpack.io")
    }
}

allprojects {
    group = Meta.GROUP

    repositories {
        mavenCentral()
        mavenLocal()
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
    }

    configure<JacocoPluginExtension> {
        toolVersion = Plugins.Jacoco.version
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }
    }
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://jitpack.io")
        }
        flatDir {
            dirs("libs")
        }
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
            testLogging {
                // set options for log level LIFECYCLE
                events = setOf(
                    TestLogEvent.FAILED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED
                )
                exceptionFormat = TestExceptionFormat.FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
            }

            reports {
                html.required.set(true)
                html.outputLocation.set(file(project.rootDir.resolve("$buildDir/reports/tests")))
            }

            configure<JacocoTaskExtension> {
                isEnabled = true
                classDumpDir = layout.buildDirectory.dir("jacoco/classpathdumps").get().asFile
            }
        }

        jacocoTestReport {
            reports {
                xml.required.set(true)
                html.required.set(true)
                csv.required.set(false)
                html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
                html.outputLocation.set(
                    file(project.rootDir.resolve("$buildDir/reports/coverage"))
                )
            }
            dependsOn("test")
        }
    }
}
