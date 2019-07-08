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
    val alert_topic = "alert"

    val props = new Properties()
    // Props settings
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "KafkaProducer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    // ! Create Producer
    val producer = new KafkaProducer[String, String](props)

    val tim1 = 10
    val tim5 = 50
    val tim10 = 100
    val tim20 = 200
    Thread.sleep(tim5)

    while (true) {
      // Change random time (5%)
      val p_tim = Random.nextInt(1000)
      if (p_tim < 50) {
        Thread.sleep(tim1)
      } else if (p_tim < 400) {
        Thread.sleep(tim5)
      }
      else if (p_tim < 600) {
        Thread.sleep(tim10)
      }
      else {
        Thread.sleep(tim20)
      }

      val i = Random.nextInt(4000)

      val MsgJson: Json = MsgClass.MsgFactory(i).asJson
        
      val data = new ProducerRecord[String, String](topic, "1", MsgJson.toString)

      // ! Send to Kafka
      val futureResult = producer.send(data)

      println("Send to kafka")

      // Wait ackn
      futureResult.get()

      val p_a = Random.nextInt(1000)
      if (p_a < 50) {
        val AlertMsgJson: Json = MsgClass.MsgAlertFactory(i).asJson
        val dataAlert = new ProducerRecord[String, String](alert_topic, "1", MsgJson.toString)
        // ! Send to Kafka
        val futureResultAlert = producer.send(dataAlert)
        println("Send Alert to kafka")
        // Wait ackn
        futureResultAlert.get()

      }

      
    }

    producer.close()
  } 
}

