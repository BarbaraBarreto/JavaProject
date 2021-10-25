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
package net.sf.jasperreports.customvisualization.export;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.repo.RepositoryContext;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class CVElementDefaultImageDataProvider implements CVElementImageDataProvider {
	private static final Log log = LogFactory.getLog(CVElementDefaultImageDataProvider.class);
	private static final CVElementDefaultImageDataProvider INSTANCE = new CVElementDefaultImageDataProvider();

	public static CVElementDefaultImageDataProvider getInstance() {
		return INSTANCE;
	}

	private CVElementImageDataProvider cvElementPhantomJSImageProvider = new CVElementPhantomJSImageDataProvider();
	private JRPhantomCVElementImageDataProvider jrPhantomCVElementImageDataProvider = new JRPhantomCVElementImageDataProvider();
	private ChromeCVElementImageDataProvider chromeCVElementImageDataProvider = new ChromeCVElementImageDataProvider();

	@Override
	public byte[] getImageData(RepositoryContext repositoryContext, JRGenericPrintElement element) throws Exception {
		JasperReportsContext jasperReportsContext = repositoryContext.getJasperReportsContext();
		if (chromeCVElementImageDataProvider.isEnabled(jasperReportsContext)) {
			if (log.isDebugEnabled()) {
				log.debug("using JR chrome");
			}
			return chromeCVElementImageDataProvider.getImageData(repositoryContext, element);
		}
		
		if (jrPhantomCVElementImageDataProvider.isEnabled()) {
			if (log.isDebugEnabled()) {
				log.debug("Using JR PhantomJS to produce custom visualization image data!");
			}

			return jrPhantomCVElementImageDataProvider.getImageData(repositoryContext, element);
		}

		String phantomjsExecutablePath = jasperReportsContext.getProperty(CVElementPhantomJSImageDataProvider.PROPERTY_PHANTOMJS_EXECUTABLE_PATH);
		if (phantomjsExecutablePath != null) {
			if (log.isDebugEnabled()) {
				log.debug("Using CVC PhantomJS to produce custom visualization image data!");
			}
			return cvElementPhantomJSImageProvider.getImageData(repositoryContext, element);
		}

		throw new JRRuntimeException("Chrome and/or PhantomJS not properly configured for server side rendering");
	}
}
