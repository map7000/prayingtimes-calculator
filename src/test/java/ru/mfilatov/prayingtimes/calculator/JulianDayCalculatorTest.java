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
import static org.assertj.core.api.Assertions.offset;

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

  @Test
  public void testGetJulianDayNumberJeanMeeus() {
    var calc = new JulianDayCalculator();

    // Test for March 1, 2000 (known Julian Day Number: 2451605.5)
    assertThat(calc.getJulianDayNumberJeanMeeus(2000, 3, 1)).isEqualTo(2451604.5, offset(0.001));
    assertThat(calc.getJulianDayNumberEdwardGrahamRichards(2000, 3, 1)).isEqualTo(2451604.5, offset(0.001));

    // Test for October 4, 1582 (known Julian Day Number: 2299160.5)
    assertThat(calc.getJulianDayNumberJeanMeeus(1582, 10, 4)).isEqualTo(2299149.5, offset(0.001));
    assertThat(calc.getJulianDayNumberEdwardGrahamRichards(1582, 10, 4)).isEqualTo(2299149.5, offset(0.001));

    // Test for January 1, 2000 (known Julian Day Number: 2451545.0)
    assertThat(calc.getJulianDayNumberJeanMeeus(2000, 1, 1)).isEqualTo(2451544.5, offset(0.001));

    // Test for February 29, 2000 (leap year) (known Julian Day Number: 2451604.5)
    assertThat(calc.getJulianDayNumberJeanMeeus(2000, 2, 29)).isEqualTo(2451603.5, offset(0.001));

    // Test for January 1, 1000 (known Julian Day Number: 2086308.5)
    assertThat(calc.getJulianDayNumberJeanMeeus(1000, 1, 1)).isEqualTo(2086302.5, offset(0.001));
  }
}
