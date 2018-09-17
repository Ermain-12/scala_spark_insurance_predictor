package org.ermain.scala.spark.insurance_cost

import java.io.File

import org.apache.spark.sql.SparkSession

object SparkSessionCreate {

  def main(args: Array[String]): Unit = {
    println("Scala Spark App....")
  }

  val sparkWareHouseLocation: String = new File("spark-warehouse").getAbsolutePath
  def createSession(appName: String = "isurance-pipeline"): SparkSession = {

    val spark = SparkSession.builder()
      .config("spark.sql.warehouse.dir", sparkWareHouseLocation)
      .master("local[*]")
      .appName(appName)
      .getOrCreate()

    spark
  }
}
