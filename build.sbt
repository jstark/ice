version := "0.0"

scalaVersion := "2.10.3"

libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"

resolvers += Resolver.sonatypeRepo("public")

Seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

