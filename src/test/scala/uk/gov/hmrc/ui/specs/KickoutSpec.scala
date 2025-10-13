/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.{Auth, Exclusions}

class KickoutSpec extends BaseSpec {

  private val exclusions = Exclusions
  private val auth       = Auth

  Feature("Kickout journeys") {

    Scenario("Cannot access the Intermediary Exclusions service without being registered on the Intermediary service") {

      Given("a user who is not registered on the Intermediary service accesses the IOSS Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(false, true, "standard")

      Then("the intermediary is on the cannot-use-this-service page")
      exclusions.checkJourneyUrl("cannot-use-this-service")
    }

    Scenario("No VAT enrolment when intermediary logs into the Intermediary Exclusions service") {

      Given(
        "the intermediary accesses the IOSS Intermediary Exclusions Service without a VAT enrolment"
      )
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, false, "notRequired")

      Then("the intermediary is shown the sorry there is a problem page")
      exclusions.checkProblemPage()
    }

    Scenario(
      "Cannot access the Intermediary Exclusions journey when already excluded and exclusion effective date is in the past"
    ) {

      Given("a user who is not registered on the Intermediary service accesses the IOSS Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "alreadyExcludedPast")

      Then("the intermediary is on the no-access-excluded page")
      exclusions.checkJourneyUrl("no-access-excluded")
    }

    Scenario(
      "Can only access the cancel request to leave page and not the main Intermediary Exclusions journey when already excluded and exclusion effective date is in the future"
    ) {

      Given("a user who is not registered on the Intermediary service accesses the IOSS Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "alreadyExcludedFuture")

      Then("the intermediary is on the no-access-excluded page")
      exclusions.checkJourneyUrl("no-access-excluded")

      And("the intermediary can access the cancel-leave-scheme page")
      exclusions.goToPage("cancel-leave-scheme")
      exclusions.checkJourneyUrl("cancel-leave-scheme")
    }

    Scenario("Failure to submit exclusion when moving country") {

      Given("the intermediary accesses the IOSS Intermediary Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "failure")
      exclusions.goToExclusionsJourney()

      When("the intermediary answers yes on the exclusions-moved-to-a-different-country page")
      exclusions.checkJourneyUrl("exclusions-moved-to-a-different-country")
      exclusions.answerRadioButton("yes")

      Then("the intermediary selects Finland on the exclusions-which-eu-country page")
      exclusions.checkJourneyUrl("exclusions-which-eu-country")
      exclusions.selectCountry("Finland")

      And("the intermediary enters today on the exclusions-move-date page")
      exclusions.checkJourneyUrl("exclusions-move-date")
      exclusions.enterDate("today")

      And("the intermediary enters a VAT number on the exclusions-tax-number page")
      exclusions.checkJourneyUrl("exclusions-tax-number")
      exclusions.enterAnswer("FI12345678")

      When("the intermediary submits their exclusion")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.submitExclusion()

      Then("the intermediary is on the submission-failure page")
      exclusions.checkJourneyUrl("submission-failure")
    }

    Scenario("Failure to submit exclusion when intermediary voluntarily leaves") {

      Given("the intermediary accesses the IOSS Intermediary Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "failure")
      exclusions.goToExclusionsJourney()

      When("the intermediary answers no on the exclusions-moved-to-a-different-country page")
      exclusions.checkJourneyUrl("exclusions-moved-to-a-different-country")
      exclusions.answerRadioButton("no")

      When("the intermediary answers yes on the exclusions-leave-scheme page")
      exclusions.checkJourneyUrl("exclusions-leave-scheme")
      exclusions.answerRadioButton("yes")

      And("the intermediary enters today on the exclusions-stopped-using-service-date page")
      exclusions.checkJourneyUrl("exclusions-stopped-using-service-date")
      exclusions.enterDate("today")

      When("the intermediary submits their exclusion")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.submitExclusion()

      Then("the intermediary is on the submission-failure page")
      exclusions.checkJourneyUrl("submission-failure")
    }
  }
}
