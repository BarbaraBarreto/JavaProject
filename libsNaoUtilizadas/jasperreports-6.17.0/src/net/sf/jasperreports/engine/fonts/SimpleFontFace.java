/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2019 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.engine.fonts;

import java.awt.Font;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.util.JRStyledText;



/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class SimpleFontFace implements FontFace, JRCloneable
{

	private static final Log log = LogFactory.getLog(SimpleFontFace.class);
	
	/**
	 * 
	 */
	private JasperReportsContext jasperReportsContext;
	private String ttf;
	private Font font;
	private String pdf;
	private String eot;
	private String svg;
	private String woff;

	@Override
	public Object clone() {
		try
		{
			return super.clone();
		} catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}
	}
	/**
	 * @deprecated Replaced by {@link #SimpleFontFace(JasperReportsContext)} and {@link #setTtf(String)}.
	 */
	public static SimpleFontFace getInstance(JasperReportsContext jasperReportsContext, String fontName)
	{
		SimpleFontFace fontFace = null;

		if (fontName != null)
		{
			fontFace = new SimpleFontFace(jasperReportsContext);
			fontFace.setTtf(fontName);
		}
		
		return fontFace;
	}

	
	/**
	 * @deprecated Replaced by #{@link #SimpleFontFace(JasperReportsContext)} and {@link #setTtf(String)}.
	 */
	public SimpleFontFace(JasperReportsContext jasperReportsContext, String ttf)
	{
		this(jasperReportsContext);
		setTtf(ttf);
	}
	

	/**
	 * @deprecated Replaced by #{@link #SimpleFontFace(JasperReportsContext)} and {@link #setTtf(String)}.
	 */
	public SimpleFontFace(String file)
	{
		this(DefaultJasperReportsContext.getInstance());
		setTtf(file);
	}

	
	/**
	 * @deprecated To be removed.
	 */
	public SimpleFontFace(Font font)
	{
		this.font = font;
	}

	
	/**
	 * 
	 */
	public SimpleFontFace(JasperReportsContext jasperReportsContext)
	{
		this.jasperReportsContext = jasperReportsContext;
	}

	
	@Override
	public String getName()
	{
		//(String)font.getAttributes().get(TextAttribute.FAMILY);
		return font == null ? null : font.getName();
	}
	
	/**
	 * @deprecated Replaced by {@link #getTtf()}.
	 */
	@Override
	public String getFile()
	{
		return getTtf();
	}
	
	@Override
	public String getTtf()
	{
		return ttf;
	}
	
	/**
	 * 
	 */
	public void setTtf(String ttf)
	{
		setTtf(ttf, true);
	}
	
	public void setTtf(String ttf, boolean load)
	{
		this.ttf = ttf;
		this.font = null;
		
		if (load)
		{
			loadFont();
		}
	}
	
	public void loadFont() throws InvalidFontException
	{
		if (ttf != null && font == null)
		{
			if (log.isDebugEnabled())
			{
				log.debug("Loading font " + ttf);
			}
			
			String upperCaseTtf = ttf.trim().toUpperCase();
			if (
				upperCaseTtf.endsWith(".TTF")
				|| upperCaseTtf.endsWith(".OTF")
				)
			{
				font = AwtFontManager.instance().getAwtFont(jasperReportsContext, ttf);
			}
			else
			{
				FontUtil.getInstance(jasperReportsContext).checkAwtFont(ttf, JRPropertiesUtil.getInstance(jasperReportsContext).getBooleanProperty(JRStyledText.PROPERTY_AWT_IGNORE_MISSING_FONT));
				
				font = new Font(ttf, Font.PLAIN, JRPropertiesUtil.getInstance(jasperReportsContext).getIntegerProperty(JRFont.DEFAULT_FONT_SIZE));
			}
		}
	}
	
	@Override
	public Font getFont()
	{
		return font;
	}
	
	@Override
	public String getPdf()
	{
		return pdf;
	}
	
	/**
	 * 
	 */
	public void setPdf(String pdf)
	{
		this.pdf = pdf;
	}
	
	@Override
	public String getEot()
	{
		return eot;
	}
	
	/**
	 * 
	 */
	public void setEot(String eot)
	{
		this.eot = eot;
	}
	
	@Override
	public String getSvg()
	{
		return svg;
	}
	
	/**
	 * 
	 */
	public void setSvg(String svg)
	{
		this.svg = svg;
	}
	
	@Override
	public String getWoff()
	{
		return woff;
	}
	
	/**
	 * 
	 */
	public void setWoff(String woff)
	{
		this.woff = woff;
	}
	
	@Override
	protected void finalize()
	{
		AwtFontManager.instance().purgeFontFiles();
	}
	
	
}
