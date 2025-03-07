/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.accessservices.dataengine.server.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.odpi.openmetadata.accessservices.dataengine.rest.LineageMappingsRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.PortAliasRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.PortImplementationRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.PortListRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.ProcessesRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.SchemaTypeRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.rest.SoftwareServerCapabilityRequestBody;
import org.odpi.openmetadata.accessservices.dataengine.server.service.DataEngineRESTServices;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class DataEngineResourceTest {

    private static final String USER = "user";
    private static final String SERVER_NAME = "serverName";

    @Mock
    private DataEngineRESTServices dataEngineRestServices;

    @InjectMocks
    private DataEngineResource dataEngineResource;

    @Test
    void testCreateSoftwareCapability() {
        SoftwareServerCapabilityRequestBody requestBody = new SoftwareServerCapabilityRequestBody();
        dataEngineResource.createSoftwareServerCapability(SERVER_NAME, USER, requestBody);

        verify(dataEngineRestServices, times(1)).createSoftwareServer(SERVER_NAME, USER, requestBody);
    }

    @Test
    void testGetSoftwareServerCapabiliyty() {
        String qualifiedName = "testQualifiedName";
        dataEngineResource.getSoftwareServerCapabilityByQualifiedName(SERVER_NAME, USER, qualifiedName);

        verify(dataEngineRestServices, times(1)).getSoftwareServerByQualifiedName(SERVER_NAME, USER, qualifiedName);
    }

    @Test
    void testCreateSchemaType() {
        SchemaTypeRequestBody requestBody = new SchemaTypeRequestBody();
        dataEngineResource.createSchemaType(SERVER_NAME, USER, requestBody);

        verify(dataEngineRestServices, times(1)).createSchemaType(SERVER_NAME, USER, requestBody);
    }

    @Test
    void testCreatePortImplementation() {
        PortImplementationRequestBody requestBody = new PortImplementationRequestBody();
        dataEngineResource.createPortImplementation(SERVER_NAME, USER, requestBody);

        verify(dataEngineRestServices, times(1)).createPortImplementation(SERVER_NAME, USER, requestBody);
    }

    @Test
    void testCreatePortAlias() {
        PortAliasRequestBody requestBody = new PortAliasRequestBody();
        dataEngineResource.createPortAlias(SERVER_NAME, USER, requestBody);

        verify(dataEngineRestServices, times(1)).createPortAlias(SERVER_NAME, USER, requestBody);
    }

    @Test
    void testCreateProcesses() {
        ProcessesRequestBody requestBody = new ProcessesRequestBody();
        dataEngineResource.createProcesses(USER, SERVER_NAME, requestBody);

        verify(dataEngineRestServices, times(1)).createProcesses(USER, SERVER_NAME, requestBody);
    }

    @Test
    void testAddPortsToProcess() {
        PortListRequestBody requestBody = new PortListRequestBody();
        String processGuid = "processGuid";
        dataEngineResource.addPortsToProcess(USER, SERVER_NAME, processGuid, requestBody);

        verify(dataEngineRestServices, times(1)).addPortsToProcess(USER, SERVER_NAME, processGuid, requestBody);
    }

    @Test
    void testAddLineageMappings() {
        LineageMappingsRequestBody requestBody = new LineageMappingsRequestBody();
        dataEngineResource.addLineageMappings(USER, SERVER_NAME, requestBody);

        verify(dataEngineRestServices, times(1)).addLineageMappings(USER, SERVER_NAME, requestBody);
    }
}