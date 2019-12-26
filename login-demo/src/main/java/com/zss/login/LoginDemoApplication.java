package com.zss.login;

import com.zss.login.filter.CompareKickOutFilter;
import com.zss.login.filter.KickOutFilter;
import com.zss.login.filter.QueueKickOutFilter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
@MapperScan({"com.zss.*.mapper"})
public class LoginDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginDemoApplication.class, args);
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec())
                .useSingleServer()
                .setAddress("redis://172.16.1.16:6379");
        return Redisson.create(config);
    }

    @ConditionalOnProperty(value = {"queue-filter.enabled"})
    @Bean
    public KickOutFilter queueKickOutFilter(){
        return new QueueKickOutFilter();
    }

    @ConditionalOnMissingBean(KickOutFilter.class)
    @Bean
    public KickOutFilter compareKickOutFilter(){
        return new CompareKickOutFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration(KickOutFilter kickOutFilter){
        log.info("基础过滤器名称：{}",kickOutFilter);
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(kickOutFilter);
        filterRegistrationBean.setName("kickOutFilter");
        filterRegistrationBean.addUrlPatterns("/user/*");
        return filterRegistrationBean;
    }

}
