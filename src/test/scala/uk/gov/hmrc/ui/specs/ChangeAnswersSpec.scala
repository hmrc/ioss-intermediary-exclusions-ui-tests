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

class ChangeAnswersSpec extends BaseSpec {

  private val exclusions = Exclusions
  private val auth       = Auth

  Feature("Change your answers journeys") {

    Scenario("Intermediary changes move date for moving to a different country exclusions journey") {

      Given("the intermediary accesses the IOSS Intermediary Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "standard")
      exclusions.goToExclusionsJourney()

      When("the intermediary answers yes on the exclusions-moved-to-a-different-country page")
      exclusions.checkJourneyUrl("exclusions-moved-to-a-different-country")
      exclusions.answerRadioButton("yes")

      Then("the intermediary selects Poland on the exclusions-which-eu-country page")
      exclusions.checkJourneyUrl("exclusions-which-eu-country")
      exclusions.selectCountry("Poland")

      And("the intermediary enters today on the exclusions-move-date page")
      exclusions.checkJourneyUrl("exclusions-move-date")
      exclusions.enterDate("today")

      And("the intermediary enters a VAT number on the exclusions-tax-number page")
      exclusions.checkJourneyUrl("exclusions-tax-number")
      exclusions.enterAnswer("PL1234567890")

      And("the trader clicks change on the check-your-answers page for exclusions-move-date")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.selectChangeLink("exclusions-move-date\\?waypoints\\=check-your-answers")

      And("the trader amends the move date to tomorrow")
      exclusions.checkJourneyUrl("exclusions-move-date?waypoints=check-your-answers")
      exclusions.enterDate("tomorrow")

      When("the intermediary submits their exclusion")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.submitExclusion()

      Then("the intermediary is on the exclusions-request-received page")
      exclusions.checkJourneyUrl("exclusions-request-received")
    }

    Scenario("Intermediary changes vat number for moving to a different country exclusions journey") {

      Given("the intermediary accesses the IOSS Intermediary Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "standard")
      exclusions.goToExclusionsJourney()

      When("the intermediary answers yes on the exclusions-moved-to-a-different-country page")
      exclusions.checkJourneyUrl("exclusions-moved-to-a-different-country")
      exclusions.answerRadioButton("yes")

      Then("the intermediary selects Slovakia on the exclusions-which-eu-country page")
      exclusions.checkJourneyUrl("exclusions-which-eu-country")
      exclusions.selectCountry("Slovakia")

      And("the intermediary enters today on the exclusions-move-date page")
      exclusions.checkJourneyUrl("exclusions-move-date")
      exclusions.enterDate("today")

      And("the intermediary enters a VAT number on the exclusions-tax-number page")
      exclusions.checkJourneyUrl("exclusions-tax-number")
      exclusions.enterAnswer("SK1234567890")

      And("the trader clicks change on the check-your-answers page for exclusions-tax-number")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.selectChangeLink("exclusions-tax-number\\?waypoints\\=check-your-answers")

      And("the trader amends the vat number")
      exclusions.checkJourneyUrl("exclusions-tax-number?waypoints=check-your-answers")
      exclusions.enterAnswer("SK1234567777")

      When("the intermediary submits their exclusion")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.submitExclusion()

      Then("the intermediary is on the exclusions-request-received page")
      exclusions.checkJourneyUrl("exclusions-request-received")
    }

    Scenario("Intermediary changes country for moving to a different country exclusions journey") {

      Given("the intermediary accesses the IOSS Intermediary Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "standard")
      exclusions.goToExclusionsJourney()

      When("the intermediary answers yes on the exclusions-moved-to-a-different-country page")
      exclusions.checkJourneyUrl("exclusions-moved-to-a-different-country")
      exclusions.answerRadioButton("yes")

      Then("the intermediary selects Czech Republic on the exclusions-which-eu-country page")
      exclusions.checkJourneyUrl("exclusions-which-eu-country")
      exclusions.selectCountry("Czech Republic")

      And("the intermediary enters today on the exclusions-move-date page")
      exclusions.checkJourneyUrl("exclusions-move-date")
      exclusions.enterDate("today")

      And("the intermediary enters a VAT number on the exclusions-tax-number page")
      exclusions.checkJourneyUrl("exclusions-tax-number")
      exclusions.enterAnswer("CZ123456789")

      And("the trader clicks change on the check-your-answers page for exclusions-which-eu-country")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.selectChangeLink("exclusions-which-eu-country\\?waypoints\\=check-your-answers")

      And("the trader amends the country")
      exclusions.checkJourneyUrl("exclusions-which-eu-country?waypoints=check-your-answers")
      exclusions.clearCountry()
      exclusions.selectCountry("Croatia")

      And("the trader enters VAT number HR01234567899 on the eu-vat-number page")
      exclusions.checkJourneyUrl("exclusions-tax-number?waypoints=check-your-answers")
      exclusions.enterAnswer("HR01234567899")

      When("the intermediary submits their exclusion")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.submitExclusion()

      Then("the intermediary is on the exclusions-request-received page")
      exclusions.checkJourneyUrl("exclusions-request-received")
    }

    Scenario("Intermediary changes from moving country journey to voluntary journey") {

      Given("the intermediary accesses the IOSS Intermediary Exclusions Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "standard")
      exclusions.goToExclusionsJourney()

      When("the intermediary answers yes on the exclusions-moved-to-a-different-country page")
      exclusions.checkJourneyUrl("exclusions-moved-to-a-different-country")
      exclusions.answerRadioButton("yes")

      Then("the intermediary selects Czech Republic on the exclusions-which-eu-country page")
      exclusions.checkJourneyUrl("exclusions-which-eu-country")
      exclusions.selectCountry("Czech Republic")

      And("the intermediary enters today on the exclusions-move-date page")
      exclusions.checkJourneyUrl("exclusions-move-date")
      exclusions.enterDate("today")

      And("the intermediary enters a VAT number on the exclusions-tax-number page")
      exclusions.checkJourneyUrl("exclusions-tax-number")
      exclusions.enterAnswer("CZ123456789")

      And("the trader clicks change on the check-your-answers page for exclusions-moved-to-a-different-country")
      exclusions.checkJourneyUrl("check-your-answers")
      exclusions.selectChangeLink("exclusions-moved-to-a-different-country\\?waypoints\\=check-your-answers")

      And("the trader changes answer to no on exclusions-moved-to-a-different-country page")
      exclusions.checkJourneyUrl("exclusions-moved-to-a-different-country?waypoints=check-your-answers")
      exclusions.answerRadioButton("no")

      And("the trader selects yes on the exclusions-leave-scheme page")
      exclusions.checkJourneyUrl("exclusions-leave-scheme?waypoints=check-your-answers")
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
