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
package com.helger.holiday;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

import org.junit.Test;

import com.helger.commons.collection.ext.CommonsHashMap;
import com.helger.commons.collection.ext.CommonsHashSet;
import com.helger.commons.collection.ext.ICommonsMap;
import com.helger.commons.collection.ext.ICommonsSet;
import com.helger.commons.lang.PropertiesHelper;

/**
 * @author sven
 */
public final class HolidayDescriptionFuncTest
{
  @Test
  public void testHolidayDescriptionsCompleteness () throws Exception
  {
    final File folder = new File ("src/main/resources/descriptions");
    assertTrue (folder.isDirectory ());
    final File [] descriptions = folder.listFiles ((FilenameFilter) (dir,
                                                                     name) -> name.startsWith ("holiday_descriptions") &&
                                                                              name.endsWith (".properties"));
    assertNotNull (descriptions);
    assertTrue (descriptions.length > 0);

    final ICommonsSet <String> propertiesNames = new CommonsHashSet <> ();
    final ICommonsMap <String, ICommonsMap <String, String>> descriptionProperties = new CommonsHashMap <> ();

    for (final File descriptionFile : descriptions)
    {
      final ICommonsMap <String, String> aProps = PropertiesHelper.loadProperties (descriptionFile);
      propertiesNames.addAll (aProps.keySet ());
      descriptionProperties.put (descriptionFile.getName (), aProps);
    }

    final ICommonsMap <String, ICommonsSet <String>> missingProperties = new CommonsHashMap <> ();

    for (final Map.Entry <String, ICommonsMap <String, String>> entry : descriptionProperties.entrySet ())
      if (!entry.getValue ().keySet ().containsAll (propertiesNames))
      {
        final ICommonsSet <String> remainingProps = new CommonsHashSet <> (propertiesNames);
        remainingProps.removeAll (entry.getValue ().keySet ());
        missingProperties.put (entry.getKey (), remainingProps);
      }

    assertTrue ("Following files are lacking properties: " + missingProperties, missingProperties.isEmpty ());
  }
}
