// my-build-src/src/main/kotlin/myco.spring-boot-app.gradle.kts
// ИМЯ файла .gradle.kts - это Project плагин
// имя будет как имя файла минус .gradle.kts т.е. plugins { id("myco.spring-boot-app") }
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("myco.code-quality")               // применяем другой наш convention-плагин
}

// --- Java toolchain ---
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// --- Kotlin настройки ---
kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xemit-jvm-type-annotations"
        )
    }
}

springBoot {
    buildInfo()    // /actuator/info получит данные сборки: версию, время, git-commit
}

// Ссылаемся на libs.versions.toml
val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
dependencies {
    // версия из libs
    // libs.findVersion("jacoco").get().requiredVersion
    // libs.findLibrary("spring-boot-bom").get()
    implementation(platform(libs.findLibrary("spring-boot-bom").get()))

    implementation(libs.findLibrary("spring-boot-starter-actuator").get())
    testImplementation(libs.findLibrary("spring-boot-starter-test").get())
    testImplementation(libs.findLibrary("spring-boot-testcontainers").get())
    testImplementation(libs.findLibrary("testcontainers-junit").get())
}

// --- Тесты ---
tasks.withType<Test> {
    useJUnitPlatform()
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)

    // Показывать вывод тестов при падении
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = TestExceptionFormat.FULL
    }
}

// --- Jar ---
tasks.named<Jar>("jar") {
    // Spring Boot создаёт fat jar через bootJar, обычный jar не нужен
    enabled = false
}
