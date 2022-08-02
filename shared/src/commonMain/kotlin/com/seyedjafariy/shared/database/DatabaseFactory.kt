package com.seyedjafariy.shared.database

import com.seyedjafariy.shuttle.database.MainDB
import com.squareup.sqldelight.db.SqlDriver

object DatabaseFactory {

    fun create(driver : SqlDriver) : MainDB =
        MainDB(driver)
}