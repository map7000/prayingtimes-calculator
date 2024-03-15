package ru.mfilatov.enums;

import static ru.mfilatov.constants.DefaultComputeTimes.DEFAULT_ASR;
import static ru.mfilatov.constants.DefaultComputeTimes.DEFAULT_FAJR;
import static ru.mfilatov.constants.DefaultComputeTimes.DEFAULT_IMSAK;
import static ru.mfilatov.constants.DefaultComputeTimes.DEFAULT_ISHA;
import static ru.mfilatov.constants.DefaultComputeTimes.DEFAULT_MAGHRIB;
import static ru.mfilatov.constants.DefaultComputeTimes.DEFAULT_SUNRISE;
import static ru.mfilatov.constants.DefaultComputeTimes.DEFAULT_SUNSET;
import static ru.mfilatov.constants.DefaultComputeTimes.DEFAULT_ZHUHR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mfilatov.functions.Times;

@Getter
@AllArgsConstructor
public enum CalculationMethods {
  JAFARI(
      "Shia Ithna-Ashari, Leva Institute, Qum",
      new Times(
          DEFAULT_IMSAK, 16, DEFAULT_SUNRISE, DEFAULT_ZHUHR, DEFAULT_ASR, DEFAULT_SUNSET, 4, 14)),

  KARACHI(
      "University of Islamic Sciences, Karachi",
      new Times(
          DEFAULT_IMSAK,
          18,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          18)),

  ISNA(
      "Islamic Society of North America (ISNA)",
      new Times(
          DEFAULT_IMSAK,
          15,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          15)),

  MWL(
      "Muslim World League",
      new Times(
          DEFAULT_IMSAK,
          18,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          17)),

  MAKKAH(
      "Umm Al-Qura University, Makkah",
      new Times(
          DEFAULT_IMSAK,
          18.5,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          90)),

  EGYPT(
      "Egyptian General Authority of Survey",
      new Times(
          DEFAULT_IMSAK,
          19.5,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          17.5)),

  TEHRAN(
      "Institute of Geophysics, University of Tehran",
      new Times(
          DEFAULT_IMSAK,
          17.7,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          4.5,
          14)),

  GULF(
      "Gulf Region",
      new Times(
          DEFAULT_IMSAK,
          19.5,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          90)),

  KUWAIT(
      "Kuwait",
      new Times(
          DEFAULT_IMSAK,
          18,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          17.5)),

  QATAR(
      "Qatar",
      new Times(
          DEFAULT_IMSAK,
          18,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          90)),

  SINGAPORE(
      "Majlis Ugama Islam Singapura, Singapore",
      new Times(
          DEFAULT_IMSAK,
          20,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          18)),

  FRANCE(
      "Union Organization Islamic de France",
      new Times(
          DEFAULT_IMSAK,
          12,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          12)),

  TURKEY(
      "Diyanet İşleri Başkanlığı, Turkey (experimental)",
      new Times(
          DEFAULT_IMSAK,
          18,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          17)),

  RUSSIA(
      "Spiritual Administration of Muslims of Russia",
      new Times(
          DEFAULT_IMSAK,
          16,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          15)),

  MOONSIGHTING(
      "Moonsighting Committee Worldwide (Moonsighting.com)",
      new Times(
          DEFAULT_IMSAK,
          DEFAULT_FAJR,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          DEFAULT_ISHA)),

  DUBAI(
      "Dubai (experimental)",
      new Times(
          DEFAULT_IMSAK,
          18.2,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          18.2)),

  JAKIM(
      "Jabatan Kemajuan Islam Malaysia (JAKIM)",
      new Times(
          DEFAULT_IMSAK,
          20,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          18)),

  TUNISIA(
      "Tunisia",
      new Times(
          DEFAULT_IMSAK,
          18,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          18)),

  ALGERIA(
      "Algeria",
      new Times(
          DEFAULT_IMSAK,
          18,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          17)),

  KEMENAG(
      "Kementerian Agama Republik Indonesia",
      new Times(
          DEFAULT_IMSAK,
          20,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          18)),

  MOROCCO(
      "Morocco",
      new Times(
          DEFAULT_IMSAK,
          19,
          DEFAULT_SUNRISE,
          DEFAULT_ZHUHR,
          DEFAULT_ASR,
          DEFAULT_SUNSET,
          DEFAULT_MAGHRIB,
          17));

  private final String name;

  private final Times times;
}

record CalculationMethodsParams(
    double imsak, double fajr, double dhuhr, double asr, double maghrib, double isha) {}
