/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;

import com.helger.commons.collection.ArrayHelper;
import com.helger.holiday.mgr.AbstractHolidayManager;
import com.helger.holiday.mgr.CalendarHierarchy;

/**
 * @author svdi1de
 */
public abstract class AbstractCountryTestBase
{
  /**
   * Compares two hierarchy structure by traversing down.
   *
   * @param expected
   *        This is the test structure which is how it should be.
   * @param found
   *        This is the real live data structure.
   */
  protected void compareHierarchies (final CalendarHierarchy expected, final CalendarHierarchy found)
  {
    final Locale aLocale = Locale.US;
    assertNotNull ("Null description", found.getDescription (aLocale));
    assertEquals ("Wrong hierarchy id.", expected.getID (), found.getID ());
    assertEquals ("Number of children wrong.", expected.getChildren ().size (), found.getChildren ().size ());
    for (final String id : expected.getChildren ().keySet ())
    {
      assertTrue ("Missing " + id + " within " + found.getID (), found.getChildren ().containsKey (id));
      compareHierarchies (expected.getChildren ().get (id), found.getChildren ().get (id));
    }
  }

  protected void compareData (final AbstractHolidayManager expected, final IHolidayManager found, final int year)
  {
    final CalendarHierarchy expectedHierarchy = expected.getHierarchy ();
    _compareDates (expected, found, expectedHierarchy, new String [0], year);
  }

  private void _compareDates (final IHolidayManager aExpected,
                              final IHolidayManager aFound,
                              final CalendarHierarchy aHierarchy,
                              final String [] args,
                              final int nYear)
  {
    final Locale aLocale = Locale.US;
    final HolidayMap aExpectedHolidays = aExpected.getHolidays (nYear, args);
    final HolidayMap aFoundHolidays = aFound.getHolidays (nYear, args);
    for (final Map.Entry <LocalDate, ISingleHoliday> aEntry : aExpectedHolidays.getMap ().entrySet ())
    {
      assertNotNull ("Description is null.", aEntry.getValue ().getHolidayName (aLocale));
      if (!aFoundHolidays.containsHolidayForDate (aEntry.getKey ()))
      {
        fail ("Could not find " +
              aEntry.getKey () +
              " // " +
              aEntry.getValue () +
              " in " +
              aHierarchy.getDescription (aLocale) +
              " - " +
              aFoundHolidays);
      }
    }
    for (final String id : aHierarchy.getChildren ().keySet ())
    {
      _compareDates (aExpected,
                     aFound,
                     aHierarchy.getChildren ().get (id),
                     ArrayHelper.getConcatenated (args, id),
                     nYear);
    }
  }

  protected void validateCalendarData (final String countryCode, final int year) throws Exception
  {
    final AbstractHolidayManager dataManager = (AbstractHolidayManager) HolidayManagerFactory.getHolidayManager (countryCode);
    final AbstractHolidayManager testManager = (AbstractHolidayManager) HolidayManagerFactory.getHolidayManager ("test_" +
                                                                                                                 countryCode +
                                                                                                                 "_" +
                                                                                                                 Integer.toString (year));

    final CalendarHierarchy dataHierarchy = dataManager.getHierarchy ();
    final CalendarHierarchy testHierarchy = dataManager.getHierarchy ();

    compareHierarchies (testHierarchy, dataHierarchy);
    compareData (testManager, dataManager, year);
  }
}
