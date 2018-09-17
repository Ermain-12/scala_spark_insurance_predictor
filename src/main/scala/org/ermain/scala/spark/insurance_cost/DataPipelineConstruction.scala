package org.ermain.scala.spark.insurance_cost

import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}

object DataPipelineConstruction {

  val smoker_index: StringIndexer = new StringIndexer()
    .setInputCol("smoker")
    .setOutputCol("ins_smoker")

  val region_index: StringIndexer = new StringIndexer()
    .setInputCol("region")
    .setOutputCol("ins_region")

  // Create the feature columns
  val featureColumns: Array[String] = Array("bmi", "children", "ins_smoker", "ins_region")

  // Use the VectorAssembly to create the feature vector
  val vectorFeatureAssembler = new VectorAssembler()
    .setInputCols(featureColumns)
    .setOutputCol("features")


}
