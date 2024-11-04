/*
 * This file is part of Airbrush
 *
 * Copyright (c) 2023 Airbrush Team
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package gg.airbrush.server.plugins

import java.io.File
import java.nio.file.Path

abstract class Plugin {
    lateinit var info: PluginInfo
    lateinit var loader: PluginClassLoader
    var isSetup = false

    lateinit var dataFolder: File

    abstract fun setup()
    abstract fun teardown()

    fun loadResource(resource: String, directory: String = dataFolder.relativeTo(pluginsFolder).path): Path {
        val file = pluginsFolder.resolve(directory).resolve(resource)
        file.parentFile.mkdirs()

        val stream = this::class.java.getResourceAsStream("/$resource")
            ?: return file.toPath()

        stream.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return file.toPath()
    }
}
