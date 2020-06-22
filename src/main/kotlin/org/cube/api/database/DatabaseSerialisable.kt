package org.cube.api.database

import org.dizitart.no2.Document

interface DatabaseSerialisable {
    fun asDocument() : Document
}