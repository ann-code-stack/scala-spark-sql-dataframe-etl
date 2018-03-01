import com.hrsat.etl._
import org.apache.spark.sql.DataFrame
import org.slf4j.LoggerFactory
trait Extractor [T] {

  def extract(): T


}


case class CustomExtractor(val resourceType: Resource.ResourceType,val resource:  String) extends Extractor[DataFrame] {
  val logger = LoggerFactory.getLogger(this.getClass)

  override def extract(): DataFrame = {

    resourceType match {
      case Resource.CSV =>
        spark.read.option("header", true).
          format("com.databricks.spark.csv").option("delimiter", ",").csv(resource)

      case Resource.JSON => spark.read.json(resource)

      case Resource.SQL =>
        spark.read
        .format("jdbc")
        .option("url", config.getString("database.source.url"))
        .option("dbtable", resource)
        .option("user", config.getString("database.source.user"))
        .option("password", config.getString("database.source.password"))
        .load()

      case Resource.NOSQL => null // TODO implementation
      case _ => null //TODO add exception


    }


  }
}