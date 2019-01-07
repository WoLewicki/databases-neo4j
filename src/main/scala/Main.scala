import java.util.Date

object Main {
  def main(args: Array[String]): Unit = {
    val graphDatabase = GraphDatabase createDatabase()

    val solution = new Solution(graphDatabase)

    solution.databaseStatistics()
    solution.runAllTests()

    val additional = new AdditionalSolutions
    val actorName = "Marcin Aman3"

    println("Creating actor:")
    additional.createActorAndRelation(actorName, "ACTS_IN", "title4", graphDatabase)

    println("Updating actor:")
    additional.updateActor(actorName, new Date(), "Krakow", graphDatabase)

    println("Finding this actor:")
    additional.findActorByName(actorName, graphDatabase)

    println("Actor with movies played > 5:")
    additional.findActorsWithMoviesPlayed(6, graphDatabase)

    println("Average from actors that played more than 7:")
    additional.findAverageAmountOfMoviesPlayedGreater(7, graphDatabase)

    println("Actors that are directors:")
    additional.findActorsThatAreDirectors(5, graphDatabase)

    println("Movies rated by a friend of maheshksp:")
    additional.findMoviesRatedByFriends("maheshksp", graphDatabase)

    println("Path excluding movies:")
    additional.findPathExcluding("Carl Reiner", "Kyra Sedgwick", graphDatabase)

    println("Index comparision:")
    additional.indexTest("Minnie Driver", "Izabella Miko", graphDatabase)
  }
}