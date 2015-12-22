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
package com.helger.datetime.domain;

import java.time.LocalDateTime;

import javax.annotation.Nullable;

/**
 * Interface for objects having a last modification date time.
 *
 * @author Philip Helger
 */
public interface IHasLastModificationDateTime
{
  /**
   * @return The last modification date time or <code>null</code> if the object
   *         has not been modified yet.
   */
  @Nullable
  LocalDateTime getLastModificationDateTime ();
}
