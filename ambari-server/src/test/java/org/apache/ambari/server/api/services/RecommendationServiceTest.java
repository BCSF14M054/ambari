/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.server.api.services;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.apache.ambari.server.api.resources.ResourceInstance;
import org.apache.ambari.server.api.services.parsers.RequestBodyParser;
import org.apache.ambari.server.api.services.serializers.ResultSerializer;
import org.apache.ambari.server.api.util.ApiVersion;

/**
 * Unit tests for RecommendationService.
 */
public class RecommendationServiceTest extends BaseServiceTest {

  @Override
  public List<ServiceTestInvocation> getTestInvocations() throws Exception {
    List<ServiceTestInvocation> listInvocations = new ArrayList<>();

    //getRecommendation
    RecommendationService service = new TestRecommendationService("stackName", "stackVersion");
    Method m = service.getClass().getMethod("getRecommendation", String.class, HttpHeaders.class, UriInfo.class);
    Object[] args = new Object[] {"body", getHttpHeaders(), getUriInfo()};
    listInvocations.add(new ServiceTestInvocation(Request.Type.POST, service, m, args, "body"));

    return listInvocations;
  }

  private class TestRecommendationService extends RecommendationService {
    private String stackName;
    private String stackVersion;

    private TestRecommendationService(String stackName, String stackVersion) {
      super(ApiVersion.Default, stackName, stackVersion);
      this.stackName = stackName;
      this.stackVersion = stackVersion;
    }

    @Override
    ResourceInstance createRecommendationResource(String stackName, String stackVersion) {
      assertEquals(this.stackName, stackName);
      assertEquals(this.stackVersion, stackVersion);
      return getTestResource();
    }

    @Override
    RequestFactory getRequestFactory() {
      return getTestRequestFactory();
    }

    @Override
    protected RequestBodyParser getBodyParser() {
      return getTestBodyParser();
    }

    @Override
    protected ResultSerializer getResultSerializer() {
      return getTestResultSerializer();
    }
  }

}
