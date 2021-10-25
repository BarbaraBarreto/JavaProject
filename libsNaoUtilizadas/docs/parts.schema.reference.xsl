<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:jr="http://jasperreports.sourceforge.net/jasperreports" 
	xmlns:c="http://jasperreports.sourceforge.net/jasperreports/parts">

<xsl:output method = "html" />
<xsl:param name="sf.net"/>
<xsl:param name="version"/>

<xsl:variable name="api.url">api/</xsl:variable>

<xsl:template match="/">
<html>
<head>
<title>JasperReports <xsl:value-of select="$version"/> - Report Parts Schema Reference</title>
<style type="text/css">
.title {
	font-family: Arial, Verdana, Helvetica, sans-serif;
	font-size: 28px;
	font-weight: normal;
}

.toc {
	font-family: Courier New, Courier, serif;
	font-size: 12px;
	font-weight: normal;
}

.name {
	font-family: Courier New, Courier, serif;
	font-size: 16px;
	font-weight: bold;
}

.label {
	font-family: Arial, Verdana, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	font-style: italic;
}

.description {
	font-family: Arial, Verdana, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: normal;
}

.value {
	font-family: Courier New, Courier, serif;
	font-size: 12px;
	font-weight: normal;
}

.element {
	font-family: Courier New, Courier, serif;
	font-size: 12px;
	font-weight: normal;
}

.attribute {
	font-family: Courier New, Courier, serif;
	font-size: 12px;
	font-weight: bold;
	text-decoration: none;
	color: #000000;
}

.copy {
	font-decoration: none;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 8pt;
	font-style: normal;
	color: #000000;
}

</style>
</head>
<body bgcolor="#FFFFFF">
<xsl:if test="$sf.net = 'true'">
<!-- Tracker -->
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-399158-5', 'sourceforge.net');
ga('send', 'pageview');
</script>
<!-- End Tracker Tag -->
</xsl:if>

<a name="top"/>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
  <tr>
    <td colspan="2" align="right">
<span class="element"><xsl:element name="a">
<xsl:attribute name="href">JasperReports-Ultimate-Guide-3.pdf</xsl:attribute>JasperReports Ultimate Guide</xsl:element> - <xsl:element name="a">
<xsl:attribute name="href">sample.reference.html</xsl:attribute>Sample Reference</xsl:element> - <xsl:element name="a">
<xsl:attribute name="href">schema.reference.html</xsl:attribute>Schema Reference</xsl:element> - <xsl:element name="a">
<xsl:attribute name="href">config.reference.html</xsl:attribute>Configuration Reference</xsl:element> - <xsl:element name="a">
<xsl:attribute name="href">http://community.jaspersoft.com/wiki/jasperreports-library-faqs</xsl:attribute>FAQ</xsl:element> - <xsl:element name="a">
<xsl:attribute name="href"><xsl:value-of select="$api.url"/>index.html</xsl:attribute>API (Javadoc)</xsl:element></span>
<br/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <hr size="1"/>
    </td>
  </tr>
  <tr valign="middle">
    <td nowrap="true">
<span class="title">JasperReports - Report Parts Schema Reference (version <xsl:value-of select="$version"/>)</span>
    </td>
    <td align="right">
<img src="resources/jasperreports.svg" border="0"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <hr size="1"/>
    </td>
  </tr>
</table>

<br/>

<span class="description">This document describes the JRXML structure of the built-in report parts for the JasperReports Library.</span>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td style="width: 20px;"><br/></td>
    <td><br/></td>
  </tr>
  <tr>
    <td colspan="2">
      <span class="label"><br/>Other JasperReports Library Schemas</span>
    </td>
  </tr>
  <tr>
    <td></td>
    <td>
      <span class="toc"><a href="schema.reference.html">Report Schema Reference</a></span>
    </td>
  </tr>
  <tr>
    <td></td>
    <td>
      <span class="toc"><a href="components.schema.reference.html">Components Schema Reference</a></span>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <span class="label"><br/>Report Parts Schema Reference</span>
    </td>
  </tr>
</table>

<!-- a list of element links is not necessary in case of a single-element schema -->

<table width="100%" cellspacing="0" cellpadding="0" border="0">
  <tr>
    <td style="width: 20px;"></td>
    <td style="width: 20px;"></td>
    <td style="width: 20px;"></td>
    <td style="width: 20px;"></td>
    <td></td>
  </tr>
  <xsl:apply-templates select="//xsd:element[@name]"/>
  
  <tr>
    <td colspan="5"><br/><br/></td>
  </tr>
  <tr>
    <td colspan="5"><hr size="1"/></td>
  </tr>
  <tr>
    <td colspan="5" align="center">
      <span class="copy">&#169; 2001-<script language="javascript">document.write((new Date()).getFullYear())</script> TIBCO Software Inc. <a href="http://www.jaspersoft.com" target="_blank" class="copy">www.jaspersoft.com</a></span>
    </td>
  </tr>
