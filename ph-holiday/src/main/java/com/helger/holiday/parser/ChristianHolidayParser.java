/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
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
package com.helger.holiday.parser;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

import javax.annotation.Nonnull;

import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.jaxb.ChristianHoliday;
import com.helger.holiday.jaxb.ChronologyType;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * This parser creates christian holidays for the given year relative to easter
 * sunday.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public final class ChristianHolidayParser extends RelativeToEasterSundayParser
{
  private static final ChristianHolidayParser s_aInstance = new ChristianHolidayParser ();

  private ChristianHolidayParser ()
  {}

  @Nonnull
  public static ChristianHolidayParser getInstance ()
  {
    return s_aInstance;
  }

  /**
   * {@inheritDoc} Parses all christian holidays relative to eastern.
   */
  @Override
  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final ChristianHoliday aChristianHoliday : aConfig.getChristianHoliday ())
    {
      if (!isValid (aChristianHoliday, nYear))
        continue;

      ChronoLocalDate aEasterSunday;
      if (aChristianHoliday.getChronology () == ChronologyType.JULIAN)
        aEasterSunday = getJulianEasterSunday (nYear);
      else
        if (aChristianHoliday.getChronology () == ChronologyType.GREGORIAN)
          aEasterSunday = getGregorianEasterSunday (nYear);
        else
          aEasterSunday = getEasterSunday (nYear);

      switch (aChristianHoliday.getType ())
      {
        case EASTER:
          break;
        case CLEAN_MONDAY:
        case SHROVE_MONDAY:
          aEasterSunday = aEasterSunday.minus (48, ChronoUnit.DAYS);
          break;
        case MARDI_GRAS:
        case CARNIVAL:
          aEasterSunday = aEasterSunday.minus (47, ChronoUnit.DAYS);
          break;
        case ASH_WEDNESDAY:
          aEasterSunday = aEasterSunday.minus (46, ChronoUnit.DAYS);
          break;
        case MAUNDY_THURSDAY:
          aEasterSunday = aEasterSunday.minus (3, ChronoUnit.DAYS);
          break;
        case GOOD_FRIDAY:
          aEasterSunday = aEasterSunday.minus (2, ChronoUnit.DAYS);
          break;
        case EASTER_SATURDAY:
          aEasterSunday = aEasterSunday.minus (1, ChronoUnit.DAYS);
          break;
        case EASTER_MONDAY:
          aEasterSunday = aEasterSunday.plus (1, ChronoUnit.DAYS);
          break;
        case EASTER_TUESDAY:
          aEasterSunday = aEasterSunday.plus (2, ChronoUnit.DAYS);
          break;
        case GENERAL_PRAYER_DAY:
          aEasterSunday = aEasterSunday.plus (26, ChronoUnit.DAYS);
          break;
        case ASCENSION_DAY:
          aEasterSunday = aEasterSunday.plus (39, ChronoUnit.DAYS);
          break;
        case PENTECOST:
        case WHIT_SUNDAY:
          aEasterSunday = aEasterSunday.plus (49, ChronoUnit.DAYS);
          break;
        case WHIT_MONDAY:
        case PENTECOST_MONDAY:
          aEasterSunday = aEasterSunday.plus (50, ChronoUnit.DAYS);
          break;
        case CORPUS_CHRISTI:
          aEasterSunday = aEasterSunday.plus (60, ChronoUnit.DAYS);
          break;
        case SACRED_HEART:
          aEasterSunday = aEasterSunday.plus (68, ChronoUnit.DAYS);
          break;
        default:
          throw new IllegalArgumentException ("Unknown christian holiday type " + aChristianHoliday.getType ());
      }
      final LocalDate aConvertedDate = LocalDate.from (aEasterSunday);
      final IHolidayType aType = XMLHolidayHelper.getType (aChristianHoliday.getLocalizedType ());
      final String sPropertiesKey = "christian." + aChristianHoliday.getType ().name ();
      addChrstianHoliday (aConvertedDate, sPropertiesKey, aType, aHolidayMap);
    }
  }
}
