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

  private def createShortestPathExcludingString(from: String, to: String, excluding: String): String = // TODO chuj kurwa path to jest twoja stara
    s"MATCH (a: Actor {name: \'$from\'}), (b: Actor {name: \'$to\'}) with (a)-[:ACTS_IN*..5]-(b) as l UNWIND l as xd return filter(x in xd where reduce(s = FALSE, y in x | (y.title =~ \'$excluding\') OR s))"

  private def createUserFriendsRatedString(login: String): String =
    s"Match (user:Person{login: \'$login\'}) -[:FRIEND]->(f)-[r:RATED]->(m: Movie) where r.stars > 2 return f.name, m.title, r.stars"

  private def createActorDirectorString(minMoviesPlayed: Int): String = //TODO nie do konca dziala + order po wystapieniach
    s"MATCH (a: Actor)-[:ACTS_IN]->(m: Movie), (d {name: a.name})-[:DIRECTED]->(md: Movie) " +
      s"with a, collect(m) as movies_played, d, collect(md) as movies_directed where length(movies_played) > $minMoviesPlayed and length(movies_directed) > 0" +
      s" return a.name, length(movies_played), length(movies_directed)"

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
