/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.helger.collection.commons.CommonsHashSet;
import com.helger.collection.commons.ICommonsSet;
import com.helger.text.locale.country.ECountry;

/**
 * The Class ISOCodesTest.
 *
 * @author svdi1de
 */
public final class ISOCodesFuncTest
{
  private static final int NUMBER_OF_ISOCOUNTRIES = 244;

  /**
   * Returns a list of ISO codes.
   *
   * @return 2-digit ISO codes.
   */
  public static final ICommonsSet <String> getISOCodes ()
  {
    final ICommonsSet <String> codes = new CommonsHashSet <> ();
    for (final ECountry eCountry : ECountry.values ())
      codes.add (eCountry.getISOCountryCode ());
    return codes;
  }

  /**
   * Test iso codes.
   */
  @Test
  public void testISOCodes ()
  {
    final ICommonsSet <String> isoCodes = getISOCodes ();
    assertNotNull (isoCodes);
    assertEquals ("Wrong number of ISO codes.", NUMBER_OF_ISOCOUNTRIES, isoCodes.size ());
  }
}
