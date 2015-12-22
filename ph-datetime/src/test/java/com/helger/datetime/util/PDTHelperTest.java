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
package com.helger.datetime.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import org.junit.Test;

import com.helger.datetime.PDTFactory;

/**
 * Test class for class {@link PDTHelper}.
 *
 * @author Philip Helger
 */
public final class PDTHelperTest
{
  private static DateTime _getDT (final int y, final int m)
  {
    return PDTFactory.createDateTime (y, m, 1);
  }

  @Test
  public void testGetWeeksOfMonth ()
  {
    // Default cases:
    assertEquals (1, PDTHelper.getStartWeekOfMonth (_getDT (2008, DateTimeConstants.JANUARY)));
    assertEquals (5, PDTHelper.getEndWeekOfMonth (_getDT (2008, DateTimeConstants.JANUARY)));

    assertEquals (5, PDTHelper.getStartWeekOfMonth (_getDT (2008, DateTimeConstants.FEBRUARY)));
    assertEquals (9, PDTHelper.getEndWeekOfMonth (_getDT (2008, DateTimeConstants.FEBRUARY)));

    assertEquals (9, PDTHelper.getStartWeekOfMonth (_getDT (2008, DateTimeConstants.MARCH)));
    assertEquals (14, PDTHelper.getEndWeekOfMonth (_getDT (2008, DateTimeConstants.MARCH)));

    // Special case: August ends with a sunday and therefore the last week is
    // different from the beginning of the following week
    assertEquals (31, PDTHelper.getStartWeekOfMonth (_getDT (2008, DateTimeConstants.AUGUST)));
    assertEquals (35, PDTHelper.getEndWeekOfMonth (_getDT (2008, DateTimeConstants.AUGUST)));

    assertEquals (36, PDTHelper.getStartWeekOfMonth (_getDT (2008, DateTimeConstants.SEPTEMBER)));
    assertEquals (40, PDTHelper.getEndWeekOfMonth (_getDT (2008, DateTimeConstants.SEPTEMBER)));
  }

  @Test
  public void testGetWeekDaysBetweenl ()
  {
    final LocalDate aWeekendDate = PDTFactory.getCurrentLocalDate ().withDayOfWeek (DateTimeConstants.SUNDAY);
    final LocalDate aStartDate = PDTFactory.getCurrentLocalDate ().withDayOfWeek (DateTimeConstants.MONDAY);
    final LocalDate aEndDate = PDTFactory.getCurrentLocalDate ().withDayOfWeek (DateTimeConstants.TUESDAY);
    assertEquals (0, PDTHelper.getWeekDays (aWeekendDate, aWeekendDate));
    assertEquals (1, PDTHelper.getWeekDays (aStartDate, aStartDate));
    assertEquals (2, PDTHelper.getWeekDays (aStartDate, aEndDate));
    assertEquals (-2, PDTHelper.getWeekDays (aEndDate, aStartDate));
    assertEquals (6, PDTHelper.getWeekDays (aStartDate, aStartDate.plusWeeks (1)));
    assertEquals (-6, PDTHelper.getWeekDays (aStartDate.plusWeeks (1), aStartDate));
  }

  @Test
  public void testMinMaxLocalDate ()
  {
    final LocalDate aDate1 = PDTFactory.createLocalDate (2009, DateTimeConstants.JANUARY, 1);
    final LocalDate aDate2 = PDTFactory.createLocalDate (2010, DateTimeConstants.JULY, 6);
    assertSame (aDate1, PDTHelper.min (aDate1, aDate2));
    assertSame (aDate1, PDTHelper.min (aDate2, aDate1));
    assertSame (aDate1, PDTHelper.min (aDate1, aDate1));
    assertSame (aDate2, PDTHelper.min (aDate2, aDate2));
    assertSame (aDate1, PDTHelper.min (aDate1, null));
    assertSame (aDate1, PDTHelper.min (null, aDate1));
    assertNull (PDTHelper.min ((LocalDate) null, null));

    assertSame (aDate2, PDTHelper.max (aDate1, aDate2));
    assertSame (aDate2, PDTHelper.max (aDate2, aDate1));
    assertSame (aDate1, PDTHelper.max (aDate1, aDate1));
    assertSame (aDate2, PDTHelper.max (aDate2, aDate2));
    assertSame (aDate1, PDTHelper.max (aDate1, null));
    assertSame (aDate1, PDTHelper.max (null, aDate1));
    assertNull (PDTHelper.min ((LocalDate) null, null));
  }

