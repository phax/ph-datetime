/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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
package com.helger.holiday.mgr;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.threeten.extra.Interval;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.typeconvert.TypeConverter;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayManager;
import com.helger.holiday.ISingleHoliday;

/**
 * Abstract base class for all holiday manager implementations.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public abstract class AbstractHolidayManager implements IHolidayManager
{
  /**
   * Caches the holidays for a given year.
   */
  private final ICommonsMap <String, HolidayMap> m_aHolidaysPerYear = new CommonsHashMap <> ();

  protected AbstractHolidayManager ()
  {}

  @Nonnull
  private static String _getKey (@Nonnull final LocalDate aDate, @Nullable final String... aArgs)
  {
    String sKey = Integer.toString (aDate.getYear ());
    if (ArrayHelper.isNotEmpty (aArgs))
    {
      final StringBuilder aKey = new StringBuilder (sKey);
      for (final String sArg : aArgs)
        aKey.append ('_').append (sArg);
      sKey = aKey.toString ();
    }
    return sKey;
  }

  @Nullable
  public ISingleHoliday getHoliday (@Nonnull final LocalDate aDate, @Nullable final String... aArgs)
  {
    ValueEnforcer.notNull (aDate, "Date");

    final String sKey = _getKey (aDate, aArgs);
    HolidayMap aHolidayMap = m_aHolidaysPerYear.get (sKey);
    if (aHolidayMap == null)
    {
      aHolidayMap = getHolidays (aDate.getYear (), aArgs);
      m_aHolidaysPerYear.put (sKey, aHolidayMap);
    }
    return aHolidayMap.getHolidayForDate (aDate);
  }

  @Nonnull
  @ReturnsMutableCopy
  public HolidayMap getHolidays (@Nonnull final Interval aInterval, @Nullable final String... aArgs)
  {
    ValueEnforcer.notNull (aInterval, "Interval");

    final HolidayMap aHolidayMap = new HolidayMap ();
    final int nStartYear = TypeConverter.convert (aInterval.getStart (), LocalDate.class).getYear ();
    final int nEndYear = TypeConverter.convert (aInterval.getEnd (), LocalDate.class).getYear ();
    for (int nYear = nStartYear; nYear <= nEndYear; nYear++)
    {
      final HolidayMap yearHolidays = getHolidays (nYear, aArgs);
      for (final Map.Entry <LocalDate, ISingleHoliday> aEntry : yearHolidays.getMap ().entrySet ())
        if (aInterval.contains (TypeConverter.convert (aEntry.getKey (), Instant.class)))
          aHolidayMap.add (aEntry.getKey (), aEntry.getValue ());
    }
    return aHolidayMap;
  }

  /**
   * Returns the configured hierarchy structure for the specific manager. This
   * hierarchy shows how the configured holidays are structured and can be
   * retrieved.
   *
   * @return The hierarchy
   */
  @Nonnull
  public abstract CalendarHierarchy getHierarchy ();
}
