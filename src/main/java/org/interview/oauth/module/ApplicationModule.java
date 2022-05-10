package org.interview.oauth.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.interview.oauth.controller.TwitterOperationsController;
import org.interview.oauth.controller.impl.TwitterOperationsControllerImpl;
import org.interview.oauth.repo.TwitterAuthRepository;
import org.interview.oauth.repo.impl.TwitterAuthRepositoryImpl;
import org.interview.oauth.service.AuthService;
import org.interview.oauth.service.JsonParsingService;
import org.interview.oauth.service.TwitService;
import org.interview.oauth.service.impl.AuthServiceImpl;
import org.interview.oauth.service.impl.JsonParsingServiceImpl;
import org.interview.oauth.service.impl.TwitServiceImpl;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(JsonParsingService.class).to(JsonParsingServiceImpl.class).in(Singleton.class);
        bind(AuthService.class).to(AuthServiceImpl.class).in(Singleton.class);
        bind(TwitterOperationsController.class).to(TwitterOperationsControllerImpl.class).in(Singleton.class);
        bind(TwitterAuthRepository.class).to(TwitterAuthRepositoryImpl.class).in(Singleton.class);
        bind(TwitService.class).to(TwitServiceImpl.class).in(Singleton.class);
    }
}
