package ru.mfilatov;

import static ru.mfilatov.functions.MathFunctions.acos;
import static ru.mfilatov.functions.MathFunctions.acot;
import static ru.mfilatov.functions.MathFunctions.cos;
import static ru.mfilatov.functions.MathFunctions.fixHour;
import static ru.mfilatov.functions.MathFunctions.sin;
import static ru.mfilatov.functions.MathFunctions.tan;

import lombok.extern.slf4j.Slf4j;
import ru.mfilatov.enums.CalculationMethods;
import ru.mfilatov.functions.JulianDayCalculator;
import ru.mfilatov.functions.MathFunctions;
import ru.mfilatov.functions.SunPositionCalculator;
import ru.mfilatov.functions.Times;

@Slf4j
public class Application {

  public static JulianDayCalculator julianDayCalculator = new JulianDayCalculator();

  public static SunPositionCalculator sunPositionCalculator = new SunPositionCalculator();

  public static void main(String[] args) {

    var latitude = 55.75222;

    var longitude = 37.61556;

    var timeZone = 3;

    var julianDate =
        julianDayCalculator.getJulianDayNumberEdwardGrahamRichards(2024, 3, 14)
            - longitude / (15 * 24.0);

    var defaultTimes = new Times(5, 5, 6, 12, 13, 18, 18, 18);

    var localTimes = CalculationMethods.RUSSIA.getTimes();

    var computedTimes =
        computePrayerTimes(defaultTimes, CalculationMethods.RUSSIA, julianDate, latitude);

    var adjustedTimes = adjustTimes(computedTimes, timeZone, longitude);

    log.info("{}", adjustTimes(computedTimes, timeZone, longitude));

    log.info("{}", computedTimes);

    log.info(getFormattedTime(adjustedTimes.fajr()));

    log.info(getFormattedTime(adjustedTimes.sunrise()));

    log.info(getFormattedTime(adjustedTimes.dhuhr()));

    log.info(getFormattedTime(adjustedTimes.asr()));

    log.info(getFormattedTime(adjustedTimes.sunset()));

    log.info(getFormattedTime(adjustedTimes.maghrib()));

    log.info(getFormattedTime(adjustedTimes.isha()));
  }

  public static Times computePrayerTimes(
      Times times, CalculationMethods method, double julianDate, double latitude) {

    var portionTimes = MathFunctions.dayPortion(times);

    var imsak = sunAngleTime(method.getTimes().imsak(), portionTimes.imsak(), julianDate, true);

    var fajr = sunAngleTime(method.getTimes().fajr(), portionTimes.fajr(), julianDate, true);

    var sunrise = sunAngleTime(riseSetAngle(0), portionTimes.sunrise(), julianDate, true);

    var dhuhr = midDay(julianDate, portionTimes.dhuhr());

    var asr = asrTime(asrFactor(portionTimes.asr()), portionTimes.asr(), julianDate);

    var sunset = sunAngleTime(riseSetAngle(0), portionTimes.sunset(), julianDate, false);

    var maghrib =
        sunAngleTime(method.getTimes().maghrib(), portionTimes.maghrib(), julianDate, false);

    var isha = sunAngleTime(method.getTimes().isha(), portionTimes.isha(), julianDate, false);

    return new Times(imsak, fajr, sunrise, dhuhr, asr, sunset, maghrib, isha);
  }

  // compute mid-day time

  public static double midDay(double julianDate, double time) {

    var eqt = sunPositionCalculator.usnoMethod(julianDate + time);

    return MathFunctions.fixHour(12 - eqt.equation());
  }

  // return sun angle for sunset/sunrise

  public static double riseSetAngle(double time) {
    var elevation = time;

    return 0.833 + 0.0347 * Math.sqrt(elevation);
  }

  // get asr shadow factor

  public static double asrFactor(double time) {

    //    def asrFactor(self, asrParam):

    //        methods = {'Standard': 1, 'Hanafi': 2}

    //        return methods[asrParam] if asrParam in methods else self.eval(asrParam)

    return 1;
  }

  // compute the time at which sun reaches a specific angle below horizon

  public static double sunAngleTime(
      double angle, double time, double julianDate, boolean directionCCW) {

    var latitude = 55.75222;

    var declination = sunPositionCalculator.usnoMethod(julianDate + time).declination();

    var noon = midDay(julianDate, time);

    var t =
        1
            / 15.0
            * acos(
                (-sin(angle) - sin(declination) * sin(latitude))
                    / (cos(declination) * cos(latitude)));

    return noon + (directionCCW ? -t : t);
  }

  // compute asr time

  public static double asrTime(double factor, double time, double julianDate) {

    var latitude = 55.75222;

    var declination = sunPositionCalculator.usnoMethod(julianDate + time).declination();

    var angle = -acot(factor + tan(Math.abs(latitude - declination)));

    return sunAngleTime(angle, time, julianDate, false);
  }

  public static String getFormattedTime(double timeFloat) {

    var time = fixHour(timeFloat + 0.5 / 60);

    var hours = Math.floor(time);

    var minutes = Math.floor((time - hours) * 60);

    return String.format("%02d:%02d", Math.round(hours), Math.round(minutes));
  }

  public static Times adjustTimes(Times times, double timeZone, double longitude) {

    var tzAdjust = timeZone - longitude / 15.0;

    return new Times(
        times.imsak() + tzAdjust,
        times.fajr() + tzAdjust,
        times.sunrise() + tzAdjust,
        times.dhuhr() + tzAdjust,
        times.asr() + tzAdjust,
        times.sunset() + tzAdjust,
        times.maghrib() + tzAdjust,
        times.isha() + tzAdjust);
  }
}
