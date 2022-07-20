package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.clients.RecommendationsServiceClient;
import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GetBookActivity_Factory implements Factory<GetBookActivity> {
  private final Provider<CatalogDao> catalogDaoProvider;

  private final Provider<RecommendationsServiceClient> recommendationServiceClientProvider;

  public GetBookActivity_Factory(
      Provider<CatalogDao> catalogDaoProvider,
      Provider<RecommendationsServiceClient> recommendationServiceClientProvider) {
    this.catalogDaoProvider = catalogDaoProvider;
    this.recommendationServiceClientProvider = recommendationServiceClientProvider;
  }

  @Override
  public GetBookActivity get() {
    return new GetBookActivity(catalogDaoProvider.get(), recommendationServiceClientProvider.get());
  }

  public static GetBookActivity_Factory create(
      Provider<CatalogDao> catalogDaoProvider,
      Provider<RecommendationsServiceClient> recommendationServiceClientProvider) {
    return new GetBookActivity_Factory(catalogDaoProvider, recommendationServiceClientProvider);
  }
}
