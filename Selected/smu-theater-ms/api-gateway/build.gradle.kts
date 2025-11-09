plugins {
    application
}

dependencies {
    implementation("io.javalin:javalin:5.6.2")
    implementation("org.slf4j:slf4j-simple:2.0.13") // tiny logger so Javalin prints
}

application {
    mainClass.set("com.smu.gateway.App")
}
