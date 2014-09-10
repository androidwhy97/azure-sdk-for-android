/**
 * Copyright Microsoft Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microsoft.azure;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration {
    /**
     * Property name for socket connection timeout used by services created with
     * this configuration.
     */
    public static final String PROPERTY_CONNECT_TIMEOUT = "com.microsoft.azure.Configuration.connectTimeout";

    /**
     * Property name for socket read timeout used by services created with this
     * configuration.
     */
    public static final String PROPERTY_READ_TIMEOUT = "com.microsoft.azure.Configuration.readTimeout";

    /**
     * Property name to control if HTTP logging to console is on or off. If
     * property is set, logging is on, regardless of value.
     */
    public static final String PROPERTY_LOG_HTTP_REQUESTS = "com.microsoft.azure.Configuration.logHttpRequests";

    /**
     * The configuration instance.
     */
    private static Configuration instance;

    /**
     * The configuration properties.
     */
    private final Map<String, Object> properties;

    public Configuration() {
        this.properties = new HashMap<String, Object>();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            try {
                instance = Configuration.load();
            } catch (IOException e) {
                instance = new Configuration();
            }
        }
        return instance;
    }

    public static void setInstance(final Configuration configuration) {
        Configuration.instance = configuration;
    }

    public static Configuration load() throws IOException {
        final Configuration config = new Configuration();

        final InputStream stream = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream(
                        "assets/com.microsoft.windowsazure.properties");
        if (stream != null) {
            final Properties properties = new Properties();
            properties.load(stream);
            for (Map.Entry<Object, Object> key : properties.entrySet()) {
                config.setProperty(key.getKey().toString(), key.getValue());
            }
        }

        return config;
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }

    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}
