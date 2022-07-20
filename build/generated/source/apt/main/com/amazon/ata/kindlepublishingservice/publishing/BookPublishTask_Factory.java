package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookPublishTask_Factory implements Factory<BookPublishTask> {
  private final Provider<BookPublishRequestManager> bookPublishRequestManagerProvider;

  private final Provider<CatalogDao> catalogDaoProvider;

  private final Provider<PublishingStatusDao> publishingStatusDaoProvider;

  public BookPublishTask_Factory(
      Provider<BookPublishRequestManager> bookPublishRequestManagerProvider,
      Provider<CatalogDao> catalogDaoProvider,
      Provider<PublishingStatusDao> publishingStatusDaoProvider) {
    this.bookPublishRequestManagerProvider = bookPublishRequestManagerProvider;
    this.catalogDaoProvider = catalogDaoProvider;
    this.publishingStatusDaoProvider = publishingStatusDaoProvider;
  }

  @Override
  public BookPublishTask get() {
    return new BookPublishTask(
        bookPublishRequestManagerProvider.get(),
        catalogDaoProvider.get(),
        publishingStatusDaoProvider.get());
  }

  public static BookPublishTask_Factory create(
      Provider<BookPublishRequestManager> bookPublishRequestManagerProvider,
      Provider<CatalogDao> catalogDaoProvider,
      Provider<PublishingStatusDao> publishingStatusDaoProvider) {
    return new BookPublishTask_Factory(
        bookPublishRequestManagerProvider, catalogDaoProvider, publishingStatusDaoProvider);
  }
}
