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

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.ResourceBundleHoliday;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.RelativeToWeekdayInMonth;
import com.helger.holiday.jaxb.When;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public class RelativeToWeekdayInMonthParser extends FixedWeekdayInMonthParser
{
  private static final RelativeToWeekdayInMonthParser s_aInstance = new RelativeToWeekdayInMonthParser ();

  private RelativeToWeekdayInMonthParser ()
  {}

  public static RelativeToWeekdayInMonthParser getInstance ()
  {
    return s_aInstance;
  }

  @Override
  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final RelativeToWeekdayInMonth aRelativeToWeekdayInMonth : aConfig.getRelativeToWeekdayInMonth ())
    {
      if (!isValid (aRelativeToWeekdayInMonth, nYear))
        continue;

      final DayOfWeek nExpectedWeekday = XMLHolidayHelper.getWeekday (aRelativeToWeekdayInMonth.getWeekday ());
      LocalDate aDate = parse (nYear, aRelativeToWeekdayInMonth.getFixedWeekday ());
      final int nDirection = (aRelativeToWeekdayInMonth.getWhen () == When.BEFORE ? -1 : 1);
      while (aDate.getDayOfWeek () != nExpectedWeekday)
      {
        aDate = aDate.plusDays (nDirection);
      }

      final IHolidayType aType = XMLHolidayHelper.getType (aRelativeToWeekdayInMonth.getLocalizedType ());
      final String sPropertyKey = aRelativeToWeekdayInMonth.getDescriptionPropertiesKey ();
      aHolidayMap.add (aDate, new ResourceBundleHoliday (aType, sPropertyKey));
    }
  }
}
