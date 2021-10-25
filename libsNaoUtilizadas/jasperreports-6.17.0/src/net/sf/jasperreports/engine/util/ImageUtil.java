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
package net.sf.jasperreports.engine.util;

import net.sf.jasperreports.engine.JRImageAlignment;
import net.sf.jasperreports.engine.type.HorizontalImageAlignEnum;
import net.sf.jasperreports.engine.type.VerticalImageAlignEnum;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class ImageUtil
{
	public static float getXAlignFactor(JRImageAlignment imageAlignment)
	{
		return getXAlignFactor(imageAlignment.getHorizontalImageAlign());
	}

	public static float getXAlignFactor(HorizontalImageAlignEnum horizontalAlign)
	{
		float xalignFactor = 0f;
		switch (horizontalAlign)
		{
			case RIGHT :
			{
				xalignFactor = 1f;
				break;
			}
			case CENTER :
			{
				xalignFactor = 0.5f;
				break;
			}
			case LEFT :
			default :
			{
				xalignFactor = 0f;
				break;
			}
		}
		return xalignFactor;
	}

	public static float getYAlignFactor(JRImageAlignment imageAlignment)
	{
		return getYAlignFactor(imageAlignment.getVerticalImageAlign());
	}

	public static float getYAlignFactor(VerticalImageAlignEnum verticalAlign)
	{
		float yalignFactor = 0f;
		switch (verticalAlign)
		{
			case BOTTOM :
			{
				yalignFactor = 1f;
				break;
			}
			case MIDDLE :
			{
				yalignFactor = 0.5f;
				break;
			}
			case TOP :
			default :
			{
				yalignFactor = 0f;
				break;
			}
		}
		return yalignFactor;
	}
}
