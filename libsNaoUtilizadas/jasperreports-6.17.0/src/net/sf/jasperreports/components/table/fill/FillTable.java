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
package net.sf.jasperreports.components.table.fill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.jasperreports.components.headertoolbar.HeaderToolbarElement;
import net.sf.jasperreports.components.table.BaseColumn;
import net.sf.jasperreports.components.table.Column;
import net.sf.jasperreports.components.table.ColumnGroup;
import net.sf.jasperreports.components.table.ColumnVisitor;
import net.sf.jasperreports.components.table.TableComponent;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.component.BaseFillComponent;
import net.sf.jasperreports.engine.component.FillPrepareResult;
import net.sf.jasperreports.engine.design.JRAbstractCompiler;
import net.sf.jasperreports.engine.design.JRReportCompileData;
import net.sf.jasperreports.engine.export.JRPdfExporterTagHelper;
import net.sf.jasperreports.engine.fill.JRFillCloneFactory;
import net.sf.jasperreports.engine.fill.JRFillComponentElement;
import net.sf.jasperreports.engine.fill.JRFillContext;
import net.sf.jasperreports.engine.fill.JRFillDatasetRun;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;
import net.sf.jasperreports.engine.fill.JRTemplateFrame;
import net.sf.jasperreports.engine.fill.JRTemplatePrintFrame;
import net.sf.jasperreports.engine.fill.VirtualizableFrame;
import net.sf.jasperreports.engine.util.JRReportUtils;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class FillTable extends BaseFillComponent
{

	private static final Log log = LogFactory.getLog(FillTable.class);
	
	protected static final String FILL_CACHE_KEY_TABLE_INSTANCE_COUNTER = FillTable.class.getName() + "#instanceCounter";
	protected static final String EXCEPTION_MESSAGE_KEY_UNSUPPORTED_REPORT_DATA_TYPE = "components.table.unsupported.report.data.type";
	
	private final TableComponent table;
	private final JRFillObjectFactory factory;
	private Map<List<FillColumn>, FillTableSubreportFactory> fillSubreportFactories;
	private FillTableSubreport fillSubreport;
	
	private boolean filling;
	private List<FillColumn> fillColumns;
	private int fillWidth;
	private Map<JRStyle, JRTemplateFrame> printFrameTemplates = new HashMap<JRStyle, JRTemplateFrame>();

	public FillTable(TableComponent table, JRFillObjectFactory factory)
	{
		this.table = table;
		this.factory = factory;
		
		// we need to do this for return values with derived variables
		JRFillDatasetRun fillDatasetRun = factory.getDatasetRun(table.getDatasetRun());
		// this is needed for returned variables with evaluationTime=Auto
		factory.registerDatasetRun(fillDatasetRun);
		
		this.fillSubreportFactories = new HashMap<List<FillColumn>, FillTableSubreportFactory>();
	}

	public FillTable(FillTable table, JRFillCloneFactory factory)
	{
		super(table, factory);
		
		this.table = table.table;
		this.factory = table.factory;
		
		this.fillSubreportFactories = table.fillSubreportFactories;
		this.printFrameTemplates = table.printFrameTemplates;
	}

	@Override
	public void evaluate(byte evaluation) throws JRException
	{
		if (filling)
		{
			log.warn("Table fill did not complete, canceling previous table subreport");
			fillSubreport.cancelSubreportFill();
		}
		
		filling = false;
		
		evaluateColumns(evaluation);
		if (!fillColumns.isEmpty())
		{
			createFillSubreport();
			setTableInstanceCounter();
			fillSubreport.evaluateSubreport(evaluation);
		}
	}
	
	protected void setTableInstanceCounter()
	{
		JRFillContext fillerContext = fillContext.getFiller().getFillContext();
		AtomicInteger counter = (AtomicInteger) fillerContext.getFillCache(FILL_CACHE_KEY_TABLE_INSTANCE_COUNTER);
		if (counter == null)
		{
			// we just need a mutable integer, there's no actual concurrency here
			counter = new AtomicInteger();
			fillerContext.setFillCache(FILL_CACHE_KEY_TABLE_INSTANCE_COUNTER, counter);
		}
		
		int instanceIndex = counter.getAndIncrement();
		if (log.isDebugEnabled())
		{
			log.debug("table instance index is " + instanceIndex);
		}
		
		fillSubreport.getTableReport().getBaseReport().setTableInstanceIndex(instanceIndex);
	}

	protected boolean toPrintColumn(BaseColumn column, byte evaluation) throws JRException
	{
		boolean toPrint;
		JRExpression printWhenExpression = column.getPrintWhenExpression();
		if (printWhenExpression == null)
		{
			toPrint = true;
		}
		else
		{
			Boolean printWhenVal = (Boolean) evaluateExpression(
					printWhenExpression, evaluation);
			if (printWhenVal == null)
			{
				toPrint = false;
			}
			else
			{
				toPrint = printWhenVal;
			}
		}
		return toPrint;
	}
	
	protected JRPropertiesMap evaluateProperties(BaseColumn column, byte evaluation) throws JRException
	{
		JRPropertiesMap staticProperties = column.hasProperties() ? column.getPropertiesMap().cloneProperties() : null;
		JRPropertiesMap mergedProperties = null;

		JRPropertyExpression[] propExprs = column.getPropertyExpressions();
		if (propExprs == null || propExprs.length == 0)
		{
			mergedProperties = staticProperties;
		}
		else
		{
			JRPropertiesMap dynamicProperties = new JRPropertiesMap();
			
			for (int i = 0; i < propExprs.length; i++)
			{
				JRPropertyExpression prop = propExprs[i];
				String value = (String) evaluateExpression(prop.getValueExpression(), evaluation);
				//if (value != null) //for some properties such as data properties in metadata exporters, the null value is significant
				{
					dynamicProperties.setProperty(prop.getName(), value);
				}
			}
			
			mergedProperties = dynamicProperties.cloneProperties();
			mergedProperties.setBaseProperties(staticProperties);
		}
		
		return mergedProperties;
	}
	
	protected class FillColumnEvaluator implements ColumnVisitor<FillColumn>
	{
		final byte evaluation;
		
		public FillColumnEvaluator(byte evaluation)
		{
			this.evaluation = evaluation;
		}

		@Override
		public FillColumn visitColumn(Column column)
		{
			try
			{
				boolean toPrint = toPrintColumn(column, evaluation);
				if (toPrint)
				{
					JRPropertiesMap properties = evaluateProperties(column, evaluation);
					return new FillColumn(column, properties); 
				}
				return null;
			}
			catch (JRException e)
			{
				throw new JRRuntimeException(e);
			}
		}

		@Override
		public FillColumn visitColumnGroup(ColumnGroup columnGroup)
		{
			try
			{
				boolean toPrint = toPrintColumn(columnGroup, evaluation);
				FillColumn fillColumn;
				if (toPrint)
				{
					List<BaseColumn> columns = columnGroup.getColumns();
					List<FillColumn> subColumns = new ArrayList<FillColumn>(columns.size());
					int printWidth = 0;
					for (BaseColumn column : columns)
					{
						FillColumn fillSubColumn = column.visitColumn(this);
						if (fillSubColumn != null)
						{
							printWidth += fillSubColumn.getWidth();
							subColumns.add(fillSubColumn);
						}
					}
					
					if (subColumns.isEmpty())
					{
						// no sub columns prints
						// the column group won't print either
						fillColumn = null;
					}
					else
					{
						JRPropertiesMap properties = evaluateProperties(columnGroup, evaluation);
						fillColumn = new FillColumn(columnGroup, printWidth, subColumns, properties);
					}
				}
				else
				{
					fillColumn = null;
				}
				return fillColumn;
			}
			catch (JRException e)
			{
				throw new JRRuntimeException(e);
			}
		}
	}
	
	protected void evaluateColumns(byte evaluation)
	{
		FillColumnEvaluator columnEvaluator = new FillColumnEvaluator(evaluation);
		List<BaseColumn> columns = table.getColumns();
		fillColumns = new ArrayList<FillColumn>(columns.size());
		fillWidth = 0;
		for (BaseColumn column : columns)
		{
			FillColumn fillColumn = column.visitColumn(columnEvaluator);
			if (fillColumn != null)
			{
				fillColumns.add(fillColumn);
				fillWidth += fillColumn.getWidth();
			}
		}
	}

	protected void createFillSubreport() throws JRException
	{
		FillTableSubreportFactory subreportFactory = fillSubreportFactories.get(fillColumns);
		if (subreportFactory == null)
		{
			subreportFactory = createFillTableSubreportFactory();
			fillSubreportFactories.put(fillColumns, subreportFactory);
		}
		
		fillSubreport = subreportFactory.createFillSubreport();
	}

	protected FillTableSubreportFactory createFillTableSubreportFactory() throws JRException
	{
		JasperReport parentReport = fillContext.getFiller().getJasperReport();
		JasperReport containingReport = containingReport(parentReport);
		JRDataset reportSubdataset = JRReportUtils.findSubdataset(table.getDatasetRun(), 
				containingReport);
		
		BuiltinExpressionEvaluatorFactory builtinEvaluatorFactory = new BuiltinExpressionEvaluatorFactory();
		
		String tableReportName = JRAbstractCompiler.getUnitName(containingReport, reportSubdataset);
		
		// clone the table subdataset in order to have a different instance for other
		// elements that might be using it.
		// we're cloning the subdataset via an object factory in order to preserve
		// the relationship between objects (e.g. variables and groups) in the cloned
		// dataset
		JRDataset tableSubdataset = DatasetCloneObjectFactory.cloneDataset(reportSubdataset);
		TableReportDataset reportDataset = new TableReportDataset(tableSubdataset, tableReportName);

		TableReport tableReport = new TableReport(fillContext, table, reportDataset, fillColumns, builtinEvaluatorFactory);
		
		if (log.isDebugEnabled())
		{
			String tableReportXml = new JRXmlWriter(fillContext.getFiller().getJasperReportsContext()).write(tableReport, "UTF-8");
			log.debug("Generated table report:\n" + tableReportXml);
		}
		
		JRReportCompileData tableReportCompileData = createTableReportCompileData(
				containingReport, reportSubdataset);
		
		TableJasperReport compiledTableReport = new TableJasperReport(parentReport, tableReport, 
				tableReportCompileData, 
				new TableReportBaseObjectFactory(reportDataset),
				"");// no suffix as already included in the report name
		
		TableSubreport subreport = 
			new TableSubreport(
				table.getDatasetRun(), 
				((JRFillComponentElement)fillContext.getComponentElement()).getParent()
				);
		return 
			new FillTableSubreportFactory(
				subreport, compiledTableReport,
				builtinEvaluatorFactory
				);
	}

	protected JasperReport containingReport(JasperReport parentReport)
	{
		JasperReport containingReport = parentReport;
		while (containingReport instanceof TableJasperReport)
		{
			containingReport = ((TableJasperReport) containingReport).getParentReport();
		}
		return containingReport;
	}
	
	protected JRReportCompileData createTableReportCompileData(
			JasperReport parentReport, JRDataset reportSubdataset)
			throws JRException
	{
		Serializable reportCompileDataObj = parentReport.getCompileData();
		if (!(reportCompileDataObj instanceof JRReportCompileData))
		{
			throw 
				new JRRuntimeException(
					EXCEPTION_MESSAGE_KEY_UNSUPPORTED_REPORT_DATA_TYPE,  
					new Object[]{reportCompileDataObj.getClass().getName()} 
					);
		}
		
		JRReportCompileData reportCompileData = (JRReportCompileData) reportCompileDataObj;
		Serializable datasetCompileData = reportCompileData.getDatasetCompileData(
				reportSubdataset);
		
		TableReportCompileData tableReportCompileData = new TableReportCompileData(
				parentReport);
		tableReportCompileData.setMainDatasetCompileData(datasetCompileData);
		
		JRDataset[] datasets = parentReport.getDatasets();
		if (datasets != null)
		{
			for (JRDataset dataset : datasets)
			{
				Serializable compileData = reportCompileData.getDatasetCompileData(dataset);
				tableReportCompileData.setDatasetCompileData(dataset, compileData);
			}
		}
		tableReportCompileData.copyCrosstabCompileData(reportCompileData);
		return tableReportCompileData;
	}
	
	@Override
	public FillPrepareResult prepare(int availableHeight)
	{
		try
		{
			if (fillColumns.isEmpty())
			{
				//no columns to print
				return FillPrepareResult.NO_PRINT_NO_OVERFLOW;
			}
			
			JRTemplatePrintFrame printFrame = new JRTemplatePrintFrame(getFrameTemplate(), printElementOriginator);
			JRLineBox lineBox = printFrame.getLineBox();
			int verticalPadding = lineBox.getTopPadding() + lineBox.getBottomPadding();
			
			FillPrepareResult result = 
				fillSubreport.prepareSubreport(
					availableHeight - verticalPadding, 
					filling
					);
			
			if (verticalPadding != 0)
			{
				result = result.addStretch(verticalPadding);
			}
			
			filling = result.willOverflow();
			return result;
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public JRPrintElement fill()
	{
		JRTemplatePrintFrame printFrame = new JRTemplatePrintFrame(getFrameTemplate(), printElementOriginator);

		if (fillSubreport.getTableReport().getBaseReport().isInteractiveTable()) {
			printFrame.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_TABLE_UUID, fillContext.getComponentElement().getUUID().toString());
		}

		JRLineBox lineBox = printFrame.getLineBox();
		
		printFrame.setUUID(fillContext.getComponentElement().getUUID());
		printFrame.setX(fillContext.getComponentElement().getX());
		printFrame.setY(fillContext.getElementPrintY());
		printFrame.setHeight(fillSubreport.getContentsStretchHeight() + lineBox.getTopPadding() + lineBox.getBottomPadding());
		if (fillSubreport.getTableReport().getBaseReport().isGeneratePdfTags())
		{
			printFrame.getPropertiesMap().setProperty(JRPdfExporterTagHelper.PROPERTY_TAG_TABLE, JRPdfExporterTagHelper.TAG_FULL);
		}
		
		List<JRStyle> styles = fillSubreport.getSubreportStyles();
		for (Iterator<JRStyle> it = styles.iterator(); it.hasNext();)
		{
			JRStyle style = it.next();
			try
			{
				fillContext.getFiller().addPrintStyle(style);
			}
			catch (JRException e)
			{
				throw new JRRuntimeException(e);
			}
		}
		
		List<JROrigin> origins = fillSubreport.getSubreportOrigins();
		for (Iterator<JROrigin> it = origins.iterator(); it.hasNext();)
		{
			JROrigin origin = it.next();
			fillContext.getFiller().getJasperPrint().addOrigin(origin);
		}
		
		int contentsWidth = fillWidth;
		Collection<JRPrintElement> elements = fillSubreport.getPrintElements();
		if (elements != null)
		{
			VirtualizableFrame virtualizableFrame = new VirtualizableFrame(printFrame, 
					fillContext.getFiller().getVirtualizationContext(), 
					fillContext.getFiller().getCurrentPage());
			
			virtualizableFrame.addOffsetElements(elements, 0, 0);
			virtualizableFrame.fill();
			
			if (fillSubreport.getPrintContentsWidth() > contentsWidth)
			{
				contentsWidth = fillSubreport.getPrintContentsWidth();
			}
		}
		
		printFrame.setWidth(contentsWidth + lineBox.getLeftPadding() + lineBox.getRightPadding());
		
		fillSubreport.subreportPageFilled();
		
		return printFrame;
	}

	protected JRTemplateFrame getFrameTemplate()
	{
		JRStyle style = fillContext.getElementStyle();
		JRTemplateFrame frameTemplate = printFrameTemplates.get(style);
		if (frameTemplate == null)
		{
			frameTemplate = new JRTemplateFrame(
						fillContext.getElementOrigin(),
						fillContext.getDefaultStyleProvider());
			frameTemplate.setElement(fillContext.getComponentElement());
			frameTemplate = deduplicate(frameTemplate);
			
			printFrameTemplates.put(style, frameTemplate);
		}

		return frameTemplate;
	}

	@Override
	public void rewind()
	{
		if (filling)
		{
			if (log.isDebugEnabled())
			{
				log.debug("Rewinding table subreport");
			}
			
			try
			{
				fillSubreport.rewind();
			}
			catch (JRException e)
			{
				throw new JRRuntimeException(e);
			}
			
			filling = false;
		}
	}

	protected class FillTableSubreportFactory
	{
		private final TableSubreport subreport;
		private final TableJasperReport compiledTableReport;
		private final BuiltinExpressionEvaluatorFactory builtinEvaluatorFactory;
		
		public FillTableSubreportFactory(TableSubreport subreport, TableJasperReport compiledTableReport, 
				BuiltinExpressionEvaluatorFactory builtinEvaluatorFactory)
		{
			this.subreport = subreport;
			this.compiledTableReport = compiledTableReport;
			this.builtinEvaluatorFactory = builtinEvaluatorFactory;
		}

		public FillTableSubreport createFillSubreport()
		{
			return new FillTableSubreport(
						fillContext, subreport, factory, compiledTableReport,
						builtinEvaluatorFactory
						);
		}
	}
	
}
