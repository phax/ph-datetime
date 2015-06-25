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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.locale.country.ECountry;
import com.helger.commons.timing.StopWatch;
import com.helger.datetime.PDTFactory;
import com.helger.datetime.config.PDTConfig;
import com.helger.datetime.holiday.mgr.AbstractHolidayManager;
import com.helger.datetime.holiday.mgr.CalendarHierarchy;

/**
 * @author svdi1de
 */
public final class HolidayManagerFactoryTest
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (HolidayManagerFactoryTest.class);

  private static final Set <LocalDate> s_aTestDays = new HashSet <LocalDate> ();
  private static final Set <LocalDate> s_aTestDays_l1 = new HashSet <LocalDate> ();
  private static final Set <LocalDate> s_aTestDays_l2 = new HashSet <LocalDate> ();
  private static final Set <LocalDate> s_aTestDays_l11 = new HashSet <LocalDate> ();

  static
  {
    s_aTestDays.add (PDTFactory.createLocalDate (2010, DateTimeConstants.FEBRUARY, 17));
    s_aTestDays.add (PDTFactory.createLocalDate (2010, DateTimeConstants.AUGUST, 30));
    s_aTestDays.add (PDTFactory.createLocalDate (2010, DateTimeConstants.APRIL, 2));
    s_aTestDays.add (PDTFactory.createLocalDate (2010, DateTimeConstants.APRIL, 5));
    s_aTestDays.add (PDTFactory.createLocalDate (2010, DateTimeConstants.NOVEMBER, 17));
    s_aTestDays.add (PDTFactory.createLocalDate (2010, DateTimeConstants.NOVEMBER, 28));
    s_aTestDays.add (PDTFactory.createLocalDate (2010, DateTimeConstants.JANUARY, 1));
    s_aTestDays.add (PDTFactory.createLocalDate (2010, DateTimeConstants.JANUARY, 18));
    s_aTestDays.add (PDTFactory.createLocalDate (2010, DateTimeConstants.NOVEMBER, 26));
    s_aTestDays_l1.addAll (s_aTestDays);
    s_aTestDays_l1.add (PDTFactory.createLocalDate (2010, DateTimeConstants.JANUARY, 2));
    s_aTestDays_l2.addAll (s_aTestDays_l1);
    s_aTestDays_l2.add (PDTFactory.createLocalDate (2010, DateTimeConstants.JANUARY, 3));

    s_aTestDays_l11.addAll (s_aTestDays);
    s_aTestDays_l11.add (PDTFactory.createLocalDate (2010, DateTimeConstants.JULY, 27));
    s_aTestDays_l11.add (PDTFactory.createLocalDate (2010, DateTimeConstants.JULY, 9));
    s_aTestDays_l11.add (PDTFactory.createLocalDate (2010, DateTimeConstants.FEBRUARY, 26));
    s_aTestDays_l11.add (PDTFactory.createLocalDate (2010, DateTimeConstants.AUGUST, 11));
    s_aTestDays_l11.add (PDTFactory.createLocalDate (2010, DateTimeConstants.SEPTEMBER, 6));
    s_aTestDays_l11.add (PDTFactory.createLocalDate (2010, DateTimeConstants.SEPTEMBER, 10));
    s_aTestDays_l11.add (PDTFactory.createLocalDate (2010, DateTimeConstants.NOVEMBER, 17));
    s_aTestDays_l11.add (PDTFactory.createLocalDate (2010, DateTimeConstants.DECEMBER, 8));
    s_aTestDays_l11.add (PDTFactory.createLocalDate (2010, DateTimeConstants.DECEMBER, 17));
  }

  @Test
  public void testMissingCountry () throws Exception
  {
    try
    {
      HolidayManagerFactory.getHolidayManager ("XXX");
      fail ("Expected some IllegalArgumentException for this missing country.");
    }
    catch (final IllegalArgumentException e)
    {}
  }

  @Test
  public void testBaseStructure () throws Exception
  {
    final AbstractHolidayManager m = (AbstractHolidayManager) HolidayManagerFactory.getHolidayManager ("test");
    final CalendarHierarchy h = m.getHierarchy ();
    assertEquals ("Wrong id.", "test", h.getID ());
    assertEquals ("Wrong number of children on first level.", 2, h.getChildren ().size ());
    for (final CalendarHierarchy hi : h.getChildren ().values ())
    {
      if (hi.getID ().equalsIgnoreCase ("level1"))
      {
        assertEquals ("Wrong number of children on second level of level 1.", 1, hi.getChildren ().size ());
      }
      else
        if (hi.getID ().equalsIgnoreCase ("level11"))
        {
          assertEquals ("Wrong number of children on second level of level 11.", 0, hi.getChildren ().size ());
        }
    }
  }

  @Test
  public void testIsHolidayPerformance () throws Exception
  {
    LocalDate date = PDTFactory.createLocalDate (2010, 1, 1);
    int count = 0;
    long sumDuration = 0;
    while (date.getYear () < 2011)
    {
      final StopWatch aSW = StopWatch.createdStarted ();
      final IHolidayManager m = HolidayManagerFactory.getHolidayManager ("test");
      m.isHoliday (date);
      long duration = aSW.stopAndGetMillis ();
      if (duration > 0)
        s_aLogger.info ("isHoliday took " + duration + " millis.");
      aSW.start ();
      date = date.plusDays (1);
      duration = aSW.stopAndGetMillis ();
      count++;
      sumDuration += duration;
    }
    if (sumDuration > 0)
      s_aLogger.info ("isHoliday took " + sumDuration / count + " millis average.");
  }

  @Test
  public void testCalendarChronology () throws Exception
  {
    final AbstractHolidayManager m = (AbstractHolidayManager) HolidayManagerFactory.getHolidayManager ("test");
    final Calendar c = Calendar.getInstance ();
    c.set (Calendar.YEAR, 2010);
    c.set (Calendar.MONTH, Calendar.FEBRUARY);
    c.set (Calendar.DAY_OF_MONTH, 16);
    assertFalse ("This date should NOT be a holiday.", m.isHoliday (c));
    c.add (Calendar.DAY_OF_YEAR, 1);
    assertTrue ("This date should be a holiday.", m.isHoliday (c));
  }

  @Test
  public void testChronology () throws Exception
  {
    final IHolidayManager aMgr = HolidayManagerFactory.getHolidayManager ("test");
    final HolidayMap aHolidays = aMgr.getHolidays (2010);
    for (final LocalDate aDate : aHolidays.getMap ().keySet ())
    {
      assertEquals ("Wrong chronology for date " + aDate, PDTConfig.getDefaultChronologyUTC (), aDate.getChronology ());
    }
  }

  @Test
  public void testBaseDates () throws Exception
  {
    final IHolidayManager m = HolidayManagerFactory.getHolidayManager ("test");
    final HolidayMap holidays = m.getHolidays (2010);
    assertNotNull (holidays);
    assertEquals ("Wrong number of dates.", s_aTestDays.size (), holidays.size ());
    assertDates (s_aTestDays, holidays);
  }

  private void assertDates (final Set <LocalDate> dates, final HolidayMap holidays)
  {
    for (final LocalDate d : dates)
    {
      if (!holidays.containsHolidayForDate (d))
      {
        fail ("Missing " + d + " in " + holidays);
      }
    }
  }

  @Test
  public void testLevel1 () throws Exception
  {
    final IHolidayManager m = HolidayManagerFactory.getHolidayManager ("test");
    final HolidayMap holidays = m.getHolidays (2010, "level1");
    assertNotNull (holidays);
    assertEquals ("Wrong number of dates.", s_aTestDays_l1.size (), holidays.size ());
    assertDates (s_aTestDays_l1, holidays);
  }

  @Test
  public void testLevel2 () throws Exception
  {
    final IHolidayManager m = HolidayManagerFactory.getHolidayManager ("test");
    final HolidayMap holidays = m.getHolidays (2010, "level1", "level2");
    assertNotNull (holidays);
    assertEquals ("Wrong number of dates.", s_aTestDays_l2.size (), holidays.size ());
    assertDates (s_aTestDays_l2, holidays);
  }

  @Test
  public void testLevel11 () throws Exception
  {
    final IHolidayManager m = HolidayManagerFactory.getHolidayManager ("test");
    final HolidayMap holidays = m.getHolidays (2010, "level11");
    assertNotNull (holidays);
    assertDates (s_aTestDays_l11, holidays);

  }

  @Test
  public void testFail ()
  {
    try
    {
      HolidayManagerFactory.getHolidayManager ("test_fail");
      fail ("Should have thrown an IllegalArgumentException.");
    }
    catch (final IllegalArgumentException e)
    {}
  }

  @Test
  public void testAllAvailableManagers () throws Exception
  {
    final Set <String> aSupportedCountryCodes = HolidayManagerFactory.getSupportedCountryCodes ();
    assertNotNull (aSupportedCountryCodes);
    assertFalse (aSupportedCountryCodes.isEmpty ());
    for (final String sCountry : aSupportedCountryCodes)
    {
      final IHolidayManager manager = HolidayManagerFactory.getHolidayManager (sCountry);
      assertNotNull (manager);
    }
  }

  @Test
  public void testAT ()
  {
    final IHolidayManager aMgr = HolidayManagerFactory.getHolidayManager (ECountry.AT);
    assertTrue (aMgr.isHoliday (PDTFactory.createLocalDate (2010, DateTimeConstants.DECEMBER, 25)));
    assertEquals ("Heilige Drei Könige",
                  aMgr.getHoliday (PDTFactory.createLocalDate (2010, DateTimeConstants.JANUARY, 6))
                      .getHolidayName (Locale.GERMAN));
  }
}