</table>

</body>
</html>
</xsl:template>


<xsl:template match="//xsd:element[@name]">
  <tr>
    <td colspan="5" align="right"><br/>
      <xsl:element name="a"><xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute></xsl:element>
      <a href="#top" class="toc">top</a>
    </td>
  </tr>
  <tr>
    <td colspan="5"><hr size="1"/></td>
  </tr>
  <tr>
    <td colspan="5"><span class="name"><xsl:value-of select="@name"/></span></td>
  </tr>
   <xsl:apply-templates select="xsd:complexType"/>
</xsl:template>

<xsl:template match="xsd:complexType">
  <xsl:apply-templates select="xsd:complexContent"/>
  <xsl:apply-templates select="xsd:sequence"/>
  <xsl:if test="xsd:attribute">
  <tr>
    <td></td>
	<td colspan="4"><span class="label"><br/>Attributes</span></td>
  </tr>
  <xsl:apply-templates select="xsd:attribute"/>
  </xsl:if>
</xsl:template>

<xsl:template match="xsd:complexContent">
  <xsl:apply-templates select="xsd:extension"/>
</xsl:template>


<xsl:template match="xsd:extension">
  <xsl:if test="@base">
    <tr>
      <td></td>
	  <td colspan="4"><span class="label"><br/>Parent type: </span><xsl:element name="a"><xsl:attribute name="href"><xsl:if test='starts-with(@base,"jr:")'>schema.reference.html</xsl:if>#<xsl:value-of select='substring-after(@base,":")'/></xsl:attribute><xsl:attribute name="target">_blank</xsl:attribute><span class="toc"><xsl:value-of select='substring-after(@base,":")'/></span></xsl:element>.</td>
    </tr>
  </xsl:if>
  <tr>
    <td></td>
    <td colspan="4"><xsl:apply-templates select="xsd:annotation/xsd:documentation"/></td>
  </tr>
  <xsl:apply-templates select="xsd:sequence"/>
  <xsl:if test="xsd:attribute">
  <tr>
    <td></td>
	<td colspan="4"><span class="label"><br/>Attributes</span></td>
  </tr>
  <xsl:apply-templates select="xsd:attribute"/>
  </xsl:if>
</xsl:template>

<xsl:template match="xsd:sequence">
  <tr>
    <td></td>
	<td colspan="4"><span class="label"><br/>Contains</span></td>
  </tr>
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="xsd:annotation/xsd:documentation">
  <xsl:apply-templates/>
</xsl:template>


<xsl:template match="xsd:*" mode="copy">
  <span class="description"><xsl:copy-of select="."/></span>
</xsl:template>


<xsl:template match="text()">
  <span class="description"><xsl:value-of select="."/></span>
</xsl:template>


<xsl:template match="xsd:p">
  <p><xsl:apply-templates/></p>
</xsl:template>


<xsl:template match="xsd:p/text()">
  <span class="description"><xsl:value-of select="." disable-output-escaping="yes" /></span>
</xsl:template>


<xsl:template match="xsd:br">
  <br/>
</xsl:template>


<xsl:template match="xsd:a">
  <span class="element"><xsl:element name="a"><xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute><xsl:value-of select="."/></xsl:element></span>
</xsl:template>


<xsl:template match="xsd:elem">
  <span class="element"><xsl:element name="a"><xsl:attribute name="href">#<xsl:value-of select="."/></xsl:attribute>&lt;<xsl:value-of select="."/>&gt;</xsl:element></span>
</xsl:template>


<xsl:template match="xsd:ul">
  <xsl:element name="ul"><xsl:apply-templates/></xsl:element>
</xsl:template>


<xsl:template match="xsd:li">
  <xsl:element name="li"><xsl:apply-templates/></xsl:element>
</xsl:template>


<xsl:template match="xsd:dl">
  <table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr valign="top">
  	  <td style="width: 20px;"></td>
  	  <td><span class="element"><xsl:element name="dl"><xsl:apply-templates/></xsl:element></span></td>
	</tr>
  </table>
</xsl:template>


<xsl:template match="xsd:dd">
  <span class="description"><xsl:element name="dd"><xsl:apply-templates/></xsl:element></span>
