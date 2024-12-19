plugins {
    id("java")
    id("java-library")
}

group = "learn.it"
version = "0.0.0"

java {
    this.targetCompatibility = JavaVersion.VERSION_18;
    this.sourceCompatibility = JavaVersion.VERSION_18;
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    this.manifest {
        this.attributes["Implementation-Version"] = project.version;
    }
}