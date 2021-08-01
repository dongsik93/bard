package com.example.bard.repository

import com.example.bard.db.DsDataBase

class DsRepository private constructor(
    private val db: DsDataBase
) {



    companion object {

        @Volatile
        private var INSTANCE: DsRepository? = null

        fun getInstance(database: DsDataBase): DsRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DsRepository(database).also { INSTANCE = it }
            }
    }
}