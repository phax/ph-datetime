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
package com.helger.datetime.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import com.helger.commons.collection.ext.CommonsLinkedHashMap;
import com.helger.commons.collection.ext.ICommonsOrderedMap;
import com.helger.commons.microdom.convert.MicroTypeConverter;
import com.helger.commons.mutable.MutableByte;
import com.helger.commons.mutable.MutableDouble;
import com.helger.commons.mutable.MutableFloat;
import com.helger.commons.mutable.MutableInt;
import com.helger.commons.mutable.MutableLong;
import com.helger.commons.mutable.MutableShort;
import com.helger.commons.typeconvert.TypeConverter;
import com.helger.datetime.PDTFactory;

/**
 * Test class for class {@link PDTTypeConverterRegistrar}.
 *
 * @author Philip Helger
 */
public final class PDTTypeConverterRegistrarTest
{
  private static final Object [] NUMBERS = new Object [] { new AtomicInteger (17),
                                                           new AtomicLong (1234567890),
                                                           new BigDecimal ("11238712367812368712368.32123213"),
                                                           new BigInteger ("23127893819732"),
                                                           Byte.valueOf ((byte) 5),
                                                           Double.valueOf (123.234234),
                                                           Float.valueOf (123433.324f),
                                                           Integer.valueOf (567),
                                                           Long.valueOf (213687123617283L),
                                                           Short.valueOf ((short) 12345),
                                                           new MutableByte ((byte) 47),
                                                           new MutableDouble (34432.45465),
                                                           new MutableFloat (3245678.1f),
                                                           new MutableInt (4711),
                                                           new MutableLong (4567890987654l),
                                                           new MutableShort (65532) };
  private static final String ELEMENT_NAME = "element";

  @Test
  public void testDateTime ()
  {
    assertNotNull (TypeConverter.convertIfNecessary (Calendar.getInstance (), ZonedDateTime.class));
    assertNotNull (TypeConverter.convertIfNecessary (new GregorianCalendar (), ZonedDateTime.class));
    assertNotNull (TypeConverter.convertIfNecessary (new Date (), ZonedDateTime.class));
    for (final Object aNumber : NUMBERS)
      assertNotNull (TypeConverter.convertIfNecessary (aNumber, ZonedDateTime.class));

    // Test auto conversion to and from string
    final ZonedDateTime aNow = PDTFactory.getCurrentZonedDateTime ();
    final String sNow = TypeConverter.convertIfNecessary (aNow, String.class);
    final ZonedDateTime aNow2 = TypeConverter.convertIfNecessary (sNow, aNow.getClass ());
    assertEquals (aNow, aNow2);
  }

  @Test
  public void testLocalDateTime ()
  {
    assertNotNull (TypeConverter.convertIfNecessary (Calendar.getInstance (), LocalDateTime.class));
    assertNotNull (TypeConverter.convertIfNecessary (new GregorianCalendar (), LocalDateTime.class));
    assertNotNull (TypeConverter.convertIfNecessary (new Date (), LocalDateTime.class));
    for (final Object aNumber : NUMBERS)
      assertNotNull (TypeConverter.convertIfNecessary (aNumber, LocalDateTime.class));

    // Test auto conversion to and from string
    final LocalDateTime aNow = PDTFactory.getCurrentLocalDateTime ();
    final String sNow = TypeConverter.convertIfNecessary (aNow, String.class);
    final LocalDateTime aNow2 = TypeConverter.convertIfNecessary (sNow, aNow.getClass ());
    assertEquals (aNow, aNow2);
  }

  @Test
  public void testLocalDate ()
  {
    assertNotNull (TypeConverter.convertIfNecessary (Calendar.getInstance (), LocalDate.class));
    assertNotNull (TypeConverter.convertIfNecessary (new GregorianCalendar (), LocalDate.class));
    assertNotNull (TypeConverter.convertIfNecessary (new Date (), LocalDate.class));
    for (final Object aNumber : NUMBERS)
      assertNotNull (TypeConverter.convertIfNecessary (aNumber, LocalDate.class));

    // Test auto conversion to and from string
    final LocalDate aNow = PDTFactory.getCurrentLocalDate ();
    final String sNow = TypeConverter.convertIfNecessary (aNow, String.class);
    final LocalDate aNow2 = TypeConverter.convertIfNecessary (sNow, aNow.getClass ());
    assertEquals (aNow, aNow2);
  }

