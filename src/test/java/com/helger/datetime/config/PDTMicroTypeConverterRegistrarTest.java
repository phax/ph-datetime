/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
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
package com.helger.datetime.config;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.helger.commons.microdom.convert.MicroTypeConverter;
import com.helger.datetime.CPDT;
import com.helger.datetime.PDTFactory;

/**
 * Test class for class {@link PDTMicroTypeConverterRegistrar}.
 * 
 * @author Philip Helger
 */
public final class PDTMicroTypeConverterRegistrarTest
{
  private static final String ELEMENT_NAME = "element";

  @Test
  public void testMicroTypeConversion ()
  {
    assertNotNull (MicroTypeConverter.convertToMicroElement (new GregorianCalendar (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (new Date (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (PDTFactory.getCurrentDateTime (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (PDTFactory.getCurrentLocalDateTime (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (PDTFactory.getCurrentLocalDate (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (PDTFactory.getCurrentLocalTime (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (CPDT.NULL_DURATION, ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (CPDT.NULL_PERIOD, ELEMENT_NAME));
  }
}
