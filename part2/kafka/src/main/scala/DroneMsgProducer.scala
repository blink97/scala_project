import argonaut._
import Argonaut._

import java.util.{Date, Properties}
import org.apache.kafka.clients.producer._ // {KafkaProducer, ProducerRecord}
import scala.util.Random
import MsgClass._

object DroneMsgProducer {
  def main(args: Array[String]): Unit = {
    new DroneMsgProducer("localhost:9092").process()
  }
}


class DroneMsgProducer(brokers: String) {
  def process(): Unit = {
    val topic = "msg"

    val props = new Properties()
    // Props settings
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "KafkaProducer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    // ! Create Producer
    val producer = new KafkaProducer[String, String](props)

    while (true) {
      val i = Random.nextInt(4000)

      val MsgJson: Json = MsgClass.MsgFactory(i).asJson
        
      val data = new ProducerRecord[String, String](topic, "1", MsgJson.toString)

      // ! Send to Kafka
      val futureResult = producer.send(data)

      println("Send to kafka")

      // Wait ackn
      futureResult.get()
    }

    // Wait ackn
    futureResult.get()

    
    producer.close()
  } 
}

