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
package xchart;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public class XYSeriesData
{
	private List<Number> xData;
	private List<Number> yData;
	private Color color;

	public XYSeriesData()
	{
		this.xData = new ArrayList<Number>();
		this.yData = new ArrayList<Number>();
	}
	
	public List<Number> getXData()
	{
		return xData;
	}
	
	public List<Number> getYData()
	{
		return yData;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
}
