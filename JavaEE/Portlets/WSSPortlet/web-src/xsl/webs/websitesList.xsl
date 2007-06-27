<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns1="http://schemas.microsoft.com/sharepoint/soap/" exclude-result-prefixes="ns1">
    <xsl:output method="html"/>
    <xsl:template match="/ns1:Webs">
        <WSSSiteList>
            <xsl:for-each select="ns1:Web">
                <Web>
                    <xsl:attribute name="Title">
                        <xsl:value-of select="@Title"/>
                    </xsl:attribute>
                    <xsl:attribute name="Url">
                        <xsl:value-of select="@Url"/>
                    </xsl:attribute>
                </Web>
            </xsl:for-each>
        </WSSSiteList>
    </xsl:template>
</xsl:stylesheet>
