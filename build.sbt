name := "Neo4j"

version := "0.1"

scalaVersion := "2.11.8"

val neo4jVersion = "3.3.0"

libraryDependencies += "org.neo4j" % "neo4j-kernel" % neo4jVersion
libraryDependencies += "org.neo4j" % "neo4j-io" % neo4jVersion
libraryDependencies += "org.neo4j" % "neo4j-cypher" % neo4jVersion


