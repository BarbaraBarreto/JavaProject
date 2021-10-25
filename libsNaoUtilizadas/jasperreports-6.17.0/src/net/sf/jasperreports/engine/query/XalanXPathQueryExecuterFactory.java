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
package net.sf.jasperreports.engine.query;

import java.util.Map;

import net.sf.jasperreports.annotations.properties.Property;
import net.sf.jasperreports.annotations.properties.PropertyScope;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.properties.PropertyConstants;

/**
 * Xalan XPath query executer factory.
 * <p/>
 * The factory creates {@link net.sf.jasperreports.engine.query.XalanXPathQueryExecuter XalanXPathQueryExecuter}
 * query executers.
 * 
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class XalanXPathQueryExecuterFactory extends JRXPathQueryExecuterFactory
{
	/**
	 * Built-in parameter holding the value of the <code>javax.xml.parsers.DocumentBuilderFactor</code> used to create
	 * documents of type <code>org.w3c.dom.Document<code>
	 */
	public final static String PARAMETER_DOCUMENT_BUILDER_FACTORY = "DOCUMENT_BUILDER_FACTORY";
	
	/**
	 * Parameter that holds the <code>java.util.Map&lt;String,String&gt;</code> with XML namespace information in the 
	 * &lt;prefix, uri&gt; format
	 */
	public final static String PARAMETER_XML_NAMESPACE_MAP = "XML_NAMESPACE_MAP";

	/**
	 * Prefix for properties holding the namespace prefix and uri:
	 * e.g. net.sf.jasperreports.xml.namespace.{prefix} = uri
	 * <p>
	 * This property has a lower priority than {@link #PARAMETER_XML_NAMESPACE_MAP}, which if it is specified it will cause
	 * the prefixed properties not to be searched for.  
	 * </p>
	 */
	@Property(
			name="net.sf.jasperreports.xml.namespace.{arbitrary_prefix}",
			category = PropertyConstants.CATEGORY_DATA_SOURCE,
			scopes = {PropertyScope.CONTEXT, PropertyScope.DATASET},
			scopeQualifications = {JRXPathQueryExecuterFactory.QUERY_EXECUTER_NAME},
			sinceVersion = PropertyConstants.VERSION_4_6_0
			)
	public final static String XML_NAMESPACE_PREFIX = JRPropertiesUtil.PROPERTY_PREFIX + "xml.namespace.";
	
	/**
	 * Boolean parameter/property that specifies whether the XML document should be parsed for namespaces or not.
	 * <p>
	 * This parameter is meaningful only when:
	 * <ul>
	 * <li>
	 * the {@link #PARAMETER_XML_NAMESPACE_MAP} parameter is not provided or provided with a <code>null</code> value
	 * </li>
	 * <li>
	 * there are no properties prefixed with {@link #XML_NAMESPACE_PREFIX};
	 * </li>
	 * <li>
	 * the xpath query expression that is provided <b>contains</b> XML namespace prefixes
	 * </li>
	 * </ul>
	 * </p>
	 * It defaults to <code>false</code>
	 */
	@Property(
			category = PropertyConstants.CATEGORY_DATA_SOURCE,
			defaultValue = PropertyConstants.BOOLEAN_FALSE,
			scopes = {PropertyScope.CONTEXT, PropertyScope.DATASET},
			scopeQualifications = {JRXPathQueryExecuterFactory.QUERY_EXECUTER_NAME},
			sinceVersion = PropertyConstants.VERSION_4_6_0,
			valueType = Boolean.class
			)
	public final static String XML_DETECT_NAMESPACES = JRPropertiesUtil.PROPERTY_PREFIX + "xml.detect.namespaces";
	
	private final static Object[] XALAN_XPATH_BUILTIN_PARAMETERS = {
		PARAMETER_XML_DATA_DOCUMENT,  "org.w3c.dom.Document",
		PARAMETER_DOCUMENT_BUILDER_FACTORY, "javax.xml.parsers.DocumentBuilderFactory",
		PARAMETER_XML_NAMESPACE_MAP, "java.util.Map",
		XML_DATE_PATTERN, "java.lang.String",
		XML_NUMBER_PATTERN, "java.lang.String",
		XML_LOCALE, "java.util.Locale",
		XML_TIME_ZONE, "java.util.TimeZone",
		};

	@Override
	public Object[] getBuiltinParameters()
	{
		return XALAN_XPATH_BUILTIN_PARAMETERS;
	}
	
	@Override
	public JRQueryExecuter createQueryExecuter(
		JasperReportsContext jasperReportsContext,
		JRDataset dataset,
		Map<String,? extends JRValueParameter> parameters
		) throws JRException
	{
		return createQueryExecuter(SimpleQueryExecutionContext.of(jasperReportsContext), dataset, parameters);
	}

	@Override
	public JRQueryExecuter createQueryExecuter(
		QueryExecutionContext context,
		JRDataset dataset,
		Map<String, ? extends JRValueParameter> parameters
		) throws JRException
	{
		return new XalanXPathQueryExecuter(context, dataset, parameters);
	}
}
