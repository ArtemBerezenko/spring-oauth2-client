package com.example.springoauth2client.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import javax.servlet.Filter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.web.filter.CompositeFilter


@Configuration
@EnableOAuth2Client
class AuthenticateConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    @Qualifier("oauth2ClientContext")
    lateinit var oauth2ClientContext: OAuth2ClientContext

    override fun configure(http: HttpSecurity) {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/error**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter::class.java)
    }

    @Bean
    fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter) =
            FilterRegistrationBean<OAuth2ClientContextFilter>().apply {
                this.filter = filter
                this.order = -100
            }

    @Bean
    @ConfigurationProperties("facebook.client")
    fun facebook() = AuthorizationCodeResourceDetails()

    @Bean
    @ConfigurationProperties("facebook.resource")
    fun facebookResource() = ResourceServerProperties()

    @Bean
    @ConfigurationProperties("github.client")
    fun github() = AuthorizationCodeResourceDetails()

    @Bean
    @ConfigurationProperties("github.resource")
    fun githubResource() = ResourceServerProperties()


    private fun ssoFilter(): Filter {
        val filter = CompositeFilter()
        val filters = mutableListOf<Filter>()

        val facebookFilter = OAuth2ClientAuthenticationProcessingFilter("/login/facebook")
        val facebookTemplate = OAuth2RestTemplate(facebook(), oauth2ClientContext)
        facebookFilter.restTemplate = facebookTemplate
        var tokenServices = UserInfoTokenServices(facebookResource().userInfoUri, facebook().clientId)
        tokenServices.setRestTemplate(facebookTemplate)
        facebookFilter.setTokenServices(tokenServices)
        filters.add(facebookFilter)

        val githubFilter = OAuth2ClientAuthenticationProcessingFilter("/login/github")
        val githubTemplate = OAuth2RestTemplate(github(), oauth2ClientContext)
        githubFilter.restTemplate = githubTemplate
        tokenServices = UserInfoTokenServices(githubResource().userInfoUri, github().clientId)
        tokenServices.setRestTemplate(githubTemplate)
        githubFilter.setTokenServices(tokenServices)
        filters.add(githubFilter)

        filter.setFilters(filters)
        return filter
    }


}