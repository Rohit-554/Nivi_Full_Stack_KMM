package io.jadu.nivi.ai

import org.apache.commons.csv.CSVFormat
import smile.classification.RandomForest
import smile.data.DataFrame
import smile.data.formula.Formula
import smile.data.measure.NominalScale
import smile.io.Read

fun main() {
    val format = CSVFormat.DEFAULT.builder()
        .setHeader()
        .setSkipHeaderRecord(true)
        .get()

    val irisData = Read.csv(
        "server/src/main/kotlin/io/jadu/nivi/ai/iris_d.csv",
        format
    ).factorize("class") // 0 1 2 3

    println("Data Structure:")
    println(irisData.schema())
    println("First few rows:")
    println(irisData.head(3))

    // Predict "class" using all other columns
    val formula = Formula.lhs("class") //y

    println("\n--- Training Random Forest ---")

    val model = RandomForest.fit(
        formula,
        irisData,
        RandomForest.Options(100) // 100 trees
    )

    println("Model trained successfully!")

    val featureNames = irisData.names().filter { it != "class" }.toTypedArray()

    val sample = DataFrame.of(
        arrayOf(
            doubleArrayOf(7.0, 3.5, 5.0, 1.5)
        ),
        *featureNames
    )


    val prediction = model.predict(sample)[0]
    println("Predicted class index: $prediction")

    val classField = irisData.schema().field("class")
    val scale = classField.measure as NominalScale

    val labels = scale.levels()     //["Iris-setosa", "Iris-versicolor", "Iris-virginica"]
    println("Predicted label: ${labels[prediction]}")
}

