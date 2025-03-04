/* SPDX-License-Identifier: Apache 2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.commonservices.multitenant;

import org.odpi.openmetadata.commonservices.multitenant.ffdc.OMAGServerInstanceErrorCode;
import org.odpi.openmetadata.commonservices.repositoryhandler.RepositoryErrorHandler;
import org.odpi.openmetadata.commonservices.repositoryhandler.RepositoryHandler;
import org.odpi.openmetadata.metadatasecurity.server.OpenMetadataServerSecurityVerifier;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLog;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollection;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryConnector;
import org.odpi.openmetadata.commonservices.ffdc.exceptions.PropertyServerException;
import org.odpi.openmetadata.commonservices.multitenant.ffdc.exceptions.NewInstanceException;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;

import java.util.List;

/**
 * OMASServiceInstance caches references to OMRS objects for a specific server.
 * It is also responsible for registering itself in the instance map.
 */
public class OMASServiceInstance extends AuditableServerServiceInstance
{
    protected OMRSRepositoryConnector repositoryConnector;
    protected OMRSMetadataCollection  metadataCollection;
    protected OMRSRepositoryHelper    repositoryHelper;

    protected RepositoryHandler       repositoryHandler;
    protected RepositoryErrorHandler  errorHandler;

    protected List<String>            supportedZones;
    protected List<String>            defaultZones;


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
    public OMASServiceInstance(String                  serviceName,
                               OMRSRepositoryConnector repositoryConnector,
                               List<String>            supportedZones,
                               List<String>            defaultZones,
                               OMRSAuditLog            auditLog) throws NewInstanceException
    {
        super(null, serviceName, auditLog);

        final String methodName = "new ServiceInstance";

        if (repositoryConnector != null)
        {
            try
            {
                this.repositoryConnector = repositoryConnector;
                this.setServerName(repositoryConnector.getServerName());
                this.metadataCollection = repositoryConnector.getMetadataCollection();
                this.repositoryHelper = repositoryConnector.getRepositoryHelper();

                this.errorHandler = new RepositoryErrorHandler(serviceName, serverName);
                this.repositoryHandler = new RepositoryHandler(errorHandler, metadataCollection);
                this.supportedZones = supportedZones;
                this.defaultZones = defaultZones;
            }
            catch (Throwable error)
            {
                OMAGServerInstanceErrorCode errorCode    = OMAGServerInstanceErrorCode.OMRS_NOT_INITIALIZED;
                String                      errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(methodName);

                throw new NewInstanceException(errorCode.getHTTPErrorCode(),
                                               this.getClass().getName(),
                                               methodName,
                                               errorMessage,
                                               errorCode.getSystemAction(),
                                               errorCode.getUserAction());

            }
        }
        else
        {
            OMAGServerInstanceErrorCode errorCode    = OMAGServerInstanceErrorCode.OMRS_NOT_INITIALIZED;
            String                      errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(methodName);

            throw new NewInstanceException(errorCode.getHTTPErrorCode(),
                                           this.getClass().getName(),
                                           methodName,
                                           errorMessage,
                                           errorCode.getSystemAction(),
                                           errorCode.getUserAction());

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
    public OMASServiceInstance(String                  serviceName,
                               OMRSRepositoryConnector repositoryConnector,
                               OMRSAuditLog            auditLog) throws NewInstanceException
    {
        this( serviceName, repositoryConnector, null, null, auditLog);
    }


    /**
     * Return the server name. Used during OMAS initialization which is why the exception
     * is different.
     *
     * @return serverName name of the server for this instance
     * @throws NewInstanceException a problem occurred during initialization
     */
    public String getServerName() throws NewInstanceException
    {
        final String methodName = "getServerName";

        if (serverName != null)
        {
            return serverName;
        }
        else
        {
            OMAGServerInstanceErrorCode errorCode    = OMAGServerInstanceErrorCode.OMRS_NOT_AVAILABLE;
            String                      errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(methodName);

            throw new NewInstanceException(errorCode.getHTTPErrorCode(),
                                           this.getClass().getName(),
                                           methodName,
                                           errorMessage,
                                           errorCode.getSystemAction(),
                                           errorCode.getUserAction());
        }
    }


    /**
     * Validate that the repository services are ok for this instance.
     *
     * @param methodName calling method
     * @throws PropertyServerException problem with the repository services
     */
    protected void validateActiveRepository(String  methodName) throws PropertyServerException
    {
        if ((repositoryConnector == null) || (metadataCollection == null) || (! repositoryConnector.isActive()))
        {
            OMAGServerInstanceErrorCode errorCode    = OMAGServerInstanceErrorCode.OMRS_NOT_AVAILABLE;
            String                      errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(methodName);

            throw new PropertyServerException(errorCode.getHTTPErrorCode(),
                                              this.getClass().getName(),
                                              methodName,
                                              errorMessage,
                                              errorCode.getSystemAction(),
                                              errorCode.getUserAction());
        }
    }


    /**
     * Return the local metadata collection for this server.
     *
     * @return OMRSMetadataCollection object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    OMRSMetadataCollection getMetadataCollection() throws PropertyServerException
    {
        final String methodName = "getMetadataCollection";

        validateActiveRepository(methodName);

        return metadataCollection;
    }


    /**
     * Return the repository connector for this server.
     *
     * @return OMRSRepositoryConnector object
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    OMRSRepositoryConnector  getRepositoryConnector() throws PropertyServerException
    {
        final String methodName = "getRepositoryConnector";

        validateActiveRepository(methodName);

        return repositoryConnector;
    }


    /**
     * Return the repository helper for this server.  It provides many helpful
     * functions for managing .
     *
     * @return repository helper
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    OMRSRepositoryHelper getRepositoryHelper() throws PropertyServerException
    {
        final String methodName = "getRepositoryHelper";

        validateActiveRepository(methodName);

        return repositoryHelper;
    }


    /**
     * Return the repository handler providing an advanced API to the repository
     * services.
     *
     * @return repository handler
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    RepositoryHandler getRepositoryHandler() throws PropertyServerException
    {
        final String methodName = "getRepositoryHandler";

        validateActiveRepository(methodName);

        return repositoryHandler;
    }


    /**
     * Return the handler for managing errors from the repository services.
     *
     * @return repository error handler
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    RepositoryErrorHandler getErrorHandler() throws PropertyServerException
    {
        final String methodName = "getErrorHandler";

        validateActiveRepository(methodName);

        return errorHandler;
    }


    /**
     * Return the list of zones that this instance of the OMAS should support.
     *
     * @return list of zone names.
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    List<String> getSupportedZones() throws PropertyServerException
    {
        final String methodName = "getSupportedZones";

        validateActiveRepository(methodName);

        return supportedZones;
    }


    /**
     * Return the list of zones that this instance of the OMAS should set in any new Asset.
     *
     * @return list of zone names.
     * @throws PropertyServerException the instance has not been initialized successfully
     */
    List<String> getDefaultZones() throws PropertyServerException
    {
        final String methodName = "getDefaultZones";

        validateActiveRepository(methodName);

        return defaultZones;
    }
}
