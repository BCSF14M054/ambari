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
 * Unit tests for ViewPermissionService.
 */
public class ViewPermissionServiceTest extends BaseServiceTest {


  public List<ServiceTestInvocation> getTestInvocations() throws Exception {
    List<ServiceTestInvocation> listInvocations = new ArrayList<>();

    //getPermission
    ViewPermissionService permissionService = new TestViewPermissionService("MY_VIEW", "1.0", "permissionName");
    Method m = permissionService.getClass().getMethod("getPermission", HttpHeaders.class, UriInfo.class, String.class);
    Object[] args = new Object[] {getHttpHeaders(), getUriInfo(), "permissionName"};
    listInvocations.add(new ServiceTestInvocation(Request.Type.GET, permissionService, m, args, null));

    //getPermissions
    permissionService = new TestViewPermissionService("MY_VIEW", "1.0",null);
    m = permissionService.getClass().getMethod("getPermissions", HttpHeaders.class, UriInfo.class);
    args = new Object[] {getHttpHeaders(), getUriInfo()};
    listInvocations.add(new ServiceTestInvocation(Request.Type.GET, permissionService, m, args, null));

    //createPermission
    permissionService = new TestViewPermissionService("MY_VIEW", "1.0","permissionName");
    m = permissionService.getClass().getMethod("createPermission", String.class, HttpHeaders.class, UriInfo.class, String.class);
    args = new Object[] {"body", getHttpHeaders(), getUriInfo(), "permissionName"};
    listInvocations.add(new ServiceTestInvocation(Request.Type.POST, permissionService, m, args, "body"));

    //deletePermission
    permissionService = new TestViewPermissionService("MY_VIEW", "1.0","permissionName");
    m = permissionService.getClass().getMethod("deletePermission", HttpHeaders.class, UriInfo.class, String.class);
    args = new Object[] {getHttpHeaders(), getUriInfo(), "permissionName"};
    listInvocations.add(new ServiceTestInvocation(Request.Type.DELETE, permissionService, m, args, null));

    return listInvocations;
  }


  private class TestViewPermissionService extends ViewPermissionService {
    private String permissionId;

    private TestViewPermissionService(String viewName, String version, String permissionId) {

      super(ApiVersion.Default, viewName, version);
      this.permissionId = permissionId;
    }

    @Override
    protected ResourceInstance createPermissionResource(String viewName, String viewVersion, String permissionId) {
      assertEquals(this.permissionId, permissionId);
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