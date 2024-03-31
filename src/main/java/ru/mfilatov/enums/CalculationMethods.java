package ru.mfilatov.enums;

import static ru.mfilatov.enums.TimeName.*;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CalculationMethods {
  JAFARI(
      "Shia Ithna-Ashari, Leva Institute, Qum",
          Map.of(FAJR, 16.0, MAGHRIB, 4.0, ISHA, 14.0),
          Map.of(IMSAK, 10.0),  MidnightMode.JAFARI),
  KARACHI(
      "University of Islamic Sciences, Karachi",
          Map.of(FAJR, 18.0, ISHA, 18.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  ISNA(
      "Islamic Society of North America (ISNA)",
          Map.of(FAJR, 15.0, ISHA, 15.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  MWL(
      "Muslim World League",
          Map.of(FAJR, 18.0, ISHA, 17.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  MAKKAH(
      "Umm Al-Qura University, Makkah",
          Map.of(FAJR, 18.5),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0, ISHA, 90.0),  MidnightMode.STANDARD),

  EGYPT(
      "Egyptian General Authority of Survey",
          Map.of(FAJR, 19.5, ISHA, 17.5),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  TEHRAN(
      "Institute of Geophysics, University of Tehran",
          Map.of(FAJR, 17.7, MAGHRIB, 4.5, ISHA, 14.0),
          Map.of(IMSAK, 10.0),  MidnightMode.JAFARI),

  GULF(
      "Gulf Region",
          Map.of(FAJR, 19.5),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0, ISHA, 90.0),  MidnightMode.STANDARD),

  KUWAIT(
      "Kuwait",
          Map.of(FAJR, 18.0, ISHA, 17.5),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  QATAR(
      "Qatar",
          Map.of(FAJR, 18.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0, ISHA, 90.0),  MidnightMode.STANDARD),

  SINGAPORE(
      "Majlis Ugama Islam Singapura, Singapore",
          Map.of(FAJR, 20.0, ISHA, 18.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  FRANCE(
      "Union Organization Islamic de France",
          Map.of(FAJR, 12.0, ISHA, 12.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  TURKEY(
      "Diyanet İşleri Başkanlığı, Turkey (experimental)",
          Map.of(FAJR, 18.0, ISHA, 17.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  RUSSIA(
      "Spiritual Administration of Muslims of Russia",
          Map.of(FAJR, 16.0, ISHA, 15.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  DUBAI(
      "Dubai (experimental)",
          Map.of(FAJR, 18.2, ISHA, 18.2),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  JAKIM(
      "Jabatan Kemajuan Islam Malaysia (JAKIM)",
          Map.of(FAJR, 20.0, ISHA, 18.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  TUNISIA(
      "Tunisia",
          Map.of(FAJR, 18.0, ISHA, 18.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  ALGERIA(
      "Algeria",
          Map.of(FAJR, 18.0, ISHA, 17.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  KEMENAG(
      "Kementerian Agama Republik Indonesia",
          Map.of(FAJR, 20.0, ISHA, 18.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD),

  MOROCCO(
      "Morocco",
          Map.of(FAJR, 19.0, ISHA, 17.0),
          Map.of(IMSAK, 10.0, MAGHRIB, 0.0),  MidnightMode.STANDARD)
  ;

  private final String name;
  private final Map<TimeName, Double> angleOffset;
  private final Map<TimeName, Double> minutesOffset;
  private final MidnightMode midnightMode;
}
