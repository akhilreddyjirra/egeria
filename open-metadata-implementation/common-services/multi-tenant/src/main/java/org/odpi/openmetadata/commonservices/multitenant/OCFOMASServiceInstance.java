/* SPDX-License-Identifier: Apache 2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.commonservices.multitenant;

import org.odpi.openmetadata.commonservices.ffdc.exceptions.PropertyServerException;
import org.odpi.openmetadata.commonservices.multitenant.ffdc.exceptions.NewInstanceException;
import org.odpi.openmetadata.commonservices.ocf.metadatamanagement.handlers.*;
import org.odpi.openmetadata.metadatasecurity.server.OpenMetadataServerSecurityVerifier;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLog;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryConnector;

import java.util.List;

/**
 * OCFOMASServiceInstance provides an instance base class for Open Metadata Access Services (OMASs)
 * that are built around the Open Connector Framework (OCF).
 */
public class OCFOMASServiceInstance extends OMASServiceInstance
{
    protected AssetHandler              assetHandler;
    protected CertificationHandler      certificationHandler;
    protected CommentHandler            commentHandler;
    protected ConnectionHandler         connectionHandler;
    protected ConnectorTypeHandler      connectorTypeHandler;
    protected EndpointHandler           endpointHandler;
    protected ExternalIdentifierHandler externalIdentifierHandler;
    protected ExternalReferenceHandler  externalReferenceHandler;
    protected InformalTagHandler        informalTagHandler;
    protected LicenseHandler            licenseHandler;
    protected LikeHandler               likeHandler;
    protected LocationHandler           locationHandler;
    protected MeaningHandler            meaningHandler;
    protected NoteHandler               noteHandler;
    protected NoteLogHandler            noteLogHandler;
    protected RatingHandler             ratingHandler;
    protected RelatedMediaHandler       relatedMediaHandler;
    protected SchemaTypeHandler         schemaTypeHandler;

    /**
     * Set up the local repository connector that will service the REST Calls.
     *
     * @param serviceName name of this service
     * @param repositoryConnector link to the repository responsible for servicing the REST calls.
     * @param supportedZones list of zones that DiscoveryEngine is allowed to serve Assets from.
     * @param defaultZones list of zones that DiscoveryEngine should set in all new Assets.
     * @param auditLog logging destination
     * @throws NewInstanceException a problem occurred during initialization
     */
    public OCFOMASServiceInstance(String                  serviceName,
                                  OMRSRepositoryConnector repositoryConnector,
                                  List<String>            supportedZones,
                                  List<String>            defaultZones,
                                  OMRSAuditLog            auditLog) throws NewInstanceException
    {
        super(serviceName, repositoryConnector, supportedZones, defaultZones, auditLog);

        this.certificationHandler      = new CertificationHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.commentHandler            = new CommentHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.connectionHandler         = new ConnectionHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.connectorTypeHandler      = new ConnectorTypeHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.endpointHandler           = new EndpointHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.externalIdentifierHandler = new ExternalIdentifierHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.externalReferenceHandler  = new ExternalReferenceHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.informalTagHandler        = new InformalTagHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.licenseHandler            = new LicenseHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.likeHandler               = new LikeHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.locationHandler           = new LocationHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.meaningHandler            = new MeaningHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.noteHandler               = new NoteHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.noteLogHandler            = new NoteLogHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.ratingHandler             = new RatingHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.relatedMediaHandler       = new RelatedMediaHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);
        this.schemaTypeHandler         = new SchemaTypeHandler(serviceName, serverName, invalidParameterHandler, repositoryHandler, repositoryHelper);

        this.assetHandler              = new AssetHandler(serviceName,
                                                          serverName,
                                                          invalidParameterHandler,
                                                          repositoryHandler,
                                                          repositoryHelper,
                                                          certificationHandler,
                                                          commentHandler,
                                                          connectionHandler,
                                                          externalIdentifierHandler,
                                                          externalReferenceHandler,
                                                          informalTagHandler,
                                                          licenseHandler,
                                                          likeHandler,
                                                          locationHandler,
                                                          noteLogHandler,
                                                          ratingHandler,
                                                          relatedMediaHandler,
                                                          schemaTypeHandler,
                                                          supportedZones,
                                                          defaultZones);