  @Test
  public void testMinMaxLocalTime ()
  {
    final LocalTime aTime1 = PDTFactory.createLocalTime (6, 30, 0);
    final LocalTime aTime2 = PDTFactory.createLocalTime (18, 15, 30);
    assertSame (aTime1, PDTHelper.min (aTime1, aTime2));
    assertSame (aTime1, PDTHelper.min (aTime2, aTime1));
    assertSame (aTime1, PDTHelper.min (aTime1, aTime1));
    assertSame (aTime2, PDTHelper.min (aTime2, aTime2));
    assertSame (aTime1, PDTHelper.min (aTime1, null));
    assertSame (aTime1, PDTHelper.min (null, aTime1));
    assertNull (PDTHelper.min ((LocalTime) null, null));

    assertSame (aTime2, PDTHelper.max (aTime1, aTime2));
    assertSame (aTime2, PDTHelper.max (aTime2, aTime1));
    assertSame (aTime1, PDTHelper.max (aTime1, aTime1));
    assertSame (aTime2, PDTHelper.max (aTime2, aTime2));
    assertSame (aTime1, PDTHelper.max (aTime1, null));
    assertSame (aTime1, PDTHelper.max (null, aTime1));
    assertNull (PDTHelper.min ((LocalTime) null, null));
  }

  @Test
  public void testMinMaxDateTime ()
  {
    final DateTime aTime1 = PDTFactory.getCurrentDateTime ();
    final DateTime aTime2 = aTime1.plusMinutes (1);
    assertSame (aTime1, PDTHelper.min (aTime1, aTime2));
    assertSame (aTime1, PDTHelper.min (aTime2, aTime1));
    assertSame (aTime1, PDTHelper.min (aTime1, aTime1));
    assertSame (aTime2, PDTHelper.min (aTime2, aTime2));
    assertSame (aTime1, PDTHelper.min (aTime1, null));
    assertSame (aTime1, PDTHelper.min (null, aTime1));
    assertNull (PDTHelper.min ((DateTime) null, null));

    assertSame (aTime2, PDTHelper.max (aTime1, aTime2));
    assertSame (aTime2, PDTHelper.max (aTime2, aTime1));
    assertSame (aTime1, PDTHelper.max (aTime1, aTime1));
    assertSame (aTime2, PDTHelper.max (aTime2, aTime2));
    assertSame (aTime1, PDTHelper.max (aTime1, null));
    assertSame (aTime1, PDTHelper.max (null, aTime1));
    assertNull (PDTHelper.min ((DateTime) null, null));
  }

  @Test
  public void testBirthdayCompare ()
  {
    final LocalDate aDate1 = PDTFactory.createLocalDate (1980, DateTimeConstants.JULY, 6);
    final LocalDate aDate2 = PDTFactory.createLocalDate (1978, DateTimeConstants.JULY, 6);
    final LocalDate aDate3 = PDTFactory.createLocalDate (1978, DateTimeConstants.DECEMBER, 2);
    assertEquals (0, PDTHelper.birthdayCompare (aDate1, aDate2));
    assertTrue (PDTHelper.birthdayCompare (aDate1, aDate3) < 0);
    assertTrue (PDTHelper.birthdayCompare (aDate3, aDate2) > 0);
    assertEquals (0, PDTHelper.birthdayCompare (null, null));
    assertTrue (PDTHelper.birthdayCompare (null, aDate3) < 0);
    assertTrue (PDTHelper.birthdayCompare (aDate3, null) > 0);

    assertTrue (PDTHelper.birthdayEquals (aDate1, aDate2));
    assertFalse (PDTHelper.birthdayEquals (aDate1, aDate3));
    assertFalse (PDTHelper.birthdayEquals (aDate3, aDate2));
    assertTrue (PDTHelper.birthdayEquals (null, null));
    assertFalse (PDTHelper.birthdayEquals (null, aDate3));
    assertFalse (PDTHelper.birthdayEquals (aDate3, null));
  }

  @Test
  public void testComparePeriods ()
  {
    final Period p1 = Period.fieldDifference (PDTFactory.createLocalTime (6, 0, 0),
                                              PDTFactory.createLocalTime (15, 0, 0));
    Period p2 = new Period ().plusHours (9);
    // Different field size
    assertFalse (p1.equals (p2));

    // But this leads to equality :)
    assertFalse (PDTHelper.isLess (p1, p2));
    assertTrue (PDTHelper.isLessOrEqual (p1, p2));
    assertEquals (0, PDTHelper.compare (p1, p2));
    assertTrue (PDTHelper.equals (p1, p2));
    assertTrue (PDTHelper.isGreaterOrEqual (p1, p2));
    assertFalse (PDTHelper.isGreater (p1, p2));

    p2 = p2.plusMinutes (1);
    assertEquals (-1, PDTHelper.compare (p1, p2));
    assertTrue (PDTHelper.isLess (p1, p2));
    assertTrue (PDTHelper.isLessOrEqual (p1, p2));
    assertFalse (PDTHelper.equals (p1, p2));
    assertFalse (PDTHelper.isGreaterOrEqual (p1, p2));
    assertFalse (PDTHelper.isGreater (p1, p2));
    assertEquals (+1, PDTHelper.compare (p2, p1));
    assertFalse (PDTHelper.equals (p2, p1));

    assertFalse (PDTHelper.isLess (p2, p1));
    assertFalse (PDTHelper.isLessOrEqual (p2, p1));
    assertFalse (PDTHelper.equals (p2, p1));
    assertTrue (PDTHelper.isGreaterOrEqual (p2, p1));
    assertTrue (PDTHelper.isGreater (p2, p1));
  }
}
