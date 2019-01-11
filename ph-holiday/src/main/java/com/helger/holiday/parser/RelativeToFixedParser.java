/**
 * Copyright (C) 2014-2019 Philip Helger (www.helger.com)
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
import com.helger.holiday.jaxb.RelativeToFixed;
import com.helger.holiday.jaxb.When;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public final class RelativeToFixedParser extends AbstractHolidayParser
{
  private static final RelativeToFixedParser s_aInstance = new RelativeToFixedParser ();

  private RelativeToFixedParser ()
  {}

  public static RelativeToFixedParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidays, final Holidays aConfig)
  {
    for (final RelativeToFixed aRelativeToFixed : aConfig.getRelativeToFixed ())
    {
      if (!isValid (aRelativeToFixed, nYear))
        continue;

      LocalDate aFixed = XMLHolidayHelper.create (nYear, aRelativeToFixed.getDate ());
      if (aRelativeToFixed.getWeekday () != null)
      {
        // if weekday is set -> move to weekday
        final DayOfWeek nExpectedWeekday = XMLHolidayHelper.getWeekday (aRelativeToFixed.getWeekday ());
        final int nDirection = (aRelativeToFixed.getWhen () == When.BEFORE ? -1 : 1);
        do
        {
          aFixed = aFixed.plusDays (nDirection);
        } while (aFixed.getDayOfWeek () != nExpectedWeekday);
      }
      else
        if (aRelativeToFixed.getDays () != null)
        {
          // if number of days set -> move number of days
          aFixed = aFixed.plusDays (aRelativeToFixed.getWhen () == When.BEFORE ? -aRelativeToFixed.getDays ()
                                                                                                  .intValue ()
                                                                               : aRelativeToFixed.getDays ()
                                                                                                 .intValue ());
        }

      final IHolidayType aType = XMLHolidayHelper.getType (aRelativeToFixed.getLocalizedType ());
      final String sPropertyKey = aRelativeToFixed.getDescriptionPropertiesKey ();
      aHolidays.add (aFixed, new ResourceBundleHoliday (aType, sPropertyKey));
    }
  }
}
