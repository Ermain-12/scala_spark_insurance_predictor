package org.ermain.scala.spark.insurance_cost

import org.apache.spark.sql.{Dataset, SparkSession}
import org.ermain.scala.spark.insurance_cost.InsuranceClaims.{Customer, schema}

object DataProcessing {

  def main(args: Array[String]): Unit = {
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

    // Here, we group the sum charges by gender to check whether this data-set is balanced
    println("Showing charges by sex")
    inputData.groupBy("sex")
      .sum("charges")
      .show()

    // Group the charges by region
    println("Showing charges by region....")
    inputData.groupBy("region")
      .sum("charges")
      .show()


    inputData.groupBy("children")
      .sum("charges")
      .show()

    val refinedInputDF = inputData
      .drop("age")

    refinedInputDF.show()
  }
}
