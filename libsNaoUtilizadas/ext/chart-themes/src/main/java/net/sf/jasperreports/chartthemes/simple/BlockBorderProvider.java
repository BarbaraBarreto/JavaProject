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
package net.sf.jasperreports.chartthemes.simple;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockFrame;
import org.jfree.ui.RectangleInsets;

import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;

/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class BlockBorderProvider implements BlockFrameProvider, JRChangeEventsSupport
{
	
	public static final String PROPERTY_INSETS = "insets";
	public static final String PROPERTY_PAINT = "paint";

	private RectangleInsets insets;
	private PaintProvider paint;
	
	public BlockBorderProvider()
	{
	}
	
	public BlockBorderProvider(RectangleInsets insets, PaintProvider paint)
	{
		this.insets = insets;
		this.paint = paint;
	}

	@Override
	public BlockFrame getBlockFrame()
	{
		Paint borderPaint = paint == null ? null : paint.getPaint();
		
		BlockBorder border;
		if (insets == null)
		{
			if (paint == null)
			{
				border = new BlockBorder();
			}
			else
			{
				border = new BlockBorder(borderPaint);
			}
		}
		else
		{
			if (borderPaint == null)
			{
				border = new BlockBorder(insets, Color.black);
			}
			else
			{
				border = new BlockBorder(insets, borderPaint);
			}
		}
		return border;
	}

	public RectangleInsets getInsets()
	{
		return insets;
	}

	public void setInsets(RectangleInsets insets)
	{
		Object old = this.insets;
		this.insets = insets;
		getEventSupport().firePropertyChange(PROPERTY_INSETS, old, this.insets);
	}

	public PaintProvider getPaint()
	{
		return paint;
	}

	public void setPaint(PaintProvider paint)
	{
		Object old = this.paint;
		this.paint = paint;
		getEventSupport().firePropertyChange(PROPERTY_PAINT, old, this.paint);
	}

	private transient JRPropertyChangeSupport eventSupport;
	
	@Override
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (eventSupport == null)
			{
				eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return eventSupport;
	}

}
