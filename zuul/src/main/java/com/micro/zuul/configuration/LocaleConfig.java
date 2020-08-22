package com.micro.zuul.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@Slf4j
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource rs = new ReloadableResourceBundleMessageSource();
        rs.setBasename("classpath:locale/messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new LocaleResolverByHeader();
    }

//    @Bean
//    public LocaleChangeInterceptor localeInterceptor() {
//        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
//        localeInterceptor.setParamName("language");
//        return localeInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeInterceptor());
//    }
}

@Slf4j
class LocaleResolverByHeader extends AcceptHeaderLocaleResolver {

    List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("es"),
            new Locale("fr"),
            new Locale("es", "MX"),
            new Locale("zh"),
            new Locale("ja"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (StringUtils.isBlank(request.getHeader("Accept-Language"))) {
            return Locale.getDefault();
        }

        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
        return Locale.lookup(list, LOCALES);
    }
}
