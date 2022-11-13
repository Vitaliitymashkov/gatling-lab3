package simulations

//Import required object and references

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.concurrent.duration._


//Extend Testclass with Simulation class

class TestFirstBasicAPISimulation extends Simulation{


//Step1: Define a common http protocol config

  val httpConf: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
    .header("Accept","application/json")

//Step 2: Define variable
  val postIdNumbers = scala.util.Random

//Step 3: Define the scenario

  val scn: ScenarioBuilder = scenario("PerfTest Scenario - 4 calls")
    .exec(http("Get all Posts - 1st Call")
      .get("/posts")
      .check(status.is(200))
    )
    .pause(5)

    .exec(http("Get Specific Post - 2nd Call")
      .get("/posts/"+postIdNumbers.nextInt(20))
      .check(status.is(200))
      .check(jsonPath("$.id").saveAs("postId"))
    )
    .pause(1, 20)

    .exec(http("Get Comments for above post - 3rd call ")
      .get("/posts/${postId}/comments")
      .check(status.is(200))
    )
    .pause(2)

    .exec(http("Add Comment to the Post - 4th Call")
      .post("/posts/${postId}/comments")
      .body(StringBody(
        """{
            "name": "gatling",
            "email": "gatling@test.com",
            "body": "This is a simple comment"
          }"""))
      .check(status.is(201))
    )


// Step 4: Define the Load Injection pattern

  setUp(
    scn.inject(
      atOnceUsers(2),
      rampUsersPerSec(2) to(3) during(120 seconds) randomized
    )
  ).protocols(httpConf)
}