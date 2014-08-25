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
package com.helger.datetime.period;

import javax.annotation.Nonnull;

import org.joda.time.Period;

/**
 * Interface for objects providing a {@link Period}.
 * 
 * @author Philip Helger
 */
public interface IPeriodProvider
{
  /**
   * @return <code>true</code> if this object can be converted to a
   *         {@link Period}.
   */
  boolean canConvertToPeriod ();

  /**
   * Get the current object as a Joda {@link Period}.
   * 
   * @return The period as a Joda time object. Never <code>null</code>.
   */
  @Nonnull
  Period getAsPeriod ();
}
