package io.jadu.nivi.ai

/* This module is for practicing more SMILE */

import org.apache.commons.csv.CSVFormat
import smile.anomaly.IsolationForest
import smile.data.formula.Formula
import smile.io.Read

fun main() {
    val format = CSVFormat.DEFAULT.builder()
        .setHeader()
        .setSkipHeaderRecord(true)
        .get()

    val irisData = Read.csv("server/src/main/kotlin/io/jadu/nivi/ai/iris_d.csv", format)

    val formula = Formula.lhs("class")
    val x = formula.x(irisData).toArray()

    println("Training Isolation Forest...")

    val model = IsolationForest.fit(x)

    val normalFlower = doubleArrayOf(5.1, 3.5, 1.4, 0.2)
    val mutantFlower = doubleArrayOf(15.0, 10.0, 15.0, 10.0)

    val normalScore = model.score(normalFlower)
    val mutantScore = model.score(mutantFlower)

    println("\n--- Results ---")

    println("Normal Flower Score: $normalScore")
    if (normalScore > 0.5) println("-> Verdict: ANOMALY") else println("-> Verdict: Normal")

    println("\nMutant Flower Score: $mutantScore")
    if (mutantScore > 0.5) println("-> Verdict: ANOMALY") else println("-> Verdict: Normal")
}