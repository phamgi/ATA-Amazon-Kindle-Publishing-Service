package com.amazon.ata.kindlepublishingservice.publishing;

import dagger.internal.Factory;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookPublisher_Factory implements Factory<BookPublisher> {
  private final Provider<ScheduledExecutorService> scheduledExecutorServiceProvider;

  private final Provider<Runnable> publishTaskProvider;

  public BookPublisher_Factory(
      Provider<ScheduledExecutorService> scheduledExecutorServiceProvider,
      Provider<Runnable> publishTaskProvider) {
    this.scheduledExecutorServiceProvider = scheduledExecutorServiceProvider;
    this.publishTaskProvider = publishTaskProvider;
  }

  @Override
  public BookPublisher get() {
    return new BookPublisher(scheduledExecutorServiceProvider.get(), publishTaskProvider.get());
  }

  public static BookPublisher_Factory create(
      Provider<ScheduledExecutorService> scheduledExecutorServiceProvider,
      Provider<Runnable> publishTaskProvider) {
    return new BookPublisher_Factory(scheduledExecutorServiceProvider, publishTaskProvider);
  }
}
