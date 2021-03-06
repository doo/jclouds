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
package org.jclouds.hpcloud.objectstorage.lvs.functions;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

import org.jclouds.hpcloud.objectstorage.lvs.reference.HPCloudObjectStorageLasVegasHeaders;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.swift.domain.AccountMetadata;

import com.google.common.base.Function;

/**
 * This parses {@link AccountMetadata} from HTTP headers.
 * 
 * @author James Murty
 */
public class ParseCDNUriFromHeaders implements Function<HttpResponse, URI> {

   /**
    * parses the http response headers to provide the CDN URI string.
    */
   public URI apply(final HttpResponse from) {
      String cdnUri = checkNotNull(from.getFirstHeaderOrNull(HPCloudObjectStorageLasVegasHeaders.CDN_URI),
               HPCloudObjectStorageLasVegasHeaders.CDN_URI);
      return URI.create(cdnUri);
   }
}