plugins { application }

dependencies {
    implementation("io.javalin:javalin:5.6.2")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")

}

application {
    mainClass.set("com.smu.event.EventApp")
}
