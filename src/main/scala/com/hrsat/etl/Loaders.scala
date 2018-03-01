import com.hrsat.etl.{Resource, config, spark}
import org.apache.spark.sql.{DataFrame, SaveMode}

trait Loader {
  def save()
}

case class CustomLoader(val resourceType: Resource.ResourceType, val df: DataFrame, val resource: String) extends Loader {
  override def save(): Unit = {

    resourceType match {
      case Resource.CSV =>
        df.repartition(config.getString("filesystem.target.partition_size").toInt)
          .write.mode(SaveMode.Overwrite).option("header", "true")
          .format("com.databricks.spark.csv").save(resource)
      case Resource.JSON =>
        df.repartition(config.getString("filesystem.target.partition_size").toInt)
          .write.mode(SaveMode.Overwrite).json(resource)

      case Resource.SQL =>
        df.write.mode(SaveMode.Overwrite).format("jdbc")
        .option("url", config.getString("database.target.url"))
        .option("dbtable", resource)
        .option("user", config.getString("database.target.user"))
        .option("password", config.getString("database.target.password")).save()

      case Resource.NOSQL => null // TODO implementation
      case _ => null //TODO add exception

    }
  }
}