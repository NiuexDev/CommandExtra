plugins {
    id("java")
}

group = "dev.niuex.morecommand"
version = "0.0.1-alpha"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.processResources {
    // 指定哪些占位符需要替换
    filteringCharset = "UTF-8"
    inputs.property("projectVersion", project.version)
    expand("projectVersion" to project.version)
}

try {
    apply(from = "copy.gradle.kts")
} catch (e: Exception) {
    println("已忽略执行copy.gradle.kts配置")
}
