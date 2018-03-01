package com.hrsat

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by hamid on 27/02/18.
  */
package object etl {

  object Config {
    val env = if (System.getenv("SCALA_ENV") == null) "development" else System.getenv("SCALA_ENV")

    val conf = ConfigFactory.load()
    def apply() = conf.getConfig(env)
  }

  val config=Config()

  val spark = SparkSession
    .builder
    .appName("spark_etl_work_shop")
    .master(config.getString("master"))
    .getOrCreate()


  object Resource extends Enumeration {
    type ResourceType = Value
    val SQL, NOSQL, JSON, CSV = Value
  }

  object EventType {
    val SALES = "1"
    val VISIT_PAGE = "2"


  }

  case class Models(salesDF: DataFrame, activitiesDF:DataFrame)



}