</xsl:template>


<xsl:template match="xsd:dt">
  <span class="value"><xsl:element name="dt"><xsl:value-of select="."/></xsl:element></span>
</xsl:template>


<xsl:template match="xsd:attribute">
  <tr>
    <td colspan="2"></td>
	<td colspan="3"><br/><span class="attribute"><xsl:element name="a">
	<xsl:choose>
	  <xsl:when test="../../../../@name">
	  	<xsl:attribute name="name"><xsl:value-of select="concat(../../../../@name,'_', @name)"/>
	  	</xsl:attribute><xsl:attribute name="href">#<xsl:value-of select="concat(../../../../@name,'_', @name)"/></xsl:attribute>
	  </xsl:when>
	  <xsl:when test="../../../@name">
	  	<xsl:attribute name="name"><xsl:value-of select="concat(../../../@name,'_', @name)"/></xsl:attribute>
	  	<xsl:attribute name="href">#<xsl:value-of select="concat(../../../@name,'_', @name)"/></xsl:attribute>
	  </xsl:when>
	  <xsl:otherwise>
	  	<xsl:attribute name="name"><xsl:value-of select="concat(../../@name,'_', @name)"/></xsl:attribute>
	  	<xsl:attribute name="href">#<xsl:value-of select="concat(../../@name,'_', @name)"/></xsl:attribute>
	  </xsl:otherwise>
    </xsl:choose>
	<xsl:attribute name="class">attribute</xsl:attribute><xsl:value-of select="@name"/></xsl:element></span></td>
  </tr>
  <tr>
    <td colspan="3"></td>
	<td colspan="2"><xsl:apply-templates select="xsd:annotation/xsd:documentation"/></td>
  </tr>
  <tr>
    <td colspan="3"></td>
    <td colspan="2"><span class="label">Type: </span><span class="description"><xsl:value-of select="@type"/></span></td>
  </tr>
  <tr>
    <td colspan="3"></td>
    <td colspan="2"><span class="label">Use: </span><span class="description"><xsl:value-of select="@use"/></span></td>
  </tr>
  <xsl:if test="xsd:simpleType/xsd:restriction/xsd:enumeration">
  <tr>
    <td colspan="3"></td>
    <td colspan="2"><span class="label">Values </span></td>
  </tr>
  <tr>
    <td colspan="4"></td>
    <td>
      <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <xsl:apply-templates select="xsd:simpleType/xsd:restriction/xsd:enumeration"/>
      </table>
	</td>
  </tr>
  </xsl:if>
  <xsl:if test="@type='jr:basicEvaluationTime'">
  <tr>
    <td colspan="3"></td>
    <td colspan="2"><span class="label">Values </span></td>
  </tr>
  <tr>
    <td colspan="4"></td>
    <td>
      <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <xsl:apply-templates select="../../../xsd:simpleType[@name='basicEvaluationTime']"/>
      </table>
	</td>
  </tr>
  </xsl:if>
  <xsl:if test="@type='jr:complexEvaluationTime'">
  <tr>
    <td colspan="3"></td>
    <td colspan="2"><span class="label">Values </span></td>
  </tr>
  <tr>
    <td colspan="4"></td>
    <td>
      <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <xsl:apply-templates select="../../../xsd:simpleType[@name='complexEvaluationTime']"/>
      </table>
	</td>
  </tr>
  </xsl:if>
  <xsl:if test="@default">
  <tr>
    <td colspan="3"></td>
    <td colspan="2"><span class="label">Default: </span><span class="description"><xsl:value-of select="@default"/></span></td>
  </tr>
  </xsl:if>
  <!--
  <tr>
    <td colspan="5"/>
  </tr>
  -->
</xsl:template>


