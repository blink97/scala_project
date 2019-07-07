import java.util.{Date, Properties}
import org.apache.kafka.clients.producer._ // {KafkaProducer, ProducerRecord}
import scala.util.Random
import DroneMsg._

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

    val msg1 = "{\"id\":\"1\",\"msg_id\":\"10\",\"timestamp\":\"'2019-05-11 17:05:45.484944'\"}"
    val msg2 = "{\"id\":\"1\",\"msg_id\":\"11\",\"timestamp\":\"'2019-05-11 17:05:45.484944'\"}"
    val msg3 = "{\"id\":\"1\",\"msg_id\":\"13\",\"timestamp\":\"'2019-05-11 17:05:45.484944'\"}"

    val data = new ProducerRecord[String, String](topic, "1", msg1)

    // ! Send to Kafka
    val futureResult = producer.send(data)

    println("Send to kafka")

    // Wait ackn
    futureResult.get()

    val data2 = new ProducerRecord[String, String](topic, "1", msg2)

    // ! Send to Kafka
    val futureResult2 = producer.send(data)

    println("Send to kafka")

    // Wait ackn
    futureResult.get()

    producer.close()
  } 
}

/*

  private fun createProducer (brokers: String): Producer < String
  , String > {
    val props = Properties()
    props["bootstrap.servers"] = brokers
    props["key.serializer"] = StringSerializer ::
    class.java.canonicalName
    props["value.serializer"] = StringSerializer ::
    class.java.canonicalName
    return KafkaProducer < String
    , String > (props)
  }

  data class Person(
        val firstName: String,
        val lastName: String,
        val birthDate: Date
  )

  val faker = Faker()
  val fakePerson = Person(
        firstName = faker.name().firstName(),
        lastName = faker.name().lastName(),
        birthDate = faker.date().birthday()
  )

  val jsonMapper = ObjectMapper().apply {
    registerKotlinModule()
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    setDateFormat(StdDateFormat())
  }

  val fakePersonJson = jsonMapper.writeValueAsString(fakePerson)

  val futureResult = producer.send(ProducerRecord(personsTopic, fakePersonJson))
  futureResult.get()


}
*/
