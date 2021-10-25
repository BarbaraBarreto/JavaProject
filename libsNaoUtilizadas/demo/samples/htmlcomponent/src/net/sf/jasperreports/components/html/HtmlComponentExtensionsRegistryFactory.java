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
package net.sf.jasperreports.components.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.component.ComponentManager;
import net.sf.jasperreports.engine.component.ComponentsBundle;
import net.sf.jasperreports.engine.component.DefaultComponentXmlParser;
import net.sf.jasperreports.engine.component.DefaultComponentsBundle;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;

/**
 * Extension registry factory that includes built-in component element
 * implementations.
 * 
 * <p>
 * This registry factory is registered by default in JasperReports.
 * 
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class HtmlComponentExtensionsRegistryFactory implements
		ExtensionsRegistryFactory
{

	public static final String NAMESPACE = 
		"http://jasperreports.sourceforge.net/htmlcomponent";
	public static final String XSD_LOCATION = 
		"http://jasperreports.sourceforge.net/xsd/htmlcomponent.xsd";
	public static final String XSD_RESOURCE = 
		"net/sf/jasperreports/components/html/htmlcomponent.xsd";
	
	protected static final String HTML_COMPONENT_NAME = "html";
	private static final ExtensionsRegistry REGISTRY;
	
	static
	{
		final DefaultComponentsBundle bundle = new DefaultComponentsBundle();

		HtmlComponentDigester htmlDigester = new HtmlComponentDigester();
		
		DefaultComponentXmlParser parser = new DefaultComponentXmlParser();
		parser.setNamespace(NAMESPACE);
		parser.setPublicSchemaLocation(XSD_LOCATION);
		parser.setInternalSchemaResource(XSD_RESOURCE);
		parser.setDigesterConfigurer(htmlDigester);
		bundle.setXmlParser(parser);
		
		HashMap<String, ComponentManager> componentManagers = new HashMap<String, ComponentManager>();
		
		HtmlComponentManager htmlManager = new HtmlComponentManager();
		htmlManager.setDesignConverter(new HtmlComponentDesignConverter());
		htmlManager.setComponentCompiler(new HtmlComponentCompiler());
		//htmlManager.setComponentXmlWriter(new HtmlComponentXmlWriter());
		htmlManager.setComponentFillFactory(new HtmlComponentFillFactory());
		componentManagers.put(HTML_COMPONENT_NAME, htmlManager);
		
		bundle.setComponentManagers(componentManagers);
		
		REGISTRY = new ExtensionsRegistry()
		{
			@Override
			@SuppressWarnings("unchecked")
			public <T> List<T> getExtensions(Class<T> extensionType)
			{
				if (ComponentsBundle.class.equals(extensionType))
				{
					return Collections.singletonList((T)bundle);
				}
				
				return null;
			}
		};
	}
	
	@Override
	public ExtensionsRegistry createRegistry(String registryId,
			JRPropertiesMap properties)
	{
		return REGISTRY;
	}

}
