package org.tarak.pms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class PMSoftApplicationConfig extends WebMvcConfigurerAdapter {
    
   @Autowired
   UserSessionInterceptor userSessionInterceptor;
    
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(userSessionInterceptor);
   }
}
