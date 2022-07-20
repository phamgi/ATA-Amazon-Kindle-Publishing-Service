package com.amazon.ata.kindlepublishingservice.publishing;

import dagger.internal.Factory;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookPublishRequestManager_Factory implements Factory<BookPublishRequestManager> {
  private final Provider<ConcurrentLinkedQueue<BookPublishRequest>> bookPublishRequestsProvider;

  public BookPublishRequestManager_Factory(
      Provider<ConcurrentLinkedQueue<BookPublishRequest>> bookPublishRequestsProvider) {
    this.bookPublishRequestsProvider = bookPublishRequestsProvider;
  }

  @Override
  public BookPublishRequestManager get() {
    return new BookPublishRequestManager(bookPublishRequestsProvider.get());
  }

  public static BookPublishRequestManager_Factory create(
      Provider<ConcurrentLinkedQueue<BookPublishRequest>> bookPublishRequestsProvider) {
    return new BookPublishRequestManager_Factory(bookPublishRequestsProvider);
  }
}
