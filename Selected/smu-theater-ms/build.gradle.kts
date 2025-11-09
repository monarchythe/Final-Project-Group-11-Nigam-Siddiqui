plugins {
    java
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}

subprojects {
    apply(plugin = "java")

    repositories { mavenCentral() }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    }

    tasks.test { useJUnitPlatform() }
}
