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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.junit.Test;

import com.helger.datetime.config.PDTConfig;

/**
 * Test class for class {@link PDTFactory}.
 *
 * @author Philip Helger
 */
public final class PDTFactoryTest
{
  @Test
  public void testLeap ()
  {
    LocalDate aLD;

    aLD = PDTFactory.createLocalDate (1900, DateTimeConstants.JANUARY, 28);
    assertFalse (aLD.year ().isLeap ());
    assertFalse (aLD.monthOfYear ().isLeap ());

    aLD = PDTFactory.createLocalDate (2000, DateTimeConstants.JANUARY, 28);
    assertTrue (aLD.year ().isLeap ());
    assertFalse (aLD.monthOfYear ().isLeap ());

    aLD = PDTFactory.createLocalDate (2008, DateTimeConstants.JANUARY, 28);
    assertTrue (aLD.year ().isLeap ());
    assertFalse (aLD.monthOfYear ().isLeap ());

    aLD = PDTFactory.createLocalDate (2008, DateTimeConstants.JANUARY, 29);
    assertTrue (aLD.year ().isLeap ());
    assertFalse (aLD.monthOfYear ().isLeap ());

    aLD = PDTFactory.createLocalDate (2008, DateTimeConstants.FEBRUARY, 28);
    assertTrue (aLD.year ().isLeap ());
    assertTrue (aLD.monthOfYear ().isLeap ());

    aLD = PDTFactory.createLocalDate (2008, DateTimeConstants.FEBRUARY, 29);
    assertTrue (aLD.year ().isLeap ());
    assertTrue (aLD.monthOfYear ().isLeap ());

    aLD = PDTFactory.createLocalDate (2008, DateTimeConstants.MARCH, 1);
    assertTrue (aLD.year ().isLeap ());
    assertFalse (aLD.monthOfYear ().isLeap ());

    aLD = PDTFactory.createLocalDate (2009, DateTimeConstants.FEBRUARY, 28);
    assertFalse (aLD.year ().isLeap ());
    assertFalse (aLD.monthOfYear ().isLeap ());
  }

  @Test
  public void testAPI ()
  {
    assertNotNull (PDTFactory.getCurrentDateTime ());
    assertNotNull (PDTFactory.getCurrentDateTimeUTC ());
    assertNotNull (PDTFactory.getCurrentLocalDate ());
    assertNotNull (PDTFactory.getCurrentLocalDateTime ());
    assertNotNull (PDTFactory.getCurrentLocalTime ());
    PDTFactory.getCurrentMillis ();
    assertNotNull (PDTFactory.getCurrentMutableDateTime ());
    assertTrue (PDTFactory.getCurrentYear () > 0);

    // Check String parameters
    assertNotNull (PDTFactory.createLocalDateTime ("1234567"));
    assertNotNull (PDTFactory.createLocalDate ("2009-02-28"));
    assertNotNull (PDTFactory.createLocalTime ("12:00:56"));

    // Check duration ws. period
    final DateTime aDT1 = PDTFactory.getCurrentDateTime ();
    final DateTime aDT2 = PDTFactory.getCurrentDateTime ().plusHours (1).plusMinutes (30);
    final Period aPeriod = new Period (aDT1, aDT2);
    assertEquals (30, aPeriod.getMinutes ());
    assertEquals (90, aPeriod.toStandardMinutes ().getMinutes ());
  }

