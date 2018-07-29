package com.msrk.es.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class LoggingConfiguration {

	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
	    crlf.setIncludeClientInfo(true);
	    crlf.setIncludeQueryString(true);
	    crlf.setIncludePayload(true);
	    return crlf;
	}
	
	@Bean
	public FilterRegistrationBean loggingFilterRegistration() {
	    FilterRegistrationBean registration = new FilterRegistrationBean(requestLoggingFilter());
	    registration.addUrlPatterns("/*");
	    return registration;
	}
	
	
	public CustomizableTraceInterceptor customizableTraceInterceptor() {
	    CustomizableTraceInterceptor cti = new CustomizableTraceInterceptor();
	    cti.setEnterMessage("Entering method '" + CustomizableTraceInterceptor.PLACEHOLDER_METHOD_NAME + "("+ CustomizableTraceInterceptor.PLACEHOLDER_ARGUMENTS+")' of class [" + CustomizableTraceInterceptor.PLACEHOLDER_TARGET_CLASS_NAME + "]");
	    cti.setExitMessage("Exiting method '" + CustomizableTraceInterceptor.PLACEHOLDER_METHOD_NAME + "' of class [" + CustomizableTraceInterceptor.PLACEHOLDER_TARGET_CLASS_NAME + "] took " + CustomizableTraceInterceptor.PLACEHOLDER_INVOCATION_TIME+"ms.");

	    return cti;
	}
	
	@Bean
	public Advisor traceAdvisor() {
	    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	    pointcut.setExpression("execution(public * com.msrk.es.repository.*.*(..))");
	    return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
	}
	
}
