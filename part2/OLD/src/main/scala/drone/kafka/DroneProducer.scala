package drone.kafka

import java.util.{Date, Properties}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.util.Random

class DroneProducer extends App {
  val events = args(0).toInt
  val topic = args(1)
  val brokers = args(2)
  val rnd = new Random()
  val props = new Properties()

  // Props settings
  props.put("bootstrap.servers", brokers)
  props.put("client.id", "DroneProducer")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)
  val start_time = System.currentTimeMillis()
  for (nEvents <- Range(0, events)) {
    val runtime = new Date().getTime()
    // TODO : fix ip!!!
    val ip = "192.168.2." + rnd.nextInt(255)
    val msg = runtime + "," + nEvents + ",www.google.com," + ip
    val data = new ProducerRecord[String, String](topic, ip, msg)

    // Async
    // producer.send(data, (m, e) => {})

    // Sync
    producer.send(data)
  }

  System.out.println("sent per seconds: " + events * 1000 / (System.currentTimeMillis() - start_time))
  // End!
  producer.close()
}
