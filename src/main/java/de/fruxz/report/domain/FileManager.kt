package de.fruxz.report.domain

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.FileNotFoundException

class FileManager(path: String) {

    val file = File("plugins/NeruxReport", path)
    val loader = YamlConfiguration.loadConfiguration(file)

    fun load() {
        try {
            loader.load(file)
        } catch (e: FileNotFoundException) {
            loader.set("installed", true)
            save()
        }
    }

    fun save() {
        loader.save(file)
    }

    fun set(path: String, value: Any?) {
        load()
        loader.set(path, value)
        save()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(path: String): T? {
        load()
        return loader.get(path) as T?
    }

}