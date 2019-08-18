
import kafka.serializer.DefaultDecoder
import org.apache.commons.codec.StringDecoder
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext, TaskContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import scala.collection.mutable.ListBuffer


object App {

  def main(args: Array[String]): Unit = {
    val sc = new SparkConf()
      .setMaster("local[*]")
      .setAppName("Kafka_streaming")
    val ssc = new StreamingContext(sc, Seconds(5))
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "enable.auto.commit" -> (true: java.lang.Boolean),
      "group.id" -> "MERONG",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("test")
    val dstream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )


    //   val wordCounts = dstream.flatMap(_.value().split(" ")).map(word => (word, 1)).reduceByKey(_ + _)
    //    wordCounts.print();
    //    wordCounts
    //   wordCounts.map(println(_))
    //     wordCounts.count()

    //    println(dstream.count());
    //    dstream.map(println(_))

    //    dstream.map(record => (record.key, record.value))

    dstream.foreachRDD { rdd =>
      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd.foreach { res =>
        val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
        val vTopic = o.topic
        val vPartition = o.partition
        val vsoffset = o.fromOffset
        val veoffset = o.untilOffset

        /* 데이터 처리하는 부분 시작 */
        println(res.value)
        /* 데이터 처리하는 부분 시작 */
      }
      dstream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
    }
    ssc.start()
    ssc.awaitTermination()

  }
}
