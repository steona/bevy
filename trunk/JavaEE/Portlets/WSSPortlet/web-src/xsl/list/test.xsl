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
                <ViewField>
                    <xsl:attribute name="Body">
                        <xsl:for-each select="ns:List/ns:Fields/ns:Field">
                            <xsl:if test="@Name='Body'">
                                <xsl:value-of select="@Name"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:attribute>

                    <xsl:for-each select="ns:View/ns:ViewFields/ns:FieldRef">

                        <xsl:variable name="fieldName" select="@Name"/>
                        <xsl:for-each select="/ns:GetListAndViewResult/ns:ListAndView/ns:List/ns:Fields/ns:Field">
                            <xsl:if test="@Name=$fieldName">
                                <xsl:attribute name="Type">
                                    <xsl:value-of select="@Type"/>
                                </xsl:attribute>
                            </xsl:if>
                        </xsl:for-each>
                        <xsl:attribute name="Name">
                            <xsl:value-of select="@Name"/>
                        </xsl:attribute>
                    </xsl:for-each>
                </ViewField>

            </DefaultView>
        </WssSingleList>
    </xsl:template>


</xsl:stylesheet>
        <!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
        <metaInformation>
        <scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="TestDiscussion.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="Xml.xml" srcSchemaRoot="GetListAndViewResult" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="ns:GetListAndViewResult/ns:ListAndView"><block path="WssSingleList/xsl:attribute/xsl:value&#x2D;of" x="282" y="40"/><block path="WssSingleList/xsl:attribute[1]/xsl:value&#x2D;of" x="322" y="60"/><block path="WssSingleList/xsl:attribute[2]/xsl:value&#x2D;of" x="282" y="80"/><block path="WssSingleList/xsl:attribute[3]/xsl:value&#x2D;of" x="322" y="100"/><block path="WssSingleList/xsl:attribute[4]/xsl:value&#x2D;of" x="282" y="120"/><block path="WssSingleList/xsl:attribute[5]/xsl:value&#x2D;of" x="322" y="140"/><block path="WssSingleList/xsl:attribute[6]/xsl:value&#x2D;of" x="282" y="160"/><block path="WssSingleList/xsl:attribute[7]/xsl:value&#x2D;of" x="322" y="180"/><block path="WssSingleList/DefaultView/xsl:for&#x2D;each" x="282" y="220"/><block path="WssSingleList/DefaultView/xsl:for&#x2D;each/ViewField/xsl:attribute/xsl:value&#x2D;of" x="322" y="240"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
        </metaInformation>
        -->