package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GetPublishingStatusActivity_Factory
    implements Factory<GetPublishingStatusActivity> {
  private final Provider<PublishingStatusDao> publishingStatusDaoProvider;

  public GetPublishingStatusActivity_Factory(
      Provider<PublishingStatusDao> publishingStatusDaoProvider) {
    this.publishingStatusDaoProvider = publishingStatusDaoProvider;
  }

  @Override
  public GetPublishingStatusActivity get() {
    return new GetPublishingStatusActivity(publishingStatusDaoProvider.get());
  }

  public static GetPublishingStatusActivity_Factory create(
      Provider<PublishingStatusDao> publishingStatusDaoProvider) {
    return new GetPublishingStatusActivity_Factory(publishingStatusDaoProvider);
  }
}
