plugins {
    id("java")
}

group = "dev.niuex.morecommand"
version = "0.0.1-Alpha"

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

tasks.register<Copy>("copyJarPluginsDirectory") {
    from(project.layout.buildDirectory.dir("libs"))
    into("/devserver/plugins/")

    // 只复制JAR文件
    include("*.jar")
}

// 配置构建任务完成后执行自定义任务
tasks.named("build") {
    finalizedBy("copyJarPluginsDirectory")
}