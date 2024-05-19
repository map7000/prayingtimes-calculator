/*
 * PrayingTimes Calculator
 * Copyright (C) 2024 Mikhail Filatov
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package ru.mfilatov.prayingtimes.calculator;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import ru.mfilatov.prayingtimes.calculator.enums.CalculationMethods;
import ru.mfilatov.prayingtimes.calculator.enums.TimeName;
import ru.mfilatov.prayingtimes.calculator.model.TestData;
import ru.mfilatov.prayingtimes.calculator.model.Times;

public class CalculationAccurancyTest {
  @Test
  void checkRussiaTest() {
    double latitude = 55.75222;
    double longitude = 37.61556;
    int timezone = 3;

    PrayingTimesCalculator prayers =
        new PrayingTimesCalculator(
            LocalDate.now(), timezone, latitude, longitude, CalculationMethods.RUSSIA);
    var times = prayers.calculate();

    TestData testData = readTestData("Russia.json");
    checkTimes(times, testData);
  }

  @Test
  void checkMWLTest() {
    double latitude = 55.75222;
    double longitude = 37.61556;
    int timezone = 3;

    PrayingTimesCalculator prayers =
        new PrayingTimesCalculator(
            LocalDate.now(), timezone, latitude, longitude, CalculationMethods.MWL);
    var times = prayers.calculate();

    TestData testData = readTestData("MWL.json");
    checkTimes(times, testData);
  }

  @Test
  void checkMakkahTest() {
    double latitude = 55.75222;
    double longitude = 37.61556;
    int timezone = 3;

    PrayingTimesCalculator prayers =
        new PrayingTimesCalculator(
            LocalDate.now(), timezone, latitude, longitude, CalculationMethods.MAKKAH);
    var times = prayers.calculate();

    TestData testData = readTestData("Makkah.json");
    checkTimes(times, testData);
  }

  @Test
  void checkTehranTest() {
    double latitude = 55.75222;
    double longitude = 37.61556;
    int timezone = 3;

    PrayingTimesCalculator prayers =
        new PrayingTimesCalculator(
            LocalDate.now(), timezone, latitude, longitude, CalculationMethods.TEHRAN);
    var times = prayers.calculate();

    TestData testData = readTestData("Tehran.json");
    checkTimes(times, testData);
  }

  private void checkTimes(Times times, TestData testData) {
    SoftAssertions assertions = new SoftAssertions();

    checkTime(times.imsak(), testData.imsak(), TimeName.IMSAK, assertions);
    checkTime(times.fajr(), testData.fajr(), TimeName.FAJR, assertions);
    checkTime(times.sunrise(), testData.sunrise(), TimeName.SUNRISE, assertions);
    checkTime(times.dhuhr(), testData.dhuhr(), TimeName.DHUHR, assertions);
    checkTime(times.asr(), testData.asr(), TimeName.ASR, assertions);
    checkTime(times.sunset(), testData.sunset(), TimeName.SUNSET, assertions);
    checkTime(times.maghrib(), testData.maghrib(), TimeName.MAGHRIB, assertions);
    checkTime(times.isha(), testData.isha(), TimeName.ISHA, assertions);
    checkTime(times.midnight(), testData.midnight(), TimeName.MIDNIGHT, assertions);

    assertions.assertAll();
  }

  private void checkTime(
      OffsetDateTime calculatedTime,
      String testDataTime,
      TimeName testTimeName,
      SoftAssertions assertions) {
    var testDataHours = Integer.parseInt(testDataTime.split(":")[0]);
    var testDataMinutes = Integer.parseInt(testDataTime.split(":")[1]);

    assertions
        .assertThat(calculatedTime.getHour())
        .as(
            String.format(
                "Check that %s equals %s for %s", calculatedTime, testDataTime, testTimeName))
        .isEqualTo(testDataHours);
    assertions
        .assertThat(calculatedTime.getMinute())
        .as(
            String.format(
                "Check that %s equals %s for %s", calculatedTime, testDataTime, testTimeName))
        .isEqualTo(testDataMinutes);
  }

  private TestData readTestData(String fileName) {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is = CalculationAccurancyTest.class.getClassLoader().getResourceAsStream(fileName);

    TestData testData = null;

    try {
      testData = mapper.readValue(is, TestData.class);
    } catch (StreamReadException e) {
      System.out.println("Cannot find test data for test");
    } catch (DatabindException e) {
      System.out.println("Cannot parse test data for test");
    } catch (IOException e) {
      System.out.println("Cannot read test data for test");
    }
    return testData;
  }
}
