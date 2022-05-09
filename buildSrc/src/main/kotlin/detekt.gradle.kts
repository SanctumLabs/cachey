import io.gitlab.arturbosch.detekt.Detekt
// Detekt Linting
val analysisDir = file(projectDir)
val configFile = file("$rootDir/conf/linting/detekt.yml")

val kotlinFiles = "**/*.kt"
val kotlinScriptFiles = "**/*.kts"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

val detektFormat by tasks.registering(Detekt::class) {
    description = "Formats whole project."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(analysisDir)
    include(kotlinFiles)
    //include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    config.setFrom(listOf(configFile))
    reports {
        htmlReportFile
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektAll by tasks.registering(Detekt::class) {
    description = "Runs the whole project at once."
    parallel = true
    buildUponDefaultConfig = true
    setSource(analysisDir)
    include(kotlinFiles)
    //include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    config.setFrom(listOf(configFile))
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

tasks.getByName<DefaultTask>("check") {
    dependsOn.add(detektAll)
}
