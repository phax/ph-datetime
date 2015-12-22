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
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
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
    final Class <?> [] aSourceClasses = new Class <?> [] { AtomicInteger.class,
                                                           AtomicLong.class,
                                                           BigDecimal.class,
                                                           BigInteger.class,
                                                           Byte.class,
                                                           Double.class,
                                                           Float.class,
                                                           Integer.class,
                                                           Long.class,
                                                           Short.class };

    // Destination: ZonedDateTime
    aRegistry.registerTypeConverter (GregorianCalendar.class, ZonedDateTime.class, GregorianCalendar::toZonedDateTime);
    aRegistry.registerTypeConverter (String.class, ZonedDateTime.class, ZonedDateTime::parse);
    aRegistry.registerTypeConverter (OffsetDateTime.class, ZonedDateTime.class, OffsetDateTime::toZonedDateTime);
    aRegistry.registerTypeConverter (LocalDate.class,
                                     ZonedDateTime.class,
                                     aSource -> ZonedDateTime.of (aSource,
                                                                  CPDT.NULL_LOCAL_TIME,
                                                                  PDTConfig.getDefaultZoneId ()));
    aRegistry.registerTypeConverter (LocalTime.class,
                                     ZonedDateTime.class,
                                     aSource -> ZonedDateTime.of (CPDT.NULL_LOCAL_DATE,
                                                                  aSource,
                                                                  PDTConfig.getDefaultZoneId ()));
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     ZonedDateTime.class,
                                     aSource -> ZonedDateTime.of (aSource, PDTConfig.getDefaultZoneId ()));

    // Destination: LocalDateTime
    aRegistry.registerTypeConverter (GregorianCalendar.class,
                                     LocalDateTime.class,
                                     aSource -> aSource.toZonedDateTime ().toLocalDateTime ());
    aRegistry.registerTypeConverter (String.class, LocalDateTime.class, LocalDateTime::parse);
    aRegistry.registerTypeConverter (ZonedDateTime.class, LocalDateTime.class, ZonedDateTime::toLocalDateTime);
    aRegistry.registerTypeConverter (OffsetDateTime.class, LocalDateTime.class, OffsetDateTime::toLocalDateTime);
    aRegistry.registerTypeConverter (LocalDate.class,
                                     LocalDateTime.class,
                                     aSource -> LocalDateTime.of (aSource, CPDT.NULL_LOCAL_TIME));
    aRegistry.registerTypeConverter (LocalTime.class,
                                     LocalDateTime.class,
                                     aSource -> LocalDateTime.of (CPDT.NULL_LOCAL_DATE, aSource));

    // Destination: LocalDate
    aRegistry.registerTypeConverter (GregorianCalendar.class,
                                     LocalDate.class,
                                     aSource -> aSource.toZonedDateTime ().toLocalDate ());
    aRegistry.registerTypeConverter (String.class, LocalDate.class, LocalDate::parse);
    aRegistry.registerTypeConverter (ZonedDateTime.class, LocalDate.class, ZonedDateTime::toLocalDate);
    aRegistry.registerTypeConverter (OffsetDateTime.class, LocalDate.class, OffsetDateTime::toLocalDate);
    aRegistry.registerTypeConverter (LocalDateTime.class, LocalDate.class, LocalDateTime::toLocalDate);

    // Destination: LocalTime
    aRegistry.registerTypeConverter (GregorianCalendar.class,
                                     LocalTime.class,
                                     aSource -> aSource.toZonedDateTime ().toLocalTime ());
    aRegistry.registerTypeConverter (String.class, LocalTime.class, LocalTime::parse);
    aRegistry.registerTypeConverter (ZonedDateTime.class, LocalTime.class, ZonedDateTime::toLocalTime);
    aRegistry.registerTypeConverter (OffsetDateTime.class, LocalTime.class, OffsetDateTime::toLocalTime);
    aRegistry.registerTypeConverter (LocalDateTime.class, LocalTime.class, LocalDateTime::toLocalTime);

    // Date -> ZonedDateTime -> destination
    aRegistry.registerTypeConverterRuleFixedSourceAnyDestination (Date.class,
                                                                  aSource -> ZonedDateTime.ofInstant (aSource.toInstant (),
                                                                                                      PDTConfig.getDefaultZoneId ()));
  }
}
