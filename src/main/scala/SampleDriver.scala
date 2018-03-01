import com.hrsat.etl._

import org.slf4j.LoggerFactory
 /**
  * Created by hamid on 27/02/18.
  */
object SampleDriver extends  App {
  val logger = LoggerFactory.getLogger(this.getClass)

  logger.info(config.getString("master"))

  val eventsDF= CustomExtractor(Resource.JSON,config.getString("filesystem.source.events")).extract()
  val companiesDF= CustomExtractor(Resource.CSV,config.getString("filesystem.source.companies")).extract()


  val model:Models=CustomTransformer(eventsDF,companiesDF).transform()


   model.activitiesDF.show()
   model.salesDF.show
   CustomLoader(Resource.CSV,model.activitiesDF,config.getString("filesystem.target.sales")).save()
   CustomLoader(Resource.CSV,model.salesDF,config.getString("filesystem.target.activities")).save()

   spark.stop()

}
