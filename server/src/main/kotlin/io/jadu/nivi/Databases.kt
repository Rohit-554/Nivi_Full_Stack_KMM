package io.jadu.nivi

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.jadu.nivi.data.db.local.TransactionTable
import io.jadu.nivi.data.db.local.UserTable
import io.ktor.server.application.Application
import io.ktor.server.application.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun Application.configureDatabases() {
    // 1. Configure db
    val config = HikariConfig().apply {
        driverClassName = "org.postgresql.Driver"
        jdbcUrl = "jdbc:postgresql://localhost:5433/nivi_db"
        username = "myuser"      // From docker-compose
        password = "mypassword"  // From docker-compose
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    // 2. Connect Exposed to this pool
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    transaction {
        SchemaUtils.create(UserTable, TransactionTable)
    }




    log.info("✅ Database connected successfully to Docker Postgres!")
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    withContext(Dispatchers.IO) {
        suspendTransaction {
            block()
        }
    }