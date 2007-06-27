<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns="http://schemas.microsoft.com/sharepoint/soap/"
                exclude-result-prefixes="ns">
    <xsl:output method="html"/>


    <xsl:template match="ns:GetListAndViewResult/ns:ListAndView">
        <WssSingleList>
            <xsl:attribute name="ID">
                <xsl:value-of select="ns:List/@ID"/>
            </xsl:attribute>

            <xsl:attribute name="Title">
                <xsl:value-of select="ns:List/@Title"/>
            </xsl:attribute>

            <xsl:attribute name="Description">
                <xsl:value-of select="ns:List/@Description"/>
            </xsl:attribute>

            <xsl:attribute name="Name">
                <xsl:value-of select="ns:List/@Name"/>
            </xsl:attribute>

            <xsl:attribute name="CreationDateTime">
                <xsl:value-of select="ns:List/@Created"/>
            </xsl:attribute>

            <xsl:attribute name="LastModified">
                <xsl:value-of select="ns:List/@Modified"/>
            </xsl:attribute>

            <xsl:attribute name="ItemCount">
                <xsl:value-of select="ns:List/@ItemCount"/>
            </xsl:attribute>

            <xsl:attribute name="Author">
                <xsl:value-of select="ns:List/@Author"/>
            </xsl:attribute>


            <DefaultView>
                <xsl:for-each
                        select="ns:View/ns:ViewFields/ns:FieldRef">
                    <ViewField>
                        <xsl:attribute name="Name">
                            <xsl:value-of select="@Name"/>
                        </xsl:attribute>
                    </ViewField>
                </xsl:for-each>
            </DefaultView>
        </WssSingleList>
    </xsl:template>


</xsl:stylesheet>
