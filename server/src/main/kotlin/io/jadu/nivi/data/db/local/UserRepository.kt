package io.jadu.nivi.data.db.local

import io.jadu.nivi.data.model.RegisterRequest
import io.jadu.nivi.data.model.User
import io.jadu.nivi.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.mindrot.jbcrypt.BCrypt

class UserRepository {
    private fun rowToUser(row: ResultRow): User {
        return User(
            id = row[UserTable.id],
            email = row[UserTable.email],
            name = row[UserTable.name],
            passwordHash = row[UserTable.password]
        )
    }

    suspend fun createUser(request: RegisterRequest): User? = dbQuery {
        val hashPass = BCrypt.hashpw(request.password, BCrypt.gensalt())

        val user = UserTable.insert {
            it[email] = request.email
            it[name] = request.name
            it[password] = hashPass
        }

        user.resultedValues?.singleOrNull()?.let(::rowToUser)
    }

    suspend fun findUserByEmail(email: String): User? = dbQuery {
        UserTable.selectAll().where { UserTable.email eq email }
            .map(::rowToUser)
            .singleOrNull()
    }
}