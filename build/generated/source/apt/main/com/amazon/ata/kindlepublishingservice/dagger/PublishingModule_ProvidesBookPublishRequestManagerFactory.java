package com.amazon.ata.kindlepublishingservice.dagger;

import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequestManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PublishingModule_ProvidesBookPublishRequestManagerFactory
    implements Factory<BookPublishRequestManager> {
  private final PublishingModule module;

  public PublishingModule_ProvidesBookPublishRequestManagerFactory(PublishingModule module) {
    this.module = module;
  }

  @Override
  public BookPublishRequestManager get() {
    return Preconditions.checkNotNull(
        module.providesBookPublishRequestManager(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static PublishingModule_ProvidesBookPublishRequestManagerFactory create(
      PublishingModule module) {
    return new PublishingModule_ProvidesBookPublishRequestManagerFactory(module);
  }

  public static BookPublishRequestManager proxyProvidesBookPublishRequestManager(
      PublishingModule instance) {
    return Preconditions.checkNotNull(
        instance.providesBookPublishRequestManager(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
