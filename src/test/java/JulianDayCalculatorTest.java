import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.mfilatov.functions.JulianDayCalculator;

@Slf4j
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
  void compareAlgorithmResults() {
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
}
