/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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

import java.util.Locale;

import org.joda.time.Period;
import org.junit.Test;

import com.helger.commons.locale.LocaleCache;

/**
 * Test class for class {@link PeriodFormatMultilingual}.
 * 
 * @author Philip Helger
 */
public final class PeriodFormatMultilingualTest
{
  @Test
  public void testSimple ()
  {
    final Locale DE = LocaleCache.getLocale ("de", "AT");
    final Period aPeriod = new Period (123456);
    assertNotNull (PeriodFormatMultilingual.getFormatterLong (DE).print (aPeriod));
    assertNotNull (PeriodFormatMultilingual.getFormatterShort (DE).print (aPeriod));
    assertNotNull (PeriodFormatMultilingual.getFormatterVeryShort (DE).print (aPeriod));
  }
}
