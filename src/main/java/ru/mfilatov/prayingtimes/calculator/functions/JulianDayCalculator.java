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

package ru.mfilatov.prayingtimes.calculator.functions;

public class JulianDayCalculator {

  /**
   * Convert Gregorian date to Julian day Algorithm by Edward Graham Richards from
   * https://en.wikipedia.org/wiki/Julian_day
   *
   * @param year
   * @param month
   * @param day
   * @return Julian day
   */
  public double getJulianDayNumberEdwardGrahamRichards(int year, int month, int day) {
    long a = (14L - month) / 12L;
    long y = year + 4800L - a;
    long m = month + 12L * a - 3L;

    return (day + ((153L * m + 2L) / 5L) + 365L * y + (y / 4L) - (y / 100L) + (y / 400L) - 32045.5);
  }

  /**
   * Convert Gregorian date to Julian day Ref: Astronomical Algorithms by Jean Meeus
   *
   * @param year
   * @param month
   * @param day
   * @return Julian day
   */
  public double getJulianDayNumberJeanMeeus(int year, int month, int day) {
    if (month <= 2) {
      year --;
      month += 12;
    }

    double A = Math.floor(year / 100.0);
    double B = 2 - A + Math.floor(A / 4.0);
    double JD =
        Math.floor(365.25 * (year + 4716)) + Math.floor(30.6001 * (month + 1)) + day + B - 1524.5;

    return JD;
  }
}
