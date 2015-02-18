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
package com.helger.datetime.holiday;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Test;

import com.helger.commons.locale.country.ECountry;
import com.helger.datetime.PDTFactory;
import com.helger.datetime.holiday.mgr.AbstractCountryTestBase;

public class HolidayDETest extends AbstractCountryTestBase
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
      final Interval interval = new Interval (PDTFactory.createLocalDate (2010, 10, 1).toDateTimeAtStartOfDay (),
                                              PDTFactory.createLocalDate (2011, 1, 31).toDateTimeAtStartOfDay ());
      final HolidayMap holidays = instance.getHolidays (interval);
      final List <LocalDate> expected = Arrays.asList (PDTFactory.createLocalDate (2010, 12, 25),
                                                       PDTFactory.createLocalDate (2010, 12, 26),
                                                       PDTFactory.createLocalDate (2010, 10, 3),
                                                       PDTFactory.createLocalDate (2011, 1, 1));
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
