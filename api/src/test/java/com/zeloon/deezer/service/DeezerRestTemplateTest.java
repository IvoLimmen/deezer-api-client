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
package com.zeloon.deezer.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zeloon.deezer.domain.Albums;
import com.zeloon.deezer.domain.RadioGenres;
import com.zeloon.deezer.domain.User;
import com.zeloon.deezer.io.FileSystemResourceConnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeezerRestTemplateTest {

  private DeezerClientService deezerRestTemplate;

  @BeforeEach
  public void setUp() {
    deezerRestTemplate = new DeezerClientService(new FileSystemResourceConnection());
  }

  @Test
  public void getWithPrefix() {
    RadioGenres radioGenres = deezerRestTemplate.get("radio/genres", RadioGenres.class);
    assertNotNull(radioGenres);
  }

  @Test
  public void getWithPrefixAndId() {
    User user = deezerRestTemplate.get("user", 2529L, User.class);
    assertNotNull(user);
  }

  @Test
  public void getWithPrefixAndIdAndPostfix() {
    Albums albums = deezerRestTemplate.get("user", 2529L, "albums", Albums.class);
    assertNotNull(albums);
  }

  @Test
  public void getWithError() {
    assertThrows(RestClientException.class, () -> {
      deezerRestTemplate.get("user", 2L, "albums", Albums.class);
    });
  }
}
