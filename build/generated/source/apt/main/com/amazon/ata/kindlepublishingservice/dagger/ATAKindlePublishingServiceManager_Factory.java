package com.amazon.ata.kindlepublishingservice.dagger;

import com.amazon.ata.kindlepublishingservice.publishing.BookPublisher;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ATAKindlePublishingServiceManager_Factory
    implements Factory<ATAKindlePublishingServiceManager> {
  private final Provider<BookPublisher> bookPublisherProvider;

  public ATAKindlePublishingServiceManager_Factory(Provider<BookPublisher> bookPublisherProvider) {
    this.bookPublisherProvider = bookPublisherProvider;
  }

  @Override
  public ATAKindlePublishingServiceManager get() {
    return new ATAKindlePublishingServiceManager(bookPublisherProvider.get());
  }

  public static ATAKindlePublishingServiceManager_Factory create(
      Provider<BookPublisher> bookPublisherProvider) {
    return new ATAKindlePublishingServiceManager_Factory(bookPublisherProvider);
  }

  public static ATAKindlePublishingServiceManager newATAKindlePublishingServiceManager(
      BookPublisher bookPublisher) {
    return new ATAKindlePublishingServiceManager(bookPublisher);
  }
}
