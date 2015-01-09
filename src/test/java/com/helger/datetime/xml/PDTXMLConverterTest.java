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
package com.helger.datetime.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

import com.helger.commons.CGlobal;
import com.helger.datetime.PDTFactory;
import com.helger.datetime.config.PDTConfig;

/**
 * Test class for class {@link PDTXMLConverter}
 * 
 * @author Philip Helger
 */
public final class PDTXMLConverterTest
{
  @Test
  public void testCreateNewCalendar ()
  {
    final XMLGregorianCalendar c1 = PDTXMLConverter.createNewCalendar ();
    assertNotNull (c1);
    final XMLGregorianCalendar c2 = PDTXMLConverter.createNewCalendar ();
    assertNotNull (c2);
    assertTrue (c1 != c2);
    assertEquals (c1, c2);
  }

  @Test
  public void testLocalDate ()
  {
    assertNull (PDTXMLConverter.getXMLCalendarDate ((LocalDate) null));
    final LocalDate aLD = PDTFactory.getCurrentLocalDate ();
    final XMLGregorianCalendar c1 = PDTXMLConverter.getXMLCalendarDate (aLD);
    assertNotNull (c1);
    assertEquals (aLD.getYear (), c1.getYear ());
    assertEquals (aLD.getMonthOfYear (), c1.getMonth ());
    assertEquals (aLD.getDayOfMonth (), c1.getDay ());
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getHour ());
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getMinute ());
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getSecond ());
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getMillisecond ());
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getTimezone ());
    final LocalDate aLD2 = PDTXMLConverter.getLocalDate (c1);
    assertNotNull (aLD2);
    assertEquals (aLD, aLD2);
    assertNull (PDTXMLConverter.getLocalDate (null));
  }

  @Test
  public void testLocalTime ()
  {
    assertNull (PDTXMLConverter.getXMLCalendarTime ((LocalTime) null));
    final LocalTime aLT = PDTFactory.getCurrentLocalTime ();
    final XMLGregorianCalendar c1 = PDTXMLConverter.getXMLCalendarTime (aLT);
    assertNotNull (c1);
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getYear ());
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getMonth ());
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getDay ());
    assertEquals (aLT.getHourOfDay (), c1.getHour ());
    assertEquals (aLT.getMinuteOfHour (), c1.getMinute ());
    assertEquals (aLT.getSecondOfMinute (), c1.getSecond ());
    assertEquals (aLT.getMillisOfSecond (), c1.getMillisecond ());
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getTimezone ());
    final LocalTime aLT2 = PDTXMLConverter.getLocalTime (c1);
    assertNotNull (aLT2);
    assertEquals (aLT, aLT2);
    assertNull (PDTXMLConverter.getLocalTime (null));
  }

  @Test
  public void testLocalDateTime ()
  {
    assertNull (PDTXMLConverter.getXMLCalendar ((LocalDateTime) null));
    final LocalDateTime aLDT = PDTFactory.getCurrentLocalDateTime ();
    final XMLGregorianCalendar c1 = PDTXMLConverter.getXMLCalendar (aLDT);
    assertNotNull (c1);
    assertEquals (aLDT.getYear (), c1.getYear ());
    assertEquals (aLDT.getMonthOfYear (), c1.getMonth ());
    assertEquals (aLDT.getDayOfMonth (), c1.getDay ());
    assertEquals (aLDT.getHourOfDay (), c1.getHour ());
    assertEquals (aLDT.getMinuteOfHour (), c1.getMinute ());
    assertEquals (aLDT.getSecondOfMinute (), c1.getSecond ());
    assertEquals (aLDT.getMillisOfSecond (), c1.getMillisecond ());
    assertEquals (DatatypeConstants.FIELD_UNDEFINED, c1.getTimezone ());
    final LocalDateTime aLT2 = PDTXMLConverter.getLocalDateTime (c1);
    assertNotNull (aLT2);
    assertEquals (aLDT, aLT2);
    assertNull (PDTXMLConverter.getLocalDateTime (null));
  }

  @Test
  public void testDateTime ()
  {
    assertNull (PDTXMLConverter.getXMLCalendar ((DateTime) null));
    final DateTime aLDT = PDTFactory.getCurrentDateTime ();
    final XMLGregorianCalendar c1 = PDTXMLConverter.getXMLCalendar (aLDT);
    assertNotNull (c1);
    assertEquals (aLDT.getYear (), c1.getYear ());
    assertEquals (aLDT.getMonthOfYear (), c1.getMonth ());
    assertEquals (aLDT.getDayOfMonth (), c1.getDay ());
    assertEquals (aLDT.getHourOfDay (), c1.getHour ());
    assertEquals (aLDT.getMinuteOfHour (), c1.getMinute ());
    assertEquals (aLDT.getSecondOfMinute (), c1.getSecond ());
    assertEquals (aLDT.getMillisOfSecond (), c1.getMillisecond ());
    assertEquals (aLDT.getChronology ().getZone ().getOffset (aLDT) / CGlobal.MILLISECONDS_PER_MINUTE,
                  c1.getTimezone ());
    final DateTime aLDT2 = PDTXMLConverter.getDateTime (c1);
    assertNotNull (aLDT2);
    assertEquals (aLDT.withChronology (PDTConfig.getDefaultChronology ()),
                  aLDT2.withChronology (PDTConfig.getDefaultChronology ()));
    assertNull (PDTXMLConverter.getDateTime (null));
  }

  @Test
  public void testNow ()
  {
    assertNotNull (PDTXMLConverter.getXMLCalendarNow ());
    assertNotNull (PDTXMLConverter.getXMLCalendarDateNow ());
    assertNotNull (PDTXMLConverter.getXMLCalendarTimeNow ());
  }
}
