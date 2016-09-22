package com.hrsat.etlsql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

/**
  * Created by hamid on 21/09/16.
  * dataset : http://www.modelingonlineauctions.com/datasets
  */
object OpenCvsAndSql {
  def main(args: Array[String]): Unit = {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder
      .appName("spark_general_cvs_sql")
      .master("local[*]")
      .getOrCreate()
    val auctionData = spark.read.
      option("header", true).option("delimiter", ",")
      .csv("data/Palm_7-day_149auctions_Curve_Clustering.csv")
      .toDF( ).cache()

    auctionData.printSchema()

    auctionData.show(5)

    //group by
    import org.apache.spark.sql.functions._
    auctionData.groupBy("bidder").count().sort(desc("count")).show(5)

    //filter and aggrigation
    import spark.implicits._
    auctionData.filter($"Seller".like("hil%")).select(max("BidAmount")).show()

  }
}
