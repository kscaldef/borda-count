name := "votecount"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "com.twitter" %% "algebird-core" % "0.3.0",
  "org.scalaz" %% "scalaz-core" % "7.0.4",
  "org.spire-math" %% "spire" % "0.6.0"
)
