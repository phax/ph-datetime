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

import java.time.Duration;

import javax.annotation.Nonnull;

/**
 * Interface for objects providing a {@link Duration}.
 *
 * @author Philip Helger
 */
public interface IDurationProvider
{
  /**
   * @return <code>true</code> if this object can be converted to a
   *         {@link Duration}.
   */
  boolean canConvertToDuration ();

  /**
   * Get the current object as a JDK {@link Duration}.
   *
   * @return The period as a JDK 8 Duration object. Never <code>null</code>.
   */
  @Nonnull
  Duration getAsDuration ();
}
