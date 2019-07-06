
class KafkaProducer {
  val process(brokers: String): Unit = {
    val props = Properties()
    // Props settings
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "KafkaProducer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    // ! Create Producer
    val producer = new KafkaProducer[String, String](props)


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
