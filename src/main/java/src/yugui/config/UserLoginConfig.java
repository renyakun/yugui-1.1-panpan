package src.yugui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import src.yugui.intercept.UserLoginInterceptor;

/**
 * @Description: 登录拦截配置类
 * @Author: XiaoPanPan
 * @Date: 2019/8/22 11:08
 */
@Configuration
public class UserLoginConfig extends WebMvcConfigurationSupport {
    @Bean
    public UserLoginInterceptor tokenAuthenticationInterceptor() {
        return new UserLoginInterceptor();
    }

    /**
     * 拦截器
     * 添加过滤放行的方法
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/report/user/login")
                .excludePathPatterns("/report/user/logout")
                .excludePathPatterns(
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html/**");
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
