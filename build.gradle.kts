plugins {
    java
    application
}

group = "borealisbot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.13") {
        exclude(module = "opus-java")
    }
    implementation("org.tinylog:tinylog-api:2.5.0")
    implementation("org.tinylog:tinylog-impl:2.5.0")
    implementation("org.tinylog:slf4j-tinylog:2.5.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("commons-io:commons-io:2.11.0")
}

application {
    mainClass.set("org.oakbrics.borealis.Main")
}