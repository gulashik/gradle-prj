// my-build-src/build.gradle.kts
plugins {
    `kotlin-dsl`   // включает поддержку Kotlin DSL и precompiled script plugins
}

dependencies {
    // Зависимости, которые нужны ВНУТРИ Convention Plugins.
    // Плагины подключаются как implementation, а не через plugins {}
    // Почему implementation, а не plugins {}?
    // Потому что нам нужен доступ к типам этих плагинов внутри Kotlin-кода Convention Plugins.
    // Блок plugins {} просто применяет плагин к текущему проекту, но не добавляет его API в classpath.
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.serialization.gradle.plugin)
    implementation(libs.spring.boot.gradle.plugin)
    implementation(libs.spring.dependency.management.plugin)
    implementation(libs.detekt.gradle.plugin)
}