package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RemoveBookFromCatalogActivity_Factory
    implements Factory<RemoveBookFromCatalogActivity> {
  private final Provider<CatalogDao> catalogDaoProvider;

  public RemoveBookFromCatalogActivity_Factory(Provider<CatalogDao> catalogDaoProvider) {
    this.catalogDaoProvider = catalogDaoProvider;
  }

  @Override
  public RemoveBookFromCatalogActivity get() {
    return new RemoveBookFromCatalogActivity(catalogDaoProvider.get());
  }

  public static RemoveBookFromCatalogActivity_Factory create(
      Provider<CatalogDao> catalogDaoProvider) {
    return new RemoveBookFromCatalogActivity_Factory(catalogDaoProvider);
  }

  public static RemoveBookFromCatalogActivity newRemoveBookFromCatalogActivity(
      CatalogDao catalogDao) {
    return new RemoveBookFromCatalogActivity(catalogDao);
  }
}
