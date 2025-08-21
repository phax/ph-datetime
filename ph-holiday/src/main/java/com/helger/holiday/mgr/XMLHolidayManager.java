/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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
package com.helger.holiday.mgr;

import java.io.InputStream;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.annotation.WillClose;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.array.ArrayHelper;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.io.stream.StreamHelper;
import com.helger.base.reflection.GenericReflection;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.CommonsHashSet;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsSet;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.jaxb.Configuration;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.parser.ChristianHolidayParser;
import com.helger.holiday.parser.EthiopianOrthodoxHolidayParser;
import com.helger.holiday.parser.FixedParser;
import com.helger.holiday.parser.FixedWeekdayBetweenFixedParser;
import com.helger.holiday.parser.FixedWeekdayInMonthParser;
import com.helger.holiday.parser.FixedWeekdayRelativeToFixedParser;
import com.helger.holiday.parser.HinduHolidayParser;
import com.helger.holiday.parser.IHolidayParser;
import com.helger.holiday.parser.IslamicHolidayParser;
import com.helger.holiday.parser.RelativeToFixedParser;
import com.helger.holiday.parser.RelativeToWeekdayInMonthParser;
import com.helger.io.resource.ClassPathResource;
import com.helger.jaxb.JAXBContextCache;
import com.helger.jaxb.JAXBContextCacheKey;
import com.helger.text.locale.country.ECountry;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Manager implementation for reading data from XML files. The files with the
 * name pattern Holidays_[country].xml will be read from the system classpath.
 * It uses a list a parsers for parsing the different type of XML nodes.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public class XMLHolidayManager extends AbstractHolidayManager
{
  private static final Logger LOGGER = LoggerFactory.getLogger (XMLHolidayManager.class);

  /**
   * Unmarshals the configuration from the stream. Uses <code>JAXB</code> for
   * this.
   *
   * @param aIS
   * @return The unmarshalled configuration.
   */
  @Nonnull
  private static Configuration _unmarshallConfiguration (@WillClose @Nonnull final InputStream aIS)
  {
    ValueEnforcer.notNull (aIS, "InputStream");

    try
    {
      final JAXBContext ctx = JAXBContextCache.getInstance ()
                                              .getFromCache (JAXBContextCacheKey.createForClass (com.helger.holiday.jaxb.ObjectFactory.class));
      final Unmarshaller um = ctx.createUnmarshaller ();
      final JAXBElement <Configuration> aElement = GenericReflection.uncheckedCast (um.unmarshal (aIS));
      return aElement.getValue ();
    }
    catch (final JAXBException ex)
    {
      throw new IllegalArgumentException ("Cannot parse holidays XML.", ex);
    }
    finally
    {
      StreamHelper.close (aIS);
    }
  }

  /**
   * Configuration parsed on initialization.
   */
  private final Configuration m_aConfiguration;

  public XMLHolidayManager (@Nonnull @Nonempty final String sCountryCode)
  {
    ValueEnforcer.notEmpty (sCountryCode, "CountryCode");

    final String sFileName = "holidays/Holidays_" + sCountryCode.toLowerCase (Locale.US) + ".xml";
    final InputStream aIS = ClassPathResource.getInputStream (sFileName);
    if (aIS == null)
      throw new IllegalArgumentException ("No holidays are defined for country code '" + sCountryCode + "'");
    m_aConfiguration = _unmarshallConfiguration (aIS);
    _validateConfigurationHierarchy (m_aConfiguration);
  }

  /**
   * Calls
   * <code>Set&lt;LocalDate&gt; getHolidays(int year, Configuration c, String... args)</code>
   * with the configuration from initialization.
   */
  public HolidayMap getHolidays (final int nYear, @Nullable final String... aArgs)
  {
    return _getHolidays (nYear, m_aConfiguration, aArgs);
  }

  /**
   * Creates a list of parsers by reading the configuration and trying to find
   * an <code>HolidayParser</code> implementation for by XML class type.
   *
   * @param aConfig
   * @return A list of parsers to for this configuration.
   */
  @Nonnull
  @ReturnsMutableCopy
  private static ICommonsList <IHolidayParser> _getParsers (@Nonnull final Holidays aConfig)
  {
    final ICommonsList <IHolidayParser> ret = new CommonsArrayList <> ();
    if (!aConfig.getChristianHoliday ().isEmpty ())
      ret.add (ChristianHolidayParser.getInstance ());
    if (!aConfig.getEthiopianOrthodoxHoliday ().isEmpty ())
      ret.add (EthiopianOrthodoxHolidayParser.getInstance ());
    if (!aConfig.getFixed ().isEmpty ())
      ret.add (FixedParser.getInstance ());
    if (!aConfig.getFixedWeekdayBetweenFixed ().isEmpty ())
      ret.add (FixedWeekdayBetweenFixedParser.getInstance ());
    if (!aConfig.getFixedWeekday ().isEmpty ())
      ret.add (FixedWeekdayInMonthParser.getInstance ());
    if (!aConfig.getFixedWeekdayRelativeToFixed ().isEmpty ())
      ret.add (FixedWeekdayRelativeToFixedParser.getInstance ());
    if (!aConfig.getHinduHoliday ().isEmpty ())
      ret.add (HinduHolidayParser.getInstance ());
    if (!aConfig.getIslamicHoliday ().isEmpty ())
      ret.add (IslamicHolidayParser.getInstance ());
    if (!aConfig.getRelativeToFixed ().isEmpty ())
      ret.add (RelativeToFixedParser.getInstance ());
    if (!aConfig.getRelativeToWeekdayInMonth ().isEmpty ())
      ret.add (RelativeToWeekdayInMonthParser.getInstance ());
    return ret;
  }

  /**
   * Parses the provided configuration for the provided year and fills the list
   * of holidays.
   *
   * @param nYear
   * @param aConfig
   * @param aArgs
   * @return the holidays
   */
  @Nonnull
  @ReturnsMutableCopy
  private HolidayMap _getHolidays (final int nYear,
                                   @Nonnull final Configuration aConfig,
                                   @Nullable final String... aArgs)
  {
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Adding holidays for " + aConfig.getDescription ());

    final HolidayMap aHolidayMap = new HolidayMap ();
    for (final IHolidayParser aParser : _getParsers (aConfig.getHolidays ()))
      aParser.parse (nYear, aHolidayMap, aConfig.getHolidays ());

    if (ArrayHelper.isNotEmpty (aArgs))
    {
      final String sHierarchy = aArgs[0];
      for (final Configuration aSubConfig : aConfig.getSubConfigurations ())
      {
        if (sHierarchy.equalsIgnoreCase (aSubConfig.getHierarchy ()))
        {
          // Recursive call
          final HolidayMap aSubHolidays = _getHolidays (nYear,
                                                        aSubConfig,
                                                        ArrayHelper.getCopy (aArgs, 1, aArgs.length - 1));
          aHolidayMap.addAll (aSubHolidays);
          break;
        }
      }
    }
    return aHolidayMap;
  }

  /**
   * Validates the content of the provided configuration by checking for
   * multiple hierarchy entries within one configuration. It traverses down the
   * configuration tree.
   */
  private static void _validateConfigurationHierarchy (@Nonnull final Configuration aConfig)
  {
    final ICommonsSet <String> aHierarchySet = new CommonsHashSet <> ();
    for (final Configuration aSubConfig : aConfig.getSubConfigurations ())
    {
      final String sHierarchy = aSubConfig.getHierarchy ();
      if (!aHierarchySet.add (sHierarchy))
        throw new IllegalArgumentException ("Configuration for " +
                                            aConfig.getHierarchy () +
                                            " contains multiple SubConfigurations with the same hierarchy id '" +
                                            sHierarchy +
                                            "'. ");
    }

    for (final Configuration aSubConfig : aConfig.getSubConfigurations ())
      _validateConfigurationHierarchy (aSubConfig);
  }

  /**
   * Returns the configurations hierarchy.<br>
   * i.e. Hierarchy 'us' -&gt; Children 'al','ak','ar', ... ,'wv','wy'. Every
   * child might itself have children. The ids be used to call
   * getHolidays()/isHoliday().
   */
  @Override
  @Nonnull
  public CalendarHierarchy getHierarchy ()
  {
    return _createConfigurationHierarchy (m_aConfiguration, null);
  }

  /**
   * Creates the configuration hierarchy for the provided configuration.
   *
   * @param aConfig
   * @return configuration hierarchy
   */
  @Nonnull
  private static CalendarHierarchy _createConfigurationHierarchy (@Nonnull final Configuration aConfig,
                                                                  @Nullable final CalendarHierarchy aParent)
  {
    final ECountry eCountry = ECountry.getFromIDOrNull (aConfig.getHierarchy ());
    final CalendarHierarchy aHierarchy = new CalendarHierarchy (aParent, aConfig.getHierarchy (), eCountry);
    for (final Configuration aSubConfig : aConfig.getSubConfigurations ())
    {
      final CalendarHierarchy aSubHierarchy = _createConfigurationHierarchy (aSubConfig, aHierarchy);
      aHierarchy.addChild (aSubHierarchy);
    }
    return aHierarchy;
  }
}
