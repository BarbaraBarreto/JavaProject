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
package net.sf.jasperreports.customizers.type;

import org.jfree.ui.Layer;

import net.sf.jasperreports.engine.type.EnumUtil;
import net.sf.jasperreports.engine.type.NamedEnum;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public enum LayerEnum implements NamedEnum
{
	/**
	 *
	 */
	BACKGROUND(Layer.BACKGROUND, "background"),

	/**
	 *
	 */
	FOREGROUND(Layer.FOREGROUND, "foreground");


	/**
	 *
	 */
	private final transient Layer value;
	private final transient String name;

	private LayerEnum(Layer layer, String name)
	{
		this.value = layer;
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}

	/**
	 *
	 */
	public final Layer getLayer()
	{
		return this.value;
	}

	/**
	 *
	 */
	public static LayerEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
}
