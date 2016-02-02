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
  public void registerTypeConverter (@Nonnull final ITypeConverterRegistry aRegistry)
  {
    // Destination: Instant
    final Function <Number, Instant> fToInstant = aSource -> Instant.ofEpochMilli (aSource.longValue ());
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class, Instant.class, fToInstant);
    aRegistry.registerTypeConverter (Date.class, Instant.class, Date::toInstant);
    aRegistry.registerTypeConverter (ZonedDateTime.class, Instant.class, ZonedDateTime::toInstant);
    aRegistry.registerTypeConverter (OffsetDateTime.class, Instant.class, OffsetDateTime::toInstant);
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     Instant.class,
                                     aSource -> PDTFactory.createZonedDateTime (aSource).toInstant ());
    aRegistry.registerTypeConverter (LocalDate.class,
                                     Instant.class,
                                     aSource -> PDTFactory.createZonedDateTime (aSource).toInstant ());
    aRegistry.registerTypeConverter (LocalTime.class,
                                     Instant.class,
                                     aSource -> PDTFactory.createZonedDateTime (aSource).toInstant ());
    aRegistry.registerTypeConverter (YearMonth.class,
                                     Instant.class,
                                     aSource -> PDTFactory.createZonedDateTime (aSource).toInstant ());
    aRegistry.registerTypeConverter (Year.class,
                                     Instant.class,
                                     aSource -> PDTFactory.createZonedDateTime (aSource).toInstant ());

    // Destination: ZonedDateTime
    aRegistry.registerTypeConverter (GregorianCalendar.class, ZonedDateTime.class, GregorianCalendar::toZonedDateTime);
    aRegistry.registerTypeConverter (String.class, ZonedDateTime.class, ZonedDateTime::parse);
    aRegistry.registerTypeConverter (OffsetDateTime.class, ZonedDateTime.class, OffsetDateTime::toZonedDateTime);
    aRegistry.registerTypeConverter (LocalDate.class, ZonedDateTime.class, PDTFactory::createZonedDateTime);
    aRegistry.registerTypeConverter (LocalTime.class, ZonedDateTime.class, PDTFactory::createZonedDateTime);
    aRegistry.registerTypeConverter (LocalDateTime.class, ZonedDateTime.class, PDTFactory::createZonedDateTime);
    aRegistry.registerTypeConverter (YearMonth.class, ZonedDateTime.class, PDTFactory::createZonedDateTime);
    aRegistry.registerTypeConverter (Year.class, ZonedDateTime.class, PDTFactory::createZonedDateTime);
    aRegistry.registerTypeConverter (Instant.class, ZonedDateTime.class, PDTFactory::createZonedDateTime);
    aRegistry.registerTypeConverter (Date.class, ZonedDateTime.class, PDTFactory::createZonedDateTime);
    aRegistry.registerTypeConverter (java.sql.Timestamp.class, ZonedDateTime.class, PDTFactory::createZonedDateTime);
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class,
                                                                         ZonedDateTime.class,
                                                                         PDTFactory::createZonedDateTime);

    // Destination: OffsetDateTime
    aRegistry.registerTypeConverter (GregorianCalendar.class, OffsetDateTime.class, PDTFactory::createOffsetDateTime);
    aRegistry.registerTypeConverter (String.class, OffsetDateTime.class, OffsetDateTime::parse);
    aRegistry.registerTypeConverter (ZonedDateTime.class, OffsetDateTime.class, ZonedDateTime::toOffsetDateTime);
    aRegistry.registerTypeConverter (LocalDate.class, OffsetDateTime.class, PDTFactory::createOffsetDateTime);
    aRegistry.registerTypeConverter (LocalTime.class, OffsetDateTime.class, PDTFactory::createOffsetDateTime);
    aRegistry.registerTypeConverter (LocalDateTime.class, OffsetDateTime.class, PDTFactory::createOffsetDateTime);
    aRegistry.registerTypeConverter (YearMonth.class, OffsetDateTime.class, PDTFactory::createOffsetDateTime);
    aRegistry.registerTypeConverter (Year.class, OffsetDateTime.class, PDTFactory::createOffsetDateTime);
    aRegistry.registerTypeConverter (Instant.class, OffsetDateTime.class, PDTFactory::createOffsetDateTime);
    aRegistry.registerTypeConverter (Date.class, OffsetDateTime.class, PDTFactory::createOffsetDateTime);
    aRegistry.registerTypeConverter (java.sql.Timestamp.class, OffsetDateTime.class, PDTFactory::createOffsetDateTime);
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class,
                                                                         OffsetDateTime.class,
                                                                         PDTFactory::createOffsetDateTime);

    // Destination: LocalDateTime
    aRegistry.registerTypeConverter (GregorianCalendar.class, LocalDateTime.class, PDTFactory::createLocalDateTime);
    aRegistry.registerTypeConverter (String.class, LocalDateTime.class, LocalDateTime::parse);
    aRegistry.registerTypeConverter (ZonedDateTime.class, LocalDateTime.class, ZonedDateTime::toLocalDateTime);
    aRegistry.registerTypeConverter (OffsetDateTime.class, LocalDateTime.class, OffsetDateTime::toLocalDateTime);
    aRegistry.registerTypeConverter (LocalDate.class, LocalDateTime.class, PDTFactory::createLocalDateTime);
    aRegistry.registerTypeConverter (LocalTime.class, LocalDateTime.class, PDTFactory::createLocalDateTime);
    aRegistry.registerTypeConverter (YearMonth.class, LocalDateTime.class, PDTFactory::createLocalDateTime);
    aRegistry.registerTypeConverter (Year.class, LocalDateTime.class, PDTFactory::createLocalDateTime);
    aRegistry.registerTypeConverter (Instant.class, LocalDateTime.class, PDTFactory::createLocalDateTime);
    aRegistry.registerTypeConverter (Date.class, LocalDateTime.class, PDTFactory::createLocalDateTime);
    aRegistry.registerTypeConverter (java.sql.Timestamp.class,
                                     LocalDateTime.class,
                                     java.sql.Timestamp::toLocalDateTime);
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class,
                                                                         LocalDateTime.class,
                                                                         PDTFactory::createLocalDateTime);

    // Destination: LocalDate
    aRegistry.registerTypeConverter (GregorianCalendar.class,
                                     LocalDate.class,
                                     aSource -> aSource.toZonedDateTime ().toLocalDate ());
    aRegistry.registerTypeConverter (String.class, LocalDate.class, LocalDate::parse);
    aRegistry.registerTypeConverter (ZonedDateTime.class, LocalDate.class, ZonedDateTime::toLocalDate);
    aRegistry.registerTypeConverter (OffsetDateTime.class, LocalDate.class, OffsetDateTime::toLocalDate);
    aRegistry.registerTypeConverter (LocalDateTime.class, LocalDate.class, LocalDateTime::toLocalDate);
    aRegistry.registerTypeConverter (Instant.class, LocalDate.class, PDTFactory::createLocalDate);
    aRegistry.registerTypeConverter (Date.class, LocalDate.class, PDTFactory::createLocalDate);
    aRegistry.registerTypeConverter (java.sql.Date.class, LocalDate.class, java.sql.Date::toLocalDate);
    aRegistry.registerTypeConverter (YearMonth.class, LocalDate.class, PDTFactory::createLocalDate);
    aRegistry.registerTypeConverter (Year.class, LocalDate.class, PDTFactory::createLocalDate);
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class,
                                                                         LocalDate.class,
                                                                         aSource -> PDTFactory.createLocalDate (fToInstant.apply (aSource)));

    // Destination: LocalTime
    aRegistry.registerTypeConverter (GregorianCalendar.class,
                                     LocalTime.class,
                                     aSource -> aSource.toZonedDateTime ().toLocalTime ());
    aRegistry.registerTypeConverter (String.class, LocalTime.class, LocalTime::parse);
    aRegistry.registerTypeConverter (ZonedDateTime.class, LocalTime.class, ZonedDateTime::toLocalTime);
    aRegistry.registerTypeConverter (OffsetDateTime.class, LocalTime.class, OffsetDateTime::toLocalTime);
    aRegistry.registerTypeConverter (LocalDateTime.class, LocalTime.class, LocalDateTime::toLocalTime);
    aRegistry.registerTypeConverter (Instant.class, LocalTime.class, PDTFactory::createLocalTime);
    aRegistry.registerTypeConverter (Date.class, LocalTime.class, PDTFactory::createLocalTime);
    aRegistry.registerTypeConverter (java.sql.Time.class, LocalTime.class, java.sql.Time::toLocalTime);
    aRegistry.registerTypeConverterRuleAssignableSourceFixedDestination (Number.class,
                                                                         LocalTime.class,
                                                                         aSource -> PDTFactory.createLocalTime (fToInstant.apply (aSource)));

    // Destination: GregorianCalendar
    aRegistry.registerTypeConverter (LocalDateTime.class,
                                     GregorianCalendar.class,
                                     aSource -> GregorianCalendar.from (PDTFactory.createZonedDateTime (aSource)));
    aRegistry.registerTypeConverter (LocalDate.class,
                                     GregorianCalendar.class,
                                     aSource -> GregorianCalendar.from (PDTFactory.createZonedDateTime (aSource)));
    aRegistry.registerTypeConverter (LocalTime.class,
                                     GregorianCalendar.class,
                                     aSource -> GregorianCalendar.from (PDTFactory.createZonedDateTime (aSource)));
    aRegistry.registerTypeConverter (YearMonth.class,
                                     GregorianCalendar.class,
                                     aSource -> GregorianCalendar.from (PDTFactory.createZonedDateTime (aSource)));
    aRegistry.registerTypeConverter (Year.class,
                                     GregorianCalendar.class,
                                     aSource -> GregorianCalendar.from (PDTFactory.createZonedDateTime (aSource)));

    // Destination: GregorianCalendar
    aRegistry.registerTypeConverter (Instant.class,
                                     GregorianCalendar.class,
                                     aSource -> GregorianCalendar.from (PDTFactory.createZonedDateTime (aSource)));

    // Destination: Date
    aRegistry.registerTypeConverterRuleAnySourceFixedDestination (Date.class,
                                                                  aSource -> Date.from (TypeConverter.convertIfNecessary (aSource,
                                                                                                                          Instant.class)));

    // Destination: YearMonth
    aRegistry.registerTypeConverterRuleAnySourceFixedDestination (YearMonth.class,
                                                                  aSource -> YearMonth.from (TypeConverter.convertIfNecessary (aSource,
                                                                                                                               LocalDate.class)));
    // Destination: Year
    aRegistry.registerTypeConverterRuleAnySourceFixedDestination (Year.class,
                                                                  aSource -> Year.from (TypeConverter.convertIfNecessary (aSource,
                                                                                                                          LocalDate.class)));

    // Destination: Duration
    aRegistry.registerTypeConverter (String.class, Duration.class, Duration::parse);

    // Destination: Period
    aRegistry.registerTypeConverter (String.class, Period.class, Period::parse);
  }
}
