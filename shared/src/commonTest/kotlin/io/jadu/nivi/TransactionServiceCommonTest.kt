package io.jadu.nivi

import io.jadu.nivi.models.Necessity
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest
import io.jadu.nivi.models.Transaction
import io.jadu.nivi.models.TransactionType
import io.jadu.nivi.service.AuthService
import io.jadu.nivi.service.TransactionService
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Common multiplatform test for TransactionService
 * Tests the /transactions endpoints defined in TransactionalRoutes.kt
 * Works on Android, iOS, and JVM by using platform-specific HTTP client engines
 */
class TransactionServiceCommonTest {

    private val testClient = createTestHttpClient()
    private val authService = AuthService(client = testClient)

    /**
     * Helper function to register and login a user, returning the auth token
     */
    private suspend fun createAuthenticatedUser(): String {
        val randomId = Random.nextInt(0, 100000)
        val registerRequest = RegisterRequest(
            name = "Test User $randomId",
            email = "testuser$randomId@example.com",
            password = "password123"
        )

        val registerResult = authService.register(registerRequest)
        assertTrue(registerResult is NetworkResult.Success, "User registration should succeed")

        return registerResult.data.token
    }

    @Test
    fun testCreateTransactionReturnsSuccess() = runBlocking {
        // First, authenticate a user
        val token = createAuthenticatedUser()
        println("✅ User authenticated")

        // Create authenticated client and service
        val authenticatedClient = createAuthenticatedTestHttpClient(token)
        val transactionService = TransactionService(client = authenticatedClient)

        // Create a test transaction
        val transaction = Transaction(
            id = 0,
            userId = 1, // This will be overridden by the server based on JWT token
            title = "Test Expense",
            amount = 150.50,
            type = TransactionType.EXPENSE,
            necessity = Necessity.NEED,
            category = "Groceries",
            date = "2026-01-09"
        )

        val result = transactionService.createTransaction(transaction)

        assertTrue(result is NetworkResult.Success, "Expected Success but got $result")
        val savedTransaction = result.data
        assertTrue(savedTransaction.id > 0, "Transaction should have a valid ID")
        assertEquals(transaction.title, savedTransaction.title)
        assertEquals(transaction.amount, savedTransaction.amount)
        println("✅ TEST PASSED: Created transaction with ID: ${savedTransaction.id}")
    }

    @Test
    fun testGetTransactionsReturnsListOfTransactions() = runBlocking {
        // Authenticate a user
        val token = createAuthenticatedUser()
        println("✅ User authenticated")

        // Create authenticated client and service
        val authenticatedClient = createAuthenticatedTestHttpClient(token)
        val transactionService = TransactionService(client = authenticatedClient)

        // Create a few transactions first
        val transaction1 = Transaction(
            id = 0,
            userId = 1,
            title = "Salary",
            amount = 5000.0,
            type = TransactionType.INCOME,
            necessity = Necessity.NEED,
            category = "Salary",
            date = "2026-01-01"
        )

        val transaction2 = Transaction(
            id = 0,
            userId = 1,
            title = "Coffee",
            amount = 5.50,
            type = TransactionType.EXPENSE,
            necessity = Necessity.WANT,
            category = "Food",
            date = "2026-01-05"
        )

        val createResult1 = transactionService.createTransaction(transaction1)
        val createResult2 = transactionService.createTransaction(transaction2)

        assertTrue(createResult1 is NetworkResult.Success, "First transaction should be created")
        assertTrue(createResult2 is NetworkResult.Success, "Second transaction should be created")

        // Now get all transactions
        val result = transactionService.getTransactions()

        assertTrue(result is NetworkResult.Success, "Expected Success but got $result")
        val transactions = result.data
        assertTrue(transactions.isNotEmpty(), "Transaction list should not be empty")
        assertTrue(transactions.size >= 2, "Should have at least 2 transactions")
        println("✅ TEST PASSED: Retrieved ${transactions.size} transactions")
    }

