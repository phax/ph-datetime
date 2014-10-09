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

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.helger.commons.collections.ContainerHelper;
import com.helger.commons.thirdparty.IThirdPartyModule;
import com.helger.commons.thirdparty.ThirdPartyModuleRegistry;

/**
 * Test class for class {@link ThirdPartyModuleProvider_ph_datetime}.
 *
 * @author Philip Helger
 */
public final class ThirdPartyModuleProvider_ph_datetimeTest
{
  @Test
  public void testAll ()
  {
    final Set <IThirdPartyModule> aSet = ThirdPartyModuleRegistry.getInstance ().getAllRegisteredThirdPartyModules ();
    final IThirdPartyModule [] aExpected = new ThirdPartyModuleProvider_ph_datetime ().getAllThirdPartyModules ();
    assertTrue (aSet.containsAll (ContainerHelper.newList (aExpected)));
  }
}
