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
package com.helger.holiday;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.threeten.extra.Interval;

import com.helger.commons.locale.country.ECountry;

public final class HolidayDEFuncTest extends AbstractCountryTestBase
{
  private static final int YEAR = 2010;
  private static final String ISO_CODE = "de";

  @Test
  public void testManagerDEStructure () throws Exception
  {
    validateCalendarData (ISO_CODE, YEAR);
  }

  @Test
  public void testManagerDEinterval ()
  {
    try
    {
      final IHolidayManager instance = HolidayManagerFactory.getHolidayManager (ECountry.DE);
      final Interval interval = Interval.of (LocalDate.of (2010, Month.OCTOBER, 1),
                                             LocalDate.of (2011, Month.JANUARY, 31));
      final HolidayMap holidays = instance.getHolidays (interval);
      final List <LocalDate> expected = Arrays.asList (LocalDate.of (2010, Month.DECEMBER, 25),
                                                       LocalDate.of (2010, Month.DECEMBER, 26),
                                                       LocalDate.of (2010, Month.OCTOBER, 3),
                                                       LocalDate.of (2011, Month.JANUARY, 1));
      assertEquals ("Wrong number of holidays", expected.size (), holidays.size ());
      for (final LocalDate d : expected)
      {
        assertTrue ("Expected date " + d + " missing.", holidays.containsHolidayForDate (d));
      }
    }
    catch (final Exception e)
    {
      fail ("Unexpected error occurred: " + e.getClass ().getName () + " - " + e.getMessage ());
    }
  }

}
