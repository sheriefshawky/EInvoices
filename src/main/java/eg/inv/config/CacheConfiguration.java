package eg.inv.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, eg.inv.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, eg.inv.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, eg.inv.domain.User.class.getName());
            createCache(cm, eg.inv.domain.Authority.class.getName());
            createCache(cm, eg.inv.domain.User.class.getName() + ".authorities");
            createCache(cm, eg.inv.domain.DocumentType.class.getName());
            createCache(cm, eg.inv.domain.DocumentType.class.getName() + ".documentTypeVersions");
            createCache(cm, eg.inv.domain.DocumentTypeVersion.class.getName());
            createCache(cm, eg.inv.domain.DocumentTypeVersion.class.getName() + ".workflowParameters");
            createCache(cm, eg.inv.domain.WorkflowParameters.class.getName());
            createCache(cm, eg.inv.domain.Document.class.getName());
            createCache(cm, eg.inv.domain.Document.class.getName() + ".invoiceLines");
            createCache(cm, eg.inv.domain.Document.class.getName() + ".taxTotals");
            createCache(cm, eg.inv.domain.Document.class.getName() + ".signatures");
            createCache(cm, eg.inv.domain.Issuer.class.getName());
            createCache(cm, eg.inv.domain.IssuerAddress.class.getName());
            createCache(cm, eg.inv.domain.Receiver.class.getName());
            createCache(cm, eg.inv.domain.ReceiverAddress.class.getName());
            createCache(cm, eg.inv.domain.Payment.class.getName());
            createCache(cm, eg.inv.domain.Delivery.class.getName());
            createCache(cm, eg.inv.domain.InvoiceLine.class.getName());
            createCache(cm, eg.inv.domain.InvoiceLine.class.getName() + ".taxableItems");
            createCache(cm, eg.inv.domain.Value.class.getName());
            createCache(cm, eg.inv.domain.Discount.class.getName());
            createCache(cm, eg.inv.domain.TaxableItem.class.getName());
            createCache(cm, eg.inv.domain.TaxTotal.class.getName());
            createCache(cm, eg.inv.domain.Signature.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
