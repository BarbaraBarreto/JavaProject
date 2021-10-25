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

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.export.GenericElementHandler;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public abstract class CVElementAbstractGenericHandler implements GenericElementHandler {

    @Override
    public boolean toExport(JRGenericPrintElement element) {
        return true;
    }

    protected static boolean isUrl(String location)
    {
        try
        {
            new URL(location);
        }
        catch (MalformedURLException e)
        {
            return false;
        }
        return true;
    }
}
