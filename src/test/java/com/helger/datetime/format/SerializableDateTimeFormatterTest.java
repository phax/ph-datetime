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
package com.helger.datetime.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Locale;

import org.junit.Test;

import com.helger.commons.mock.PHTestUtils;
import com.helger.datetime.format.SerializableDateTimeFormatter.EFormatStyle;

/**
 * Test class for class {@link SerializableDateTimeFormatter}
 *
 * @author Philip Helger
 */
public final class SerializableDateTimeFormatterTest
{
  @Test
  public void testBasic ()
  {
    SerializableDateTimeFormatter s = SerializableDateTimeFormatter.create (EFormatStyle.DEFAULT, EFormatStyle.DEFAULT);
    assertNotNull (s.getFormatter ());
    assertSame (EFormatStyle.DEFAULT, s.getDateStyle ());
    assertSame (EFormatStyle.DEFAULT, s.getTimeStyle ());
    assertNull (s.getPattern ());
    assertNull (s.getLocale ());
    PHTestUtils.testDefaultSerialization (s);

    s = SerializableDateTimeFormatter.create (EFormatStyle.DEFAULT, EFormatStyle.DEFAULT, Locale.US);
    assertNotNull (s.getFormatter ());
    assertSame (EFormatStyle.DEFAULT, s.getDateStyle ());
    assertSame (EFormatStyle.DEFAULT, s.getTimeStyle ());
    assertNull (s.getPattern ());
    assertSame (Locale.US, s.getLocale ());
    PHTestUtils.testDefaultSerialization (s);

    s = SerializableDateTimeFormatter.create ("mmYY", Locale.US);
    assertNotNull (s.getFormatter ());
    assertNull (s.getDateStyle ());
    assertNull (s.getTimeStyle ());
    assertEquals ("mmYY", s.getPattern ());
    assertSame (Locale.US, s.getLocale ());
    PHTestUtils.testDefaultSerialization (s);
  }
}
