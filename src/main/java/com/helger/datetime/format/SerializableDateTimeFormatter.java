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
package com.helger.datetime.format;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.helger.commons.annotations.Nonempty;
import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * This class wraps a {@link DateTimeFormatter} and makes it serializable by
 * remember the creation parameters. As DateTimeFormatter itself is not
 * serializable, this is a bloody workaround, handling either the style creation
 * parameters (short/medium/long/full/none) or the pattern string plus the
 * respective optional locale that was used on creation.
 * 
 * @author Philip Helger
 */
public final class SerializableDateTimeFormatter implements Serializable
{
  private static final long serialVersionUID = 582607745264511789L;

  public static enum EFormatStyle
  {
    SHORT ("S", DateFormat.SHORT),
    MEDIUM ("M", DateFormat.MEDIUM),
    LONG ("L", DateFormat.LONG),
    FULL ("F", DateFormat.FULL),
    NONE ("-", 4); // DateFormat.NONE

    /** Default format style: {@link #MEDIUM} */
    public static final EFormatStyle DEFAULT = MEDIUM;

    private String m_sFormat;
    private int m_nFormat;

    /**
     * @param sFormat
     *        Joda style constant
     * @param nFormat
     *        Joda style constant
     */
    private EFormatStyle (@Nonnull @Nonempty final String sFormat, final int nFormat)
    {
      m_sFormat = sFormat;
      m_nFormat = nFormat;
    }

    public String getStyleString ()
    {
      return m_sFormat;
    }

    public int getStyleInt ()
    {
      return m_nFormat;
    }
  }

  private transient DateTimeFormatter m_aFormatter;
  private EFormatStyle m_eDateStyle;
  private EFormatStyle m_eTimeStyle;
  private Locale m_aLocale;
  private String m_sPattern;

  private SerializableDateTimeFormatter (@Nonnull final DateTimeFormatter aFormatter,
                                         @Nullable final EFormatStyle eDateStyle,
                                         @Nullable final EFormatStyle eTimeStyle,
                                         @Nullable final String sPattern,
                                         @Nullable final Locale aLocale)
  {
    if (aFormatter == null)
      throw new NullPointerException ("formatter");
    if (eDateStyle == null && eTimeStyle == null && sPattern == null)
      throw new IllegalArgumentException ("At least on descriptor must be present!");
    if ((eDateStyle != null && eTimeStyle == null) || (eDateStyle == null && eTimeStyle != null))
      throw new IllegalArgumentException ("Either both or no date and time style must be present!");
    m_aFormatter = aFormatter;
    m_eDateStyle = eDateStyle;
    m_eTimeStyle = eTimeStyle;
    m_sPattern = sPattern;
    m_aLocale = aLocale;
  }

  private void writeObject (final ObjectOutputStream aOOS) throws IOException
  {
    aOOS.writeObject (m_eDateStyle);
    aOOS.writeObject (m_eTimeStyle);
    aOOS.writeObject (m_sPattern);
    aOOS.writeObject (m_aLocale);
  }

  private void readObject (final ObjectInputStream aOIS) throws IOException, ClassNotFoundException
  {
    m_eDateStyle = (EFormatStyle) aOIS.readObject ();
    m_eTimeStyle = (EFormatStyle) aOIS.readObject ();
    m_sPattern = (String) aOIS.readObject ();
    m_aLocale = (Locale) aOIS.readObject ();

    if (m_eDateStyle != null && m_eTimeStyle != null)
      m_aFormatter = _createFormatter (m_eDateStyle, m_eTimeStyle, m_aLocale);
    else
      if (m_sPattern != null)
        m_aFormatter = _createFormatter (m_sPattern, m_aLocale);
      else
        throw new IllegalStateException ("Don't know how to revuild the formatter from " +
                                         m_eDateStyle +
                                         "/" +
                                         m_eTimeStyle +
                                         "/" +
                                         m_sPattern +
                                         "/" +
                                         m_aLocale);
  }

  /**
   * @return The main date and time formatter to be used. Never
   *         <code>null</code>.
   */
  @Nonnull
  public DateTimeFormatter getFormatter ()
  {
    return m_aFormatter;
  }

  @Nullable
  public EFormatStyle getDateStyle ()
  {
    return m_eDateStyle;
  }

