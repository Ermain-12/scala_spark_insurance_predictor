package org.ermain.scala.spark.insurance_cost.regression

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.ermain.scala.spark.insurance_cost.{DataPipelineConstruction, SparkSessionCreate}
import org.ermain.scala.spark.insurance_cost.DataProcessing.trainingSet

object LogisticRegression {


  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSessionCreate.createSession()

    import spark.implicits._

    val numFolds = 10
    val MaxIter: Seq[Int] = Seq(100)
    val RegParam: Seq[Double] = Seq(1.0) // L2 regularization param, set 0.10 with L1 reguarization
    val Tol: Seq[Double] = Seq(1e-8)
    val ElasticNetParam: Seq[Double] = Seq(1.0) // Combination of L1 and L2

    // Create Logistic Regression model
    val logisticRegression = new LogisticRegression()
      .setLabelCol("label")
      .setFeaturesCol("features")



    // Chain indexers and tree in a Pipeline.
    val logRegressPipeLine = new Pipeline()
      .setStages(Array(
        DataPipelineConstruction.smoker_index,
        DataPipelineConstruction.region_index,
        DataPipelineConstruction.vectorFeatureAssembler,
        logisticRegression
      ))
    // Fit the data to the regression model
    val logisticRegressionModel = logRegressPipeLine
        .fit(trainingSet)


  }
}
