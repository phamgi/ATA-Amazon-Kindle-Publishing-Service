package com.amazon.ata.kindlepublishingservice.dagger;

import com.amazon.ata.kindlepublishingservice.publishing.BookPublishTask;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublisher;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PublishingModule_ProvideBookPublisherFactory implements Factory<BookPublisher> {
  private final PublishingModule module;

  private final Provider<ScheduledExecutorService> scheduledExecutorServiceProvider;

  private final Provider<BookPublishTask> bookPublishTaskProvider;

  public PublishingModule_ProvideBookPublisherFactory(
      PublishingModule module,
      Provider<ScheduledExecutorService> scheduledExecutorServiceProvider,
      Provider<BookPublishTask> bookPublishTaskProvider) {
    this.module = module;
    this.scheduledExecutorServiceProvider = scheduledExecutorServiceProvider;
    this.bookPublishTaskProvider = bookPublishTaskProvider;
  }

  @Override
  public BookPublisher get() {
    return Preconditions.checkNotNull(
        module.provideBookPublisher(
            scheduledExecutorServiceProvider.get(), bookPublishTaskProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static PublishingModule_ProvideBookPublisherFactory create(
      PublishingModule module,
      Provider<ScheduledExecutorService> scheduledExecutorServiceProvider,
      Provider<BookPublishTask> bookPublishTaskProvider) {
    return new PublishingModule_ProvideBookPublisherFactory(
        module, scheduledExecutorServiceProvider, bookPublishTaskProvider);
  }

  public static BookPublisher proxyProvideBookPublisher(
      PublishingModule instance,
      ScheduledExecutorService scheduledExecutorService,
      BookPublishTask bookPublishTask) {
    return Preconditions.checkNotNull(
        instance.provideBookPublisher(scheduledExecutorService, bookPublishTask),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
