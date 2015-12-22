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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.commons.typeconvert.ITypeConverter;
import com.helger.commons.typeconvert.ITypeConverterRegistrarSPI;
import com.helger.commons.typeconvert.ITypeConverterRegistry;
import com.helger.commons.typeconvert.TypeConverter;
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
    // Destination: Instant
    final Function <Number, Instant> fToInstant = aSource -> Instant.ofEpochMilli (aSource.longValue ());
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class, Instant.class, fToInstant);
    aRegistry.registerTypeConverter (Date.class, Instant.class, Date::toInstant);
    aRegistry.registerTypeConverter (ZonedDateTime.class, Instant.class, ZonedDateTime::toInstant);
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     Instant.class,
                                     aSource -> TypeConverter.convertIfNecessary (aSource, ZonedDateTime.class)
                                                             .toInstant ());
    aRegistry.registerTypeConverter (LocalDate.class,
                                     Instant.class,
                                     aSource -> TypeConverter.convertIfNecessary (aSource, ZonedDateTime.class)
                                                             .toInstant ());
    aRegistry.registerTypeConverter (LocalTime.class,
                                     Instant.class,
                                     aSource -> TypeConverter.convertIfNecessary (aSource, ZonedDateTime.class)
                                                             .toInstant ());

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
    aRegistry.registerTypeConverter (Instant.class,
                                     ZonedDateTime.class,
                                     aSource -> ZonedDateTime.ofInstant (aSource, PDTConfig.getDefaultZoneId ()));
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class,
                                                                         ZonedDateTime.class,
                                                                         aSource -> TypeConverter.convertIfNecessary (fToInstant.apply (aSource),
                                                                                                                      ZonedDateTime.class));

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
    aRegistry.registerTypeConverter (Instant.class,
                                     LocalDateTime.class,
                                     aSource -> LocalDateTime.ofInstant (aSource, PDTConfig.getDefaultZoneId ()));
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class,
                                                                         LocalDateTime.class,
                                                                         aSource -> TypeConverter.convertIfNecessary (fToInstant.apply (aSource),
                                                                                                                      LocalDateTime.class));

    // Destination: LocalDate
    aRegistry.registerTypeConverter (GregorianCalendar.class,
                                     LocalDate.class,
                                     aSource -> aSource.toZonedDateTime ().toLocalDate ());
    aRegistry.registerTypeConverter (String.class, LocalDate.class, LocalDate::parse);
    aRegistry.registerTypeConverter (ZonedDateTime.class, LocalDate.class, ZonedDateTime::toLocalDate);
    aRegistry.registerTypeConverter (OffsetDateTime.class, LocalDate.class, OffsetDateTime::toLocalDate);
    aRegistry.registerTypeConverter (LocalDateTime.class, LocalDate.class, LocalDateTime::toLocalDate);
    aRegistry.registerTypeConverter (Instant.class,
                                     LocalDate.class,
                                     aSource -> LocalDateTime.ofInstant (aSource, PDTConfig.getDefaultZoneId ())
                                                             .toLocalDate ());
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class,
                                                                         LocalDate.class,
                                                                         aSource -> TypeConverter.convertIfNecessary (fToInstant.apply (aSource),
                                                                                                                      LocalDate.class));

    // Destination: LocalTime
    aRegistry.registerTypeConverter (GregorianCalendar.class,
                                     LocalTime.class,
                                     aSource -> aSource.toZonedDateTime ().toLocalTime ());
    aRegistry.registerTypeConverter (String.class, LocalTime.class, LocalTime::parse);
    aRegistry.registerTypeConverter (ZonedDateTime.class, LocalTime.class, ZonedDateTime::toLocalTime);
    aRegistry.registerTypeConverter (OffsetDateTime.class, LocalTime.class, OffsetDateTime::toLocalTime);
    aRegistry.registerTypeConverter (LocalDateTime.class, LocalTime.class, LocalDateTime::toLocalTime);
    aRegistry.registerTypeConverter (Instant.class,
                                     LocalTime.class,
                                     aSource -> LocalDateTime.ofInstant (aSource, PDTConfig.getDefaultZoneId ())
                                                             .toLocalTime ());
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class,
                                                                         LocalTime.class,
                                                                         aSource -> TypeConverter.convertIfNecessary (fToInstant.apply (aSource),
                                                                                                                      LocalTime.class));
  }
}
