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
package net.sf.jasperreports.data.http;

import org.exolab.castor.mapping.GeneralizedFieldHandler;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class RequestMethodFieldHandler extends GeneralizedFieldHandler
{
	public RequestMethodFieldHandler()
	{
		super();
	}
	
	@Override
	public Object convertUponGet(Object value)
	{
		if (value == null)
		{
			return null;
		}
		return ((RequestMethod) value).name();
	}

	@Override
	public Object convertUponSet(Object value)
	{
		if (value == null)
		{
			return null;
		}
		return RequestMethod.valueOf((String) value);
	}
	
	@Override
	public Class<?> getFieldType()
	{
		return RequestMethod.class;
	}

	@Override
	public Object newInstance(Object parent) throws IllegalStateException
	{
		return null;
	}
}
