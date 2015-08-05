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
package com.helger.datetime.period;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.datetime.PDTFactory;

/**
 * Test class for class {@link LocalDatePeriod}.
 *
 * @author Philip Helger
 */
public final class LocalDatePeriodTest
{
  @Test
  public void testAll ()
  {
    LocalDatePeriod p = new LocalDatePeriod ();
    assertNull (p.getStart ());
    assertNull (p.getEnd ());

    try
    {
      p.getAsPeriod ();
      fail ();
    }
    catch (final IllegalStateException ex)
    {}

    p.setStart (PDTFactory.createLocalDate (2010, DateTimeConstants.FEBRUARY, 10));

    try
    {
      p.getAsPeriod ();
      fail ();
    }
    catch (final IllegalStateException ex)
    {}

    p.setEnd (PDTFactory.createLocalDate (2010, DateTimeConstants.FEBRUARY, 11));

    final Period per = p.getAsPeriod ();
    assertNotNull (per);
    assertEquals (0, per.getYears ());
    assertEquals (0, per.getMonths ());
    assertEquals (1, per.getDays ());
    assertEquals (0, per.getHours ());
    assertEquals (0, per.getMinutes ());
    assertEquals (0, per.getSeconds ());
    assertEquals (0, per.getMillis ());

    p = new LocalDatePeriod (PDTFactory.createLocalDate (2010, DateTimeConstants.FEBRUARY, 10));
    assertNotNull (p.getStart ());
    assertNull (p.getEnd ());

    p = new LocalDatePeriod (PDTFactory.createLocalDate (2010, DateTimeConstants.FEBRUARY, 10),
                             PDTFactory.createLocalDate (2010, DateTimeConstants.FEBRUARY, 11));
    assertNotNull (p.getStart ());
    assertNotNull (p.getEnd ());
    assertEquals (per, p.getAsPeriod ());

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (p,
                                                                       new LocalDatePeriod (PDTFactory.createLocalDate (2010,
                                                                                                                        DateTimeConstants.FEBRUARY,
                                                                                                                        10),
                                                                                            PDTFactory.createLocalDate (2010,
                                                                                                                        DateTimeConstants.FEBRUARY,
                                                                                                                        11)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (p,
                                                                           new LocalDatePeriod (PDTFactory.createLocalDate (2010,
                                                                                                                            DateTimeConstants.FEBRUARY,
                                                                                                                            11),
                                                                                                PDTFactory.createLocalDate (2010,
                                                                                                                            DateTimeConstants.FEBRUARY,
                                                                                                                            11)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (p,
                                                                           new LocalDatePeriod (PDTFactory.createLocalDate (2010,
                                                                                                                            DateTimeConstants.FEBRUARY,
                                                                                                                            10),
                                                                                                PDTFactory.createLocalDate (2010,
                                                                                                                            DateTimeConstants.FEBRUARY,
                                                                                                                            12)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (p,
                                                                           new LocalDatePeriod (null,
                                                                                                PDTFactory.createLocalDate (2010,
                                                                                                                            DateTimeConstants.FEBRUARY,
                                                                                                                            11)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (p,
                                                                           new LocalDatePeriod (PDTFactory.createLocalDate (2010,
                                                                                                                            DateTimeConstants.FEBRUARY,
                                                                                                                            10),
                                                                                                null));
  }

  @Test
  public void testValidity ()
  {
    LocalDatePeriod vr = new LocalDatePeriod (null, null);
    assertNull (vr.getStart ());
    assertNull (vr.getEnd ());
    assertTrue (vr.isValidForNow ());
    assertTrue (vr.isValidFor (PDTFactory.createLocalDate (2000, DateTimeConstants.JANUARY, 1)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalDate (9999, DateTimeConstants.DECEMBER, 31)));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (vr, new LocalDatePeriod (null, null));

    try
    {
      vr.isValidFor (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}

    final LocalDate aStart = PDTFactory.createLocalDate (2011, DateTimeConstants.JULY, 18);
    vr = new LocalDatePeriod (aStart, null);
    assertEquals (aStart, vr.getStart ());
    assertNull (vr.getEnd ());
    assertTrue (vr.isValidForNow ());
    // Start date
    assertFalse (vr.isValidFor (PDTFactory.createLocalDate (2000, DateTimeConstants.JANUARY, 1)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalDate (2011, DateTimeConstants.JULY, 17)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalDate (2011, DateTimeConstants.JULY, 18)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalDate (2011, DateTimeConstants.JULY, 19)));
    // End date
    assertTrue (vr.isValidFor (PDTFactory.createLocalDate (9999, DateTimeConstants.DECEMBER, 31)));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (vr, new LocalDatePeriod (aStart, null));

    final LocalDate aEnd = PDTFactory.createLocalDate (2011, DateTimeConstants.NOVEMBER, 18);
    vr = new LocalDatePeriod (aStart, aEnd);
    assertEquals (aStart, vr.getStart ());
    assertEquals (aEnd, vr.getEnd ());
    assertFalse (vr.isValidForNow ());
    // Start date
    assertFalse (vr.isValidFor (PDTFactory.createLocalDate (2000, DateTimeConstants.JANUARY, 1)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalDate (2011, DateTimeConstants.JULY, 17)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalDate (2011, DateTimeConstants.JULY, 18)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalDate (2011, DateTimeConstants.JULY, 19)));
    // End date
    assertTrue (vr.isValidFor (PDTFactory.createLocalDate (2011, DateTimeConstants.NOVEMBER, 17)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalDate (2011, DateTimeConstants.NOVEMBER, 18)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalDate (2011, DateTimeConstants.NOVEMBER, 19)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalDate (9999, DateTimeConstants.DECEMBER, 31)));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (vr, new LocalDatePeriod (aStart, aEnd));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (vr,
                                                                           new LocalDatePeriod (aStart.plusDays (1),
                                                                                                aEnd));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (vr,
                                                                           new LocalDatePeriod (aStart,
                                                                                                aEnd.plusDays (1)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (vr, new LocalDatePeriod (null, aEnd));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (vr, new LocalDatePeriod (aStart, null));
  }
}
