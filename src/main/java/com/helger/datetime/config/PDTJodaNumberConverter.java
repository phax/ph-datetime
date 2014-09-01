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
package com.helger.datetime.config;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.joda.time.Chronology;
import org.joda.time.convert.AbstractConverter;
import org.joda.time.convert.DurationConverter;
import org.joda.time.convert.InstantConverter;
import org.joda.time.convert.PartialConverter;

/**
 * A generic Joda converter that is based on Joda's LongConverter but works with
 * arbitrary {@link Number} objects. This makes it easier to use
 * {@link java.math.BigInteger} and the like for type conversion.
 * 
 * @author Philip Helger
 */
@Immutable
final class PDTJodaNumberConverter extends AbstractConverter implements
                                                            InstantConverter,
                                                            PartialConverter,
                                                            DurationConverter
{
  /**
   * Singleton instance.
   */
  static final PDTJodaNumberConverter INSTANCE = new PDTJodaNumberConverter ();

  /**
   * Restricted constructor.
   */
  private PDTJodaNumberConverter ()
  {}

  @Override
  public long getInstantMillis (@Nonnull final Object aObject, final Chronology aChrono)
  {
    return ((Number) aObject).longValue ();
  }

  public long getDurationMillis (@Nonnull final Object aObject)
  {
    return ((Number) aObject).longValue ();
  }

  @Nonnull
  public Class <Number> getSupportedType ()
  {
    return Number.class;
  }
}
