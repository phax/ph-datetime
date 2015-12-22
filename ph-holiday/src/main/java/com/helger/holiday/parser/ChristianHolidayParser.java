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
package com.helger.holiday.parser;

import java.time.LocalDate;

import javax.annotation.Nonnull;

import com.helger.holiday.CalendarHelper;
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
public class ChristianHolidayParser extends RelativeToEasterSundayParser
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

      LocalDate aEasterSunday;
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
          aEasterSunday = aEasterSunday.minusDays (48);
          break;
        case MARDI_GRAS:
        case CARNIVAL:
          aEasterSunday = aEasterSunday.minusDays (47);
          break;
        case ASH_WEDNESDAY:
          aEasterSunday = aEasterSunday.minusDays (46);
          break;
        case MAUNDY_THURSDAY:
          aEasterSunday = aEasterSunday.minusDays (3);
          break;
        case GOOD_FRIDAY:
          aEasterSunday = aEasterSunday.minusDays (2);
          break;
        case EASTER_SATURDAY:
          aEasterSunday = aEasterSunday.minusDays (1);
          break;
        case EASTER_MONDAY:
          aEasterSunday = aEasterSunday.plusDays (1);
          break;
        case EASTER_TUESDAY:
          aEasterSunday = aEasterSunday.plusDays (2);
          break;
        case GENERAL_PRAYER_DAY:
          aEasterSunday = aEasterSunday.plusDays (26);
          break;
        case ASCENSION_DAY:
          aEasterSunday = aEasterSunday.plusDays (39);
          break;
        case PENTECOST:
        case WHIT_SUNDAY:
          aEasterSunday = aEasterSunday.plusDays (49);
          break;
        case WHIT_MONDAY:
        case PENTECOST_MONDAY:
          aEasterSunday = aEasterSunday.plusDays (50);
          break;
        case CORPUS_CHRISTI:
          aEasterSunday = aEasterSunday.plusDays (60);
          break;
        case SACRED_HEART:
          aEasterSunday = aEasterSunday.plusDays (68);
          break;
        default:
          throw new IllegalArgumentException ("Unknown christian holiday type " + aChristianHoliday.getType ());
      }
      final LocalDate aConvertedDate = CalendarHelper.convertToGregorianDate (aEasterSunday);
      final IHolidayType aType = XMLHolidayHelper.getType (aChristianHoliday.getLocalizedType ());
      final String sPropertiesKey = "christian." + aChristianHoliday.getType ().name ();
      addChrstianHoliday (aConvertedDate, sPropertiesKey, aType, aHolidayMap);
    }
  }
}
