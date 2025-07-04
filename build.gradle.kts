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
    implementation("com.google.api-client:google-api-client:2.8.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.39.0")
    implementation("com.google.apis:google-api-services-drive:v3-rev20250511-2.0.0")
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
