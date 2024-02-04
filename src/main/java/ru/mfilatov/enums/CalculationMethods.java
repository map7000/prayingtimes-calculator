package ru.mfilatov.enums;

import static ru.mfilatov.constants.DefaultComputeTimes.*;

public enum CalculationMethods {
  JAFARI("Shia Ithna-Ashari, Leva Institute, Qum",
          new CalculationMethodsParams(DEFAULT_IMSAK, 16, DEFAULT_ZHUHR,DEFAULT_ASR, 4, 14)),
  KARACHI("University of Islamic Sciences, Karachi",
          new CalculationMethodsParams(DEFAULT_IMSAK, 18, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 18)),
  ISNA("Islamic Society of North America (ISNA)",
          new CalculationMethodsParams(DEFAULT_IMSAK, 15, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 15)),
  MWL("Muslim World League",
          new CalculationMethodsParams(DEFAULT_IMSAK, 18, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 17)),
  MAKKAH("Umm Al-Qura University, Makkah",
          new CalculationMethodsParams(DEFAULT_IMSAK, 18.5, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 90)),
  EGYPT("Egyptian General Authority of Survey",
          new CalculationMethodsParams(DEFAULT_IMSAK, 19.5, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 17.5)),
  TEHRAN("Institute of Geophysics, University of Tehran",
          new CalculationMethodsParams(DEFAULT_IMSAK, 17.7, DEFAULT_ZHUHR,DEFAULT_ASR, 4.5, 14)),
  GULF("Gulf Region",
          new CalculationMethodsParams(DEFAULT_IMSAK, 19.5, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 90)),
  KUWAIT("Kuwait",
          new CalculationMethodsParams(DEFAULT_IMSAK, 18, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 17.5)),
  QATAR("Qatar",
          new CalculationMethodsParams(DEFAULT_IMSAK, 18, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 90)),
  SINGAPORE("Majlis Ugama Islam Singapura, Singapore",
          new CalculationMethodsParams(DEFAULT_IMSAK, 20, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 18)),
  FRANCE("Union Organization Islamic de France",
          new CalculationMethodsParams(DEFAULT_IMSAK, 12, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 12)),
  TURKEY("Diyanet İşleri Başkanlığı, Turkey (experimental)",
          new CalculationMethodsParams(DEFAULT_IMSAK, 18, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 17)),
  RUSSIA("Spiritual Administration of Muslims of Russia",
          new CalculationMethodsParams(DEFAULT_IMSAK, 16, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 15)),
  MOONSIGHTING("Moonsighting Committee Worldwide (Moonsighting.com)",
          new CalculationMethodsParams(DEFAULT_IMSAK, DEFAULT_FAJR, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, DEFAULT_ISHA)),
  DUBAI("Dubai (experimental)",
          new CalculationMethodsParams(DEFAULT_IMSAK, 18.2, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 18.2)),
  JAKIM("Jabatan Kemajuan Islam Malaysia (JAKIM)",
          new CalculationMethodsParams(DEFAULT_IMSAK, 20, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 18)),
  TUNISIA("Tunisia",
          new CalculationMethodsParams(DEFAULT_IMSAK, 18, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 18)),
  ALGERIA("Algeria",
          new CalculationMethodsParams(DEFAULT_IMSAK, 18, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 17)),
  KEMENAG("Kementerian Agama Republik Indonesia",
          new CalculationMethodsParams(DEFAULT_IMSAK, 20, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 18)),
  MOROCCO("Morocco",
          new CalculationMethodsParams(DEFAULT_IMSAK, 19, DEFAULT_ZHUHR,DEFAULT_ASR, DEFAULT_MAGHRIB, 17));

private final String name;
private final CalculationMethodsParams params;

  CalculationMethods(String name, ru.mfilatov.enums.CalculationMethodsParams params) {
  this.name = name;
  this.params = params;
}
}

record CalculationMethodsParams(
        double imsak,
        double fajr,
        double dhuhr,
        double asr,
        double maghrib,
        double isha) {}