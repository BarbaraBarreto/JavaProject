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
package net.sf.jasperreports.engine.base;

import java.awt.Color;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.type.LineStyleEnum;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRBaseBoxPen extends JRBasePen implements JRBoxPen
{

	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	protected JRLineBox lineBox;
	
	/**
	 *
	 */
	public JRBaseBoxPen(JRLineBox box)
	{
		super(box);

		this.lineBox = box;
	}
	
	@Override
	public JRLineBox getBox() 
	{
		return lineBox;
	}

	@Override
	public Float getLineWidth()
	{
		return getStyleResolver().getLineWidth(this, penContainer.getDefaultLineWidth());
	}

	@Override
	public LineStyleEnum getLineStyleValue()
	{
		return getStyleResolver().getLineStyleValue(this);
	}

	@Override
	public Color getLineColor()
	{
		return getStyleResolver().getLineColor(this, penContainer.getDefaultLineColor());
	}

	@Override
	public JRPen getPen(JRLineBox box) 
	{
		return box.getPen();
	}

	@Override
	public JRBoxPen clone(JRLineBox lineBox)
	{
		JRBaseBoxPen clone = (JRBaseBoxPen)super.clone(lineBox);
		
		clone.lineBox = lineBox;
		
		return clone;
	}
	
}
