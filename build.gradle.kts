plugins {
    application
    java
}

group = "codes.everybody"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
    workingDir = rootDir
}

application {
    mainClass.set("codes.everybody.Runner")
}

tasks.named<JavaExec>("run") {
    workingDir = rootDir
}

tasks.register<JavaExec>("newQuest") {
    group = "everybody codes"
    description = "Create a quest class, test, and example file. Usage: ./gradlew newQuest -Pquest=2"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("codes.everybody.tool.NewQuest")
    workingDir = rootDir
    val quest = providers.gradleProperty("quest").orNull
    if (!quest.isNullOrBlank()) {
        args(quest)
    }
    dependsOn(tasks.named("classes"))
}
