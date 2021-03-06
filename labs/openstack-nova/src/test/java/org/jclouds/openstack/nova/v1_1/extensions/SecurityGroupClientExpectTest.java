/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.openstack.nova.v1_1.extensions;

import static org.testng.Assert.assertEquals;

import java.net.URI;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.nova.v1_1.NovaClient;
import org.jclouds.openstack.nova.v1_1.internal.BaseNovaClientExpectTest;
import org.jclouds.openstack.nova.v1_1.parse.ParseSecurityGroupListTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

/**
 * Tests annotation parsing of {@code SecurityGroupAsyncClient}
 *
 * @author Michael Arnold
 */
@Test(groups = "unit", testName = "SecurityGroupClientExpectTest")
public class SecurityGroupClientExpectTest extends BaseNovaClientExpectTest {
    public void testListSecurityGroupsWhenResponseIs2xx() throws Exception {
        HttpRequest listSecurityGroups = HttpRequest
                .builder()
                .method("GET")
                .endpoint(
                        URI.create("https://compute.north.host/v1.1/3456/os-security-groups"))
                .headers(
                        ImmutableMultimap.<String, String> builder()
                                .put("Accept", "application/json")
                                .put("X-Auth-Token", authToken).build()).build();

        HttpResponse listSecurityGroupsResponse = HttpResponse.builder().statusCode(200)
                .payload(payloadFromResource("/securitygroup_list.json")).build();


        NovaClient clientWhenSecurityGroupsExist = requestsSendResponses(
                keystoneAuthWithAccessKeyAndSecretKey, responseWithKeystoneAccess,
                listSecurityGroups, listSecurityGroupsResponse);

        assertEquals(clientWhenSecurityGroupsExist.getConfiguredRegions(),
                ImmutableSet.of("North"));

        assertEquals(clientWhenSecurityGroupsExist.getSecurityGroupClientForRegion("North")
                .listSecurityGroups().toString(), new ParseSecurityGroupListTest().expected()
                .toString());
    }
}
