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

package uk.gov.hmrc.ui.pages

import uk.gov.hmrc.configuration.TestEnvironment
import org.openqa.selenium.{By, Keys}
import org.scalatest.matchers.should.Matchers.*
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait}
import uk.gov.hmrc.selenium.webdriver.Driver
import org.junit.Assert

import java.time.LocalDate

object Exclusions extends BasePage {

  private val exclusionsUrl: String =
    TestEnvironment.url("ioss-intermediary-exclusions-frontend")

  private val journeyUrl: String = "/pay-clients-vat-on-eu-sales/leave-import-one-stop-shop-intermediary"

  def goToExclusionsJourney(): Unit =
    get(exclusionsUrl + journeyUrl)

  def checkJourneyUrl(page: String): Unit =
    getCurrentUrl should startWith(s"$exclusionsUrl$journeyUrl/$page")

  def goToPage(page: String): Unit =
    get(s"$exclusionsUrl$journeyUrl/$page")

  def answerRadioButton(answer: String): Unit = {

    answer match {
      case "yes" => click(By.id("value"))
      case "no"  => click(By.id("value-no"))
      case _     => throw new Exception("Option doesn't exist")
    }
    click(continueButton)
  }

  def waitForElement(by: By): Unit =
    new FluentWait(Driver.instance).until(ExpectedConditions.presenceOfElementLocated(by))

  def selectCountry(country: String): Unit = {
    val inputId = "value"
    sendKeys(By.id(inputId), country)
    waitForElement(By.id(inputId))
    click(By.cssSelector("li#value__option--0"))
    click(continueButton)
  }

  def enterDate(day: String): Unit = {

    val date =
      if (day == "today") {
        LocalDate.now()
      } else if (day == "mid-month") {
        LocalDate.now().withDayOfMonth(15)
      } else {
        LocalDate.now().plusDays(1)
      }

    sendKeys(By.id("value.day"), date.getDayOfMonth.toString)
    sendKeys(By.id("value.month"), date.getMonthValue.toString)
    sendKeys(By.id("value.year"), date.getYear.toString)

    click(continueButton)
  }

  def enterAnswer(answer: String): Unit =
    sendKeys(By.id("value"), answer)
    click(continueButton)

  def submitExclusion(): Unit =
    click(submitButton)

  def clearCountry(): Unit = {
    val input = Driver.instance.findElement(By.id("value")).getAttribute("value")
    if (input != null) {
      for (n <- input)
        Driver.instance.findElement(By.id("value")).sendKeys(Keys.BACK_SPACE)
    }
  }

  def checkProblemPage(): Unit = {
    val h1 = Driver.instance.findElement(By.tagName("h1")).getText
    Assert.assertTrue(h1.equals("Sorry, there is a problem with the service"))
  }

  def selectChangeLink(link: String): Unit =
    click(By.cssSelector(s"a[href*=$link]"))
}
