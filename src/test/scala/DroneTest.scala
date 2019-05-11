import Client.Drone
import org.scalatest.{FunSuite, run}


object DroneTest extends App {

  val drone : Drone = new Drone(1)
  drone.postJson()

  class Test extends FunSuite {

    val nb_drones = 10
    val nb_messages = 100


    def generateDrones(id : Int = 0) : Unit = {

      val drone : Drone = new Drone(id)
      calls(drone, nb_messages)

      if (id <= nb_drones)
        generateDrones(id + 1)
    }


    def calls(drone: Drone, id : Int) : Unit = {

      drone.postJson()

      if (id <= nb_messages)
        calls(drone, id - 1)
    }

    generateDrones()

  }

  run(new Test)
}
