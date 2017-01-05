/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.holiday;

/**
 * Represents a localized version of a holiday type. This type identifies a
 * holiday and can be used to make a distinction for all returned holidays.
 * (e.g. is the holiday a public holiday or not)
 *
 * @author tboven
 * @author Philip Helger
 */
public enum EHolidayType implements IHolidayType
{
  OFFICIAL_HOLIDAY (true),
  UNOFFICIAL_HOLIDAY (false);

  private final boolean m_bOfficial;

  private EHolidayType (final boolean bOfficial)
  {
    m_bOfficial = bOfficial;
  }

  public boolean isOfficialHoliday ()
  {
    return m_bOfficial;
  }
}
