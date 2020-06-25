package org.cube.api.database

import org.dizitart.no2.Nitrite


object DatabaseManager {

    private lateinit var database : Nitrite

    /**
     *  Initialise the [Nitrite] database.
     */
    fun init(path : String) {
        database = Nitrite.builder()
            .compressed()
            .filePath(path)
            .openOrCreate()

    }

    /**
     *  Close the database connection
     */
    fun close() {
        database.close()
    }

    fun getCollection(name : String) = database.getCollection(name)

}
