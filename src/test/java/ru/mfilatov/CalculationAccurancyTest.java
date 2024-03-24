package ru.mfilatov;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.mfilatov.enums.CalculationMethods;
import ru.mfilatov.enums.TimeName;

import java.time.OffsetDateTime;

@Slf4j
public class CalculationAccurancyTest {

  //  imsak: '04:18',
  //  fajr: '04:28',
  //  sunrise: '06:21',
  //  dhuhr: '12:36',
  //  asr: '15:55',
  //  sunset: '18:52',
  //  maghrib: '18:52',
  //  isha: '20:37',
  //  midnight: '00:36'
  @Test
  void checkMoscowTimeTest() {
        double latitude = 55.75222;
        double longitude = 37.61556;
        int timezone = 3;

    PrayingTimesCalculator prayers = new PrayingTimesCalculator(OffsetDateTime.now(), timezone, latitude, longitude, CalculationMethods.RUSSIA);
    var times = prayers.calculate();
    log.info("{}: {}", TimeName.IMSAK, prayers.getFormattedTime(times.imsak()));
    log.info("{}: {}", TimeName.FAJR, prayers.getFormattedTime(times.fajr()));
    log.info("{}: {}", TimeName.SUNRISE, prayers.getFormattedTime(times.sunrise()));
    log.info("{}: {}", TimeName.DHUHR, prayers.getFormattedTime(times.dhuhr()));
    log.info("{}: {}", TimeName.ASR, prayers.getFormattedTime(times.asr()));
    log.info("{}: {}", TimeName.SUNSET, prayers.getFormattedTime(times.sunset()));
    log.info("{}: {}", TimeName.MAGHRIB, prayers.getFormattedTime(times.maghrib()));
    log.info("{}: {}", TimeName.ISHA, prayers.getFormattedTime(times.isha()));
    log.info("{}: {}", TimeName.MIDNIGHT, prayers.getFormattedTime(times.midnight()));
    }
}