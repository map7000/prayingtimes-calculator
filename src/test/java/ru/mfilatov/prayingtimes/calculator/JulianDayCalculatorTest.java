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

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import ru.mfilatov.prayingtimes.calculator.functions.JulianDayCalculator;

public class JulianDayCalculatorTest {
  @Test
  void compareResultsWithPrecalculatedData() {
    var TEST_DAY = 8;
    var TEST_MONTH = 9;
    var TEST_YEAR = 2023;
    var TEST_RESULT = 2460195.5; // value taken from site https://ssd.jpl.nasa.gov/tools/jdc/
    var calc = new JulianDayCalculator();

    assertThat(calc.getJulianDayNumberJeanMeeus(TEST_YEAR, TEST_MONTH, TEST_DAY))
        .isEqualTo(TEST_RESULT);
    assertThat(calc.getJulianDayNumberEdwardGrahamRichards(TEST_YEAR, TEST_MONTH, TEST_DAY))
        .isEqualTo(TEST_RESULT);
  }

  @Test
  void compareAlgorithmResultsTest() {
    var calc = new JulianDayCalculator();
    var startDate = LocalDate.now();
    var daysToTest = 365;
    for (int i = 0; i < daysToTest; i++) {
      var testDate = startDate.plusDays(i);
      var year = testDate.getYear();
      var month = testDate.getMonthValue();
      var day = testDate.getDayOfMonth();

      assertThat(calc.getJulianDayNumberJeanMeeus(year, month, day))
          .isEqualTo(calc.getJulianDayNumberEdwardGrahamRichards(year, month, day));
    }
  }
}