  @Test
  public void testLocalTime ()
  {
    assertNotNull (TypeConverter.convertIfNecessary (Calendar.getInstance (), LocalTime.class));
    assertNotNull (TypeConverter.convertIfNecessary (new GregorianCalendar (), LocalTime.class));
    assertNotNull (TypeConverter.convertIfNecessary (new Date (), LocalTime.class));
    for (final Object aNumber : NUMBERS)
      assertNotNull (TypeConverter.convertIfNecessary (aNumber, LocalTime.class));

    // Test auto conversion to and from string
    final LocalTime aNowTime = PDTFactory.getCurrentLocalTime ();
    final String sNow = TypeConverter.convertIfNecessary (aNowTime, String.class);
    final LocalTime aNowTime2 = TypeConverter.convertIfNecessary (sNow, aNowTime.getClass ());
    assertEquals (aNowTime, aNowTime2);

    // Test auto conversion between default types
    for (final Class <?> aDestClass : new Class <?> [] { ZonedDateTime.class, LocalDateTime.class })
    {
      final LocalTime aNow = PDTFactory.getCurrentLocalTime ();
      final Object aDT = TypeConverter.convertIfNecessary (aNow, aDestClass);
      final LocalTime aNow2 = TypeConverter.convertIfNecessary (aDT, aNow.getClass ());
      assertEquals (aNow, aNow2);
    }
    for (final Class <?> aDestClass : new Class <?> [] { ZonedDateTime.class, LocalDateTime.class })
    {
      final LocalDate aNow = PDTFactory.getCurrentLocalDate ();
      final Object aDT = TypeConverter.convertIfNecessary (aNow, aDestClass);
      final LocalDate aNow2 = TypeConverter.convertIfNecessary (aDT, aNow.getClass ());
      assertEquals (aNow, aNow2);
    }
  }

  @Test
  public void testConvertIntoEachOther ()
  {
    final ICommonsOrderedMap <Class <?>, Object> aValues = new CommonsLinkedHashMap <> ();
    aValues.put (Date.class, new Date ());
    aValues.put (Calendar.class, Calendar.getInstance ());
    aValues.put (GregorianCalendar.class, new GregorianCalendar ());
    aValues.put (ZonedDateTime.class, PDTFactory.getCurrentZonedDateTime ());
    aValues.put (OffsetDateTime.class, PDTFactory.getCurrentOffsetDateTime ());
    aValues.put (LocalDateTime.class, PDTFactory.getCurrentLocalDateTime ());
    aValues.put (LocalDate.class, PDTFactory.getCurrentLocalDate ());
    aValues.put (LocalTime.class, PDTFactory.getCurrentLocalTime ());
    aValues.put (YearMonth.class, YearMonth.now ());
    aValues.put (Year.class, Year.now ());
    aValues.put (Instant.class, Instant.now ());

    for (final Map.Entry <Class <?>, Object> aSrc : aValues.entrySet ())
    {
      // Convert to String and back
      final String s = TypeConverter.convertIfNecessary (aSrc.getValue (), String.class);
      assertNotNull (s);
      final Object aSrcValue2 = TypeConverter.convertIfNecessary (s, aSrc.getKey ());
      assertNotNull (aSrcValue2);
      assertEquals ("Difference after reading from: " + s, aSrc.getValue (), aSrcValue2);

      // COnvert to any other type
      for (final Class <?> aDst : aValues.keySet ())
        if (aSrc.getKey () != aDst)
        {
          final boolean bIsTime = aSrc.getKey () == LocalTime.class || aDst == LocalTime.class;
          if (bIsTime &&
              (aSrc.getKey () == LocalDate.class ||
               aDst == LocalDate.class ||
               aSrc.getKey () == YearMonth.class ||
               aDst == YearMonth.class ||
               aSrc.getKey () == Year.class ||
               aDst == Year.class))
          {
            // Not convertible
          }
          else
          {
            System.out.println ("Converting from " + aSrc.getKey ().getName () + " to " + aDst.getName ());
            final Object aDstValue = TypeConverter.convertIfNecessary (aSrc.getValue (), aDst);
            assertNotNull (aDstValue);
          }
        }
    }
  }

  @Test
  public void testMicroTypeConversion ()
  {
    assertNotNull (MicroTypeConverter.convertToMicroElement (new GregorianCalendar (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (new Date (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (PDTFactory.getCurrentZonedDateTime (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (PDTFactory.getCurrentOffsetDateTime (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (PDTFactory.getCurrentLocalDateTime (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (PDTFactory.getCurrentLocalDate (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (PDTFactory.getCurrentLocalTime (), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (Duration.ofHours (3), ELEMENT_NAME));
    assertNotNull (MicroTypeConverter.convertToMicroElement (Period.ofDays (8), ELEMENT_NAME));
  }
}
