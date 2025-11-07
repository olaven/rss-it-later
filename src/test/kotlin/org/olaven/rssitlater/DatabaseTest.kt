package org.olaven.rssitlater

import org.junit.jupiter.api.BeforeAll
import org.olaven.rssitlater.database.entities.connectDatabase

abstract class DatabaseTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            connectDatabase()
        }
    }
}

