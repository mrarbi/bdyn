package com.orbit.dynamix.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

import com.orbit.dynamix.security.Utilisateur;

@Configuration
@Lazy
public class BeanConfig {

	@Bean
//	@Scope("session")
	public Utilisateur getUtilisateur() {
		if (null != SecurityContextHolder.getContext().getAuthentication()) {
			return (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return null;
	}
	
//	@Bean
//	public Mongo mongo() throws Exception {
//		return new MongoClient();
//	}
	
//	@Bean
//    public ICreativeService getCreativeService() {
//        return new CreativeService();
//    }
//
//    @Bean
//    public IMatchingService getMatchingService() {
//        return new MatchingService();
//    }

	@Bean
	@Scope("singleton")
	public ISaveCreaServiceAws getSaveCreaServiceAws() {
		return new SaveCreaServiceAws();
	}
}