    @Test
    fun testCreateIncomeTransactionReturnsSuccess() = runBlocking {
        val token = createAuthenticatedUser()
        val authenticatedClient = createAuthenticatedTestHttpClient(token)
        val transactionService = TransactionService(client = authenticatedClient)

        val incomeTransaction = Transaction(
            id = 0,
            userId = 1,
            title = "Freelance Project",
            amount = 1500.0,
            type = TransactionType.INCOME,
            necessity = Necessity.NEED,
            category = "Freelance",
            date = "2026-01-08"
        )

        val result = transactionService.createTransaction(incomeTransaction)

        assertTrue(result is NetworkResult.Success, "Expected Success but got $result")
        val savedTransaction = result.data
        assertEquals(TransactionType.INCOME, savedTransaction.type)
        assertEquals("Freelance Project", savedTransaction.title)
        println("✅ TEST PASSED: Created INCOME transaction with ID: ${savedTransaction.id}")
    }

    @Test
    fun testCreateInvestmentTransactionReturnsSuccess() = runBlocking {
        val token = createAuthenticatedUser()
        val authenticatedClient = createAuthenticatedTestHttpClient(token)
        val transactionService = TransactionService(client = authenticatedClient)

        val investmentTransaction = Transaction(
            id = 0,
            userId = 1,
            title = "Stock Purchase",
            amount = 2000.0,
            type = TransactionType.EXPENSE,
            necessity = Necessity.INVESTMENT,
            category = "Stocks",
            date = "2026-01-09"
        )

        val result = transactionService.createTransaction(investmentTransaction)

        assertTrue(result is NetworkResult.Success, "Expected Success but got $result")
        val savedTransaction = result.data
        assertEquals(Necessity.INVESTMENT, savedTransaction.necessity)
        assertEquals("Stock Purchase", savedTransaction.title)
        println("✅ TEST PASSED: Created INVESTMENT transaction with ID: ${savedTransaction.id}")
    }

    @Test
    fun testGetTransactionsForNewUserReturnsEmptyList() = runBlocking {
        // Create a brand new user who has no transactions
        val token = createAuthenticatedUser()
        println("✅ New user authenticated")

        val authenticatedClient = createAuthenticatedTestHttpClient(token)
        val transactionService = TransactionService(client = authenticatedClient)

        // Get transactions without creating any
        val result = transactionService.getTransactions()

        assertTrue(result is NetworkResult.Success, "Expected Success but got $result")
        val transactions = result.data
        assertTrue(transactions.isEmpty(), "New user should have no transactions")
        println("✅ TEST PASSED: New user has 0 transactions")
    }

    @Test
    fun testCreateMultipleTransactionsAndVerifyCount() = runBlocking {
        val token = createAuthenticatedUser()
        val authenticatedClient = createAuthenticatedTestHttpClient(token)
        val transactionService = TransactionService(client = authenticatedClient)

        // Create 5 different transactions
        val transactionsToCreate = listOf(
            Transaction(
                id = 0,
                userId = 1,
                title = "Rent",
                amount = 1200.0,
                type = TransactionType.EXPENSE,
                necessity = Necessity.NEED,
                category = "Housing",
                date = "2026-01-01"
            ),
            Transaction(
                id = 0,
                userId = 1,
                title = "Salary",
                amount = 4000.0,
                type = TransactionType.INCOME,
                necessity = Necessity.NEED,
                category = "Salary",
                date = "2026-01-01"
            ),
            Transaction(
                id = 0,
                userId = 1,
                title = "Netflix",
                amount = 15.99,
                type = TransactionType.EXPENSE,
                necessity = Necessity.WANT,
                category = "Entertainment",
                date = "2026-01-05"
            ),
            Transaction(
                id = 0,
                userId = 1,
                title = "ETF Investment",
                amount = 500.0,
                type = TransactionType.EXPENSE,
                necessity = Necessity.INVESTMENT,
                category = "Investments",
                date = "2026-01-07"
            ),
            Transaction(
                id = 0,
                userId = 1,
                title = "Bonus",
                amount = 1000.0,
                type = TransactionType.INCOME,
                necessity = Necessity.NEED,
                category = "Bonus",
                date = "2026-01-09"
            )
        )

        // Create all transactions
        transactionsToCreate.forEach { transaction ->
            val result = transactionService.createTransaction(transaction)
            assertTrue(result is NetworkResult.Success, "Transaction ${transaction.title} should be created")
        }

        // Verify count
        val result = transactionService.getTransactions()
        assertTrue(result is NetworkResult.Success, "Should retrieve transactions")
        assertEquals(5, result.data.size, "Should have exactly 5 transactions")
        println("✅ TEST PASSED: Created and verified 5 transactions")
    }
}

