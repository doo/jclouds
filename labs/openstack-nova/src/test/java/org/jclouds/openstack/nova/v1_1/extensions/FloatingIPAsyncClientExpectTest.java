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
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.net.URI;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.nova.v1_1.NovaAsyncClient;
import org.jclouds.openstack.nova.v1_1.internal.BaseNovaAsyncClientExpectTest;
import org.jclouds.openstack.nova.v1_1.parse.ParseFloatingIPListTest;
import org.jclouds.openstack.nova.v1_1.parse.ParseFloatingIPTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

/**
 * Tests annotation parsing of {@code FloatingIPAsyncClient}
 * 
 * @author Michael Arnold
 */
@Test(groups = "unit", testName = "FloatingIPAsyncClientExpectTest")
public class FloatingIPAsyncClientExpectTest extends BaseNovaAsyncClientExpectTest {

   public void testListFloatingIPsWhenResponseIs2xx() throws Exception {
      HttpRequest listFloatingIPs = HttpRequest
            .builder()
            .method("GET")
            .endpoint(URI.create("https://compute.north.host/v1.1/3456/os-floating-ips"))
            .headers(
                  ImmutableMultimap.<String, String> builder().put("Accept", "application/json")
                        .put("X-Auth-Token", authToken).build()).build();

      HttpResponse listFloatingIPsResponse = HttpResponse.builder().statusCode(200)
            .payload(payloadFromResource("/floatingip_list.json")).build();

      NovaAsyncClient clientWhenFloatingIPsExist = requestsSendResponses(keystoneAuthWithAccessKeyAndSecretKey,
            responseWithKeystoneAccess, listFloatingIPs, listFloatingIPsResponse);

      assertEquals(clientWhenFloatingIPsExist.getConfiguredRegions(), ImmutableSet.of("North"));

      assertEquals(clientWhenFloatingIPsExist.getFloatingIPExtensionForRegion("North").get().listFloatingIPs().get()
            .toString(), new ParseFloatingIPListTest().expected().toString());
   }

   public void testListFloatingIPsWhenResponseIs404() throws Exception {
      HttpRequest listFloatingIPs = HttpRequest
            .builder()
            .method("GET")
            .endpoint(URI.create("https://compute.north.host/v1.1/3456/os-floating-ips"))
            .headers(
                  ImmutableMultimap.<String, String> builder().put("Accept", "application/json")
                        .put("X-Auth-Token", authToken).build()).build();

      HttpResponse listFloatingIPsResponse = HttpResponse.builder().statusCode(404).build();

      NovaAsyncClient clientWhenNoServersExist = requestsSendResponses(keystoneAuthWithAccessKeyAndSecretKey,
            responseWithKeystoneAccess, listFloatingIPs, listFloatingIPsResponse);

      assertTrue(clientWhenNoServersExist.getFloatingIPExtensionForRegion("North").get().listFloatingIPs().get()
            .isEmpty());
   }

   public void testGetFloatingIPWhenResponseIs2xx() throws Exception {
      HttpRequest getFloatingIP = HttpRequest
            .builder()
            .method("GET")
            .endpoint(URI.create("https://compute.north.host/v1.1/3456/os-floating-ips/1"))
            .headers(
                  ImmutableMultimap.<String, String> builder().put("Accept", "application/json")
                        .put("X-Auth-Token", authToken).build()).build();

      HttpResponse getFloatingIPResponse = HttpResponse.builder().statusCode(200)
            .payload(payloadFromResource("/floatingip_details.json")).build();

      NovaAsyncClient clientWhenFloatingIPsExist = requestsSendResponses(keystoneAuthWithAccessKeyAndSecretKey,
            responseWithKeystoneAccess, getFloatingIP, getFloatingIPResponse);

      assertEquals(clientWhenFloatingIPsExist.getFloatingIPExtensionForRegion("North").get().getFloatingIP("1").get()
            .toString(), new ParseFloatingIPTest().expected().toString());
   }

   public void testGetFloatingIPWhenResponseIs404() throws Exception {
      HttpRequest getFloatingIP = HttpRequest
            .builder()
            .method("GET")
            .endpoint(URI.create("https://compute.north.host/v1.1/3456/os-floating-ips/1"))
            .headers(
                  ImmutableMultimap.<String, String> builder().put("Accept", "application/json")
                        .put("X-Auth-Token", authToken).build()).build();

      HttpResponse getFloatingIPResponse = HttpResponse.builder().statusCode(404).build();

      NovaAsyncClient clientWhenNoServersExist = requestsSendResponses(keystoneAuthWithAccessKeyAndSecretKey,
            responseWithKeystoneAccess, getFloatingIP, getFloatingIPResponse);

      assertNull(clientWhenNoServersExist.getFloatingIPExtensionForRegion("North").get().getFloatingIP("1").get());
   }

   public void testAllocateWhenResponseIs2xx() throws Exception {
      HttpRequest allocateFloatingIP = HttpRequest
            .builder()
            .method("POST")
            .endpoint(URI.create("https://compute.north.host/v1.1/3456/os-floating-ips"))
            .headers(
                  ImmutableMultimap.<String, String> builder().put("Accept", "application/json")
                        .put("X-Auth-Token", authToken).build())
            .payload(payloadFromStringWithContentType("{}", "application/json")).build();

      HttpResponse allocateFloatingIPResponse = HttpResponse.builder().statusCode(200)
            .payload(payloadFromResource("/floatingip_details.json")).build();

      NovaAsyncClient clientWhenFloatingIPsExist = requestsSendResponses(keystoneAuthWithAccessKeyAndSecretKey,
            responseWithKeystoneAccess, allocateFloatingIP, allocateFloatingIPResponse);

      assertEquals(clientWhenFloatingIPsExist.getFloatingIPExtensionForRegion("North").get().allocate().get()
            .toString(), new ParseFloatingIPTest().expected().toString());

   }

   public void testAllocateWhenResponseIs404() throws Exception {
      HttpRequest allocateFloatingIP = HttpRequest
            .builder()
            .method("POST")
            .endpoint(URI.create("https://compute.north.host/v1.1/3456/os-floating-ips"))
            .headers(
                  ImmutableMultimap.<String, String> builder().put("Accept", "application/json")
                        .put("X-Auth-Token", authToken).build())
            .payload(payloadFromStringWithContentType("{}", "application/json")).build();

      HttpResponse allocateFloatingIPResponse = HttpResponse.builder().statusCode(404).build();

      NovaAsyncClient clientWhenNoServersExist = requestsSendResponses(keystoneAuthWithAccessKeyAndSecretKey,
            responseWithKeystoneAccess, allocateFloatingIP, allocateFloatingIPResponse);

      assertNull(clientWhenNoServersExist.getFloatingIPExtensionForRegion("North").get().allocate().get());
   }

}