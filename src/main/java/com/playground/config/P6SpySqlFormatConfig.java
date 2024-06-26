package com.playground.config;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Predicate;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

@Configuration
public class P6SpySqlFormatConfig implements MessageFormattingStrategy {
  private static final String NEW_LINE = System.lineSeparator();
  private static final String PACKAGE = "com.playground"; // 패키지를 설정해야 메서드 스택을 확인할 수 있다.
  private static final String CREATE = "create";
  private static final String ALTER = "alter";
  private static final String COMMENT = "comment";
  private static final String SEPARATOR_HYP_STRING =
      "----------------------------------------------------------------------------------------------------";
  private static final String SEPARATOR_EQL_STRING =
      "====================================================================================================";
  private static final String TAB = "    ";

  @Override
  public String formatMessage(final int connectionId, final String now, final long elapsed, final String category, final String prepared,
      final String sql, final String url) {
    return sqlFormatToUpper(sql, category, getMessage(connectionId, elapsed, getStackBuilder()));
  }

  private String sqlFormatToUpper(final String sql, final String category, final String message) {
    if (sql.trim().isEmpty()) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    sb.append(NEW_LINE);
    sb.append(SEPARATOR_EQL_STRING);
    sb.append(NEW_LINE);
    sb.append(" Query");
    sb.append(NEW_LINE);
    sb.append(SEPARATOR_HYP_STRING);
    sb.append(sqlFormatToUpper(sql, category));
    sb.append(";");
    sb.append(message);

    return sb.toString();
  }

  private String sqlFormatToUpper(final String sql, final String category) {
    if (isStatementDDL(sql, category)) {
      return FormatStyle.DDL.getFormatter().format(sql).toUpperCase(Locale.ROOT).replace("+0900", "");
    }

    return FormatStyle.BASIC.getFormatter().format(sql).replace("+0900", "");
  }

  private boolean isStatementDDL(final String sql, final String category) {
    return isStatement(category) && isDDL(sql.trim().toLowerCase(Locale.ROOT));
  }

  private boolean isStatement(final String category) {
    return Category.STATEMENT.getName().equals(category);
  }

  private boolean isDDL(final String lowerSql) {
    return lowerSql.startsWith(CREATE) || lowerSql.startsWith(ALTER) || lowerSql.startsWith(COMMENT);
  }

  private String getMessage(final int connectionId, final long elapsed, final StringBuilder callStackBuilder) {
    StringBuilder sb = new StringBuilder();

    sb.append(NEW_LINE);
    sb.append(SEPARATOR_HYP_STRING);
    sb.append(NEW_LINE);
    sb.append(" Info");
    sb.append(NEW_LINE);
    sb.append(SEPARATOR_HYP_STRING);
    sb.append(NEW_LINE);
    sb.append(TAB);
    sb.append(String.format("Connection ID : %s", connectionId));
    sb.append(NEW_LINE);
    sb.append(TAB);
    sb.append(String.format("Execution Time : %s ms", elapsed));
    sb.append(NEW_LINE);
    sb.append(NEW_LINE);
    sb.append(TAB);
    sb.append(String.format("Call Stack : %s", callStackBuilder));
    sb.append(NEW_LINE);
    sb.append(SEPARATOR_EQL_STRING);
    sb.append(NEW_LINE);

    return sb.toString();
  }

  private StringBuilder getStackBuilder() {
    StringBuilder callStackBuilder = new StringBuilder();

    Arrays.stream(new Throwable().getStackTrace()).map(StackTraceElement::toString).filter(isExcludeWords())
        .forEach(stack -> callStackBuilder.append(NEW_LINE).append(TAB).append(TAB).append(stack));

    return callStackBuilder;
  }

  private Predicate<String> isExcludeWords() {
    return charSequence -> charSequence.startsWith(PACKAGE) && !charSequence.contains(this.getClass().getName());
  }
}
