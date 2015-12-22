/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.datetime.config.PDTConfig;

/**
 * Create common {@link DateTimeFormatter} objects used for printing and parsing
 * date and time objects.
 *
 * @author Philip Helger
 */
@Immutable
public final class PDTFormatter
{
  public static final String PATTERN_DATE_SHORT = ((SimpleDateFormat) DateFormat.getDateInstance (DateFormat.SHORT)).toPattern ();
  public static final String PATTERN_DATE_MEDIUM = ((SimpleDateFormat) DateFormat.getDateInstance (DateFormat.MEDIUM)).toPattern ();
  public static final String PATTERN_DATE_LONG = ((SimpleDateFormat) DateFormat.getDateInstance (DateFormat.LONG)).toPattern ();
  public static final String PATTERN_DATE_FULL = ((SimpleDateFormat) DateFormat.getDateInstance (DateFormat.FULL)).toPattern ();
  public static final String PATTERN_TIME_SHORT = ((SimpleDateFormat) DateFormat.getTimeInstance (DateFormat.SHORT)).toPattern ();
  public static final String PATTERN_TIME_MEDIUM = ((SimpleDateFormat) DateFormat.getTimeInstance (DateFormat.MEDIUM)).toPattern ();
  public static final String PATTERN_TIME_LONG = ((SimpleDateFormat) DateFormat.getTimeInstance (DateFormat.LONG)).toPattern ();
  public static final String PATTERN_TIME_FULL = ((SimpleDateFormat) DateFormat.getTimeInstance (DateFormat.FULL)).toPattern ();
  public static final String PATTERN_DATETIME_SHORT = ((SimpleDateFormat) DateFormat.getDateTimeInstance (DateFormat.SHORT,
                                                                                                          DateFormat.SHORT)).toPattern ();
  public static final String PATTERN_DATETIME_MEDIUM = ((SimpleDateFormat) DateFormat.getDateTimeInstance (DateFormat.MEDIUM,
                                                                                                           DateFormat.MEDIUM)).toPattern ();
  public static final String PATTERN_DATETIME_LONG = ((SimpleDateFormat) DateFormat.getDateTimeInstance (DateFormat.LONG,
                                                                                                         DateFormat.LONG)).toPattern ();
  public static final String PATTERN_DATETIME_FULL = ((SimpleDateFormat) DateFormat.getDateTimeInstance (DateFormat.FULL,
                                                                                                         DateFormat.FULL)).toPattern ();

  @PresentForCodeCoverage
  private static final PDTFormatter s_aInstance = new PDTFormatter ();

  private PDTFormatter ()
  {}

  /**
   * Assign the passed display locale and the default chronology to the passed
   * date time formatter.
   *
   * @param aFormatter
   *        The formatter to be modified. May not be <code>null</code>.
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The modified date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getWithLocaleAndChrono (@Nonnull final DateTimeFormatter aFormatter,
                                                          @Nullable final Locale aDisplayLocale)
  {
    DateTimeFormatter ret = aFormatter;
    if (aDisplayLocale != null)
      ret = ret.withLocale (aDisplayLocale);
    return ret.withChronology (PDTConfig.getDefaultChronology ());
  }

  /**
   * Get the default date formatter for the passed locale. This used medium
   * style.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getDefaultFormatterDate (@Nullable final Locale aDisplayLocale)
  {
    return getMediumFormatterDate (aDisplayLocale);
  }

  /**
   * Get the short date formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getShortFormatterDate (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_DATE_SHORT, aDisplayLocale);
  }

  /**
   * Get the medium date formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getMediumFormatterDate (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_DATE_MEDIUM, aDisplayLocale);
  }

  /**
   * Get the long date formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getLongFormatterDate (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_DATE_LONG, aDisplayLocale);
  }

  /**
   * Get the full date formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getFullFormatterDate (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_DATE_FULL, aDisplayLocale);
  }

  /**
   * Get the default time formatter for the passed locale. This used medium
   * style.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getDefaultFormatterTime (@Nullable final Locale aDisplayLocale)
  {
    return getMediumFormatterTime (aDisplayLocale);
  }

  /**
   * Get the short time formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getShortFormatterTime (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_TIME_SHORT, aDisplayLocale);
  }

  /**
   * Get the medium time formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getMediumFormatterTime (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_TIME_MEDIUM, aDisplayLocale);
  }

  /**
   * Get the long time formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getLongFormatterTime (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_TIME_LONG, aDisplayLocale);
  }

  /**
   * Get the full time formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getFullFormatterTime (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_TIME_FULL, aDisplayLocale);
  }

  /**
   * Get the default date time formatter for the passed locale. The default
   * style is medium.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getDefaultFormatterDateTime (@Nullable final Locale aDisplayLocale)
  {
    return getMediumFormatterDateTime (aDisplayLocale);
  }

  /**
   * Get the short date time formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getShortFormatterDateTime (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_DATETIME_SHORT, aDisplayLocale);
  }

  /**
   * Get the medium date time formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getMediumFormatterDateTime (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_DATETIME_MEDIUM, aDisplayLocale);
  }

  /**
   * Get the long date time formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getLongFormatterDateTime (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_DATETIME_LONG, aDisplayLocale);
  }

  /**
   * Get the full date time formatter for the passed locale.
   *
   * @param aDisplayLocale
   *        The display locale to be used. May be <code>null</code>.
   * @return The created date time formatter. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeFormatter getFullFormatterDateTime (@Nullable final Locale aDisplayLocale)
  {
    return getForPattern (PATTERN_DATETIME_FULL, aDisplayLocale);
  }

  /**
   * Get the {@link DateTimeFormatter} for the given pattern, using our default
   * chronology.
   *
   * @param sPattern
   *        The pattern to be parsed
   * @return The formatter object.
   * @throws IllegalArgumentException
   *         If the pattern is illegal
   */
  @Nonnull
  public static DateTimeFormatter getForPattern (@Nonnull final String sPattern) throws IllegalArgumentException
  {
    return getForPattern (sPattern, null);
  }

  /**
   * Get the {@link DateTimeFormatter} for the given pattern and locale, using
   * our default chronology.
   *
   * @param sPattern
   *        The pattern to be parsed
   * @param aDisplayLocale
   *        The locale to be used. May be <code>null</code>.
   * @return The formatter object.
   * @throws IllegalArgumentException
   *         If the pattern is illegal
   */
  @Nonnull
  public static DateTimeFormatter getForPattern (@Nonnull final String sPattern,
                                                 @Nullable final Locale aDisplayLocale) throws IllegalArgumentException
  {
    return getWithLocaleAndChrono (DateTimeFormatter.ofPattern (sPattern), aDisplayLocale);
  }
}
