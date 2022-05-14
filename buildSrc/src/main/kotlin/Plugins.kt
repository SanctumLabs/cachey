object Plugins {

    object Jetbrains {
        const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
        const val allOpen = "org.jetbrains.kotlin:kotlin-allopen:${Versions.KOTLIN}"
        const val noargs = "org.jetbrains.kotlin:kotlin-noarg:${Versions.KOTLIN}"
        const val kotlinx = "org.jetbrains.kotlinx"
    }

    object Jacoco {
        const val version = "0.8.8"
        const val plugin = "jacoco"
        const val core = "org.jacoco:org.jacoco.core:$version"
    }

    object Detekt {
        const val version = "1.20.0"
        const val core = "io.gitlab.arturbosch.detekt"
        const val gradle = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin"
        const val formatting = "io.gitlab.arturbosch.detekt:detekt-formatting"
    }

    object Maven {
        const val publish = "maven-publish"
    }
}
