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

import static ru.mfilatov.prayingtimes.calculator.functions.MathFunctions.*;

import java.time.*;
import lombok.AllArgsConstructor;
import ru.mfilatov.prayingtimes.calculator.constants.SunPositionOffsets;
import ru.mfilatov.prayingtimes.calculator.enums.*;
import ru.mfilatov.prayingtimes.calculator.functions.JulianDayCalculator;
import ru.mfilatov.prayingtimes.calculator.functions.MathFunctions;
import ru.mfilatov.prayingtimes.calculator.functions.SunPositionCalculator;
import ru.mfilatov.prayingtimes.calculator.functions.SunPositionCalculator.SunPosition;
import ru.mfilatov.prayingtimes.calculator.model.Times;
import ru.mfilatov.prayingtimes.calculator.model.TimesDouble;
import ru.mfilatov.prayingtimes.calculator.model.TimesDoubleBuilder;

@AllArgsConstructor
public class PrayingTimesCalculator {
  private static final double HOURS_IN_DAY = 24.0;
  private static final double SUNRISE_SUNSET_ANGLE = 0.833;

  private final JulianDayCalculator julianDayCalculator = new JulianDayCalculator();
  private final SunPositionCalculator sunPositionCalculator = new SunPositionCalculator();

  private final LocalDate time;
  private final int timeZone;
  private final double latitude;
  private final double longitude;
  private final CalculationMethods method;


  public Times calculate() {
    var julianDate =
        julianDayCalculator.getJulianDayNumberEdwardGrahamRichards(
                time.getYear(), time.getMonthValue(), time.getDayOfMonth())
            - longitude / (15 * 24.0);

    var computedTimes = computePrayerTimes(method, julianDate, latitude);

    var adjustedTimes = adjustTimes(computedTimes, timeZone, longitude);

    var adjustedTimesHighLats =
        adjustHighLats(adjustedTimes, method.getAdjustForHigherLatitudes(), method);

    var minAdjustTimes = applyOffsets(adjustedTimesHighLats, method);

    var timesWithMidnight = calculateMidnight(minAdjustTimes, method);

    return convertToTimes(timesWithMidnight);
  }

