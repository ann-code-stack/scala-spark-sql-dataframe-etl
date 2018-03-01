import com.hrsat.etl._
import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.sql.DataFrame
import org.slf4j.LoggerFactory
import org.apache.spark.sql.functions._


trait Transformer[T] {
  def transform():T
}





case class CustomTransformer(val eventDF: DataFrame,val companyDF: DataFrame) extends Transformer[Models] {
  val logger = LoggerFactory.getLogger(this.getClass)

  override def transform(): Models = {

   val activitiesDF= eventDF.filter(eventDF("type") === EventType.VISIT_PAGE).join(companyDF, eventDF("customer_id") === companyDF("company_id"))
      .select( "company_id", "company_name","page_viewed","ts")
     .withColumn("activity_id",monotonically_increasing_id())

    val salesDF=eventDF.filter(eventDF("type") === EventType.SALES).join(companyDF, eventDF("customer_id") === companyDF("company_id"))

      .select("company_id", "company_name","orders","ts")
      .withColumn("orders_exp", explode(col("orders")))
      .withColumn("order_id",  col("orders_exp.order_id"  ))
      .withColumn("price", col("orders_exp.price"  ))
      .withColumn("sales_id",monotonically_increasing_id())
      .drop(col("orders_exp"  ))
      .drop(col("orders"  ))

    Models(salesDF,activitiesDF)

    }


  }


