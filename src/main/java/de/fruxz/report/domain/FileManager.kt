package de.fruxz.report.domain

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.FileNotFoundException

/**
 * This class helps to easily manage files, its values
 * and its structure
 * @author Fruxz
 * @since v1.0
 */
class FileManager(path: String) {

    val file = File("plugins/NeruxReport", path)
    val loader = YamlConfiguration.loadConfiguration(file)

    /**
     * Loads the current file
     * @author Fruxz
     * @since v1.0
     */
    fun load() {
        try {
            loader.load(file)
        } catch (e: FileNotFoundException) {
            loader.set("installed", true)
            save()
        }
    }

    /**
     * Saves the current file and its changed values
     * @author Fruxz
     * @since v1.0
     */
    fun save() {
        loader.save(file)
    }

    /**
     * Sets the current object behind [path] to [value]
     * @author Fruxz
     * @since v1.0
     */
    fun set(path: String, value: Any?) {
        load()
        loader.set(path, value)
        save()
    }

    /**
     * Returns the object behind [path]
     * @author Fruxz
     * @since v1.0
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> get(path: String): T? {
        load()
        return loader.get(path) as T?
    }

}