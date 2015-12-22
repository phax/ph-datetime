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

import java.time.LocalTime;
import java.time.Period;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.datetime.PDTFactory;

/**
 * Test class for class {@link LocalTimeDuration}.
 *
 * @author Philip Helger
 */
public final class LocalTimePeriodTest
{
  @Test
  public void testAll ()
  {
    LocalTimeDuration p = new LocalTimeDuration ();
    assertNull (p.getStart ());
    assertNull (p.getEnd ());

    try
    {
      p.getAsDuration ();
      fail ();
    }
    catch (final IllegalStateException ex)
    {}

    p.setStart (PDTFactory.createLocalTime (17, 15, 30));

    try
    {
      p.getAsDuration ();
      fail ();
    }
    catch (final IllegalStateException ex)
    {}

    p.setEnd (PDTFactory.createLocalTime (17, 16, 30));

    final Period per = p.getAsDuration ();
    assertNotNull (per);
    assertEquals (0, per.getYears ());
    assertEquals (0, per.getMonths ());
    assertEquals (0, per.getDays ());
    assertEquals (0, per.getHours ());
    assertEquals (1, per.getMinutes ());
    assertEquals (0, per.getSeconds ());
    assertEquals (0, per.getMillis ());

    p = new LocalTimeDuration (PDTFactory.createLocalTime (17, 15, 30));
    assertNotNull (p.getStart ());
    assertNull (p.getEnd ());

    p = new LocalTimeDuration (PDTFactory.createLocalTime (17, 15, 30), PDTFactory.createLocalTime (17, 16, 30));
    assertNotNull (p.getStart ());
    assertNotNull (p.getEnd ());
    assertEquals (per, p.getAsDuration ());

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (p,
                                                                       new LocalTimeDuration (PDTFactory.createLocalTime (17,
                                                                                                                        15,
                                                                                                                        30),
                                                                                            PDTFactory.createLocalTime (17,
                                                                                                                        16,
                                                                                                                        30)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (p,
                                                                           new LocalTimeDuration (PDTFactory.createLocalTime (17,
                                                                                                                            15,
                                                                                                                            30),
                                                                                                PDTFactory.createLocalTime (17,
                                                                                                                            15,
                                                                                                                            30)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (p,
                                                                           new LocalTimeDuration (PDTFactory.createLocalTime (17,
                                                                                                                            15,
                                                                                                                            30),
                                                                                                PDTFactory.createLocalTime (17,
                                                                                                                            17,
                                                                                                                            30)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (p,
                                                                           new LocalTimeDuration (null,
                                                                                                PDTFactory.createLocalTime (17,
                                                                                                                            16,
                                                                                                                            30)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (p,
                                                                           new LocalTimeDuration (PDTFactory.createLocalTime (17,
                                                                                                                            15,
                                                                                                                            30),
                                                                                                null));
  }

  @Test
  public void testValidity ()
  {
    LocalTimeDuration vr = new LocalTimeDuration (null, null);
    assertNull (vr.getStart ());
    assertNull (vr.getEnd ());
    assertTrue (vr.isValidForNow ());
    assertTrue (vr.isValidFor (PDTFactory.createLocalTime (0, 0, 0)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalTime (23, 59, 59)));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (vr, new LocalTimeDuration (null, null));

    try
    {
      vr.isValidFor (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}

    final LocalTime aStart = PDTFactory.createLocalTime (12, 35);
    vr = new LocalTimeDuration (aStart, null);
    assertEquals (aStart, vr.getStart ());
    assertNull (vr.getEnd ());
    assertFalse (vr.isValidFor (PDTFactory.createLocalTime (0, 0)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalTime (12, 0)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalTime (12, 34)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalTime (12, 35)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalTime (23, 59)));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (vr, new LocalTimeDuration (aStart, null));

    final LocalTime aEnd = PDTFactory.createLocalTime (15, 12);
    vr = new LocalTimeDuration (aStart, aEnd);
    assertEquals (aStart, vr.getStart ());
    assertEquals (aEnd, vr.getEnd ());
    // Start date
    assertFalse (vr.isValidFor (PDTFactory.createLocalTime (0, 0)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalTime (12, 0)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalTime (12, 34)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalTime (12, 35)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalTime (15, 0)));
    assertTrue (vr.isValidFor (PDTFactory.createLocalTime (15, 12)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalTime (15, 13)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalTime (16, 0)));
    assertFalse (vr.isValidFor (PDTFactory.createLocalTime (23, 59)));

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (vr, new LocalTimeDuration (aStart, aEnd));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (vr,
                                                                           new LocalTimeDuration (aStart.plusMinutes (1),
                                                                                                aEnd));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (vr,
                                                                           new LocalTimeDuration (aStart,
                                                                                                aEnd.plusMinutes (1)));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (vr, new LocalTimeDuration (null, aEnd));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (vr, new LocalTimeDuration (aStart, null));
  }
}