        if (securityVerifier != null)
        {
            assetHandler.setSecurityVerifier(securityVerifier);
            connectionHandler.setSecurityVerifier(securityVerifier);
        }
    }


    /**
     * Set up the local repository connector that will service the REST Calls.
     *
     * @param serviceName name of this service
     * @param repositoryConnector link to the repository responsible for servicing the REST calls.
     * @param auditLog logging destination
     * @throws NewInstanceException a problem occurred during initialization
     */
    public OCFOMASServiceInstance(String                  serviceName,
                                  OMRSRepositoryConnector repositoryConnector,
                                  OMRSAuditLog            auditLog) throws NewInstanceException
    {
        this(serviceName, repositoryConnector, null, null, auditLog);
    }


    /**
     * Return the handler for managing asset objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    AssetHandler getAssetHandler() throws PropertyServerException
    {
        final String methodName = "getAssetHandler";

        validateActiveRepository(methodName);

        return assetHandler;
    }


    /**
     * Return the handler for managing certification objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    CertificationHandler getCertificationHandler() throws PropertyServerException
    {
        final String methodName = "getCertificationHandler";

        validateActiveRepository(methodName);

        return certificationHandler;
    }


    /**
     * Return the handler for managing comment objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    CommentHandler getCommentHandler() throws PropertyServerException
    {
        final String methodName = "getCommentHandler";

        validateActiveRepository(methodName);

        return commentHandler;
    }


    /**
     * Return the handler for managing connection objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    ConnectionHandler getConnectionHandler() throws PropertyServerException
    {
        final String methodName = "getConnectionHandler";

        validateActiveRepository(methodName);

        return connectionHandler;
    }


    /**
     * Return the handler for managing connector type objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    ConnectorTypeHandler getConnectorTypeHandler() throws PropertyServerException
    {
        final String methodName = "getConnectorTypeHandler";

        validateActiveRepository(methodName);

        return connectorTypeHandler;
    }


    /**
     * Return the handler for managing endpoint objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    EndpointHandler getEndpointHandler() throws PropertyServerException
    {
        final String methodName = "getEndpointHandler";

        validateActiveRepository(methodName);

        return endpointHandler;
    }


    /**
     * Return the handler for managing external identifier objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    ExternalIdentifierHandler getExternalIdentifierHandler() throws PropertyServerException
    {
        final String methodName = "getExternalIdentifierHandler";

        validateActiveRepository(methodName);

        return externalIdentifierHandler;
    }


    /**
     * Return the handler for managing external reference objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    ExternalReferenceHandler getExternalReferenceHandler() throws PropertyServerException
    {
        final String methodName = "getExternalReferenceHandler";

        validateActiveRepository(methodName);

        return externalReferenceHandler;
    }


    /**
     * Return the handler for managing informal tag objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    InformalTagHandler getInformalTagHandler() throws PropertyServerException
    {
        final String methodName = "getInformalTagHandler";

        validateActiveRepository(methodName);

        return informalTagHandler;
    }


    /**
     * Return the handler for managing license objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    LicenseHandler getLicenseHandler() throws PropertyServerException
    {
        final String methodName = "getLicenseHandler";

        validateActiveRepository(methodName);

        return licenseHandler;
    }


    /**
     * Return the handler for managing like objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    LikeHandler getLikeHandler() throws PropertyServerException
    {
        final String methodName = "getLikeHandler";

        validateActiveRepository(methodName);

        return likeHandler;
    }


    /**
     * Return the handler for managing location objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    LocationHandler getLocationHandler() throws PropertyServerException
    {
        final String methodName = "getLocationHandler";

        validateActiveRepository(methodName);

        return locationHandler;
    }


    /**
     * Return the handler for managing meaning objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    MeaningHandler getMeaningHandler() throws PropertyServerException
    {
        final String methodName = "getMeaningHandler";

        validateActiveRepository(methodName);

        return meaningHandler;
    }


    /**
     * Return the handler for managing note objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    NoteHandler getNoteHandler() throws PropertyServerException
    {
        final String methodName = "getNoteHandler";

        validateActiveRepository(methodName);

        return noteHandler;
    }


    /**
     * Return the handler for managing note log objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    NoteLogHandler getNoteLogHandler() throws PropertyServerException
    {
        final String methodName = "getNoteLogHandler";

        validateActiveRepository(methodName);

        return noteLogHandler;
    }


    /**
     * Return the handler for managing rating objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    RatingHandler getRatingHandler() throws PropertyServerException
    {
        final String methodName = "getRatingHandler";

        validateActiveRepository(methodName);

        return ratingHandler;
    }


    /**
     * Return the handler for managing related media objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    RelatedMediaHandler getRelatedMediaHandler() throws PropertyServerException
    {
        final String methodName = "getRelatedMediaHandler";

        validateActiveRepository(methodName);

        return relatedMediaHandler;
    }


    /**
     * Return the handler for managing related media objects.
     *
     * @return  handler object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    SchemaTypeHandler getSchemaTypeHandler() throws PropertyServerException
    {
        final String methodName = "getSchemaTypeHandler";

        validateActiveRepository(methodName);

        return schemaTypeHandler;
    }

}