  public TimesDouble computePrayerTimes(
      CalculationMethods method, double julianDate, double latitude) {

    var sunPositionFajr =
        sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.FAJR / HOURS_IN_DAY);
    var sunPositionSunrise =
        sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.SUNRISE / HOURS_IN_DAY);
    var sunPositionDhuhr =
        sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.DHUHR / HOURS_IN_DAY);
    var sunPositionAsr =
        sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.ASR / HOURS_IN_DAY);
    var sunPositionSunset =
        sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.SUNSET / HOURS_IN_DAY);

    var fajr =
        sunAngleTime(
            method.getAngleOffset().getOrDefault(TimeName.FAJR, SunPositionOffsets.FAJR),
            latitude,
            sunPositionFajr,
            true);

    var imsak =
        method.getMinutesOffset().containsKey(TimeName.IMSAK)
            ? fajr
            : sunAngleTime(
                method.getAngleOffset().getOrDefault(TimeName.IMSAK, SunPositionOffsets.FAJR),
                latitude,
                sunPositionFajr,
                true);

    var sunrise = sunAngleTime(SUNRISE_SUNSET_ANGLE, latitude, sunPositionSunrise, true);

    var dhuhr =
        midDay(sunPositionDhuhr)
            + method.getMinutesOffset().getOrDefault(TimeName.DHUHR, 0.0) / 60.0;

    var asr = asrTime(asrFactor(method.getAsrJuristicMethod()), latitude, sunPositionAsr);

    var sunset = sunAngleTime(SUNRISE_SUNSET_ANGLE, latitude, sunPositionSunset, false);

    var maghrib =
        method.getMinutesOffset().containsKey(TimeName.MAGHRIB)
            ? sunset
            : sunAngleTime(
                method.getAngleOffset().getOrDefault(TimeName.MAGHRIB, SunPositionOffsets.SUNSET),
                latitude,
                sunPositionSunset,
                false);

    var isha =
        method.getMinutesOffset().containsKey(TimeName.ISHA)
            ? maghrib
            : sunAngleTime(
                method.getAngleOffset().getOrDefault(TimeName.ISHA, SunPositionOffsets.SUNSET),
                latitude,
                sunPositionSunset,
                false);

    return new TimesDouble(imsak, fajr, sunrise, dhuhr, asr, sunset, maghrib, isha, 0);
  }

  // compute mid-day time
  public double midDay(SunPosition sunPosition) {
    return MathFunctions.fixHour(12 - sunPosition.equation());
  }

  // get asr shadow factor
  public double asrFactor(AsrJuristicMethod asrJuristicMethod) {
    return asrJuristicMethod.equals(AsrJuristicMethod.STANDARD) ? 1 : 2;
  }

  public TimesDouble calculateMidnight(TimesDouble times, CalculationMethods method) {

    var midnight =
        method.getMidnightMode().equals(MidnightMode.JAFARI)
            ? times.sunset() + timeDiff(times.sunset(), times.fajr()) / 2
            : times.sunset() + timeDiff(times.sunset(), times.sunrise()) / 2;

    return new TimesDoubleBuilder(times).midnight(midnight).build();
  }

  // compute the time at which sun reaches a specific angle below horizon
  public double sunAngleTime(
      double angle, double latitude, SunPosition sunPosition, boolean directionCCW) {

    var declination = sunPosition.declination();
    var noon = midDay(sunPosition);
    var t =
        1
            / 15.0
            * acos(
                (-sin(angle) - sin(declination) * sin(latitude))
                    / (cos(declination) * cos(latitude)));

    return noon + (directionCCW ? -t : t);
  }

  // compute asr time
  public double asrTime(double factor, double latitude, SunPosition sunPosition) {
    var declination = sunPosition.declination();
    var angle = -acot(factor + tan(Math.abs(latitude - declination)));

    return sunAngleTime(angle, latitude, sunPosition, false);
  }

  public OffsetDateTime getFormattedTime(double timeFloat) {
    var time = timeFloat + 0.5 / 60;
    boolean isNextDay = timeFloat > 24;
    time = fixHour(time);

    int hours = (int) Math.floor(time);
    int minutes = (int) Math.floor((time - hours) * 60);

    var result =
        OffsetDateTime.of(
            LocalDateTime.of(this.time, LocalTime.of(hours, minutes)),
            ZoneOffset.ofHours(timeZone));

    if (isNextDay) {
      result = result.plusDays(1L);
    }

    return result;
  }

  private Times convertToTimes(TimesDouble times) {
    return new Times(
        getFormattedTime(times.imsak()),
        getFormattedTime(times.fajr()),
        getFormattedTime(times.sunrise()),
        getFormattedTime(times.dhuhr()),
        getFormattedTime(times.asr()),
        getFormattedTime(times.sunset()),
        getFormattedTime(times.maghrib()),
        getFormattedTime(times.isha()),
        getFormattedTime(times.midnight()));
  }

  public TimesDouble adjustTimes(TimesDouble times, double timeZone, double longitude) {
    var tzAdjust = timeZone - longitude / 15.0;

    return new TimesDouble(
        times.imsak() + tzAdjust,
        times.fajr() + tzAdjust,
        times.sunrise() + tzAdjust,
        times.dhuhr() + tzAdjust,
        times.asr() + tzAdjust,
        times.sunset() + tzAdjust,
        times.maghrib() + tzAdjust,
        times.isha() + tzAdjust,
        times.midnight() + tzAdjust);
  }

  // adjust times for locations in higher latitudes
  public TimesDouble adjustHighLats(
      TimesDouble times,
      AdjustForHigherLatitudes adjustForHigherLatitudes,
      CalculationMethods method) {
    var nightTime = timeDiff(times.sunset(), times.sunrise());

    return new TimesDouble(
        adjustHLTime(
            times.imsak(),
            times.sunrise(),
            method.getMinutesOffset().containsKey(TimeName.IMSAK)
                ? method.getMinutesOffset().get(TimeName.IMSAK)
                : method.getAngleOffset().get(TimeName.IMSAK),
            nightTime,
            true,
            adjustForHigherLatitudes),
        adjustHLTime(
            times.fajr(),
            times.sunrise(),
            method.getMinutesOffset().containsKey(TimeName.FAJR)
                ? method.getMinutesOffset().get(TimeName.FAJR)
                : method.getAngleOffset().get(TimeName.FAJR),
            nightTime,
            true,
            adjustForHigherLatitudes),
        times.sunrise(),
        times.dhuhr(),
        times.asr(),
        times.sunset(),
        adjustHLTime(
            times.maghrib(),
            times.sunset(),
            SunPositionOffsets.SUNSET,
            nightTime,
            false,
            adjustForHigherLatitudes),
        adjustHLTime(
            times.isha(),
            times.sunset(),
            SunPositionOffsets.SUNSET,
            nightTime,
            false,
            adjustForHigherLatitudes),
        times.midnight());
  }

  // the night portion used for adjusting times in higher latitudes
  public double nightPortion(
      double angle, double nightTime, AdjustForHigherLatitudes adjustForHigherLatitudes) {
    if (adjustForHigherLatitudes.equals(AdjustForHigherLatitudes.ANGLE_BASED)) {
      return nightTime / (60.0 * angle);
    }
    if (adjustForHigherLatitudes.equals(AdjustForHigherLatitudes.ONE_SEVENTH)) {
      return nightTime / 7.0;
    }
    return nightTime / 2.0;
  }

  public double adjustHLTime(
      double time,
      double base,
      double angle,
      double night,
      boolean directionCCW,
      AdjustForHigherLatitudes adjustForHigherLatitudes) {
    var portion = nightPortion(angle, night, adjustForHigherLatitudes);
    var timeDiff = directionCCW ? timeDiff(time, base) : timeDiff(base, time);
    if (Double.isNaN(time) || timeDiff > portion) {
      time = base + (directionCCW ? -portion : portion);
    }
    return time;
  }

  public TimesDouble applyOffsets(TimesDouble times, CalculationMethods method) {
    var imsak =
        method.getMinutesOffset().containsKey(TimeName.IMSAK)
            ? times.fajr() - method.getMinutesOffset().get(TimeName.IMSAK) / 60
            : times.imsak();

    var maghrib =
        method.getMinutesOffset().containsKey(TimeName.MAGHRIB)
            ? times.sunset() + method.getMinutesOffset().get(TimeName.MAGHRIB) / 60
            : times.maghrib();

    var isha =
        method.getMinutesOffset().containsKey(TimeName.ISHA)
            ? maghrib + method.getMinutesOffset().get(TimeName.ISHA) / 60
            : times.isha();

    return new TimesDoubleBuilder(times).imsak(imsak).maghrib(maghrib).isha(isha).build();
  }
}
