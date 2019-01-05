public class Solution {

    private final GraphDatabase graphDatabase;

    public Solution(GraphDatabase graphDatabase){
        this.graphDatabase = graphDatabase;
    }

    public void databaseStatistics() {
        System.out.println(graphDatabase.runCypher("CALL db.labels()"));
        System.out.println(graphDatabase.runCypher("CALL db.relationshipTypes()"));
    }

    public void runAllTests() {
        System.out.println(findActorByName("Emma Watson"));
        System.out.println(findMovieByTitleLike("Star Wars"));
        System.out.println(findRatedMoviesForUser("maheshksp"));
        System.out.println(findCommonMoviesForActors("Emma Watson", "Daniel Radcliffe"));
        System.out.println(findMovieRecommendationForUser("emileifrem"));
    }

    private String findActorByName(final String actorName) {
        return graphDatabase.runCypher("MATCH (p:Actor) WHERE p.name = \'" + actorName + "\' RETURN p");
    }

    private String findMovieByTitleLike(final String movieName) {
        return graphDatabase.runCypher("MATCH (m:Movie) where m.title CONTAINS \'" + movieName + " \' RETURN m");
    }

    private String findRatedMoviesForUser(final String userLogin) {
        return graphDatabase.runCypher("MATCH (u: User {login : \'" + userLogin + "\'})-[:RATED]->(m: Movie) RETURN m");
    }

    private String findCommonMoviesForActors(String actorOne, String actrorTwo) {
        return graphDatabase.runCypher("MATCH (f:Actor {name: \'"+actorOne+"\'})-[:ACTS_IN]->(m)<-[:ACTS_IN]-(s: Actor {name: \'"+actrorTwo+"\'}) RETURN m");
    }

    private String findMovieRecommendationForUser(final String userLogin) {
        return graphDatabase.runCypher("Match (user:Person{login: \'"+userLogin+"\'}) -[:RATED]->(m:Movie)<-[:RATED]-(user2)-[:RATED]->(movies) return movies.title ");
    }
}