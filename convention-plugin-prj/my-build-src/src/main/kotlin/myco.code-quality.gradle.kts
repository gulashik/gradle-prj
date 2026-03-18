// my-build-src/src/main/kotlin/myco.code-quality.gradle.kts
// ИМЯ файла .gradle.kts - это Project плагин
// имя будет как имя файла минус .gradle.kts т.е. plugins { id("myco.code-quality") }
plugins {
    id("io.gitlab.arturbosch.detekt")
    id("jacoco")
}

detekt {
    config.setFrom(rootProject.file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    allRules = false
}

// Переменная для ссылки на внешний libs.versions.toml
val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

jacoco {
    // версия из libs
    // libs.findVersion("jacoco").get().requiredVersion
    // libs.findLibrary("spring-boot-bom").get()
    toolVersion = libs.findVersion("jacoco").get().requiredVersion
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"))

    reports {
        xml.required = true          // для CI (SonarQube, Codecov)
        html.required = true         // для локального просмотра
        csv.required = false
    }
}

// Порог покрытия — сборка упадёт, если упадёт ниже
tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    violationRules {
        rule {
            limit {
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

tasks.named("check") {
    dependsOn(tasks.named("jacocoTestCoverageVerification"))
}