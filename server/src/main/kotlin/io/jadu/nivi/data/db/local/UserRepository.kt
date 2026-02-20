package io.jadu.nivi.data.db.local

import io.jadu.nivi.dbQuery
import io.jadu.nivi.models.RegisterRequest
import io.jadu.nivi.models.User
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
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