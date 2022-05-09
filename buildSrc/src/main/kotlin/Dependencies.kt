object Dependencies {

    object Kotlin {
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.KOTLIN}"
    }

    object Test {
        const val mockK = "io.mockk:mockk:1.11.0"

        object Jupiter {
            private const val version = "5.8.2"
            const val engine = "org.junit.jupiter:junit-jupiter-engine"
            const val vintageEngine = "org.junit.vintage:junit-vintage-engine"
            const val api = "org.junit.jupiter:junit-jupiter-api:$version"
        }

        object Spek {
            private const val version = "2.0.17"
            const val dslJvm = "org.spekframework.spek2:spek-dsl-jvm:$version"
            const val runnerJunit5 = "org.spekframework.spek2:spek-runner-junit5:$version"
        }

        object Kotest {
            private const val version = "5.2.2"
            const val runnerJunit5 = "io.kotest:kotest-runner-junit5:$version"
            const val assertionsCore = "io.kotest:kotest-assertions-core:$version"
            const val property = "io.kotest:kotest-property:$version"
        }
    }
}
