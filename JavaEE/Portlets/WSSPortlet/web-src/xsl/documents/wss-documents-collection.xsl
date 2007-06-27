<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns1="http://schemas.microsoft.com/sharepoint/soap/" exclude-result-prefixes="ns1">
    <xsl:output method="xml"/>
    <!-- This XSL will be run on getting the collection of lists from a WSS Site.
       It will transform the retrieved XML into an XML representation of how we would
       like the list to be represented in portal.

      Our representation will comply to its correspondingly named XSD file from which we can
      serialize and deserialize Java Objects.

    -->

    <xsl:template match="/">
        <WssListCollection>
            <xsl:for-each select="ns1:Lists/ns1:List">
                <!-- Select all the lists which have non empty DocTemplateUrl value
                 This is the set of all Documents. -->
                <xsl:if test="@DocTemplateUrl != ''">
                    <WssList>
                        <xsl:attribute name="ID">
                            <xsl:value-of select="@ID"/>
                        </xsl:attribute>
                        <xsl:attribute name="Title">
                            <xsl:value-of select="@Title"/>
                        </xsl:attribute>
                        <xsl:attribute name="Description">
                            <xsl:value-of select="@Description"/>
                        </xsl:attribute>
                        <xsl:attribute name="Created">
                            <xsl:value-of select="@Created"/>
                        </xsl:attribute>
                        <xsl:attribute name="Modified">
                            <xsl:value-of select="@Modified"/>
                        </xsl:attribute>
                        <xsl:attribute name="ItemCount">
                            <xsl:value-of select="@ItemCount"/>
                        </xsl:attribute>
                        <xsl:attribute name="Author">
                            <xsl:value-of select="@Author"/>
                        </xsl:attribute>
                    </WssList>
                </xsl:if>

            </xsl:for-each>
        </WssListCollection>
    </xsl:template>


</xsl:stylesheet>
