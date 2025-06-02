package cn.byssted.bbs.bbsrd.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置类
 */
@Configuration
@MapperScan("cn.byssted.bbs.bbsrd.mapper")
public class MyBatisPlusConfig {
}
