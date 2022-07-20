package com.amazon.ata.kindlepublishingservice.dagger;

import com.amazon.ata.kindlepublishingservice.clients.RecommendationsServiceClient;
import com.amazon.ata.recommendationsservice.RecommendationsService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientsModule_ProvideRecommendationsServiceClientFactory
    implements Factory<RecommendationsServiceClient> {
  private final ClientsModule module;

  private final Provider<RecommendationsService> recommendationsServiceProvider;

  public ClientsModule_ProvideRecommendationsServiceClientFactory(
      ClientsModule module, Provider<RecommendationsService> recommendationsServiceProvider) {
    this.module = module;
    this.recommendationsServiceProvider = recommendationsServiceProvider;
  }

  @Override
  public RecommendationsServiceClient get() {
    return Preconditions.checkNotNull(
        module.provideRecommendationsServiceClient(recommendationsServiceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static ClientsModule_ProvideRecommendationsServiceClientFactory create(
      ClientsModule module, Provider<RecommendationsService> recommendationsServiceProvider) {
    return new ClientsModule_ProvideRecommendationsServiceClientFactory(
        module, recommendationsServiceProvider);
  }

  public static RecommendationsServiceClient proxyProvideRecommendationsServiceClient(
      ClientsModule instance, RecommendationsService recommendationsService) {
    return Preconditions.checkNotNull(
        instance.provideRecommendationsServiceClient(recommendationsService),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
