// (root) settings.gradle.kts
/*
    Порядок важен
    pluginManagement
    plugins { ... }
    rootProject.name
    include(...)
*/
pluginManagement {
    // Подключаем my-build-src как источник плагинов.
    // Gradle найдёт плагины из my-build-src РАНЬШЕ, чем из Gradle Plugin Portal.
    includeBuild("my-build-src")

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
    }

    // Version Catalog из gradle/ доступен и в my-build-src, и во всех subprojects
    // для gradle 7.4+ не нужно
//    versionCatalogs {
//        create("libs") {
//            from(files("gradle/libs.versions.toml"))
//        }
//    }
}

rootProject.name = "convention-plugin-prj"

include("some-service")