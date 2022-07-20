package com.amazon.ata.kindlepublishingservice.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PublishingStatusDao_Factory implements Factory<PublishingStatusDao> {
  private final Provider<DynamoDBMapper> dynamoDbMapperProvider;

  public PublishingStatusDao_Factory(Provider<DynamoDBMapper> dynamoDbMapperProvider) {
    this.dynamoDbMapperProvider = dynamoDbMapperProvider;
  }

  @Override
  public PublishingStatusDao get() {
    return new PublishingStatusDao(dynamoDbMapperProvider.get());
  }

  public static PublishingStatusDao_Factory create(
      Provider<DynamoDBMapper> dynamoDbMapperProvider) {
    return new PublishingStatusDao_Factory(dynamoDbMapperProvider);
  }
}
