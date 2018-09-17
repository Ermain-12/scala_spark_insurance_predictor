package org.ermain.scala.spark.insurance_cost

import org.apache.spark.sql.types._

object InsuranceClaims {

  case class Customer(
                     age: String,
                     sex: String,
                     bmi: Double,
                     children: Integer,
                     smoker: Boolean,
                     region: String,
                     charges: Double
                     )

  val schema = StructType(
    Array(
      StructField("age", IntegerType, true),
      StructField("sex", StringType, true),
      StructField("bmi", DoubleType, true),
      StructField("children", IntegerType, true),
      StructField("smoker", StringType, true),
      StructField("region", StringType, true),
      StructField("charges", DoubleType, true)
    )
  )
}
