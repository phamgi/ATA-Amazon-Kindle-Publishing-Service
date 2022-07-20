package com.amazon.ata.kindlepublishingservice.clients;

import com.amazon.ata.recommendationsservice.RecommendationsService;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RecommendationsServiceClient_Factory
    implements Factory<RecommendationsServiceClient> {
  private final Provider<RecommendationsService> serviceProvider;

  public RecommendationsServiceClient_Factory(Provider<RecommendationsService> serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  @Override
  public RecommendationsServiceClient get() {
    return new RecommendationsServiceClient(serviceProvider.get());
  }

  public static RecommendationsServiceClient_Factory create(
      Provider<RecommendationsService> serviceProvider) {
    return new RecommendationsServiceClient_Factory(serviceProvider);
  }
}
