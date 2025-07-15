plugins {
	id("java")
	application
}

repositories {
	mavenLocal()
	maven {
		url = uri("https://repo.maven.apache.org/maven2/")
	}
}

group = "net.callisto"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_24
java.targetCompatibility = java.sourceCompatibility

application {
	mainClass = "net.callisto.jhmm.Main"
}

dependencies {
	val jacksonVersion = "2.18.2"
	val lanternaVersion = "3.1.3"
	
	// What is needed for Jackson to work: I don't know; just use all of them
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
	implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
	implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations
	implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")

	// TUI
	// https://mvnrepository.com/artifact/com.googlecode.lanterna/lanterna
	implementation("com.googlecode.lanterna:lanterna:$lanternaVersion")
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
	options.encoding = "UTF-8"
}

// copied from StackOverflow
// I hate this
tasks.register<Jar>("uberJar") {
	description = "generate a runnable jar"
	group = "build"
	
	// idk Gradle complained about a licence
	duplicatesStrategy = DuplicatesStrategy.WARN
	
	archiveClassifier = "uber"
	
	from(sourceSets.main.get().output)
	
	manifest {
		attributes["Main-Class"] = application.mainClass
	}
	
	dependsOn(configurations.runtimeClasspath)
	from({
		configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
	})
}
