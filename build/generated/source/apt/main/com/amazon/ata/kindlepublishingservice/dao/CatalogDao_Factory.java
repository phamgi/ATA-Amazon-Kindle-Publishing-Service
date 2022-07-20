package com.amazon.ata.kindlepublishingservice.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogDao_Factory implements Factory<CatalogDao> {
  private final Provider<DynamoDBMapper> dynamoDbMapperProvider;

  public CatalogDao_Factory(Provider<DynamoDBMapper> dynamoDbMapperProvider) {
    this.dynamoDbMapperProvider = dynamoDbMapperProvider;
  }

  @Override
  public CatalogDao get() {
    return new CatalogDao(dynamoDbMapperProvider.get());
  }

  public static CatalogDao_Factory create(Provider<DynamoDBMapper> dynamoDbMapperProvider) {
    return new CatalogDao_Factory(dynamoDbMapperProvider);
  }
}
