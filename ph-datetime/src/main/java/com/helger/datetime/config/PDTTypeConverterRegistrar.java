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
package com.helger.datetime.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.convert.ConverterManager;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.commons.typeconvert.ITypeConverter;
import com.helger.commons.typeconvert.ITypeConverterRegistrarSPI;
import com.helger.commons.typeconvert.ITypeConverterRegistry;
import com.helger.commons.typeconvert.TypeConverterRegistry;
import com.helger.datetime.PDTFactory;

/**
 * Register all {@link ITypeConverter} objects to the central
 * {@link TypeConverterRegistry}.
 *
 * @author Philip Helger
 */
@Immutable
@IsSPIImplementation
public final class PDTTypeConverterRegistrar implements ITypeConverterRegistrarSPI
{
  private static void _registerJodaConverter ()
  {
    // Register generic Number converters, that work with Long, Integer,
    // BigInteger etc.
    ConverterManager.getInstance ().addInstantConverter (PDTJodaNumberConverter.INSTANCE);
    ConverterManager.getInstance ().addPartialConverter (PDTJodaNumberConverter.INSTANCE);
    ConverterManager.getInstance ().addDurationConverter (PDTJodaNumberConverter.INSTANCE);
  }

  public void registerTypeConverter (@Nonnull final ITypeConverterRegistry aRegistry)
  {
    // Register Joda native converters
    _registerJodaConverter ();

    final Class <?> [] aSourceClasses = new Class <?> [] { String.class,
                                                           Calendar.class,
                                                           GregorianCalendar.class,
                                                           Date.class,
                                                           AtomicInteger.class,
                                                           AtomicLong.class,
                                                           BigDecimal.class,
                                                           BigInteger.class,
                                                           Byte.class,
                                                           Double.class,
                                                           Float.class,
                                                           Integer.class,
                                                           Long.class,
                                                           Short.class };

    // DateTime
    aRegistry.registerTypeConverter (aSourceClasses,
                                     DateTime.class,
                                     aSource -> new DateTime (aSource, PDTConfig.getDefaultChronology ()));
    aRegistry.registerTypeConverter (LocalDate.class,
                                     DateTime.class,
                                     aSource -> PDTFactory.createDateTime ((LocalDate) aSource));
    aRegistry.registerTypeConverter (LocalTime.class,
                                     DateTime.class,
                                     aSource -> PDTFactory.createDateTime ((LocalTime) aSource));
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     DateTime.class,
                                     aSource -> PDTFactory.createDateTime ((LocalDateTime) aSource));

    // LocalDateTime
    aRegistry.registerTypeConverter (aSourceClasses,
                                     LocalDateTime.class,
                                     aSource -> new LocalDateTime (aSource, PDTConfig.getDefaultChronology ()));
    aRegistry.registerTypeConverter (DateTime.class,
                                     LocalDateTime.class,
                                     aSource -> PDTFactory.createLocalDateTime ((DateTime) aSource));
    aRegistry.registerTypeConverter (LocalDate.class,
                                     LocalDateTime.class,
                                     aSource -> PDTFactory.createLocalDateTime ((LocalDate) aSource));
    aRegistry.registerTypeConverter (LocalTime.class,
                                     LocalDateTime.class,
                                     aSource -> PDTFactory.createLocalDateTime ((LocalTime) aSource));

    // LocalDate
    aRegistry.registerTypeConverter (aSourceClasses,
                                     LocalDate.class,
                                     aSource -> new LocalDate (aSource, PDTConfig.getDefaultChronology ()));
    aRegistry.registerTypeConverter (DateTime.class,
                                     LocalDate.class,
                                     aSource -> PDTFactory.createLocalDate ((DateTime) aSource));
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     LocalDate.class,
                                     aSource -> PDTFactory.createLocalDate ((LocalDateTime) aSource));

    // LocalTime
    aRegistry.registerTypeConverter (aSourceClasses,
                                     LocalTime.class,
                                     aSource -> new LocalTime (aSource, PDTConfig.getDefaultChronology ()));
    aRegistry.registerTypeConverter (DateTime.class,
                                     LocalTime.class,
                                     aSource -> PDTFactory.createLocalTime ((DateTime) aSource));
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     LocalTime.class,
                                     aSource -> PDTFactory.createLocalTime ((LocalDateTime) aSource));

    // Duration
    aRegistry.registerTypeConverter (new Class <?> [] { String.class,
                                                        AtomicInteger.class,
                                                        AtomicLong.class,
                                                        BigDecimal.class,
                                                        BigInteger.class,
                                                        Byte.class,
                                                        Double.class,
                                                        Float.class,
                                                        Integer.class,
                                                        Long.class,
                                                        Short.class },
                                     Duration.class,
                                     aSource -> new Duration (aSource));

    // Period
    aRegistry.registerTypeConverter (new Class <?> [] { String.class,
                                                        AtomicInteger.class,
                                                        AtomicLong.class,
                                                        BigDecimal.class,
                                                        BigInteger.class,
                                                        Byte.class,
                                                        Double.class,
                                                        Float.class,
                                                        Integer.class,
                                                        Long.class,
                                                        Short.class },
                                     Period.class, aSource -> new Period (aSource));

    // MutablePeriod
    aRegistry.registerTypeConverter (new Class <?> [] { String.class,
                                                        AtomicInteger.class,
                                                        AtomicLong.class,
                                                        BigDecimal.class,
                                                        BigInteger.class,
                                                        Byte.class,
                                                        Double.class,
                                                        Float.class,
                                                        Integer.class,
                                                        Long.class,
                                                        Short.class },
                                     MutablePeriod.class,
                                     aSource -> new MutablePeriod (aSource));
  }
}
