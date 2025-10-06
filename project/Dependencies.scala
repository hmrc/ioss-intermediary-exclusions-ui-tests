import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "ui-test-runner" % "0.49.0" % Test,
    "junit"        % "junit"          % "4.13.2" % Test
  )

}
