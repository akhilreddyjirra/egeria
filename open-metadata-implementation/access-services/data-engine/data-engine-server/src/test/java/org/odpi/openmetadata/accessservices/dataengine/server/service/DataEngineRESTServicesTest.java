/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.accessservices.dataengine.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.odpi.openmetadata.accessservices.dataengine.model.LineageMapping;
import org.odpi.openmetadata.accessservices.dataengine.model.PortAlias;
import org.odpi.openmetadata.accessservices.dataengine.model.PortImplementation;
import org.odpi.openmetadata.accessservices.dataengine.model.PortType;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.odpi.openmetadata.accessservices.dataengine.model.SchemaType;
import org.odpi.openmetadata.accessservices.dataengine.model.SoftwareServerCapability;
import org.odpi.openmetadata.accessservices.dataengine.rest.LineageMappingsRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.PortAliasRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.PortImplementationRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.PortListRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.ProcessesRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.SchemaTypeRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.SoftwareServerCapabilityRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.server.admin.DataEngineInstanceHandler;
import org.odpi.openmetadata.accessservices.dataengine.server.handlers.DataEngineSchemaTypeHandler;
import org.odpi.openmetadata.accessservices.dataengine.server.handlers.PortHandler;
import org.odpi.openmetadata.accessservices.dataengine.server.handlers.ProcessHandler;
import org.odpi.openmetadata.accessservices.dataengine.server.handlers.SoftwareServerRegistrationHandler;
import org.odpi.openmetadata.commonservices.ffdc.RESTExceptionHandler;
import org.odpi.openmetadata.commonservices.ffdc.rest.GUIDListResponse;
import org.odpi.openmetadata.commonservices.ffdc.rest.GUIDResponse;
import org.odpi.openmetadata.commonservices.ffdc.rest.VoidResponse;
import org.odpi.openmetadata.frameworks.connectors.ffdc.InvalidParameterException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.UserNotAuthorizedException;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.OwnerType;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.odpi.openmetadata.accessservices.dataengine.server.util.MockedExceptionUtil.mockException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class DataEngineRESTServicesTest {

    private static final String SERVER_NAME = "server";
    private static final String USER = "user";
    private static final String QUALIFIED_NAME = "qualifiedName";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "desc";
    private static final String TYPE = "type";
    private static final String VERSION = "version";
    private static final String PATCH_LEVEL = "patchLevel";
    private static final String SOURCE = "source";
    private static final String GUID = "guid";
    private static final String AUTHOR = "author";
    private static final String USAGE = "usage";
    private static final String ENCODING_STANDARD = "encodingStandard";
    private static final String VERSION_NUMBER = "versionNumber";
    private static final String DELEGATED_QUALIFIED_NAME = "delegatedQualifiedName";
    private static final String LATEST_CHANGE = "latestChange";
    private static final String FORMULA = "formula";
    private static final String OWNER = "OWNER";
    private static final String SOURCE_QUALIFIED_NAME = "source";
    private static final String TARGET_QUALIFIED_NAME = "target";
    private static final String PROCESS_GUID = "processGuid";

    @Mock
    RESTExceptionHandler restExceptionHandler;

    @Mock
    DataEngineInstanceHandler instanceHandler;

    @InjectMocks
    private DataEngineRESTServices dataEngineRESTServices;

    @Mock
    private SoftwareServerRegistrationHandler softwareServerRegistrationHandler;

    @Mock
    private DataEngineSchemaTypeHandler dataEngineSchemaTypeHandler;

    @Mock
    private PortHandler portHandler;

    @Mock
    private ProcessHandler processHandler;

    @BeforeEach
    void before() {
        MockitoAnnotations.initMocks(this);

        Field instanceHandlerField = ReflectionUtils.findField(DataEngineRESTServices.class, "instanceHandler");
        instanceHandlerField.setAccessible(true);
        ReflectionUtils.setField(instanceHandlerField, dataEngineRESTServices, instanceHandler);
        instanceHandlerField.setAccessible(false);

        Field restExceptionHandlerField = ReflectionUtils.findField(DataEngineRESTServices.class,
                "restExceptionHandler");
        restExceptionHandlerField.setAccessible(true);
        ReflectionUtils.setField(restExceptionHandlerField, dataEngineRESTServices, restExceptionHandler);
        restExceptionHandlerField.setAccessible(false);

    }

    @Test
    void createSoftwareServer() throws InvalidParameterException, PropertyServerException, UserNotAuthorizedException {
        mockRegistrationHandler("createSoftwareServer");

        when(softwareServerRegistrationHandler.createSoftwareServerCapability(USER, QUALIFIED_NAME, NAME, DESCRIPTION,
                TYPE, VERSION, PATCH_LEVEL, SOURCE)).thenReturn(GUID);

        SoftwareServerCapabilityRequestBody requestBody = mockSoftwareServerCapabilityRequestBody();

        GUIDResponse response = dataEngineRESTServices.createSoftwareServer(SERVER_NAME, USER, requestBody);
        assertEquals(GUID, response.getGUID());

    }

    @Test
    void createSoftwareServer_ResponseWithCapturedInvalidParameterException() throws InvalidParameterException,
                                                                                     PropertyServerException,
                                                                                     UserNotAuthorizedException,
                                                                                     InvocationTargetException,
                                                                                     NoSuchMethodException,
                                                                                     InstantiationException,
                                                                                     IllegalAccessException {

        String methodName = "createSoftwareServer";
        mockRegistrationHandler(methodName);

        InvalidParameterException mockedException = mockException(InvalidParameterException.class,
                methodName);
        when(softwareServerRegistrationHandler.createSoftwareServerCapability(USER, QUALIFIED_NAME, NAME, DESCRIPTION,
                TYPE, VERSION, PATCH_LEVEL, SOURCE)).thenThrow(mockedException);

        SoftwareServerCapabilityRequestBody requestBody = mockSoftwareServerCapabilityRequestBody();

        GUIDResponse response = dataEngineRESTServices.createSoftwareServer(SERVER_NAME, USER, requestBody);

        verify(restExceptionHandler, times(1)).captureInvalidParameterException(response, mockedException);
    }

    @Test
    void createSoftwareServer_ResponseWithCapturedUserNotAuthorizedException() throws InvalidParameterException,
                                                                                      PropertyServerException,
                                                                                      UserNotAuthorizedException,
                                                                                      InvocationTargetException,
                                                                                      NoSuchMethodException,
                                                                                      InstantiationException,
                                                                                      IllegalAccessException {

        String methodName = "createSoftwareServer";
        mockRegistrationHandler(methodName);

        UserNotAuthorizedException mockedException = mockException(UserNotAuthorizedException.class, methodName);
        when(softwareServerRegistrationHandler.createSoftwareServerCapability(USER, QUALIFIED_NAME, NAME, DESCRIPTION,
                TYPE, VERSION, PATCH_LEVEL, SOURCE)).thenThrow(mockedException);

        SoftwareServerCapabilityRequestBody requestBody = mockSoftwareServerCapabilityRequestBody();

        GUIDResponse response = dataEngineRESTServices.createSoftwareServer(SERVER_NAME, USER, requestBody);

        verify(restExceptionHandler, times(1)).captureUserNotAuthorizedException(response, mockedException);
    }

    @Test
    void getSoftwareServerByQualifiedName() throws InvalidParameterException, PropertyServerException,
                                                   UserNotAuthorizedException {
        mockRegistrationHandler("getSoftwareServerByQualifiedName");

        when(softwareServerRegistrationHandler.getSoftwareServerCapabilityByQualifiedName(USER, QUALIFIED_NAME)).thenReturn(GUID);

        GUIDResponse response = dataEngineRESTServices.getSoftwareServerByQualifiedName(SERVER_NAME, USER,
                QUALIFIED_NAME);
        assertEquals(GUID, response.getGUID());
    }

    @Test
    void getSoftwareServerByQualifiedName_ResponseWithCapturedUserNotAuthorizedException() throws
                                                                                           InvalidParameterException,
                                                                                           PropertyServerException,
                                                                                           UserNotAuthorizedException,
                                                                                           InvocationTargetException,
                                                                                           NoSuchMethodException,
                                                                                           InstantiationException,
                                                                                           IllegalAccessException {

        String methodName = "getSoftwareServerByQualifiedName";
        mockRegistrationHandler(methodName);

        UserNotAuthorizedException mockedException = mockException(UserNotAuthorizedException.class, methodName);
        when(softwareServerRegistrationHandler.getSoftwareServerCapabilityByQualifiedName(USER, QUALIFIED_NAME)).thenThrow(mockedException);


        GUIDResponse response = dataEngineRESTServices.getSoftwareServerByQualifiedName(SERVER_NAME, USER,
                QUALIFIED_NAME);

        verify(restExceptionHandler, times(1)).captureUserNotAuthorizedException(response, mockedException);
    }

    @Test
    void getSoftwareServerByQualifiedName_ResponseWithCapturedInvalidParameterException() throws
                                                                                          InvalidParameterException,
                                                                                          PropertyServerException,
                                                                                          UserNotAuthorizedException,
                                                                                          InvocationTargetException,
                                                                                          NoSuchMethodException,
                                                                                          InstantiationException,
                                                                                          IllegalAccessException {

        String methodName = "getSoftwareServerByQualifiedName";
        mockRegistrationHandler(methodName);

        InvalidParameterException mockedException = mockException(InvalidParameterException.class, methodName);
        when(softwareServerRegistrationHandler.getSoftwareServerCapabilityByQualifiedName(USER, QUALIFIED_NAME)).thenThrow(mockedException);


        GUIDResponse response = dataEngineRESTServices.getSoftwareServerByQualifiedName(SERVER_NAME, USER,
                QUALIFIED_NAME);

        verify(restExceptionHandler, times(1)).captureInvalidParameterException(response, mockedException);
    }

    @Test
    void createSchemaType() throws InvalidParameterException, PropertyServerException, UserNotAuthorizedException {
        mockSchemaTypeHandler("createSchemaType");

        when(dataEngineSchemaTypeHandler.createSchemaType(USER, QUALIFIED_NAME, NAME, AUTHOR, ENCODING_STANDARD,
                USAGE, VERSION_NUMBER, null)).thenReturn(GUID);

        SchemaTypeRequestBody requestBody = mockSchemaTypeRequestBody();

        GUIDResponse response = dataEngineRESTServices.createSchemaType(USER, SERVER_NAME, requestBody);
        assertEquals(GUID, response.getGUID());
    }

    @Test
    void createSchemaType_ResponseWithCapturedInvalidParameterException() throws InvalidParameterException,
                                                                                 PropertyServerException,
                                                                                 UserNotAuthorizedException,
                                                                                 InvocationTargetException,
                                                                                 NoSuchMethodException,
                                                                                 InstantiationException,
                                                                                 IllegalAccessException {
        String methodName = "createSchemaType";

        mockSchemaTypeHandler(methodName);

        InvalidParameterException mockedException = mockException(InvalidParameterException.class, methodName);
        when(dataEngineSchemaTypeHandler.createSchemaType(USER, QUALIFIED_NAME, NAME, AUTHOR, ENCODING_STANDARD,
                USAGE, VERSION_NUMBER, null)).thenThrow(mockedException);

        SchemaTypeRequestBody requestBody = mockSchemaTypeRequestBody();

        GUIDResponse response = dataEngineRESTServices.createSchemaType(USER, SERVER_NAME, requestBody);
        verify(restExceptionHandler, times(1)).captureInvalidParameterException(response, mockedException);
    }

    @Test
    void createSchemaType_ResponseWithCapturedUserNotAuthorizedException() throws InvalidParameterException,
                                                                                  PropertyServerException,
                                                                                  UserNotAuthorizedException,
                                                                                  InvocationTargetException,
                                                                                  NoSuchMethodException,
                                                                                  InstantiationException,
                                                                                  IllegalAccessException {
        String methodName = "createSchemaType";

        mockSchemaTypeHandler(methodName);

        UserNotAuthorizedException mockedException = mockException(UserNotAuthorizedException.class, methodName);
        when(dataEngineSchemaTypeHandler.createSchemaType(USER, QUALIFIED_NAME, NAME, AUTHOR, ENCODING_STANDARD,
                USAGE, VERSION_NUMBER, null)).thenThrow(mockedException);

        SchemaTypeRequestBody requestBody = mockSchemaTypeRequestBody();

        GUIDResponse response = dataEngineRESTServices.createSchemaType(USER, SERVER_NAME, requestBody);
        verify(restExceptionHandler, times(1)).captureUserNotAuthorizedException(response, mockedException);
    }

    @Test
    void createPortImplementation() throws InvalidParameterException, PropertyServerException,
                                           UserNotAuthorizedException {
        mockSchemaTypeHandler("createSchemaType");
        mockPortHandler("createPortImplementationWithSchemaType");

        when(portHandler.createPortImplementation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT)).thenReturn(GUID);

        PortImplementationRequestBody requestBody = mockPortImplementationRequestBody();

        GUIDResponse response = dataEngineRESTServices.createPortImplementation(USER, SERVER_NAME, requestBody);

        verify(dataEngineSchemaTypeHandler, times(1)).createSchemaType(USER, QUALIFIED_NAME, NAME, AUTHOR,
                ENCODING_STANDARD, USAGE, VERSION_NUMBER, null);
        assertEquals(GUID, response.getGUID());
    }

    @Test
    void createPortImplementation_ResponseWithCapturedUserNotAuthorizedException() throws InvalidParameterException,
                                                                                          PropertyServerException,
                                                                                          UserNotAuthorizedException,
                                                                                          InvocationTargetException,
                                                                                          NoSuchMethodException,
                                                                                          InstantiationException,
                                                                                          IllegalAccessException {
        String methodName = "createPortImplementationWithSchemaType";

        mockSchemaTypeHandler("createSchemaType");
        mockPortHandler(methodName);


        UserNotAuthorizedException mockedException = mockException(UserNotAuthorizedException.class, methodName);
        when(portHandler.createPortImplementation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT)).thenThrow(mockedException);

        PortImplementationRequestBody requestBody = mockPortImplementationRequestBody();

        GUIDResponse response = dataEngineRESTServices.createPortImplementation(USER, SERVER_NAME, requestBody);

        verify(dataEngineSchemaTypeHandler, times(1)).createSchemaType(USER, QUALIFIED_NAME, NAME, AUTHOR,
                ENCODING_STANDARD, USAGE, VERSION_NUMBER, null);
        verify(restExceptionHandler, times(1)).captureUserNotAuthorizedException(response, mockedException);
    }

    @Test
    void createPortImplementation_ResponseWithCapturedInvalidParameterException() throws InvalidParameterException,
                                                                                         PropertyServerException,
                                                                                         UserNotAuthorizedException,
                                                                                         InvocationTargetException,
                                                                                         NoSuchMethodException,
                                                                                         InstantiationException,
                                                                                         IllegalAccessException {
        String methodName = "createPortImplementationWithSchemaType";

        mockSchemaTypeHandler("createSchemaType");
        mockPortHandler(methodName);


        InvalidParameterException mockedException = mockException(InvalidParameterException.class, methodName);
        when(portHandler.createPortImplementation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT)).thenThrow(mockedException);

        PortImplementationRequestBody requestBody = mockPortImplementationRequestBody();

        GUIDResponse response = dataEngineRESTServices.createPortImplementation(USER, SERVER_NAME, requestBody);

        verify(dataEngineSchemaTypeHandler, times(1)).createSchemaType(USER, QUALIFIED_NAME, NAME, AUTHOR,
                ENCODING_STANDARD, USAGE, VERSION_NUMBER, null);
        verify(restExceptionHandler, times(1)).captureInvalidParameterException(response, mockedException);
    }

    @Test
    void createPortAlias() throws InvalidParameterException, PropertyServerException, UserNotAuthorizedException {
        mockPortHandler("createPortAliasWithDelegation");

        when(portHandler.createPortAliasWithDelegation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT,
                DELEGATED_QUALIFIED_NAME)).thenReturn(GUID);

        PortAliasRequestBody requestBody = mockPortAliasRequestBody();

        GUIDResponse response = dataEngineRESTServices.createPortAlias(USER, SERVER_NAME, requestBody);

        assertEquals(GUID, response.getGUID());
    }

    @Test
    void createPortAlias_ResponseWithCapturedInvalidParameterException() throws InvalidParameterException,
                                                                                PropertyServerException,
                                                                                UserNotAuthorizedException,
                                                                                InvocationTargetException,
                                                                                NoSuchMethodException,
                                                                                InstantiationException,
                                                                                IllegalAccessException {
        String methodName = "createPortAliasWithDelegation";
        mockPortHandler(methodName);

        InvalidParameterException mockedException = mockException(InvalidParameterException.class, methodName);
        when(portHandler.createPortAliasWithDelegation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT,
                DELEGATED_QUALIFIED_NAME)).thenThrow(mockedException);

        PortAliasRequestBody requestBody = mockPortAliasRequestBody();

        GUIDResponse response = dataEngineRESTServices.createPortAlias(USER, SERVER_NAME, requestBody);

        verify(restExceptionHandler, times(1)).captureInvalidParameterException(response, mockedException);
    }

    @Test
    void createPortAlias_ResponseWithCaptureUserNotAuthorizedException() throws InvalidParameterException,
                                                                                PropertyServerException,
                                                                                UserNotAuthorizedException,
                                                                                InvocationTargetException,
                                                                                NoSuchMethodException,
                                                                                InstantiationException,
                                                                                IllegalAccessException {
        String methodName = "createPortAliasWithDelegation";
        mockPortHandler(methodName);

        UserNotAuthorizedException mockedException = mockException(UserNotAuthorizedException.class, methodName);
        when(portHandler.createPortAliasWithDelegation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT,
                DELEGATED_QUALIFIED_NAME)).thenThrow(mockedException);

        PortAliasRequestBody requestBody = mockPortAliasRequestBody();

        GUIDResponse response = dataEngineRESTServices.createPortAlias(USER, SERVER_NAME, requestBody);

        verify(restExceptionHandler, times(1)).captureUserNotAuthorizedException(response, mockedException);
    }

    @Test
    void createProcesses() throws InvalidParameterException, PropertyServerException, UserNotAuthorizedException {
        mockSchemaTypeHandler("createSchemaType");
        mockSchemaTypeHandler("createLineageMappings");
        mockPortHandler("createPortImplementationWithSchemaType");
        mockPortHandler("createPortAliases");
        mockProcessHandler("createProcess");

        when(processHandler.createProcess(USER, QUALIFIED_NAME, NAME, DESCRIPTION, LATEST_CHANGE,
                null, NAME, FORMULA, OWNER, OwnerType.USER_ID)).thenReturn(GUID);

        ProcessesRequestBody requestBody = mockProcessesRequestBody();

        GUIDListResponse response = dataEngineRESTServices.createProcesses(USER, SERVER_NAME, requestBody);

        verify(dataEngineSchemaTypeHandler, times(1)).createSchemaType(USER, QUALIFIED_NAME, NAME, AUTHOR,
                ENCODING_STANDARD, USAGE, VERSION_NUMBER, null);
        verify(portHandler, times(1)).createPortImplementation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT);
        verify(portHandler, times(1)).createPortAliasWithDelegation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT,
                DELEGATED_QUALIFIED_NAME);
        assertEquals(GUID, response.getGUIDs().get(0));
    }

    @Test
    void createProcesses_ResponseWithCapturedInvalidParameterException() throws InvalidParameterException,
                                                                                PropertyServerException,
                                                                                UserNotAuthorizedException,
                                                                                InvocationTargetException,
                                                                                NoSuchMethodException,
                                                                                InstantiationException,
                                                                                IllegalAccessException {
        mockSchemaTypeHandler("createSchemaType");
        mockSchemaTypeHandler("createLineageMappings");
        mockPortHandler("createPortImplementationWithSchemaType");
        mockPortHandler("createPortAliases");

        String methodName = "createProcess";
        mockProcessHandler(methodName);

        InvalidParameterException mockedException = mockException(InvalidParameterException.class, methodName);
        when(processHandler.createProcess(USER, QUALIFIED_NAME, NAME, DESCRIPTION, LATEST_CHANGE,
                null, NAME, FORMULA, OWNER, OwnerType.USER_ID)).thenThrow(mockedException);

        ProcessesRequestBody requestBody = mockProcessesRequestBody();

        GUIDListResponse response = dataEngineRESTServices.createProcesses(USER, SERVER_NAME, requestBody);

        verify(dataEngineSchemaTypeHandler, times(1)).createSchemaType(USER, QUALIFIED_NAME, NAME, AUTHOR,
                ENCODING_STANDARD, USAGE, VERSION_NUMBER, null);
        verify(portHandler, times(1)).createPortImplementation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT);
        verify(portHandler, times(1)).createPortAliasWithDelegation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT,
                DELEGATED_QUALIFIED_NAME);

        verify(restExceptionHandler, times(1)).captureInvalidParameterException(response, mockedException);
    }

    @Test
    void createProcesses_ResponseWithCapturedUserNotAuthorizedException() throws InvalidParameterException,
                                                                                 PropertyServerException,
                                                                                 UserNotAuthorizedException,
                                                                                 InvocationTargetException,
                                                                                 NoSuchMethodException,
                                                                                 InstantiationException,
                                                                                 IllegalAccessException {
        mockSchemaTypeHandler("createSchemaType");
        mockSchemaTypeHandler("createLineageMappings");
        mockPortHandler("createPortImplementationWithSchemaType");
        mockPortHandler("createPortAliases");

        String methodName = "createProcess";
        mockProcessHandler(methodName);

        UserNotAuthorizedException mockedException = mockException(UserNotAuthorizedException.class, methodName);
        when(processHandler.createProcess(USER, QUALIFIED_NAME, NAME, DESCRIPTION, LATEST_CHANGE,
                null, NAME, FORMULA, OWNER, OwnerType.USER_ID)).thenThrow(mockedException);

        ProcessesRequestBody requestBody = mockProcessesRequestBody();

        GUIDListResponse response = dataEngineRESTServices.createProcesses(USER, SERVER_NAME, requestBody);

        verify(dataEngineSchemaTypeHandler, times(1)).createSchemaType(USER, QUALIFIED_NAME, NAME, AUTHOR,
                ENCODING_STANDARD, USAGE, VERSION_NUMBER, null);
        verify(portHandler, times(1)).createPortImplementation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT);
        verify(portHandler, times(1)).createPortAliasWithDelegation(USER, QUALIFIED_NAME, NAME, PortType.INOUT_PORT,
                DELEGATED_QUALIFIED_NAME);

        verify(restExceptionHandler, times(1)).captureUserNotAuthorizedException(response, mockedException);
    }

    @Test
    void addPortsToProcess() throws InvalidParameterException, PropertyServerException, UserNotAuthorizedException {
        mockProcessHandler("addPortsToProcess");

        PortListRequestBody requestBody = mockPortListRequestBody();

        GUIDResponse response = dataEngineRESTServices.addPortsToProcess(USER, SERVER_NAME, PROCESS_GUID, requestBody);

        verify(processHandler, times(1)).addProcessPortRelationship(USER, PROCESS_GUID, GUID);
        assertEquals(PROCESS_GUID, response.getGUID());
    }


    @Test
    void addPortsToProcess_ResponseWithCapturedInvalidParameterException() throws InvalidParameterException,
                                                                                  PropertyServerException,
                                                                                  UserNotAuthorizedException,
                                                                                  InvocationTargetException,
                                                                                  NoSuchMethodException,
                                                                                  InstantiationException,
                                                                                  IllegalAccessException {

        String methodName = "addPortsToProcess";
        mockProcessHandler(methodName);

        PortListRequestBody requestBody = mockPortListRequestBody();

        org.odpi.openmetadata.commonservices.ffdc.exceptions.InvalidParameterException mockedException =
                mockException(org.odpi.openmetadata.commonservices.ffdc.exceptions.InvalidParameterException.class,
                        methodName);
        doThrow(mockedException).when(processHandler).addProcessPortRelationship(USER, PROCESS_GUID, GUID);

        GUIDResponse response = dataEngineRESTServices.addPortsToProcess(USER, SERVER_NAME, PROCESS_GUID, requestBody);
        verify(restExceptionHandler, times(1)).captureInvalidParameterException(response, mockedException);
    }

    @Test
    void addPortsToProcess_ResponseWithCapturedUserNotAuthorizedException() throws InvalidParameterException,
                                                                                   PropertyServerException,
                                                                                   UserNotAuthorizedException,
                                                                                   InvocationTargetException,
                                                                                   NoSuchMethodException,
                                                                                   InstantiationException,
                                                                                   IllegalAccessException {

        String methodName = "addPortsToProcess";
        mockProcessHandler(methodName);

        PortListRequestBody requestBody = mockPortListRequestBody();

        org.odpi.openmetadata.frameworks.connectors.ffdc.UserNotAuthorizedException mockedException =
                mockException(org.odpi.openmetadata.frameworks.connectors.ffdc.UserNotAuthorizedException.class,
                        methodName);
        doThrow(mockedException).when(processHandler).addProcessPortRelationship(USER, PROCESS_GUID, GUID);

        GUIDResponse response = dataEngineRESTServices.addPortsToProcess(USER, SERVER_NAME, PROCESS_GUID, requestBody);
        verify(restExceptionHandler, times(1)).captureUserNotAuthorizedException(response, mockedException);
    }

    @Test
    void addLineageMappings() throws InvalidParameterException, PropertyServerException, UserNotAuthorizedException {
        mockSchemaTypeHandler("createLineageMappings");

        LineageMappingsRequestBody requestBody = mockLineageMappingsRequestBody();

        dataEngineRESTServices.addLineageMappings(USER, SERVER_NAME, requestBody);

        verify(dataEngineSchemaTypeHandler, times(1)).addLineageMappingRelationship(USER, SOURCE_QUALIFIED_NAME,
                TARGET_QUALIFIED_NAME);
    }

    @Test
    void addLineageMappings_ResponseWithCapturedInvalidParameterException() throws InvalidParameterException,
                                                                                   PropertyServerException,
                                                                                   UserNotAuthorizedException,
                                                                                   InvocationTargetException,
                                                                                   NoSuchMethodException,
                                                                                   InstantiationException,
                                                                                   IllegalAccessException {
        String methodName = "createLineageMappings";
        mockSchemaTypeHandler(methodName);

        LineageMappingsRequestBody requestBody = mockLineageMappingsRequestBody();

        org.odpi.openmetadata.commonservices.ffdc.exceptions.InvalidParameterException mockedException =
                mockException(org.odpi.openmetadata.commonservices.ffdc.exceptions.InvalidParameterException.class,
                        methodName);
        doThrow(mockedException).when(dataEngineSchemaTypeHandler).addLineageMappingRelationship(USER,
                SOURCE_QUALIFIED_NAME, TARGET_QUALIFIED_NAME);

        VoidResponse response = dataEngineRESTServices.addLineageMappings(USER, SERVER_NAME, requestBody);

        verify(restExceptionHandler, times(1)).captureInvalidParameterException(response, mockedException);
    }

    @Test
    void addLineageMappings_ResponseWithCapturedUserNotAuthorizedException() throws InvalidParameterException,
                                                                                    PropertyServerException,
                                                                                    UserNotAuthorizedException,
                                                                                    InvocationTargetException,
                                                                                    NoSuchMethodException,
                                                                                    InstantiationException,
                                                                                    IllegalAccessException {
        String methodName = "createLineageMappings";
        mockSchemaTypeHandler(methodName);

        LineageMappingsRequestBody requestBody = mockLineageMappingsRequestBody();

        org.odpi.openmetadata.frameworks.connectors.ffdc.UserNotAuthorizedException mockedException =
                mockException(org.odpi.openmetadata.frameworks.connectors.ffdc.UserNotAuthorizedException.class,
                        methodName);
        doThrow(mockedException).when(dataEngineSchemaTypeHandler).addLineageMappingRelationship(USER,
                SOURCE_QUALIFIED_NAME, TARGET_QUALIFIED_NAME);

        VoidResponse response = dataEngineRESTServices.addLineageMappings(USER, SERVER_NAME, requestBody);

        verify(restExceptionHandler, times(1)).captureUserNotAuthorizedException(response, mockedException);
    }

    private LineageMappingsRequestBody mockLineageMappingsRequestBody() {
        LineageMappingsRequestBody requestBody = new LineageMappingsRequestBody();
        requestBody.setLineageMappings(Collections.singletonList(new LineageMapping(SOURCE_QUALIFIED_NAME,
                TARGET_QUALIFIED_NAME)));
        return requestBody;
    }

    private void mockRegistrationHandler(String methodName) throws InvalidParameterException, PropertyServerException,
                                                                   UserNotAuthorizedException {
        when(instanceHandler.getRegistrationHandler(USER, SERVER_NAME, methodName)).thenReturn(softwareServerRegistrationHandler);
    }

    private void mockSchemaTypeHandler(String methodName) throws InvalidParameterException, UserNotAuthorizedException,
                                                                 PropertyServerException {
        when(instanceHandler.getDataEngineSchemaTypeHandler(USER, SERVER_NAME, methodName)).thenReturn(dataEngineSchemaTypeHandler);
    }

    private void mockPortHandler(String methodName) throws InvalidParameterException, UserNotAuthorizedException,
                                                           PropertyServerException {
        when(instanceHandler.getPortHandler(USER, SERVER_NAME, methodName)).thenReturn(portHandler);
    }

    private void mockProcessHandler(String methodName) throws InvalidParameterException, UserNotAuthorizedException,
                                                              PropertyServerException {
        when(instanceHandler.getProcessHandler(USER, SERVER_NAME, methodName)).thenReturn(processHandler);
    }

    private SoftwareServerCapabilityRequestBody mockSoftwareServerCapabilityRequestBody() {
        SoftwareServerCapabilityRequestBody requestBody = new SoftwareServerCapabilityRequestBody();
        requestBody.setSoftwareServerCapability(new SoftwareServerCapability(QUALIFIED_NAME, NAME, DESCRIPTION, TYPE,
                VERSION, PATCH_LEVEL, SOURCE));
        return requestBody;
    }

    private SchemaTypeRequestBody mockSchemaTypeRequestBody() {
        SchemaTypeRequestBody requestBody = new SchemaTypeRequestBody();
        requestBody.setSchemaType(new SchemaType(QUALIFIED_NAME, NAME, AUTHOR, USAGE, ENCODING_STANDARD,
                VERSION_NUMBER, null));
        return requestBody;
    }

    private PortImplementationRequestBody mockPortImplementationRequestBody() {
        PortImplementationRequestBody requestBody = new PortImplementationRequestBody();
        requestBody.setPortImplementation(new PortImplementation(NAME, QUALIFIED_NAME, PortType.INOUT_PORT,
                new SchemaType(QUALIFIED_NAME, NAME, AUTHOR, USAGE, ENCODING_STANDARD,
                        VERSION_NUMBER, null)));
        return requestBody;
    }

    private PortAliasRequestBody mockPortAliasRequestBody() {
        PortAliasRequestBody requestBody = new PortAliasRequestBody();
        requestBody.setPort(new PortAlias(NAME, QUALIFIED_NAME, PortType.INOUT_PORT, DELEGATED_QUALIFIED_NAME));
        return requestBody;
    }

    private ProcessesRequestBody mockProcessesRequestBody() {
        List<PortImplementation> portImplementations = Collections.singletonList(
                new PortImplementation(NAME, QUALIFIED_NAME, PortType.INOUT_PORT,
                        new SchemaType(QUALIFIED_NAME, NAME, AUTHOR, USAGE, ENCODING_STANDARD, VERSION_NUMBER, null)));
        List<PortAlias> portAliases = Collections.singletonList(
                new PortAlias(NAME, QUALIFIED_NAME, PortType.INOUT_PORT, DELEGATED_QUALIFIED_NAME));
        List<LineageMapping> lineageMappings = Collections.singletonList(new LineageMapping(SOURCE_QUALIFIED_NAME,
                TARGET_QUALIFIED_NAME));

        Process process = new Process(QUALIFIED_NAME, NAME, DESCRIPTION, LATEST_CHANGE, null, NAME, FORMULA, OWNER,
                OwnerType.USER_ID, portImplementations, portAliases, lineageMappings);

        ProcessesRequestBody requestBody = new ProcessesRequestBody();
        requestBody.setProcesses(Collections.singletonList(process));
        return requestBody;
    }

    private PortListRequestBody mockPortListRequestBody() {
        PortListRequestBody requestBody = new PortListRequestBody();
        requestBody.setPorts(Collections.singletonList(GUID));
        return requestBody;
    }

}