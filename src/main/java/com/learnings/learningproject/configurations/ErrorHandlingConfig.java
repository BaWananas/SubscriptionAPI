package com.learnings.learningproject.configurations;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class ErrorHandlingConfig {

    @Configuration
    @Profile("dev")
    public static class ErrorHandlingDev {

        /**
         * Redefine specific errors.
         * @return ErrorAttributes
         */
        @Bean
        public ErrorAttributes errorAttributes() {
            return new DefaultErrorAttributes() {
                @Override
                public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                    Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
                    return errorAttributes;
                }
            };
        }
    }

    @Configuration
    @Profile("prod")
    public static class ErrorHandlingProd {

        /**
         * Remove error traces for a production environment.
         * @return ErrorAttributes
         */
        @Bean
        public ErrorAttributes errorAttributes() {
            return new DefaultErrorAttributes() {
                @Override
                public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                    Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
                    errorAttributes.remove("trace");
                    return errorAttributes;
                }
            };
        }
    }

}
