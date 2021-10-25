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
package net.sf.jasperreports.customizers.axis;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;

import net.sf.jasperreports.engine.JRChart;

/**
 * Customizer to define the minimum and maximum value of the domain axis, works for 
 * XY plot
 * 
 * @author Marco Orlandin (dejawho2@users.sourceforge.net)
 */
public class DomainAxisCustomizer extends AbstractAxisCustomizer
{
	@Override
	public void customize(JFreeChart jfc, JRChart jrc) 
	{
		if (jfc.getPlot() instanceof XYPlot)
		{
			ValueAxis valueAxis = jfc.getXYPlot().getDomainAxis();
			
			configValueAxis(valueAxis, PROPERTY_MIN_VALUE, PROPERTY_MAX_VALUE);
			
			if (valueAxis instanceof NumberAxis)
			{
				configNumberAxis((NumberAxis)valueAxis, PROPERTY_TICK_UNIT);
			}
		}
	}
}
