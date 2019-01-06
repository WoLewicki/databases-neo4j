import java.util.Date

object Main {
  def main(args: Array[String]): Unit = {
    val graphDatabase = GraphDatabase createDatabase()

    val solution = new Solution(graphDatabase)
    solution.databaseStatistics()
//    solution.runAllTests()

    val additional = new AdditionalSolutions
    val actorName = "Marcin Aman"
    additional.createActorAndRelation(actorName, "ACTS_IN", "title2", graphDatabase)

    additional.updateActor(actorName, new Date(), "Krakow", graphDatabase)

    additional.findActorByName(actorName, graphDatabase)

    additional.findActorsWithMoviesPlayed(6, graphDatabase)

    additional.findAverageAmountOfMoviesPlayedGreater(7, graphDatabase)

    additional.findActorsThatAreDirectors(5, graphDatabase)

    additional.findMoviesRatedByFriends("maheshksp", graphDatabase)

    additional.findPathExcluding("Sean Connery", "Scarlett Johansson", "Jim Malone", graphDatabase)

    additional.indexTest("Minnie Driver", "Izabella Miko", graphDatabase)
  }
}