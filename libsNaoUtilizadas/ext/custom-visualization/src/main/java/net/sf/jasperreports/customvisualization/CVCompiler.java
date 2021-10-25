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
package net.sf.jasperreports.customvisualization;

import java.util.List;

import net.sf.jasperreports.components.items.ItemCompiler;
import net.sf.jasperreports.components.items.ItemData;
import net.sf.jasperreports.components.items.ItemProperty;
import net.sf.jasperreports.customvisualization.design.CVDesignComponent;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.component.ComponentCompiler;
import net.sf.jasperreports.engine.design.JRVerifier;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;

public class CVCompiler implements ComponentCompiler
{

	@Override
	public void collectExpressions(Component component, JRExpressionCollector collector)
	{
		CVComponent cmp = (CVComponent) component;

		for (ItemProperty p : cmp.getItemProperties())
		{
			collector.addExpression(p.getValueExpression());
		}

		List<ItemData> cvDataList = cmp.getItemData();
		if (cvDataList != null) // cvDatasets should neber be null...
		{
			for (ItemData cvData : cvDataList)
			{
				ItemCompiler.collectExpressions(cvData, collector);
			}
		}

	}

	/**
	 * @deprecated Replaced by
	 *             {@link ItemCompiler#collectExpressions(ItemData, JRExpressionCollector)}
	 *             .
	 */
	public static void collectExpressions(ItemData data, JRExpressionCollector collector)
	{
		ItemCompiler.collectExpressions(data, collector);
	}
	// /**
	// * Collect the expressions in a cv item dataset.
	// *
	// * @param cvDataset
	// * @param collector
	// */
	// public static void collectExpressions(ItemData cvData,
	// JRExpressionCollector collector) {
	// if (cvData != null) {
	// JRExpressionCollector datasetCollector = collector;
	//
	// JRElementDataset dataset = cvData.getDataset();
	// if (dataset != null) {
	// collector.collect(dataset);
	// }
	//
	// List<Item> items = cvData.getItems();
	// if (items != null && !items.isEmpty()) {
	// for (Item item : items) {
	// List<ItemProperty> itemProperties = item.getProperties();
	// if (itemProperties != null) {
	// for (ItemProperty property : itemProperties) {
	// datasetCollector.addExpression(property
	// .getValueExpression());
	// }
	// }
	// }
	// }
	// }
	// }

	@Override
	public Component toCompiledComponent(Component component, JRBaseObjectFactory baseFactory)
	{
		CVComponent cmp = (CVComponent) component;
		CVComponent compiledComponent = new CVDesignComponent(cmp, baseFactory);
		return compiledComponent;
	}

	@Override
	public void verify(Component component, JRVerifier verifier)
	{
		CVComponent cmp = (CVComponent) component;

		EvaluationTimeEnum evaluationTime = cmp.getEvaluationTime();
		if (evaluationTime == EvaluationTimeEnum.AUTO)
		{
			verifier.addBrokenRule("Auto evaluation time is not supported for this component", cmp);
		}
		else if (evaluationTime == EvaluationTimeEnum.GROUP)
		{
			String evaluationGroup = cmp.getEvaluationGroup();
			if (evaluationGroup == null || evaluationGroup.length() == 0)
			{
				verifier.addBrokenRule("No evaluation group set", cmp);
			}
			else if (!verifier.getReportDesign().getGroupsMap().containsKey(evaluationGroup))
			{
				verifier.addBrokenRule("Evalution group \"" + evaluationGroup + " not found", cmp);
			}
		}

		for (ItemProperty p : cmp.getItemProperties())
		{
			verifier.verifyExpression(p.getValueExpression(), p, null);
		}
		List<ItemData> pathDataList = cmp.getItemData();
		if (pathDataList != null && pathDataList.size() > 0)
			for (ItemData pathData : pathDataList)
				ItemCompiler.verifyItemData(verifier, pathData, null, null, null);

	}

}
