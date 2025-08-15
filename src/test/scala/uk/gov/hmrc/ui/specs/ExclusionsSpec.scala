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

class ExclusionsSpec extends BaseSpec {

  private val exclusions = Exclusions
  private val auth       = Auth

  Feature("Exclusions journeys") {

    Scenario("Intermediary leaves the IOSS Intermediary service due to moving country") {

      Given("the intermediary accesses the IOSS Intermediary Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "standard")
      exclusions.goToExclusionsJourney()

      When("the intermediary answers yes on the exclusions-moved-to-a-different-country page")
      exclusions.checkJourneyUrl("exclusions-moved-to-a-different-country")
      exclusions.answerRadioButton("yes")

      Then("the intermediary selects Belgium on the exclusions-which-eu-country page")
      exclusions.checkJourneyUrl("exclusions-which-eu-country")
      exclusions.clearCountry()
      exclusions.selectCountry("Belgium")

      And("the intermediary enters today on the exclusions-move-date page")
      exclusions.checkJourneyUrl("exclusions-move-date")
      exclusions.enterDate("today")

      And("the intermediary enters a VAT number on the exclusions-tax-number page")
      exclusions.checkJourneyUrl("exclusions-tax-number")
      exclusions.enterAnswer("BG123456789")

      When("the intermediary submits their exclusion")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.submitExclusion()

      Then("the intermediary is on the exclusions-request-received page")
      exclusions.checkJourneyUrl("exclusions-request-received")
    }

    Scenario("Intermediary voluntarily leaves the IOSS Intermediary service") {

      Given("the intermediary accesses the IOSS Intermediary Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "standard")
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

      Then("the intermediary is on the exclusions-request-received page")
      exclusions.checkJourneyUrl("exclusions-request-received")
    }
  }
}