<xsl:template match="xsd:element">
  <tr>
  	<td colspan="2"></td>
    <td colspan="3"><xsl:element name="a">
     <xsl:choose>
      <xsl:when test="@name"><xsl:attribute name="href">#<xsl:choose>
	    <xsl:when test="../../../../@name"><xsl:value-of select="concat(../../../../@name,'_', @name)"/></xsl:when>
	    <xsl:when test="../../../@name"><xsl:value-of select="concat(../../../@name,'_', @name)"/></xsl:when>
	    <xsl:otherwise><xsl:value-of select="@name"/></xsl:otherwise>
       </xsl:choose>
      </xsl:attribute><span class="element"><xsl:value-of select="@name"/></span></xsl:when>
      <!--xsl:otherwise><xsl:attribute name="href">#<xsl:value-of select='substring-after(@ref,":")'/></xsl:attribute><span class="element"><xsl:value-of select='substring-after(@ref,":")'/></span></xsl:otherwise-->
      <xsl:otherwise>
      <xsl:attribute name="href"><xsl:if test='starts-with(@ref,"jr:")'>schema.reference.html</xsl:if>#<xsl:value-of select='substring-after(@ref,":")'/></xsl:attribute>
      <xsl:if test='starts-with(@ref,"jr:")'><xsl:attribute name="target">_blank</xsl:attribute></xsl:if>
      <span class="element"><xsl:value-of select='substring-after(@ref,":")'/></span></xsl:otherwise>
     </xsl:choose>
    </xsl:element>
    <xsl:choose><xsl:when test="@maxOccurs='unbounded' or ../@maxOccurs='unbounded'"><span class="description">*</span></xsl:when><xsl:when test="@maxOccurs='1' or ../@maxOccurs='1'"><span class="description">?</span></xsl:when></xsl:choose></td>
  </tr>
</xsl:template>


<xsl:template match="xsd:choice">
  <tr>
  	<td colspan="2"></td>
    <td colspan="3">
    <span class="description">( </span>
    <xsl:for-each select="./xsd:element">
    <xsl:element name="a">
     <xsl:choose>
      <xsl:when test="@name"><xsl:attribute name="href">#<xsl:choose>
	    <xsl:when test="../../../../@name"><xsl:value-of select="concat(../../../../@name,'_', @name)"/></xsl:when>
	    <xsl:when test="../../../@name"><xsl:value-of select="concat(../../../@name,'_', @name)"/></xsl:when>
	    <xsl:otherwise><xsl:value-of select="@name"/></xsl:otherwise>
       </xsl:choose>
      </xsl:attribute><span class="element"><xsl:value-of select="@name"/></span></xsl:when>
      <xsl:otherwise><xsl:attribute name="href"><xsl:if test='starts-with(@ref,"jr:")'>schema.reference.html</xsl:if>#<xsl:value-of select='substring-after(@ref,":")'/></xsl:attribute>
        <xsl:if test='starts-with(@ref,"jr:")'><xsl:attribute name="target">_blank</xsl:attribute></xsl:if><span class="element"><xsl:value-of select='substring-after(@ref,":")'/></span></xsl:otherwise>
     </xsl:choose>
    </xsl:element>
    <xsl:choose><xsl:when test="@maxOccurs='unbounded'"><span class="description">*</span></xsl:when><xsl:when test="@maxOccurs='1' or ../@maxOccurs='1'"><span class="description">?</span></xsl:when></xsl:choose>
    <xsl:if test="@name">
    <span class="element">
	<xsl:choose>
	  <xsl:when test="../../../../@name"> (in <xsl:value-of select="../../../../@name"/>)</xsl:when>
	  <xsl:when test="../../../@name"> (in <xsl:value-of select="../../../@name"/>)</xsl:when>
    </xsl:choose>
    </span>
    </xsl:if>  
    <xsl:if test="position() &lt; count(../xsd:element)"><span class="description">
     | </span></xsl:if>
    </xsl:for-each><span class="description"> )<xsl:choose><xsl:when test="@maxOccurs='unbounded' or ../@maxOccurs='unbounded'"><span class="description">*</span></xsl:when><xsl:when test="@maxOccurs='1' or ../@maxOccurs='1'"><span class="description">?</span></xsl:when></xsl:choose>
    </span>
    </td>
  </tr>
</xsl:template>


<xsl:template match="xsd:simpleType/xsd:restriction/xsd:enumeration">
  <xsl:apply-templates select="xsd:restriction/xsd:enumeration"/>
</xsl:template>


<xsl:template match="xsd:simpleType[@name='basicEvaluationTime']">
  <xsl:apply-templates select="xsd:restriction/xsd:enumeration"/>
</xsl:template>


<xsl:template match="xsd:simpleType[@name='complexEvaluationTime']">
  <xsl:apply-templates select="xsd:restriction/xsd:enumeration"/>
</xsl:template>


<xsl:template match="xsd:restriction/xsd:enumeration">
  <tr valign="top">
    <td style="width: 10px;" nowrap="nowrap"><span class="value"><xsl:value-of select="@value"/></span></td>
    <td style="width: 10px;"></td>
    <td><xsl:if test="xsd:annotation/xsd:documentation and xsd:annotation/xsd:documentation[.!='']"><span class="description"><xsl:value-of select="xsd:annotation/xsd:documentation"/></span></xsl:if></td>
  </tr>
</xsl:template>

</xsl:stylesheet>
