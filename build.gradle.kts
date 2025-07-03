plugins {
    id("java")
    id("application")
}

group = "com.xyphias"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

}

application.mainClass = "PGNUploader"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
        targetCompatibility = JavaVersion.VERSION_24
    }
}

tasks.compileJava {
    options.compilerArgs.add("--enable-preview")
    options.compilerArgs.add("-Xlint:-preview")
}

tasks.withType<Test>().configureEach {
    jvmArgs("--enable-preview")
}

tasks.run.configure { 
    jvmArgs("--enable-preview")
}
