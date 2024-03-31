package ru.mfilatov;

import static ru.mfilatov.functions.MathFunctions.*;

import java.time.*;

import lombok.AllArgsConstructor;
import ru.mfilatov.constants.SunPositionOffsets;
import ru.mfilatov.enums.CalculationMethods;
import ru.mfilatov.enums.TimeName;
import ru.mfilatov.functions.JulianDayCalculator;
import ru.mfilatov.functions.MathFunctions;
import ru.mfilatov.functions.SunPositionCalculator;
import ru.mfilatov.functions.SunPositionCalculator.SunPosition;
import ru.mfilatov.model.Times;
import ru.mfilatov.model.TimesDouble;

@AllArgsConstructor
public class PrayingTimesCalculator {
  private static final double HOURS_IN_DAY = 24.0;

  private final LocalDate time;
  private final int timeZone;
  private final double latitude;
  private final double longitude;
  private final CalculationMethods method;
  private final JulianDayCalculator julianDayCalculator = new JulianDayCalculator();
  private final SunPositionCalculator sunPositionCalculator = new SunPositionCalculator();

  public Times calculate() {
    var julianDate =
        julianDayCalculator.getJulianDayNumberEdwardGrahamRichards(time.getYear(), time.getMonthValue(), time.getDayOfMonth())
            - longitude / (15 * 24.0);

    var computedTimes =
        computePrayerTimes(method, julianDate, latitude);

    var adjustedTimes = adjustTimes(computedTimes, timeZone, longitude);

    return convertToTimes(adjustedTimes);
  }

  public TimesDouble computePrayerTimes(CalculationMethods method, double julianDate, double latitude) {

    var sunPositionFajr = sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.FAJR / HOURS_IN_DAY);
    var sunPositionSunrise = sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.SUNRISE / HOURS_IN_DAY);
    var sunPositionDhuhr = sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.DHUHR / HOURS_IN_DAY);
    var sunPositionAsr = sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.ASR / HOURS_IN_DAY);
    var sunPositionSunset = sunPositionCalculator.usnoMethod(julianDate + SunPositionOffsets.SUNSET / HOURS_IN_DAY);

    var fajr = sunAngleTime(method.getAngleOffset().getOrDefault(TimeName.FAJR, SunPositionOffsets.FAJR), latitude, sunPositionFajr, true);

    var imsak = method.getMinutesOffset().containsKey(TimeName.IMSAK) ?
            fajr - method.getMinutesOffset().get(TimeName.IMSAK)/ 60 :
            sunAngleTime(method.getAngleOffset().getOrDefault(TimeName.IMSAK, SunPositionOffsets.FAJR), latitude, sunPositionFajr, true);

    var sunrise = sunAngleTime(riseSetAngle(0), latitude, sunPositionSunrise, true);

    var dhuhr = midDay(sunPositionDhuhr) + method.getMinutesOffset().getOrDefault(TimeName.DHUHR, 0.0) / 60.0;

    var asr = asrTime(asrFactor(method.getAngleOffset().getOrDefault(TimeName.ASR, SunPositionOffsets.ASR)), latitude, sunPositionAsr);

    var sunset = sunAngleTime(riseSetAngle(0), latitude, sunPositionSunset, false);

    var maghrib = method.getMinutesOffset().containsKey(TimeName.MAGHRIB) ?
            sunset + method.getMinutesOffset().get(TimeName.MAGHRIB) / 60 :
            sunAngleTime(method.getAngleOffset().getOrDefault(TimeName.MAGHRIB, SunPositionOffsets.SUNSET), latitude, sunPositionSunset, false);

    var isha = method.getMinutesOffset().containsKey(TimeName.ISHA) ?
            maghrib + method.getMinutesOffset().get(TimeName.ISHA) / 60 :
            sunAngleTime(method.getAngleOffset().getOrDefault(TimeName.ISHA, SunPositionOffsets.SUNSET), latitude, sunPositionSunset, false);

    var midnight = sunset + timeDiff(sunset, sunrise) / 2;

    return new TimesDouble(imsak, fajr, sunrise, dhuhr, asr, sunset, maghrib, isha, midnight);
  }

  // compute mid-day time
  public double midDay(SunPosition sunPosition) {
    return MathFunctions.fixHour(12 - sunPosition.equation());
  }

  // return sun angle for sunset/sunrise
  public double riseSetAngle(double time) {
    var elevation = time;

    return 0.833 + 0.0347 * Math.sqrt(elevation);
  }

  // get asr shadow factor
  public double asrFactor(double time) {
    //    def asrFactor(self, asrParam):
    //        methods = {'Standard': 1, 'Hanafi': 2}
    //        return methods[asrParam] if asrParam in methods else self.eval(asrParam)

    return 1;
  }

  // compute the time at which sun reaches a specific angle below horizon
  public double sunAngleTime(
      double angle, double latitude, SunPosition sunPosition, boolean directionCCW) {

    var declination = sunPosition.declination();
    var noon = midDay(sunPosition);
    var t = 1 / 15.0 * acos(
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

    var result = OffsetDateTime.of(LocalDateTime.of(this.time, LocalTime.of(hours, minutes)), ZoneOffset.ofHours(timeZone));

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
            getFormattedTime(times.midnight())
    );
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
}
