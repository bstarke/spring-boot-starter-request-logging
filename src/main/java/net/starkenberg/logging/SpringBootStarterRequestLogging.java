package net.starkenberg.logging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties
@ConditionalOnProperty(name = "request.logging.enabled", matchIfMissing = true)
public class SpringBootStarterRequestLogging {

    @Bean
    @ConditionalOnMissingBean
    public AbstractRequestLoggingFilter logFilter() {
        AbstractRequestLoggingFilter filter = new CustomRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setIncludeClientInfo(true);
        return filter;
    }

    private class CustomRequestLoggingFilter extends AbstractRequestLoggingFilter {
        @Override
        protected void beforeRequest(HttpServletRequest httpServletRequest, String message) {
            // do nothing
        }

        @Override
        protected void afterRequest(HttpServletRequest request, String message) {
            logger.info(message);
        }
    }

}
