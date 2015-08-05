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
package com.helger.datetime;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Test;

import com.helger.datetime.config.PDTConfig;

/**
 * Test class for class {@link CPDT}.
 *
 * @author Philip Helger
 */
public final class CPDTTest
{
  @Test
  public void testNullLocalDate ()
  {
    assertEquals (1, CPDT.NULL_LOCAL_DATE.getDayOfMonth ());
    assertEquals (DateTimeConstants.JANUARY, CPDT.NULL_LOCAL_DATE.getMonthOfYear ());
    assertEquals (CPDT.MIN_YEAR_INT32, CPDT.NULL_LOCAL_DATE.getYear ());
  }

  @Test
  public void testNullLocalTime ()
  {
    assertEquals (0, CPDT.NULL_LOCAL_TIME.getHourOfDay ());
    assertEquals (0, CPDT.NULL_LOCAL_TIME.getMinuteOfHour ());
    assertEquals (0, CPDT.NULL_LOCAL_TIME.getSecondOfMinute ());
    assertEquals (0, CPDT.NULL_LOCAL_TIME.getMillisOfSecond ());
  }

  @Test
  public void testNullLocalDateTime ()
  {
    assertEquals (1, CPDT.NULL_LOCAL_DATETIME.getDayOfMonth ());
    assertEquals (DateTimeConstants.JANUARY, CPDT.NULL_LOCAL_DATETIME.getMonthOfYear ());
    assertEquals (CPDT.MIN_YEAR_INT32, CPDT.NULL_LOCAL_DATETIME.getYear ());
    assertEquals (0, CPDT.NULL_LOCAL_DATETIME.getHourOfDay ());
    assertEquals (0, CPDT.NULL_LOCAL_DATETIME.getMinuteOfHour ());
    assertEquals (0, CPDT.NULL_LOCAL_DATETIME.getSecondOfMinute ());
    assertEquals (0, CPDT.NULL_LOCAL_DATETIME.getMillisOfSecond ());
  }

  @Test
  public void testNullDateTime ()
  {
    // By default the local chronology is used - must be converted to UTC to
    // deliver a reall NULL date
    final DateTime aNullDTUTC = CPDT.NULL_DATETIME.withChronology (PDTConfig.getDefaultChronologyUTC ());
    assertEquals (1, aNullDTUTC.getDayOfMonth ());
    assertEquals (DateTimeConstants.JANUARY, aNullDTUTC.getMonthOfYear ());
    assertEquals (CPDT.MIN_YEAR_INT32, aNullDTUTC.getYear ());
    assertEquals (0, aNullDTUTC.getHourOfDay ());
    assertEquals (0, aNullDTUTC.getMinuteOfHour ());
    assertEquals (0, aNullDTUTC.getSecondOfMinute ());
    assertEquals (0, aNullDTUTC.getMillisOfSecond ());
  }

  @Test
  public void testNullDateTimeUTC ()
  {
    assertEquals (1, CPDT.NULL_DATETIME_UTC.getDayOfMonth ());
    assertEquals (DateTimeConstants.JANUARY, CPDT.NULL_DATETIME_UTC.getMonthOfYear ());
    assertEquals (CPDT.MIN_YEAR_INT32, CPDT.NULL_DATETIME_UTC.getYear ());
    assertEquals (0, CPDT.NULL_DATETIME_UTC.getHourOfDay ());
    assertEquals (0, CPDT.NULL_DATETIME_UTC.getMinuteOfHour ());
    assertEquals (0, CPDT.NULL_DATETIME_UTC.getSecondOfMinute ());
    assertEquals (0, CPDT.NULL_DATETIME_UTC.getMillisOfSecond ());
  }

  @Test
  public void testNullPeriod ()
  {
    assertEquals (0, CPDT.NULL_PERIOD.getMillis ());
    assertEquals (0, CPDT.NULL_PERIOD.getSeconds ());
    assertEquals (0, CPDT.NULL_PERIOD.getMinutes ());
    assertEquals (0, CPDT.NULL_PERIOD.getHours ());
    assertEquals (0, CPDT.NULL_PERIOD.getDays ());
    assertEquals (0, CPDT.NULL_PERIOD.getWeeks ());
    assertEquals (0, CPDT.NULL_PERIOD.getMonths ());
    assertEquals (0, CPDT.NULL_PERIOD.getYears ());
  }

  @Test
  public void testNullDuration ()
  {
    assertEquals (0, CPDT.NULL_DURATION.getMillis ());
    assertEquals (0, CPDT.NULL_DURATION.getStandardSeconds ());
    assertEquals (0, CPDT.NULL_DURATION.getStandardMinutes ());
    assertEquals (0, CPDT.NULL_DURATION.getStandardHours ());
    assertEquals (0, CPDT.NULL_DURATION.getStandardDays ());
  }
}
