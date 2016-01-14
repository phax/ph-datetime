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
package com.helger.holiday;

import java.time.LocalDate;
import java.util.Calendar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.threeten.extra.Interval;

import com.helger.commons.typeconvert.TypeConverter;

/**
 * Base interface for a holiday manager for one country.
 *
 * @author Philip Helger
 */
public interface IHolidayManager
{
  /**
   * Check if the passed calendar is a holiday
   *
   * @param aCalendar
   *        Calendar
   * @param args
   *        Optional args
   * @return <code>true</code> if it is a holiday
   * @see #isHoliday(LocalDate, String...)
   */
  default boolean isHoliday (@Nonnull final Calendar aCalendar, final String... args)
  {
    return isHoliday (TypeConverter.convertIfNecessary (aCalendar, LocalDate.class), args);
  }

  /**
   * Check if the requested date is a holiday.
   *
   * @param aDate
   *        The potential holiday.
   * @param aArgs
   *        Hierarchy to request the holidays for. i.e. args = {'ny'} -&gt; New
   *        York holidays
   * @return is a holiday in the state/region
   */
  default boolean isHoliday (@Nonnull final LocalDate aDate, @Nullable final String... aArgs)
  {
    return getHoliday (aDate, aArgs) != null;
  }

  /**
   * Get the holiday information for the requested date.
   *
   * @param aDate
   *        The potential holiday.
   * @param aArgs
   *        Hierarchy to request the holidays for. i.e. args = {'ny'} -&gt; New
   *        York holidays
   * @return The respective holiday.
   */
  @Nullable
  ISingleHoliday getHoliday (@Nonnull LocalDate aDate, @Nullable String... aArgs);

  /**
   * Returns the holidays for the requested year and hierarchy structure.
   *
   * @param nYear
   *        i.e. 2010
   * @param aArgs
   *        i.e. args = {'ny'}. returns US/New York holidays. No args -&gt;
   *        holidays common to whole country
   * @return the list of holidays for the requested year
   */
  @Nonnull
  HolidayMap getHolidays (int nYear, @Nullable String... aArgs);

  /**
   * Returns the holidays for the requested interval and hierarchy structure.
   *
   * @param aInterval
   *        the interval in which the holidays lie.
   * @param aArgs
   *        a {@link java.lang.String} object.
   * @return list of holidays within the interval
   */
  @Nonnull
  HolidayMap getHolidays (@Nonnull Interval aInterval, @Nullable String... aArgs);
}
