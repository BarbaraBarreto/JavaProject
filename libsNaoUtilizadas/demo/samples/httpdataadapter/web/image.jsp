<%--
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
--%><%@ 

page errorPage="error.jsp" %><%@ 
page import="java.io.*" %><%

	String imageName = request.getParameter("name");
	
	if (imageName != null)
	{
		response.setContentType("image/jpeg");

		InputStream is = new FileInputStream(application.getRealPath("WEB-INF/images/" + imageName));
		OutputStream os = response.getOutputStream();
		
		int ln = 0;
		byte[] bytes = new byte[1000];
		
		while((ln = is.read(bytes)) > 0)
		{
			os.write(bytes, 0, ln);
		}
	}
%>