  @Nullable
  public EFormatStyle getTimeStyle ()
  {
    return m_eTimeStyle;
  }

  @Nullable
  public String getPattern ()
  {
    return m_sPattern;
  }

  @Nullable
  public Locale getLocale ()
  {
    return m_aLocale;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof SerializableDateTimeFormatter))
      return false;
    final SerializableDateTimeFormatter rhs = (SerializableDateTimeFormatter) o;
    return EqualsUtils.equals (m_eDateStyle, rhs.m_eDateStyle) &&
           EqualsUtils.equals (m_eTimeStyle, rhs.m_eTimeStyle) &&
           EqualsUtils.equals (m_sPattern, rhs.m_sPattern) &&
           EqualsUtils.equals (m_aLocale, rhs.m_aLocale);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eDateStyle)
                                       .append (m_eTimeStyle)
                                       .append (m_sPattern)
                                       .append (m_aLocale)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("dateStyle", m_eDateStyle)
                                       .append ("timeStyle", m_eTimeStyle)
                                       .append ("pattern", m_sPattern)
                                       .append ("locale", m_aLocale)
                                       .toString ();
  }

  @Nonnull
  private static DateTimeFormatter _createFormatter (@Nonnull final EFormatStyle eDateStyle,
                                                     @Nonnull final EFormatStyle eTimeStyle,
                                                     @Nullable final Locale aLocale)
  {
    return PDTFormatter.getWithLocaleAndChrono (DateTimeFormat.forStyle (eDateStyle.getStyleString () +
                                                                         eTimeStyle.getStyleString ()),
                                                aLocale);
  }

  @Nonnull
  private static DateTimeFormatter _createFormatter (@Nonnull final String sPattern, @Nullable final Locale aLocale)
  {
    return PDTFormatter.getWithLocaleAndChrono (DateTimeFormat.forPattern (sPattern), aLocale);
  }

  @Nonnull
  public static SerializableDateTimeFormatter createForDate (@Nonnull final EFormatStyle eDateStyle)
  {
    return createForDate (eDateStyle, null);
  }

  @Nonnull
  public static SerializableDateTimeFormatter createForDate (@Nonnull final EFormatStyle eDateStyle,
                                                             @Nullable final Locale aLocale)
  {
    return create (eDateStyle, EFormatStyle.NONE, aLocale);
  }

  @Nonnull
  public static SerializableDateTimeFormatter createForTime (@Nonnull final EFormatStyle eTimeStyle)
  {
    return createForTime (eTimeStyle, null);
  }

  @Nonnull
  public static SerializableDateTimeFormatter createForTime (@Nonnull final EFormatStyle eTimeStyle,
                                                             @Nullable final Locale aLocale)
  {
    return create (EFormatStyle.NONE, eTimeStyle, aLocale);
  }

  @Nonnull
  public static SerializableDateTimeFormatter create (@Nonnull final EFormatStyle eDateStyle,
                                                      @Nonnull final EFormatStyle eTimeStyle)
  {
    return create (eDateStyle, eTimeStyle, null);
  }

  @Nonnull
  public static SerializableDateTimeFormatter create (@Nonnull final EFormatStyle eDateStyle,
                                                      @Nonnull final EFormatStyle eTimeStyle,
                                                      @Nullable final Locale aLocale)
  {
    if (eDateStyle == null)
      throw new NullPointerException ("dateStyle");
    if (eTimeStyle == null)
      throw new NullPointerException ("timeStyle");
    return new SerializableDateTimeFormatter (_createFormatter (eDateStyle, eTimeStyle, aLocale),
                                              eDateStyle,
                                              eTimeStyle,
                                              null,
                                              aLocale);
  }

  @Nonnull
  public static SerializableDateTimeFormatter create (@Nonnull final String sPattern)
  {
    return create (sPattern, null);
  }

  @Nonnull
  public static SerializableDateTimeFormatter create (@Nonnull final String sPattern, @Nullable final Locale aLocale) throws IllegalArgumentException
  {
    if (sPattern == null)
      throw new NullPointerException ("pattern");
    return new SerializableDateTimeFormatter (_createFormatter (sPattern, aLocale), null, null, sPattern, aLocale);
  }
}
