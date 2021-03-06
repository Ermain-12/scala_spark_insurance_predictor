package org.ermain.scala.spark.insurance_cost

import org.apache.spark.sql.{Dataset, SparkSession}
import org.ermain.scala.spark.insurance_cost.InsuranceClaims.{Customer, schema}

object DataProcessing {

    // Create the spark session
    val spark: SparkSession = SparkSessionCreate.createSession()

    import spark.implicits._
    val inputData: Dataset[Customer] = spark.read
      .option("inferSchema", "false")
      .format("com.databricks.spark.csv")
      .schema(schema)
      .load("data/insurance.csv")
      .as[Customer]
      .cache()


    inputData.show(10)

    /*
    Here, we group the sum charges by gender to check whether this data-set is balanced
                      +------+-----------------+
                      |   sex|     sum(charges)|
                      +------+-----------------+
                      |  null|             null|
                      |female|8321061.194618994|
                      |  male|9434763.796139995|
                      +------+-----------------+
     */
    println("Showing charges by sex")
    inputData.groupBy("sex")
      .sum("label")
      .show()

    /*
                  Group the charges by region:
                  +---------+------------------+
                  |   region|      sum(charges)|
                  +---------+------------------+
                  |northwest|4035711.9965399993|
                  |southeast| 5363689.763290002|
                  |northeast| 4343668.583308999|
                  |     null|              null|
                  |southwest| 4012754.647620001|
                  +---------+------------------+
     */
    // Group the charges by region

    println("Showing charges by region....")
    inputData.groupBy("region")
      .sum("label")
      .show()


    /*
                +--------+------------------+
                |children|      sum(charges)|
                +--------+------------------+
                |    null|              null|
                |       1| 4124899.673449997|
                |       3| 2410784.983589999|
                |       5|      158148.63445|
                |       4|346266.40777999995|
                |       2| 3617655.296149999|
                |       0| 7098069.995338997|
                +--------+------------------+
     */
    inputData.groupBy("children")
      .sum("label")
      .show()

    /*
    // Determine the kind of relationship between the individual being a smoker
    // and the claims made
                  +------+-----------------+
                  |smoker|     sum(charges)|
                  +------+-----------------+
                  |  null|             null|
                  |    no|8974061.468918996|
                  |   yes|    8781763.52184|
                  +------+-----------------+
            There, does not seem to be any relation, as there is some balance when comparing
            the claim amounts of non-smokers and smoker
    */
    inputData.groupBy("smoker")
      .sum("label")
      .show()


    inputData.groupBy("bmi")
      .sum("label")
      .show()

    val refinedInputDF = inputData
      .drop("age")
      .drop("sex")


    refinedInputDF.show()

    println("Splitting Dataset into training and testing set....")

    // Perform the split on the data-set
    val seed: Long = 8657L
    val splits = refinedInputDF.randomSplit(Array(0.85, 0.15), seed)

    // Training Set
    val trainingSet = splits(0)

    //trainingSet.show()

    // Create the testing set
    val testingSet = splits(1)
    //testingSet.show()
}
