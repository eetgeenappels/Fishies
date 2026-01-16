import org.gradle.internal.os.OperatingSystem
import java.nio.file.Files
import java.nio.file.Paths

plugins {
    kotlin("jvm") version "2.2.20"
}

group = "nl.eetgeenappels"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val serverJar = when {
    OperatingSystem.current().isLinux -> {
        val home = Paths.get(System.getProperty("user.home"))
        file("$home/.var/app/com.hypixel.HytaleLauncher/data/Hytale/install/release/package/game/latest/Server/HytaleServer.jar")
    }
    // not tested at all
    OperatingSystem.current().isWindows -> file("%appdata%\\Hytale\\install\\release\\package\\game\\latest")
    OperatingSystem.current().isMacOsX -> {
        val home = Paths.get(System.getProperty("user.home"))
        file("$home/Library/Application Support/Hytale/install/release/package/game/latest/Server/HytaleServer.jar")}
    else -> error("Unsupported OS")
}
val assetsZip = when {
    OperatingSystem.current().isLinux -> {
        val home = Paths.get(System.getProperty("user.home"))
        file("$home/.var/app/com.hypixel.HytaleLauncher/data/Hytale/install/release/package/game/latest/Assets.zip")
    }
    // not tested at all
    OperatingSystem.current().isWindows -> file("%appdata%\\Hytale\\install\\release\\package\\game\\latest\\Assets.zip")
    OperatingSystem.current().isMacOsX -> {
        val home = Paths.get(System.getProperty("user.home"))
        file("$home/Library/Application Support/Hytale/install/release/package/game/latest/Assets.zip")}
    else -> error("Unsupported OS")
}


dependencies {
    compileOnly(files(serverJar))
}

tasks.test {
    useJUnitPlatform()
}

// custom runServer task
tasks.register<JavaExec>("runServer") {
    // compile jar before running
    dependsOn("jar")

    group = "application"
    description = "Runs the Hytale server."

    classpath = files(serverJar)
    // Set JVM arguments if needed
    jvmArgs = listOf("-Xmx2G", "-Xms1G")

    // check if the run folder exists otherwise create it
    val runDir = file("run")
    if (!runDir.exists()) {
        runDir.mkdirs()
    }
    // do the same for run/mods
    val modsDir = file("run/mods")
    if (!modsDir.exists()) {
        modsDir.mkdirs()
    }

    // symlink the built mod jar to the run/mods folder
    val builtJar = tasks.jar.get().archiveFile.get().asFile
    val symlink = File(modsDir, builtJar.name)
    if (!symlink.exists()) {
        Files.createSymbolicLink(
            symlink.toPath(),
            builtJar.toPath()
        )
    }

    standardInput = System.`in`

    args(
        "--assets=${assetsZip.absolutePath}"
    )

    jvmArgs("-Xmx12G", "-Xms1G")

    // Set the working directory to the run folder
    workingDir = runDir
}