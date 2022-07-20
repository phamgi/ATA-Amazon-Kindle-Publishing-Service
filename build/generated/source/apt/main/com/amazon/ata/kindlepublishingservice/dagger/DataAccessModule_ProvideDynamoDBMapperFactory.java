package com.amazon.ata.kindlepublishingservice.dagger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DataAccessModule_ProvideDynamoDBMapperFactory
    implements Factory<DynamoDBMapper> {
  private final DataAccessModule module;

  public DataAccessModule_ProvideDynamoDBMapperFactory(DataAccessModule module) {
    this.module = module;
  }

  @Override
  public DynamoDBMapper get() {
    return Preconditions.checkNotNull(
        module.provideDynamoDBMapper(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static DataAccessModule_ProvideDynamoDBMapperFactory create(DataAccessModule module) {
    return new DataAccessModule_ProvideDynamoDBMapperFactory(module);
  }

  public static DynamoDBMapper proxyProvideDynamoDBMapper(DataAccessModule instance) {
    return Preconditions.checkNotNull(
        instance.provideDynamoDBMapper(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
