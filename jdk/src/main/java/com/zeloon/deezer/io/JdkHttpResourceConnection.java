/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zeloon.deezer.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;

public class JdkHttpResourceConnection implements ResourceConnection {

  private final String proxyHost;

  private final Integer proxyPort;

  private final HttpClient httpClient;

  public JdkHttpResourceConnection() {
    this(null, null);
  }

  public JdkHttpResourceConnection(String proxyHost, Integer proxyPort) {
    this.proxyHost = proxyHost;
    this.proxyPort = proxyPort;

    var httpBuilder = HttpClient.newBuilder();

    if (useProxy()) {
      httpBuilder.proxy(ProxySelector.of(InetSocketAddress.createUnresolved(proxyHost, proxyPort)));
    }

    this.httpClient = httpBuilder.build();
  }

  @Override
  public String getData(String url) throws IOException {

    var request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(url))
        .build();

    HttpResponse<String> response;
    try {
      response = this.httpClient.send(request, BodyHandlers.ofString(Charset.forName("UTF-8")));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    return response.body();
  }

  public boolean useProxy() {
    return proxyHost != null && proxyHost.length() > 0 && proxyPort != null;
  }

  public String getProxyHost() {
    return proxyHost;
  }

  public Integer getProxyPort() {
    return proxyPort;
  }
}
