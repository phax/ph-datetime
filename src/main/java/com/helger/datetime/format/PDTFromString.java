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

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotations.PresentForCodeCoverage;
import com.helger.commons.string.StringHelper;

/**
 * Handles the conversion to date, time or date time objects from a
 * {@link String}.
 * 
 * @author Philip Helger
 */
@Immutable
public final class PDTFromString
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (PDTFromString.class);

  @PresentForCodeCoverage
  @SuppressWarnings ("unused")
  private static final PDTFromString s_aInstance = new PDTFromString ();

  private PDTFromString ()
  {}

  @Nullable
  public static DateTime getDateTimeFromString (@Nullable final String sValue, @Nonnull final String sPattern)
  {
    return getDateTimeFromString (sValue, PDTFormatter.getForPattern (sPattern, null));
  }

  @Nullable
  public static DateTime getDateTimeFromString (@Nullable final String sValue, @Nonnull final DateTimeFormatter aDF)
  {
    if (aDF == null)
      throw new NullPointerException ("dateTimeFormatter");

    if (StringHelper.hasText (sValue))
      try
      {
        return aDF.parseDateTime (sValue);
      }
      catch (final IllegalArgumentException ex)
      {
        if (s_aLogger.isDebugEnabled ())
          s_aLogger.debug ("Failed to parse date '" + sValue + "' with " + aDF);
      }
    return null;
  }

  /**
   * @param sValue
   *        Date string to parse.
   * @param aParseLocale
   *        Locale to use.
   * @return <code>null</code> if parsing failed.
   */
  @Nullable
  public static DateTime getDefaultDateFromString (@Nullable final String sValue, @Nullable final Locale aParseLocale)
  {
    return getDateTimeFromString (sValue, PDTFormatter.getDefaultFormatterDate (aParseLocale));
  }

  /**
   * @param sValue
   *        Time string to parse.
   * @param aParseLocale
   *        Locale to use.
   * @return <code>null</code> if parsing failed.
   */
  @Nullable
  public static DateTime getDefaultTimeFromString (@Nullable final String sValue, @Nullable final Locale aParseLocale)
  {
    return getDateTimeFromString (sValue, PDTFormatter.getDefaultFormatterTime (aParseLocale));
  }

  /**
   * @param sValue
   *        Date and time string to parse.
   * @param aParseLocale
   *        Locale to use.
   * @return <code>null</code> if parsing failed.
   */
  @Nullable
  public static DateTime getDefaultDateTimeFromString (@Nullable final String sValue,
                                                       @Nullable final Locale aParseLocale)
  {
    return getDateTimeFromString (sValue, PDTFormatter.getDefaultFormatterDateTime (aParseLocale));
  }

  @Nullable
  public static LocalDate getLocalDateFromString (@Nullable final String sValue, @Nullable final Locale aParseLocale)
  {
    return getLocalDateFromString (sValue, PDTFormatter.getDefaultFormatterDate (aParseLocale));
  }

  @Nullable
  public static LocalDate getLocalDateFromString (@Nullable final String sValue, @Nonnull final DateTimeFormatter aDF)
  {
    final DateTime aDT = getDateTimeFromString (sValue, aDF);
    return aDT == null ? null : aDT.toLocalDate ();
  }

  @Nullable
  public static LocalDate getLocalDateFromString (@Nullable final String sValue, @Nonnull final String sPattern)
  {
    final DateTime aDT = getDateTimeFromString (sValue, sPattern);
    return aDT == null ? null : aDT.toLocalDate ();
  }

  @Nullable
  public static LocalDateTime getLocalDateTimeFromString (@Nullable final String sValue,
                                                          @Nullable final Locale aParseLocale)
  {
    return getLocalDateTimeFromString (sValue, PDTFormatter.getDefaultFormatterDateTime (aParseLocale));
  }

  @Nullable
  public static LocalDateTime getLocalDateTimeFromString (@Nullable final String sValue,
                                                          @Nonnull final DateTimeFormatter aDF)
  {
    final DateTime aDT = getDateTimeFromString (sValue, aDF);
    return aDT == null ? null : aDT.toLocalDateTime ();
  }

  @Nullable
  public static LocalDateTime getLocalDateTimeFromString (@Nullable final String sValue, @Nonnull final String sPattern)
  {
    final DateTime aDT = getDateTimeFromString (sValue, sPattern);
    return aDT == null ? null : aDT.toLocalDateTime ();
  }

  @Nullable
  public static LocalTime getLocalTimeFromString (@Nullable final String sValue, @Nullable final Locale aParseLocale)
  {
    return getLocalTimeFromString (sValue, PDTFormatter.getDefaultFormatterTime (aParseLocale));
  }

  @Nullable
  public static LocalTime getLocalTimeFromString (@Nullable final String sValue, @Nonnull final DateTimeFormatter aDF)
  {
    final DateTime aDT = getDateTimeFromString (sValue, aDF);
    return aDT == null ? null : aDT.toLocalTime ();
  }

  @Nullable
  public static LocalTime getLocalTimeFromString (@Nullable final String sValue, @Nonnull final String sPattern)
  {
    final DateTime aDT = getDateTimeFromString (sValue, sPattern);
    return aDT == null ? null : aDT.toLocalTime ();
  }
}
