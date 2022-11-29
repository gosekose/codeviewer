package codeview.main.businessservice.membergroup.infra.config;

import codeview.main.businessservice.membergroup.presentation.interceptor.GroupAdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GroupAdminConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(groupAdminInterceptor())
                .order(1)
                .addPathPatterns("/api/v1/groups/admin/**")
                .excludePathPatterns("/static/**", "/css/**", "/*.icon", "/error", "/js/**",
                        "/api/v1/groups/admin/errors",
                        "/api/v1/groups/admin/list",
                        "/api/v1/groups/admin/new");
    }

    @Bean
    public GroupAdminInterceptor groupAdminInterceptor() {
        return new GroupAdminInterceptor();
    }
}
