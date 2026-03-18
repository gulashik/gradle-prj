// my-build-src/settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    // Повторно подключаем тот же Version Catalog —
    // build-logic — это отдельный проект и не наследует настройки родителя
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "my-build-src"