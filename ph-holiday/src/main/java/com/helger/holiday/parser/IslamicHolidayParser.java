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
package com.helger.holiday.parser;

import java.time.LocalDate;
import java.util.Set;

import com.helger.holiday.CalendarHelper;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.ResourceBundleHoliday;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.IslamicHoliday;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * This parser calculates gregorian dates for the different islamic holidays.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public final class IslamicHolidayParser extends AbstractHolidayParser
{
  private static final IslamicHolidayParser s_aInstance = new IslamicHolidayParser ();

  private IslamicHolidayParser ()
  {}

  public static IslamicHolidayParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final IslamicHoliday aIslamicHoliday : aConfig.getIslamicHoliday ())
    {
      if (!isValid (aIslamicHoliday, nYear))
        continue;

      Set <LocalDate> aIslamicHolidays;
      switch (aIslamicHoliday.getType ())
      {
        case NEWYEAR:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, 1, 1);
          break;
        case ASCHURA:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, 1, 10);
          break;
        case ID_AL_FITR:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, 10, 1);
          break;
        case ID_UL_ADHA:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, 12, 10);
          break;
        case LAILAT_AL_BARAT:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, 8, 15);
          break;
        case LAILAT_AL_MIRAJ:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, 7, 27);
          break;
        case LAILAT_AL_QADR:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, 9, 27);
          break;
        case MAWLID_AN_NABI:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, 3, 12);
          break;
        case RAMADAN:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, 9, 1);
          break;
        default:
          throw new IllegalArgumentException ("Unknown islamic holiday " + aIslamicHoliday.getType ());
      }

      final IHolidayType aType = XMLHolidayHelper.getType (aIslamicHoliday.getLocalizedType ());
      final String sPropertiesKey = "islamic." + aIslamicHoliday.getType ().name ();
      for (final LocalDate aDate : aIslamicHolidays)
        aHolidayMap.add (aDate, new ResourceBundleHoliday (aType, sPropertiesKey));
    }
  }
}
