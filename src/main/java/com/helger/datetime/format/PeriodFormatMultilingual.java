/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.datetime.format;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.helger.commons.annotations.PresentForCodeCoverage;
import com.helger.commons.annotations.Translatable;
import com.helger.commons.name.IHasDisplayText;
import com.helger.commons.text.impl.TextProvider;
import com.helger.commons.text.resolve.DefaultTextResolver;

/**
 * Factory that creates instances of PeriodFormatter.
 * <p>
 * Period formatting is performed by the {@link PeriodFormatter} class.
 * <p>
 * PeriodFormat is thread-safe and immutable, and the formatters it returns are
 * as well.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public final class PeriodFormatMultilingual
{
  @Translatable
  private static enum EText implements IHasDisplayText
  {
    LONG_YEAR_SINGULAR (" Jahr", " year"),
    LONG_YEAR_PLURAL (" Jahre", " years"),
    LONG_MONTH_SINGULAR (" Monat", " month"),
    LONG_MONTH_PLURAL (" Monate", " month"),
    LONG_WEEK_SINGULAR (" Woche", " week"),
    LONG_WEEK_PLURAL (" Wochen", " weeks"),
    LONG_DAY_SINGULAR (" Tag", " day"),
    LONG_DAY_PLURAL (" Tage", " days"),
    LONG_HOUR_SINGULAR (" Stunde", " hours"),
    LONG_HOUR_PLURAL (" Stunden", " hours"),
    LONG_MINUTE_SINGULAR (" Minute", " minute"),
    LONG_MINUTE_PLURAL (" Minuten", " minutes"),
    LONG_SECOND_SINGULAR (" Sekunde", " second"),
    LONG_SECOND_PLURAL (" Sekunden", " seconds"),
    LONG_MILLISECOND_SINGULAR (" Millisekunde", " millisecond"),
    LONG_MILLISECOND_PLURAL (" Millisekunden", " millisecond"),
    LONG_SEPARATOR_FRONT (", ", ", "),
    LONG_SEPARATOR_LAST (" und ", " and "),
    SHORT_YEAR (" J", " y"),
    SHORT_MONTH (" M", " m"),
    SHORT_WEEK (" W", " w"),
    SHORT_DAY (" T", " d"),
    SHORT_HOUR (" Std", " h"),
    SHORT_MINUTE (" Min", " min"),
    SHORT_SECOND (" Sek", " sec"),
    SHORT_MILLISECOND (" Millisek", " millisec");

    private final TextProvider m_aTP;

    private EText (@Nonnull final String sDE, @Nonnull final String sEN)
    {
      m_aTP = TextProvider.create_DE_EN (sDE, sEN);
    }

    public String getDisplayText (@Nonnull final Locale aContentLocale)
    {
      return DefaultTextResolver.getText (this, m_aTP, aContentLocale);
    }
  }

  @PresentForCodeCoverage
  @SuppressWarnings ("unused")
  private static final PeriodFormatMultilingual s_aInstance = new PeriodFormatMultilingual ();

  /**
   * Constructor.
   */
  private PeriodFormatMultilingual ()
  {}

  @Nonnull
  private static String [] _getSeparatorVariants (final Locale aContentLocale)
  {
    final String sSepFront = EText.LONG_SEPARATOR_FRONT.getDisplayText (aContentLocale);
    final String sSepFinal = EText.LONG_SEPARATOR_LAST.getDisplayText (aContentLocale);
    return new String [] { " ",
                          sSepFront.trim (),
                          sSepFront.trim () + sSepFinal.trim () + " ",
                          sSepFront.trim () + sSepFinal };
  }

  @Nonnull
  public static PeriodFormatter getFormatterLong (@Nonnull final Locale aContentLocale)
  {
    final String sSepFront = EText.LONG_SEPARATOR_FRONT.getDisplayText (aContentLocale);
    final String sSepFinal = EText.LONG_SEPARATOR_LAST.getDisplayText (aContentLocale);
    final String [] aVariants = _getSeparatorVariants (aContentLocale);
    return new PeriodFormatterBuilder ().appendYears ()
                                        .appendSuffix (EText.LONG_YEAR_SINGULAR.getDisplayText (aContentLocale),
                                                       EText.LONG_YEAR_PLURAL.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendMonths ()
                                        .appendSuffix (EText.LONG_MONTH_SINGULAR.getDisplayText (aContentLocale),
                                                       EText.LONG_MONTH_PLURAL.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendWeeks ()
                                        .appendSuffix (EText.LONG_WEEK_SINGULAR.getDisplayText (aContentLocale),
                                                       EText.LONG_WEEK_PLURAL.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendDays ()
                                        .appendSuffix (EText.LONG_DAY_SINGULAR.getDisplayText (aContentLocale),
                                                       EText.LONG_DAY_PLURAL.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendHours ()
                                        .appendSuffix (EText.LONG_HOUR_SINGULAR.getDisplayText (aContentLocale),
                                                       EText.LONG_HOUR_PLURAL.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendMinutes ()
                                        .appendSuffix (EText.LONG_MINUTE_SINGULAR.getDisplayText (aContentLocale),
                                                       EText.LONG_MINUTE_PLURAL.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendSeconds ()
                                        .appendSuffix (EText.LONG_SECOND_SINGULAR.getDisplayText (aContentLocale),
                                                       EText.LONG_SECOND_PLURAL.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendMillis ()
                                        .appendSuffix (EText.LONG_MILLISECOND_SINGULAR.getDisplayText (aContentLocale),
                                                       EText.LONG_MILLISECOND_PLURAL.getDisplayText (aContentLocale))
                                        .toFormatter ();
  }

  /**
   * Gets the short {@link PeriodFormatter}.
   *
   * @param aContentLocale
   *        The content locale for which the formatter is requested. May not be
   *        <code>null</code>.
   * @return the formatter
   */
  @Nonnull
  public static PeriodFormatter getFormatterShort (@Nonnull final Locale aContentLocale)
  {
    final String sSepFront = EText.LONG_SEPARATOR_FRONT.getDisplayText (aContentLocale);
    final String sSepFinal = EText.LONG_SEPARATOR_LAST.getDisplayText (aContentLocale);
    final String [] aVariants = _getSeparatorVariants (aContentLocale);
    return new PeriodFormatterBuilder ().appendYears ()
                                        .appendSuffix (EText.SHORT_YEAR.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendMonths ()
                                        .appendSuffix (EText.SHORT_MONTH.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendWeeks ()
                                        .appendSuffix (EText.SHORT_WEEK.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendDays ()
                                        .appendSuffix (EText.SHORT_DAY.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendHours ()
                                        .appendSuffix (EText.SHORT_HOUR.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendMinutes ()
                                        .appendSuffix (EText.SHORT_MINUTE.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendSeconds ()
                                        .appendSuffix (EText.SHORT_SECOND.getDisplayText (aContentLocale))
                                        .appendSeparator (sSepFront, sSepFinal, aVariants)
                                        .appendMillis ()
                                        .appendSuffix (EText.SHORT_MILLISECOND.getDisplayText (aContentLocale))
                                        .toFormatter ();
  }

  /**
   * Gets the very short {@link PeriodFormatter}.
   *
   * @param aContentLocale
   *        The content locale for which the formatter is requested. May not be
   *        <code>null</code>.
   * @return the formatter
   */
  @Nonnull
  public static PeriodFormatter getFormatterVeryShort (@Nonnull final Locale aContentLocale)
  {
    final String [] aVariants = { " ", ",", ", " };
    return new PeriodFormatterBuilder ().appendYears ()
                                        .appendSuffix (EText.SHORT_YEAR.getDisplayText (aContentLocale))
                                        .appendSeparator (" ", " ", aVariants)
                                        .appendMonths ()
                                        .appendSuffix (EText.SHORT_MONTH.getDisplayText (aContentLocale))
                                        .appendSeparator (" ", " ", aVariants)
                                        .appendWeeks ()
                                        .appendSuffix (EText.SHORT_WEEK.getDisplayText (aContentLocale))
                                        .appendSeparator (" ", " ", aVariants)
                                        .appendDays ()
                                        .appendSuffix (EText.SHORT_DAY.getDisplayText (aContentLocale))
                                        .appendSeparator (" ", " ", aVariants)
                                        .appendHours ()
                                        .appendSuffix (EText.SHORT_HOUR.getDisplayText (aContentLocale))
                                        .appendSeparator (" ", " ", aVariants)
                                        .appendMinutes ()
                                        .appendSuffix (EText.SHORT_MINUTE.getDisplayText (aContentLocale))
                                        .appendSeparator (" ", " ", aVariants)
                                        .appendSeconds ()
                                        .appendSuffix (EText.SHORT_SECOND.getDisplayText (aContentLocale))
                                        .appendSeparator (" ", " ", aVariants)
                                        .appendMillis ()
                                        .appendSuffix (EText.SHORT_MILLISECOND.getDisplayText (aContentLocale))
                                        .toFormatter ();
  }
}
