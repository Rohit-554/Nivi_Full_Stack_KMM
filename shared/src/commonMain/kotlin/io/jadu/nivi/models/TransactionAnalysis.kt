package io.jadu.nivi.models

import kotlinx.serialization.Serializable

/**
 * Result of transaction analysis providing insights and recommendations
 */
@Serializable
data class TransactionAnalysis(
    val summary: TransactionSummary,
    val insights: List<Insight>,
    val predictions: SpendingPrediction,
    val recommendations: List<Recommendation>,
    val anomalies: List<TransactionAnomaly>
)

@Serializable
data class TransactionSummary(
    val totalIncome: Double,
    val totalExpense: Double,
    val netBalance: Double,
    val averageExpense: Double,
    val categoryBreakdown: Map<String, Double>,
    val necessityBreakdown: Map<String, Double>,
    val transactionCount: Int
)

@Serializable
data class Insight(
    val type: InsightType,
    val title: String,
    val description: String,
    val severity: Severity,
    val affectedAmount: Double? = null,
    val affectedCategory: String? = null
)

@Serializable
enum class InsightType {
    SPENDING_PATTERN,
    CATEGORY_ALERT,
    BUDGET_WARNING,
    SAVING_OPPORTUNITY,
    UNUSUAL_ACTIVITY
}

@Serializable
enum class Severity {
    INFO,
    WARNING,
    CRITICAL
}

@Serializable
data class SpendingPrediction(
    val nextMonthExpense: Double,
    val confidence: Double,
    val trend: Trend
)

@Serializable
enum class Trend {
    INCREASING,
    STABLE,
    DECREASING
}

@Serializable
data class Recommendation(
    val title: String,
    val description: String,
    val potentialSavings: Double? = null,
    val priority: Priority
)

@Serializable
enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

@Serializable
data class TransactionAnomaly(
    val transaction: Transaction,
    val anomalyScore: Double,
    val reason: String
)

