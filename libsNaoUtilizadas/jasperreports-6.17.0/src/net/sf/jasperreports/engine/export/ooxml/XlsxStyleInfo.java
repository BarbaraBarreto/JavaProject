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
package net.sf.jasperreports.engine.export.ooxml;

import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRTextAlignment;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporter;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.LineDirectionEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import net.sf.jasperreports.engine.util.JRColorUtil;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class XlsxStyleInfo
{
	/**
	 *
	 */
	protected int formatIndex; 
	protected int fontIndex; 
	protected int borderIndex; 
	protected String backcolor; 
	protected String horizontalAlign;
	protected String verticalAlign;
	protected boolean isWrapText = true;
	protected boolean isHidden;
	protected boolean isLocked;
	protected boolean isShrinkToFit;
	protected boolean isIgnoreTextFormatting;
	protected int rotation;
	protected boolean whitePageBackground;
	protected boolean ignoreCellBackground;
	protected boolean ignoreCellBorder;
	protected LineDirectionEnum direction;

	/**
	 *
	 */
	public XlsxStyleInfo(
		int formatIndex, 
		int fontIndex, 
		int borderIndex, 
		JRExporterGridCell gridCell, 
		boolean isWrapText,
		boolean isHidden,
		boolean isLocked,
		boolean isShrinkToFit,
		boolean isIgnoreTextFormatting,
		int rotation,
		JRXlsAbstractExporter.SheetInfo sheetInfo,
		LineDirectionEnum direction
		)
	{
		this.formatIndex = formatIndex;
		this.fontIndex = isIgnoreTextFormatting ? -1 : fontIndex;
		this.borderIndex = isIgnoreTextFormatting ? -1 : borderIndex;
		
		JRPrintElement element = gridCell.getElement();
		
		if (!isIgnoreTextFormatting)
		{
			if (element != null && element.getModeValue() == ModeEnum.OPAQUE)
			{
				this.backcolor = JRColorUtil.getColorHexa(element.getBackcolor());
			}
			else if (gridCell.getBackcolor() != null)
			{
				this.backcolor = JRColorUtil.getColorHexa(gridCell.getBackcolor());
			}
		}
		
		JRTextAlignment align = element instanceof JRTextAlignment ? (JRTextAlignment)element : null;
		if (align != null)
		{
			this.horizontalAlign = getHorizontalAlignment(align.getHorizontalTextAlign(), align.getVerticalTextAlign(), rotation);//FIXMEXLSX use common util
			this.verticalAlign = getVerticalAlignment(align.getHorizontalTextAlign(), align.getVerticalTextAlign(), rotation);//FIXMEXLSX use common util
		}
		
		this.isWrapText = isShrinkToFit ? false : isWrapText;
		this.isHidden = isHidden;
		this.isLocked = isLocked;
		this.isShrinkToFit = isShrinkToFit;
		this.rotation = rotation;
		if(sheetInfo.whitePageBackground != null)
		{
			this.whitePageBackground = sheetInfo.whitePageBackground;
		}
		if(sheetInfo.ignoreCellBackground != null)
		{
			this.ignoreCellBackground = sheetInfo.ignoreCellBackground;
		}
		if(sheetInfo.ignoreCellBorder != null)
		{
			this.ignoreCellBorder = sheetInfo.ignoreCellBorder;
		}
		this.direction = direction;
	}
	
	protected String getHorizontalAlignment(HorizontalTextAlignEnum hAlign, VerticalTextAlignEnum vAlign, int rotation)
	{
		switch (rotation)
		{
			case 90:
			{
				switch (vAlign)
				{
					case JUSTIFIED : 
					{
						hAlign = HorizontalTextAlignEnum.JUSTIFIED;
						break;
					}
					case BOTTOM : 
					{
						hAlign = HorizontalTextAlignEnum.RIGHT;
						break;
					}
					case MIDDLE : 
					{
						hAlign = HorizontalTextAlignEnum.CENTER;
						break;
					}
					case TOP : 
					default: 
					{
						hAlign = HorizontalTextAlignEnum.LEFT;
					}
				}
				break;
			}
			case 180:
			{
				switch (vAlign)
				{
					case JUSTIFIED : 
					{
						hAlign = HorizontalTextAlignEnum.JUSTIFIED;
						break;
					}
					case BOTTOM : 
					{
						hAlign = HorizontalTextAlignEnum.LEFT;
						break;
					}
					case MIDDLE : 
					{
						hAlign = HorizontalTextAlignEnum.CENTER;
						break;
					}
					case TOP : 
					default: 
					{
						hAlign = HorizontalTextAlignEnum.RIGHT;
					}
				}
				break;
			}
			default:
			{
			}	
		}
		
		return XlsxParagraphHelper.getHorizontalAlignment(hAlign);
	}
	
	protected String getVerticalAlignment(HorizontalTextAlignEnum hAlign, VerticalTextAlignEnum vAlign, int rotation)
	{
		switch (rotation)
		{
			case 90:
			{
				switch (hAlign)
				{
					case JUSTIFIED : 
					{
						vAlign = VerticalTextAlignEnum.JUSTIFIED;
						break;
					}
					case RIGHT : 
					{
						vAlign = VerticalTextAlignEnum.TOP;
						break;
					}
					case CENTER : 
					{
						vAlign = VerticalTextAlignEnum.MIDDLE;
						break;
					}
					case LEFT : 
					default: 
					{
						vAlign = VerticalTextAlignEnum.BOTTOM;
					}
				}
				break;
			}
			case 180:
			{
				switch (hAlign)
				{
					case JUSTIFIED : 
					{
						vAlign = VerticalTextAlignEnum.JUSTIFIED;
						break;
					}
					case RIGHT : 
					{
						vAlign = VerticalTextAlignEnum.BOTTOM;
						break;
					}
					case CENTER : 
					{
						vAlign = VerticalTextAlignEnum.MIDDLE;
						break;
					}
					case LEFT : 
					default: 
					{
						vAlign = VerticalTextAlignEnum.TOP;
					}
				}
				break;
			}
			default: 
			{	
			}
		}
		
		return XlsxParagraphHelper.getVerticalAlignment(vAlign);
	}
	
	public String getId()
	{
		return 
		formatIndex + "|" + fontIndex + "|" + borderIndex + "|" + backcolor + "|" + horizontalAlign + "|" + verticalAlign 
		+ "|" + isWrapText + "|" + isHidden + "|" + isLocked + "|" + isShrinkToFit + "|" + rotation + "|" + whitePageBackground 
		+ "|" + ignoreCellBackground + "|" + ignoreCellBorder + "|" + direction;
	}
}
