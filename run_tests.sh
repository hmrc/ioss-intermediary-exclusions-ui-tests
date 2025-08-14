#!/bin/bash -e

sbt clean -Dbrowser="chrome" -Denvironment="local" -Daccessibility.timeout="1000" "testOnly uk.gov.hmrc.ui.specs.*" testReport