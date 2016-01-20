/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.datetime.DateTimeFormatterCache;

/**
 * Create common {@link DateTimeFormatter} objects used for printing and parsing
 * date and time objects.
 *
 * @author Philip Helger
 */
@Immutable
public final class PDTFormatter
{
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
  public static DateTimeFormatter getStrictWithLocale (@Nonnull final DateTimeFormatter aFormatter,
                                                       @Nullable final Locale aDisplayLocale)
  {
    DateTimeFormatter ret = aFormatter;
    if (aDisplayLocale != null)
      ret = ret.withLocale (aDisplayLocale);
    return ret.withResolverStyle (ResolverStyle.STRICT);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedDate (FormatStyle.SHORT), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedDate (FormatStyle.MEDIUM), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedDate (FormatStyle.LONG), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedDate (FormatStyle.FULL), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedTime (FormatStyle.SHORT), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedTime (FormatStyle.MEDIUM), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedTime (FormatStyle.LONG), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedTime (FormatStyle.FULL), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedDateTime (FormatStyle.SHORT), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedDateTime (FormatStyle.MEDIUM), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedDateTime (FormatStyle.LONG), aDisplayLocale);
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
    return getStrictWithLocale (DateTimeFormatter.ofLocalizedDateTime (FormatStyle.FULL), aDisplayLocale);
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
   * Get the STRICT {@link DateTimeFormatter} for the given pattern and locale,
   * using our default chronology.
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
    final DateTimeFormatter aDTF = DateTimeFormatterCache.getDateTimeFormatterStrict (sPattern);
    return getStrictWithLocale (aDTF, aDisplayLocale);
  }
}
