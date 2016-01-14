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
package com.helger.datetime.format;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Test;

import com.helger.commons.locale.LocaleCache;

/**
 * Test class for class {@link PDTFormatter}.
 *
 * @author Philip Helger
 */
public final class PDTFormatterTest
{
  @Test
  public void testGetDefaultFormatter ()
  {
    for (final Locale aLocale : LocaleCache.getInstance ().getAllLocales ())
    {
      // get formatter
      assertNotNull (PDTFormatter.getDefaultFormatterDate (aLocale));
      assertNotNull (PDTFormatter.getDefaultFormatterTime (aLocale));
      assertNotNull (PDTFormatter.getDefaultFormatterDateTime (aLocale));
    }
    assertNotNull (PDTFormatter.getDefaultFormatterDate (null));
    assertNotNull (PDTFormatter.getDefaultFormatterTime (null));
    assertNotNull (PDTFormatter.getDefaultFormatterDateTime (null));
  }

  @Test
  public void testGetForPattern ()
  {
    for (final Locale aLocale : LocaleCache.getInstance ().getAllLocales ())
      assertNotNull (PDTFormatter.getForPattern ("yyyy-MM-dd", aLocale));
    assertNotNull (PDTFormatter.getForPattern ("yyyy-MM-dd"));
    assertNotNull (PDTFormatter.getForPattern ("yyyy-MM-dd", null));

    try
    {
      PDTFormatter.getForPattern ("abcdefghijklmnopqrstuvwxyz");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }
}
