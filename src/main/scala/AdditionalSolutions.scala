import java.util.Date

class AdditionalSolutions {
  def createActorAndRelation(actorName: String, relationName: String, title: String, graphDatabase: GraphDatabase): Unit = {
    val valuesToExecute = (createActorString(actorName), createMovieString(title), createRelationString(actorName, title, relationName))

    println("values to execute:")
    println(valuesToExecute)

    println("actor: \n" + graphDatabase.runCypher(valuesToExecute._1))
    println("movie: \n" + graphDatabase.runCypher(valuesToExecute._2))
    println("relation: \n" + graphDatabase.runCypher(valuesToExecute._3))
  }

  def updateActor(actorName: String, birthDate: Date, birthPlace: String, graphDatabase: GraphDatabase): Unit = {
    val updateActor = createUpdateActorString(actorName, birthDate, birthPlace, new Date())

    println("Update actor query: " + updateActor)
    println(graphDatabase.runCypher(updateActor))
  }

  def findActorByName(actorName: String, graphDatabase: GraphDatabase): Unit = {
    println(graphDatabase.runCypher(createSearchingString(actorName)))
  }

  def findActorsWithMoviesPlayed(moviesPlayed: Int, graphDatabase: GraphDatabase): Unit = {
    println(graphDatabase.runCypher(createfindActorsWithMoviesPlayedString(moviesPlayed)))
  }

  def findAverageAmountOfMoviesPlayedGreater(moviesPlayedMin: Int, graphDatabase: GraphDatabase): Unit = {
    println(graphDatabase.runCypher(createAvgString(moviesPlayedMin)))
  }

  def findActorsThatAreDirectors(moviesPlayedMin: Int, graphDatabase: GraphDatabase): Unit = {
    println(graphDatabase.runCypher(createActorDirectorString(moviesPlayedMin)))
  }

  def findMoviesRatedByFriends(login: String, graphDatabase: GraphDatabase): Unit = {
    println(graphDatabase.runCypher(createUserFriendsRatedString(login)))
  }

  def findPathExcluding(fromActor: String, toActor: String, excluding: String, graphDatabase: GraphDatabase): Unit = {
    println(graphDatabase.runCypher(createShortestPathExcludingString(fromActor, toActor, excluding)))
  }

  private def createShortestPathExcludingString(from: String, to: String, excluding: String): String =
    s"MATCH (a: Actor {name: \'$from\'}), (b: Actor {name: \'$to\'}) UNWIND nodes(shortestPath((a)-[:ACTS_IN*..3]-(b))) as l return l"

  private def createUserFriendsRatedString(login: String): String =
    s"Match (user:Person{login: \'$login\'}) -[:FRIEND]->(f)-[r:RATED]->(m: Movie) where r.stars > 2 return f.name, m.title, r.stars"

  private def createActorDirectorString(minMoviesPlayed: Int): String =
    "Match (a:Actor) -[:ACTS_IN]-> (m:Movie) " +
    "With a, count(m) as movies " +
    s"where movies > $minMoviesPlayed " +
    "With a,movies " +
    "Match (a:Director) -[:DIRECTED]-> (m:Movie) " +
    "With a,movies,count(m.title) as directed, collect(m.title) as directedTitles " +
    "Where directed > 0 " +
    "Return a.name,movies,directed,directedTitles " +
    "Order By movies DESC "

  private def createAvgString(minMoviesPlayed: Int): String =
    s"MATCH (a: Actor)-[:ACTS_IN]->(m: Movie) with a, collect(m) as movies where length(movies) > $minMoviesPlayed return avg(length(movies)) as average"

  private def createfindActorsWithMoviesPlayedString(moviesPlayed: Int) =
    s"MATCH (a: Actor)-[:ACTS_IN]->(m: Movie) with a, collect(m) as movies where length(movies) > $moviesPlayed return a.name, length(movies)"

  private def createSearchingString(actorName: String): String = s"MATCH (a: Actor) where a.name = \'$actorName\' return a"

  private def createActorString(actorName: String): String = s"CREATE (n: Actor {name: \'$actorName\'}) RETURN n"

  private def createMovieString(title: String): String = s"CREATE (n: Movie {title: \'$title\'}) RETURN n"

  private def createRelationString(actorName: String, movieName: String, relationName: String): String =
    s"MATCH (a: Actor), (m: Movie) WHERE a.name = \'$actorName\' and m.title = \'$movieName\' CREATE (a)-[r: $relationName]->(m) RETURN r"

  private def createUpdateActorString(actorName: String, birthDate: Date, birthPlace: String, currentDate: Date): String =
    s"MATCH (a: Actor) where a.name = \'$actorName\' SET a.birthplace=\'$birthPlace\', a.birthday=\'$birthPlace\', a.lastModified=\'$currentDate\'"
}
