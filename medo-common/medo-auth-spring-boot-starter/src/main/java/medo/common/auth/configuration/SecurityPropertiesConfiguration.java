package medo.common.auth.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import medo.common.auth.properties.SecurityProperties;

@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityPropertiesConfiguration {
}
