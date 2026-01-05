package io.jadu.nivi.data.db.local

import io.jadu.nivi.data.model.Transaction
import io.jadu.nivi.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.time.LocalDate

class TransactionRepository {
    private fun rowToTransaction(row: ResultRow)  = Transaction (
        id = row[TransactionTable.id],
        userId = row[TransactionTable.userId],
        title = row[TransactionTable.title],
        amount = row[TransactionTable.amount],
        type = TransactionType.valueOf(row[TransactionTable.type]),
        necessity = Necessity.valueOf(row[TransactionTable.necessity]),
        category = row[TransactionTable.category],
        date = row[TransactionTable.date].toString()
    )

    suspend fun getAllTransactions(usedId: Int) : List<Transaction> = dbQuery {
        TransactionTable.selectAll().where { TransactionTable.userId eq usedId }.map(::rowToTransaction)
    }

    suspend fun addTransaction(t: Transaction) : Transaction = dbQuery {
        val insertValue = TransactionTable.insert {
            it[userId] = t.userId
            it[title] = t.title
            it[amount] = t.amount
            it[type] = t.type.name
            it[necessity] = t.necessity.name
            it[category] = t.category
            it[date] = LocalDate.parse(t.date)
        }
        t.copy(id = insertValue[TransactionTable.id])
    }
}