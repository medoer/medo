package medo.common.swagger2.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * @author: bryce
 * @date: 2020-08-04
 */
@ConditionalOnProperty(name = "medo.swagger.enabled", matchIfMissing = true)
@Import({Swagger2DocumentationConfiguration.class})
public class Swagger2Configuration {}
