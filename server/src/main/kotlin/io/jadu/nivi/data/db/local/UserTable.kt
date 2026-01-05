package io.jadu.nivi.data.db.local

import org.jetbrains.exposed.sql.Table

object UserTable : Table("users") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 128).uniqueIndex()
    val password = varchar("password_hash", 256)
    val name = varchar("name", 64)

    override val primaryKey = PrimaryKey(id)
}