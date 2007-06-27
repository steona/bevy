<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns1="http://schemas.microsoft.com/sharepoint/soap/meetings/" exclude-result-prefixes="ns1">
    <xsl:output method="xml"/>
    <!-- This XSL will be run on getting the collection of lists from a WSS Site.
       It will transform the retrieved XML into an XML representation of how we would
       like the list to be represented in portal.

      Our representation will comply to its correspondingly named XSD file from which we can
      serialize and deserialize Java Objects.

    -->

    <xsl:template match="/">
        <WSSMeetingsCollection>
            <xsl:for-each select="ns1:MeetingWorkspaces/ns1:Workspace">
                <WSSMeetings>
                    <xsl:attribute name="Url">
                        <xsl:value-of select="@Url"/>
                    </xsl:attribute>
                    <xsl:attribute name="Title">
                        <xsl:value-of select="@Title"/>
                    </xsl:attribute>
                </WSSMeetings>

            </xsl:for-each>
        </WSSMeetingsCollection>
    </xsl:template>
</xsl:stylesheet>

        <!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
        <metaInformation>
        <scenarios ><scenario default="yes" name="meetingqw" userelativepaths="yes" externalpreview="no" url="meetings1.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="meetings1.xml" srcSchemaRoot="GetMeetingWorkspacesResult" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="ns:GetMeetingWorkspacesResult"><block path="WssMeetingsCollection/xsl:for&#x2D;each" x="223" y="40"/><block path="WssMeetingsCollection/xsl:for&#x2D;each/WssMeetings/xsl:attribute/xsl:value&#x2D;of" x="263" y="60"/><block path="WssMeetingsCollection/xsl:for&#x2D;each/WssMeetings/xsl:attribute[1]/xsl:value&#x2D;of" x="223" y="80"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
        </metaInformation>
        -->