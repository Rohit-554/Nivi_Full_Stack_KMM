package io.jadu.nivi.data.db.local

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date



object TransactionTable : Table("transactions") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id")
    val title = varchar("title", 128)
    val amount = double("amount")
    val necessity = varchar("necessity", 20)
    val type = varchar("type", 20)
    val category = varchar("category", 64)
    val date = date("transaction_date")

    override val primaryKey = PrimaryKey(id)
}