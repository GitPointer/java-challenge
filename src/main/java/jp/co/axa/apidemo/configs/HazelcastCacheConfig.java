package jp.co.axa.apidemo.configs;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class HazelcastCacheConfig {
    @Bean
    public com.hazelcast.config.Config getHazelcastConfig() {

        return new Config().setInstanceName("employeeCacheInstance")
                .addMapConfig(
                        new MapConfig()
                                .setName("employeeCache")
                                .setEvictionConfig(new EvictionConfig().setEvictionPolicy(EvictionPolicy.LRU))
                                .setTimeToLiveSeconds(60)
                                .setMaxIdleSeconds(40));
    }

}
