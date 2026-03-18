// some-service/build.gradle.kts
plugins {
    id("java")
    id("myco.spring-boot-app") // применяем Convention Plugin
}

group = "ru.gulash.home"
version = "unspecified"

dependencies {
    // Демонстрация использования Version Catalog (gradle/libs.versions.toml)
    // bom
    implementation(platform(libs.spring.boot.bom))
    // dependencies
    implementation(libs.spring.boot.starter.actuator)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.junit)

    // Оставим и junit-bom для примера
    testImplementation(platform("org.junit:junit-bom"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}