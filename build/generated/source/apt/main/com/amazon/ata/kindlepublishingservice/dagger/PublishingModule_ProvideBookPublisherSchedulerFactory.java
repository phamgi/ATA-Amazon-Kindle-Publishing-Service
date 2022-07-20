package com.amazon.ata.kindlepublishingservice.dagger;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PublishingModule_ProvideBookPublisherSchedulerFactory
    implements Factory<ScheduledExecutorService> {
  private final PublishingModule module;

  public PublishingModule_ProvideBookPublisherSchedulerFactory(PublishingModule module) {
    this.module = module;
  }

  @Override
  public ScheduledExecutorService get() {
    return Preconditions.checkNotNull(
        module.provideBookPublisherScheduler(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static PublishingModule_ProvideBookPublisherSchedulerFactory create(
      PublishingModule module) {
    return new PublishingModule_ProvideBookPublisherSchedulerFactory(module);
  }

  public static ScheduledExecutorService proxyProvideBookPublisherScheduler(
      PublishingModule instance) {
    return Preconditions.checkNotNull(
        instance.provideBookPublisherScheduler(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
