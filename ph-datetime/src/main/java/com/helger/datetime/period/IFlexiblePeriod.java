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
package com.helger.datetime.period;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * Base interface for an object that has a validity range based on arbitrary
 * objects.
 * 
 * @author Philip Helger
 * @param <DATATYPE>
 *        Date and time type
 */
public interface IFlexiblePeriod <DATATYPE> extends IPeriodProvider, IHasStartAndEnd <DATATYPE>, Serializable
{
  /**
   * Check if this object is valid for this specific date.
   * 
   * @param aDate
   *        The date to be checked. May not be <code>null</code>.
   * @return <code>true</code> if this object is valid for this date,
   *         <code>false</code> otherwise.
   */
  boolean isValidFor (@Nonnull DATATYPE aDate);

  /**
   * This is a shortcut method for checking the validity of the object for the
   * current date and time.
   * 
   * @return <code>true</code> if this object is valid for the current date,
   *         <code>false</code> otherwise.
   */
  boolean isValidForNow ();
}
