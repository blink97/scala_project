import spark.implicits._

class SparkConsumor(brokers: String) {
  def process(): Unit = {
    val spark = SparkSession.builder()
      .appName("spark")
      .master("local[*]")
      .getOrCreate()
  }
}

