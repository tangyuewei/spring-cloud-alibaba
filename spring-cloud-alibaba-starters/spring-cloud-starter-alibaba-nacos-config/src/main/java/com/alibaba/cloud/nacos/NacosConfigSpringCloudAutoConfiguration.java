/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.nacos;

import com.alibaba.cloud.nacos.client.NacosPropertySourceLocator;
import com.alibaba.cloud.nacos.configdata.NacosConfigRefreshEventListener;
import com.alibaba.cloud.nacos.refresh.SmartConfigurationPropertiesRebinder;
import com.alibaba.cloud.nacos.refresh.condition.ConditionalOnNonDefaultBehavior;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.cloud.context.properties.ConfigurationPropertiesBeans;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author juven.xuxb
 * @author freeman
 */
@Configuration(proxyBeanMethods = false)
@Conditional(NacosConfigEnabledCondition.class)
public class NacosConfigSpringCloudAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
	@ConditionalOnNonDefaultBehavior
	public ConfigurationPropertiesRebinder smartConfigurationPropertiesRebinder(ConfigurationPropertiesBeans beans) {
		// If using default behavior, not use SmartConfigurationPropertiesRebinder.
		// Minimize te possibility of making mistakes.
		return new SmartConfigurationPropertiesRebinder(beans);
	}

	@Bean
	public NacosPropertySourceLocator nacosPropertySourceLocator(
			NacosConfigManager nacosConfigManager) {
		return new NacosPropertySourceLocator(nacosConfigManager);
	}

	@Bean(name = "nacosConfigSpringCloudRefreshEventListener")
	public NacosConfigRefreshEventListener nacosConfigRefreshEventListener() {
		return new NacosConfigRefreshEventListener();
	}

}
