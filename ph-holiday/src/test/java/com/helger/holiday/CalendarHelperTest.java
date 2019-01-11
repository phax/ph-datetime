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
package com.helger.holiday;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.datetime.util.PDTHelper;

/**
 * @author svdi1de
 */
public final class CalendarHelperTest
{
  @Test
  public void testWeekend ()
  {
    final LocalDate dateFriday = LocalDate.of (2010, Month.MARCH, 12);
    final LocalDate dateSaturday = LocalDate.of (2010, Month.MARCH, 13);
    final LocalDate dateSunday = LocalDate.of (2010, Month.MARCH, 14);
    final LocalDate dateMonday = LocalDate.of (2010, Month.MARCH, 15);
    assertFalse (PDTHelper.isWeekend (dateFriday));
    assertTrue (PDTHelper.isWeekend (dateSaturday));
    assertTrue (PDTHelper.isWeekend (dateSunday));
    assertFalse (PDTHelper.isWeekend (dateMonday));
  }

  @Test
  public void testCalendarIslamicNewYear2008 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2008, Month.JANUARY, 10));
    expected.add (LocalDate.of (2008, Month.DECEMBER, 29));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2008, 1, 1);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic new years in 2008.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic New Year holidays in 2008.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicNewYear2015 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2015, Month.OCTOBER, 14));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2015, 1, 1);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic new years in 2015.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic New Year holidays in 2015.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicAschura2008 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2008, Month.JANUARY, 19));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2008, 1, 10);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic Aschura holidays in 2008.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic Aschura holidays in 2008.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicAschura2009 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2009, Month.JANUARY, 7));
    expected.add (LocalDate.of (2009, Month.DECEMBER, 27));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2009, 1, 10);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic Aschura holidays in 2009.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic Aschura holidays in 2009.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicAschura2010 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2010, Month.DECEMBER, 16));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2010, 1, 10);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic Aschura holidays in 2010.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic Aschura holidays in 2010.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicIdAlFitr2008 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2008, Month.OCTOBER, 1));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2008, 10, 1);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic IdAlFitr holidays in 2008.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic IdAlFitr holidays in 2008.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicIdAlFitr2009 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2009, Month.SEPTEMBER, 20));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2009, 10, 1);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic IdAlFitr holidays in 2009.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic IdAlFitr holidays in 2009.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicIdAlFitr2013 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2013, Month.AUGUST, 8));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2013, 10, 1);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic IdAlFitr holidays in 2013.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic IdAlFitr holidays in 2013.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicIdAlFitr2014 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2014, Month.JULY, 28));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2014, 10, 1);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic IdAlFitr holidays in 2014.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic IdAlFitr holidays in 2014.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicIdAlFitr2015 ()
  {
    final ICommonsSet <LocalDate> expected = new CommonsHashSet<> ();
    expected.add (LocalDate.of (2015, Month.JULY, 17));
    final ICommonsSet <LocalDate> holidays = CalendarHelper.getIslamicHolidaysInGregorianYear (2015, 10, 1);
    assertNotNull (holidays);
    assertEquals ("Wrong number of islamic IdAlFitr holidays in 2015.", expected.size (), holidays.size ());
    assertEquals ("Wrong islamic IdAlFitr holidays in 2015.", expected, holidays);
  }
}
