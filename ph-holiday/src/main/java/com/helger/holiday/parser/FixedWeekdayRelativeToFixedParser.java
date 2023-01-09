/*
 * Copyright (C) 2014-2023 Philip Helger (www.helger.com)
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

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.ResourceBundleHoliday;
import com.helger.holiday.jaxb.FixedWeekdayRelativeToFixed;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.When;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * Parses fixed weekday relative to fixed date.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public final class FixedWeekdayRelativeToFixedParser extends AbstractHolidayParser
{
  private static final FixedWeekdayRelativeToFixedParser s_aInstance = new FixedWeekdayRelativeToFixedParser ();

  private FixedWeekdayRelativeToFixedParser ()
  {}

  public static FixedWeekdayRelativeToFixedParser getInstance ()
  {
    return s_aInstance;
  }

  /**
   * Parses the provided configuration and creates holidays for the provided
   * year.
   */
  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final FixedWeekdayRelativeToFixed aFixedWeekdayRelativeToFixed : aConfig.getFixedWeekdayRelativeToFixed ())
    {
      if (!isValid (aFixedWeekdayRelativeToFixed, nYear))
        continue;

      // parsing fixed day
      final DayOfWeek nExpectedWeekday = XMLHolidayHelper.getWeekday (aFixedWeekdayRelativeToFixed.getWeekday ());
      LocalDate aDay = XMLHolidayHelper.create (nYear, aFixedWeekdayRelativeToFixed.getDay ());
      do
      {
        // move fixed to first occurrence of weekday
        aDay = aFixedWeekdayRelativeToFixed.getWhen () == When.AFTER ? aDay.plusDays (1) : aDay.minusDays (1);
      } while (aDay.getDayOfWeek () != nExpectedWeekday);
      int nDays = 0;
      switch (aFixedWeekdayRelativeToFixed.getWhich ())
      {
        case FIRST:
          break;
        case SECOND:
          nDays = 7;
          break;
        case THIRD:
          nDays = 14;
          break;
        case FOURTH:
          nDays = 21;
          break;
        case LAST:
          // seems to be unsupported
          break;
      }
      // move day further if it is second, third or fourth weekday
      aDay = aFixedWeekdayRelativeToFixed.getWhen () == When.AFTER ? aDay.plusDays (nDays) : aDay.minusDays (nDays);
      final IHolidayType aType = XMLHolidayHelper.getType (aFixedWeekdayRelativeToFixed.getLocalizedType ());
      final String sPropertyKey = aFixedWeekdayRelativeToFixed.getDescriptionPropertiesKey ();
      aHolidayMap.add (aDay, new ResourceBundleHoliday (aType, sPropertyKey));
    }
  }
}
