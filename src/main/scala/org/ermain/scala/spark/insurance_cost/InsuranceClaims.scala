package org.ermain.scala.spark.insurance_cost

import org.apache.spark.sql.types._

object InsuranceClaims {

  case class Customer(
                     age: Int,
                     sex: String,
                     bmi: Double,
                     children: Integer,
                     smoker: String,
                     region: String,
                     label: Double
                     )

  val schema = StructType(
    Array(
      StructField("age", IntegerType, true),
      StructField("sex", StringType, true),
      StructField("bmi", DoubleType, true),
      StructField("children", IntegerType, true),
      StructField("smoker", StringType, true),
      StructField("region", StringType, true),
      StructField("label", DoubleType, true)
    )
  )
}
