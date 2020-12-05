/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.soul.admin.config;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import java.time.Duration;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

/**
 * Test cases for HttpSyncProperties.
 *
 * @author onlyonezhongjinhui
 */
@RunWith(MockitoJUnitRunner.class)
public final class HttpSyncPropertiesTest {

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    /**
     * clear context.
     */
    @AfterEach
    public void clear() {
        context.close();
    }

    @Test
    public void testDefault() {
        load();
        HttpSyncProperties httpSyncProperties = context.getBean(HttpSyncProperties.class);
        assertThat(httpSyncProperties.isEnabled(), comparesEqualTo(true));
        assertThat(httpSyncProperties.getRefreshInterval(), comparesEqualTo(Duration.ofMinutes(5)));
    }

    @Test
    public void testSpecified() {
        load("soul.sync.http.enabled=false", "soul.sync.http.refreshInterval=1m");
        HttpSyncProperties httpSyncProperties = context.getBean(HttpSyncProperties.class);
        assertThat(httpSyncProperties.isEnabled(), comparesEqualTo(false));
        assertThat(httpSyncProperties.getRefreshInterval(), comparesEqualTo(Duration.ofMinutes(1)));
    }

    private void load(final String... inlinedProperties) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context, inlinedProperties);
        context.register(HttpSyncPropertiesConfiguration.class);
        context.refresh();
    }

    @Configuration
    @EnableConfigurationProperties(HttpSyncProperties.class)
    static class HttpSyncPropertiesConfiguration {

    }
}