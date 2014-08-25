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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;

import com.helger.commons.annotations.IsSPIImplementation;
import com.helger.commons.microdom.convert.IMicroTypeConverterRegistrarSPI;
import com.helger.commons.microdom.convert.IMicroTypeConverterRegistry;
import com.helger.commons.microdom.convert.impl.StringBasedMicroTypeConverter;

/**
 * Implementation of {@link IMicroTypeConverterRegistrarSPI} for date time stuff
 * 
 * @author Philip Helger
 */
@Immutable
@IsSPIImplementation
public final class PDTMicroTypeConverterRegistrar implements IMicroTypeConverterRegistrarSPI
{
  public void registerMicroTypeConverter (@Nonnull final IMicroTypeConverterRegistry aRegistry)
  {
    // Register XML converters based on the String converter

    // joda stuff
    aRegistry.registerMicroElementTypeConverter (DateTime.class, new StringBasedMicroTypeConverter (DateTime.class));
    aRegistry.registerMicroElementTypeConverter (Duration.class, new StringBasedMicroTypeConverter (Duration.class));
    aRegistry.registerMicroElementTypeConverter (Interval.class, new StringBasedMicroTypeConverter (Interval.class));
    aRegistry.registerMicroElementTypeConverter (LocalDate.class, new StringBasedMicroTypeConverter (LocalDate.class));
    aRegistry.registerMicroElementTypeConverter (LocalDateTime.class,
                                                 new StringBasedMicroTypeConverter (LocalDateTime.class));
    aRegistry.registerMicroElementTypeConverter (LocalTime.class, new StringBasedMicroTypeConverter (LocalTime.class));
    aRegistry.registerMicroElementTypeConverter (MutableDateTime.class,
                                                 new StringBasedMicroTypeConverter (MutableDateTime.class));
    aRegistry.registerMicroElementTypeConverter (Period.class, new StringBasedMicroTypeConverter (Period.class));

    // J2SE stuff
    aRegistry.registerMicroElementTypeConverter (Date.class, new StringBasedMicroTypeConverter (Date.class));
    aRegistry.registerMicroElementTypeConverter (GregorianCalendar.class,
                                                 new StringBasedMicroTypeConverter (Calendar.class));
  }
}
