<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns1="http://schemas.microsoft.com/sharepoint/soap/"
                exclude-result-prefixes="ns1">
    <xsl:output method="html"/>


    <xsl:template match="/ns1:ListAndView">
        <WssSingleList>
            <xsl:attribute name="ID">
                <xsl:value-of select="ns1:List/@ID"/>
            </xsl:attribute>

            <xsl:attribute name="Title">
                <xsl:value-of select="ns1:List/@Title"/>
            </xsl:attribute>

            <xsl:attribute name="RootFolder">
                <xsl:value-of select="ns1:List/@RootFolder"/>
            </xsl:attribute>

            <xsl:attribute name="Description">
                <xsl:value-of select="ns1:List/@Description"/>
            </xsl:attribute>

            <xsl:attribute name="Name">
                <xsl:value-of select="ns1:List/@Name"/>
            </xsl:attribute>

            <xsl:attribute name="CreationDateTime">
                <xsl:value-of select="ns1:List/@Created"/>
            </xsl:attribute>

            <xsl:attribute name="LastModified">
                <xsl:value-of select="ns1:List/@Modified"/>
            </xsl:attribute>

            <xsl:attribute name="ItemCount">
                <xsl:value-of select="ns1:List/@ItemCount"/>
            </xsl:attribute>

            <xsl:attribute name="Author">
                <xsl:value-of select="ns1:List/@Author"/>
            </xsl:attribute>


            <DefaultView>
                <xsl:for-each
                        select="ns1:View/ns1:ViewFields/ns1:FieldRef">
                    <ViewField>
                        <xsl:variable name="fieldName" select="@Name"/>
                        <xsl:for-each select="/ns1:ListAndView/ns1:List/ns1:Fields/ns1:Field">
                            <xsl:if test="@Name=$fieldName">
                                <xsl:attribute name="Type">
                                    <xsl:value-of select="@Type"/>
                                </xsl:attribute>
                                <xsl:attribute name="DisplayName">
                                    <xsl:value-of select="@DisplayName"/>
                                </xsl:attribute>
                            </xsl:if>
                        </xsl:for-each>
                        <xsl:attribute name="Name">
                            <xsl:value-of select="@Name"/>
                        </xsl:attribute>
                    </ViewField>
                </xsl:for-each>
            </DefaultView>
        </WssSingleList>
    </xsl:template>


</xsl:stylesheet>
        <!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
        <metaInformation>
        <scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="file:///i:/Xml.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="Xml.xml" srcSchemaRoot="GetListAndViewResult" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="ns:GetListAndViewResult/ns:ListAndView"><block path="WssSingleList/xsl:attribute/xsl:value&#x2D;of" x="282" y="40"/><block path="WssSingleList/xsl:attribute[1]/xsl:value&#x2D;of" x="322" y="60"/><block path="WssSingleList/xsl:attribute[2]/xsl:value&#x2D;of" x="282" y="80"/><block path="WssSingleList/xsl:attribute[3]/xsl:value&#x2D;of" x="322" y="100"/><block path="WssSingleList/xsl:attribute[4]/xsl:value&#x2D;of" x="282" y="120"/><block path="WssSingleList/xsl:attribute[5]/xsl:value&#x2D;of" x="322" y="140"/><block path="WssSingleList/xsl:attribute[6]/xsl:value&#x2D;of" x="282" y="160"/><block path="WssSingleList/xsl:attribute[7]/xsl:value&#x2D;of" x="322" y="180"/><block path="WssSingleList/DefaultView/xsl:for&#x2D;each" x="282" y="220"/><block path="WssSingleList/DefaultView/xsl:for&#x2D;each/ViewField/xsl:attribute/xsl:value&#x2D;of" x="322" y="240"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
        </metaInformation>
        -->