package medo.common.log.configuration;

import org.slf4j.TtlMDCAdapter;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 初始化 TtlMDCAdapter 实例，并替换 MDC 中的 adapter 对象。 TODO
 * 
 * @author: bryce
 * @date: 2020-08-05
 */
public class TtlMDCAdapterInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        //加载TtlMDCAdapter实例
        TtlMDCAdapter.getInstance();
    }

}