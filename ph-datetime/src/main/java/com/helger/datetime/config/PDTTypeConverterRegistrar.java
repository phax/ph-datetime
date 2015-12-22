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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.commons.typeconvert.ITypeConverter;
import com.helger.commons.typeconvert.ITypeConverterRegistrarSPI;
import com.helger.commons.typeconvert.ITypeConverterRegistry;
import com.helger.commons.typeconvert.TypeConverterRegistry;
import com.helger.datetime.CPDT;

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
  public void registerTypeConverter (@Nonnull final ITypeConverterRegistry aRegistry)
  {
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

    // ZonedDateTime
    // aRegistry.registerTypeConverter (aSourceClasses,
    // ZonedDateTime.class,
    // aSource -> new ZonedDateTime (aSource, PDTConfig.getDefaultChronology
    // ()));
    aRegistry.registerTypeConverter (LocalDate.class,
                                     ZonedDateTime.class,
                                     aSource -> ZonedDateTime.of ((LocalDate) aSource,
                                                                  CPDT.NULL_LOCAL_TIME,
                                                                  PDTConfig.getDefaultZoneId ()));
    aRegistry.registerTypeConverter (LocalTime.class,
                                     ZonedDateTime.class,
                                     aSource -> ZonedDateTime.of (CPDT.NULL_LOCAL_DATE,
                                                                  (LocalTime) aSource,
                                                                  PDTConfig.getDefaultZoneId ()));
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     ZonedDateTime.class,
                                     aSource -> ZonedDateTime.of ((LocalDateTime) aSource,
                                                                  PDTConfig.getDefaultZoneId ()));

    // LocalDateTime
    // aRegistry.registerTypeConverter (aSourceClasses,
    // LocalDateTime.class,
    // aSource -> new LocalDateTime (aSource, PDTConfig.getDefaultChronology
    // ()));
    aRegistry.registerTypeConverter (ZonedDateTime.class,
                                     LocalDateTime.class,
                                     aSource -> ((ZonedDateTime) aSource).toLocalDateTime ());
    aRegistry.registerTypeConverter (LocalDate.class,
                                     LocalDateTime.class,
                                     aSource -> LocalDateTime.of ((LocalDate) aSource, CPDT.NULL_LOCAL_TIME));
    aRegistry.registerTypeConverter (LocalTime.class,
                                     LocalDateTime.class,
                                     aSource -> LocalDateTime.of (CPDT.NULL_LOCAL_DATE, (LocalTime) aSource));

    // LocalDate
    // aRegistry.registerTypeConverter (aSourceClasses,
    // LocalDate.class,
    // aSource -> new LocalDate (aSource, PDTConfig.getDefaultChronology ()));
    aRegistry.registerTypeConverter (ZonedDateTime.class,
                                     LocalDate.class,
                                     aSource -> ((ZonedDateTime) aSource).toLocalDate ());
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     LocalDate.class,
                                     aSource -> ((LocalDateTime) aSource).toLocalDate ());

    // LocalTime
    // aRegistry.registerTypeConverter (aSourceClasses,
    // LocalTime.class,
    // aSource -> new LocalTime (aSource, PDTConfig.getDefaultChronology ()));
    aRegistry.registerTypeConverter (ZonedDateTime.class,
                                     LocalTime.class,
                                     aSource -> ((ZonedDateTime) aSource).toLocalTime ());
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     LocalTime.class,
                                     aSource -> ((LocalDateTime) aSource).toLocalTime ());

    // Duration
    // aRegistry.registerTypeConverter (new Class <?> [] { String.class,
    // AtomicInteger.class,
    // AtomicLong.class,
    // BigDecimal.class,
    // BigInteger.class,
    // Byte.class,
    // Double.class,
    // Float.class,
    // Integer.class,
    // Long.class,
    // Short.class },
    // Duration.class,
    // aSource -> new Duration (aSource));

    // Period
    // aRegistry.registerTypeConverter (new Class <?> [] { String.class,
    // AtomicInteger.class,
    // AtomicLong.class,
    // BigDecimal.class,
    // BigInteger.class,
    // Byte.class,
    // Double.class,
    // Float.class,
    // Integer.class,
    // Long.class,
    // Short.class },
    // Period.class, aSource -> new Period (aSource));
  }
}
