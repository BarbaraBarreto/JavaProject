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
package net.sf.jasperreports.engine.export;

import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.type.RunDirectionEnum;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.export.pdf.PdfProducer;
import net.sf.jasperreports.export.pdf.PdfTextAlignment;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class AbstractPdfTextRenderer extends AbstractTextRenderer
{
	/**
	 * 
	 */
	protected JRPdfExporter pdfExporter;
	protected PdfProducer pdfProducer;
	protected PdfTextAlignment horizontalAlignment;
	protected float leftOffsetFactor;
	protected float rightOffsetFactor;

	
	/**
	 * @deprecated Replaced by {@link #AbstractPdfTextRenderer(JasperReportsContext, boolean, boolean, boolean)}.
	 */
	public AbstractPdfTextRenderer(
		JasperReportsContext jasperReportsContext, 
		boolean ignoreMissingFont
		)
	{
		this(jasperReportsContext, ignoreMissingFont, true, false);
	}
	
	
	/**
	 * 
	 */
	public AbstractPdfTextRenderer(
		JasperReportsContext jasperReportsContext, 
		boolean ignoreMissingFont,
		boolean defaultIndentFirstLine,
		boolean defaultJustifyLastLine
		)
	{
		super(
			jasperReportsContext, 
			false, 
			ignoreMissingFont, 
			defaultIndentFirstLine, 
			defaultJustifyLastLine
			);
	}
	
	
	/**
	 * 
	 */
	public void initialize(
		JRPdfExporter pdfExporter, 
		PdfProducer pdfProducer,
		JRPrintText text, 
		JRStyledText styledText, 
		int offsetX,
		int offsetY
		)
	{
		this.pdfExporter = pdfExporter;
		this.pdfProducer = pdfProducer;
		
		horizontalAlignment = PdfTextAlignment.LEFT;
		leftOffsetFactor = 0f;
		rightOffsetFactor = 0f;
		
		//FIXMETAB 0.2f was a fair approximation
		switch (text.getHorizontalTextAlign())
		{
			case JUSTIFIED :
			{
				horizontalAlignment = PdfTextAlignment.JUSTIFIED;
				leftOffsetFactor = 0f;
				rightOffsetFactor = 0f;
				break;
			}
			case RIGHT :
			{
				if (text.getRunDirectionValue() == RunDirectionEnum.LTR)
				{
					horizontalAlignment = PdfTextAlignment.RIGHT;
				}
				else
				{
					horizontalAlignment =  PdfTextAlignment.LEFT;
				}
				leftOffsetFactor = -0.2f;
				rightOffsetFactor = 0f;
				break;
			}
			case CENTER :
			{
				horizontalAlignment = PdfTextAlignment.CENTER;
				leftOffsetFactor = -0.1f;
				rightOffsetFactor = 0.1f;
				break;
			}
			case LEFT :
			default :
			{
				if (text.getRunDirectionValue() == RunDirectionEnum.LTR)
				{
					horizontalAlignment = PdfTextAlignment.LEFT;
				}
				else
				{
					horizontalAlignment = PdfTextAlignment.RIGHT;
				}
				leftOffsetFactor = 0f;
				rightOffsetFactor = 0.2f;
				break;
			}
		}

		super.initialize(text, styledText, offsetX, offsetY);
	}
	
	public abstract boolean addActualText();
}
