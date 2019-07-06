import SparkConsumor._

object SparkConsumor {
  def main(args: Array[String]): Unit = {
    new SparkConsumor("localhost:9092").process()
  }
}

