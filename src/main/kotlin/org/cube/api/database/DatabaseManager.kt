package org.cube.api.database

import org.dizitart.no2.Nitrite
import org.cube.api.CubePlugin.Companion.logger

object DatabaseManager {

    private lateinit var database : Nitrite

    /**
     *  Initialise the [Nitrite] database.
     */
    fun init(path : String) {
        logger.info { "Initialising datastore" }
        database = Nitrite.builder()
            .compressed()
            .filePath(path)
            .openOrCreate()
        logger.info { "Successfully initialised datastore" }

    }

    /**
     *  Close the database connection
     */
    fun close() {
        database.close()
    }

    fun getCollection(name : String) = database.getCollection(name)

}
