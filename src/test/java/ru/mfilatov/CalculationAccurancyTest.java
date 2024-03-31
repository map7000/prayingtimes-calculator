package ru.mfilatov;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.mfilatov.enums.CalculationMethods;
import ru.mfilatov.enums.TimeName;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Slf4j
public class CalculationAccurancyTest {
  @Test
  void checkMoscowTimeTest() {
        double latitude = 55.75222;
        double longitude = 37.61556;
        int timezone = 3;

    PrayingTimesCalculator prayers = new PrayingTimesCalculator(LocalDate.now(), timezone, latitude, longitude, CalculationMethods.RUSSIA);
    var times = prayers.calculate();
    log.info("{}: {}", TimeName.IMSAK, times.imsak());
    log.info("{}: {}", TimeName.FAJR, times.fajr());
    log.info("{}: {}", TimeName.SUNRISE, times.sunrise());
    log.info("{}: {}", TimeName.DHUHR, times.dhuhr());
    log.info("{}: {}", TimeName.ASR, times.asr());
    log.info("{}: {}", TimeName.SUNSET, times.sunset());
    log.info("{}: {}", TimeName.MAGHRIB, times.maghrib());
    log.info("{}: {}", TimeName.ISHA, times.isha());
    log.info("{}: {}", TimeName.MIDNIGHT, times.midnight());
    }
}