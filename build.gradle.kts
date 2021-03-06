import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.net.URI

group = Meta.GROUP

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
    id(Plugins.Jacoco.plugin)
    kotlin("jvm")
    id(Plugins.Maven.publish)
}

apply(plugin = Plugins.Detekt.core)

repositories {
    mavenCentral()
    mavenLocal()

    maven {
        url = uri("https://jitpack.io")
        url = URI.create("https://plugins.gradle.org/m2/")
    }

    flatDir {
        dirs("libs")
    }
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_11
}

configure<JacocoPluginExtension> {
    toolVersion = Plugins.Jacoco.version
}

configure<PublishingExtension> {
    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/sanctumlabs/cachey")
            credentials {
                username = System.getenv("GH_RELEASE_ACTOR")
                password = System.getenv("GH_PACKAGE_TOKEN")
            }
        }

        // uncomment when we have a public repository
//        maven {
//            name = "OSSRH"
//            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
//            credentials {
//                username = System.getenv("MAVEN_USERNAME")
//                password = System.getenv("MAVEN_PASSWORD")
//            }
//        }
    }

    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "${JavaVersion.VERSION_11}"
        targetCompatibility = "${JavaVersion.VERSION_11}"
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "${JavaVersion.VERSION_11}"
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
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

dependencies {
    implementation(kotlin("stdlib-jdk8", Versions.KOTLIN))

    testImplementation(Dependencies.Kotlin.testJunit)
    testRuntimeOnly(Dependencies.Kotlin.reflect)
    testImplementation(Dependencies.Test.mockK)
    testImplementation(Dependencies.Test.Spek.dslJvm)
    testImplementation(Dependencies.Test.Spek.runnerJunit5)
    testImplementation(Dependencies.Test.Jupiter.api)
    testImplementation(Dependencies.Test.Jupiter.engine)
    testImplementation(Dependencies.Test.Jupiter.vintageEngine)
}