  @Test
  public void testChronology ()
  {
    final Calendar aCal = new GregorianCalendar (2011, Calendar.JULY, 31);
    aCal.setTimeZone (PDTConfig.getDefaultTimeZone ());
    final Date aDate = aCal.getTime ();

    LocalDate aLD = PDTFactory.createLocalDate (aDate);
    assertEquals (aDate, aLD.toDateTimeAtStartOfDay (PDTConfig.getDefaultDateTimeZone ()).toDate ());
    assertEquals (aCal, aLD.toDateTimeAtStartOfDay (PDTConfig.getDefaultDateTimeZone ()).toCalendar (null));
    assertEquals (PDTConfig.getDefaultChronologyUTC (), aLD.getChronology ());
    DateTime aDT = PDTFactory.createDateTime (aLD);
    assertEquals (PDTConfig.getDefaultChronology (), aDT.getChronology ());

    aLD = PDTFactory.createLocalDate (aCal);
    assertEquals (aDate, aLD.toDateTimeAtStartOfDay ().toDate ());
    assertEquals (aCal, aLD.toDateTimeAtStartOfDay (PDTConfig.getDefaultDateTimeZone ()).toCalendar (null));

    PDTConfig.setUseISOChronology (!PDTConfig.DEFAULT_USE_ISO_CHRONOLOGY);
    try
    {
      aLD = PDTFactory.createLocalDate (aDate);
      assertEquals (aDate, aLD.toDateTimeAtStartOfDay ().toDate ());
      assertEquals (aCal, aLD.toDateTimeAtStartOfDay (PDTConfig.getDefaultDateTimeZone ()).toCalendar (null));
      assertEquals (PDTConfig.getDefaultChronologyUTC (), aLD.getChronology ());
      aDT = PDTFactory.createDateTime (aLD);
      assertEquals (PDTConfig.getDefaultChronology (), aDT.getChronology ());

      aLD = PDTFactory.createLocalDate (aCal);
      assertEquals (aDate, aLD.toDateTimeAtStartOfDay ().toDate ());
      assertEquals (aCal, aLD.toDateTimeAtStartOfDay (PDTConfig.getDefaultDateTimeZone ()).toCalendar (null));
    }
    finally
    {
      PDTConfig.setUseISOChronology (PDTConfig.DEFAULT_USE_ISO_CHRONOLOGY);
    }
  }

  @Test
  public void testConvert ()
  {
    LocalDate aLD1 = PDTFactory.getCurrentLocalDate ();
    DateTime aDT = PDTFactory.createDateTime (aLD1);
    assertEquals (aLD1, aDT.toLocalDate ());

    LocalTime aLT1 = PDTFactory.getCurrentLocalTime ();
    aDT = PDTFactory.createDateTime (aLT1);
    assertEquals (aLT1, aDT.toLocalTime ());

    LocalDateTime aLDT1 = PDTFactory.getCurrentLocalDateTime ();
    aDT = PDTFactory.createDateTime (aLDT1);
    assertEquals (aLDT1, aDT.toLocalDateTime ());

    aDT = PDTFactory.getCurrentDateTime ();
    final LocalDateTime aLDT = PDTFactory.createLocalDateTime (aDT);
    final DateTime aDT2 = PDTFactory.createDateTime (aLDT);
    assertEquals (aDT, aDT2);

    final long nMillis = 1328008438966L;
    aDT = PDTFactory.createDateTimeFromMillis (nMillis);
    assertNotNull (aDT);
    assertEquals (aDT, PDTFactory.createDateTime (2012, DateTimeConstants.JANUARY, 31, 12, 13, 58, 966));
    assertEquals (aDT, PDTFactory.createDateTime (new Date (nMillis)));

    aLD1 = PDTFactory.createLocalDateFromMillis (nMillis);
    assertNotNull (aLD1);
    assertEquals (aLD1, PDTFactory.createLocalDate (2012, DateTimeConstants.JANUARY, 31));
    assertEquals (aLD1, PDTFactory.createLocalDate (new Date (nMillis)));

    aLT1 = PDTFactory.createLocalTimeFromMillis (nMillis);
    assertNotNull (aLT1);
    assertEquals (aLT1, PDTFactory.createLocalTime (12, 13, 58, 966));
    assertEquals (aLT1, PDTFactory.createLocalTime (new Date (nMillis)));

    aLDT1 = PDTFactory.createLocalDateTimeFromMillis (nMillis);
    assertNotNull (aLDT1);
    assertEquals (aLDT1, PDTFactory.createLocalDateTime (2012, DateTimeConstants.JANUARY, 31, 12, 13, 58, 966));
    assertEquals (aLDT1, PDTFactory.createLocalDateTime (new Date (nMillis)));
  }
}
