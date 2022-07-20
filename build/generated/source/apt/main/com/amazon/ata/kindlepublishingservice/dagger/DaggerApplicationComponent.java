package com.amazon.ata.kindlepublishingservice.dagger;

import com.amazon.ata.kindlepublishingservice.activity.GetBookActivity;
import com.amazon.ata.kindlepublishingservice.activity.GetPublishingStatusActivity;
import com.amazon.ata.kindlepublishingservice.activity.RemoveBookFromCatalogActivity;
import com.amazon.ata.kindlepublishingservice.activity.RemoveBookFromCatalogActivity_Factory;
import com.amazon.ata.kindlepublishingservice.activity.SubmitBookForPublishingActivity;
import com.amazon.ata.kindlepublishingservice.clients.RecommendationsServiceClient;
import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.CatalogDao_Factory;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao_Factory;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequestManager;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublishTask_Factory;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublisher;
import com.amazon.ata.recommendationsservice.RecommendationsService_Factory;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerApplicationComponent implements ApplicationComponent {
  private Provider<DynamoDBMapper> provideDynamoDBMapperProvider;

  private Provider<RecommendationsServiceClient> provideRecommendationsServiceClientProvider;

  private Provider<BookPublishRequestManager> providesBookPublishRequestManagerProvider;

  private Provider<ScheduledExecutorService> provideBookPublisherSchedulerProvider;

  private CatalogDao_Factory catalogDaoProvider;

  private PublishingStatusDao_Factory publishingStatusDaoProvider;

  private BookPublishTask_Factory bookPublishTaskProvider;

  private Provider<BookPublisher> provideBookPublisherProvider;

  private Provider<ATAKindlePublishingServiceManager> aTAKindlePublishingServiceManagerProvider;

  private DaggerApplicationComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ApplicationComponent create() {
    return new Builder().build();
  }

  private CatalogDao getCatalogDao() {
    return new CatalogDao(provideDynamoDBMapperProvider.get());
  }

  private PublishingStatusDao getPublishingStatusDao() {
    return new PublishingStatusDao(provideDynamoDBMapperProvider.get());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideDynamoDBMapperProvider =
        DoubleCheck.provider(
            DataAccessModule_ProvideDynamoDBMapperFactory.create(builder.dataAccessModule));
    this.provideRecommendationsServiceClientProvider =
        DoubleCheck.provider(
            ClientsModule_ProvideRecommendationsServiceClientFactory.create(
                builder.clientsModule, RecommendationsService_Factory.create()));
    this.providesBookPublishRequestManagerProvider =
        DoubleCheck.provider(
            PublishingModule_ProvidesBookPublishRequestManagerFactory.create(
                builder.publishingModule));
    this.provideBookPublisherSchedulerProvider =
        DoubleCheck.provider(
            PublishingModule_ProvideBookPublisherSchedulerFactory.create(builder.publishingModule));
    this.catalogDaoProvider = CatalogDao_Factory.create(provideDynamoDBMapperProvider);
    this.publishingStatusDaoProvider =
        PublishingStatusDao_Factory.create(provideDynamoDBMapperProvider);
    this.bookPublishTaskProvider =
        BookPublishTask_Factory.create(
            providesBookPublishRequestManagerProvider,
            catalogDaoProvider,
            publishingStatusDaoProvider);
    this.provideBookPublisherProvider =
        DoubleCheck.provider(
            PublishingModule_ProvideBookPublisherFactory.create(
                builder.publishingModule,
                provideBookPublisherSchedulerProvider,
                bookPublishTaskProvider));
    this.aTAKindlePublishingServiceManagerProvider =
        DoubleCheck.provider(
            ATAKindlePublishingServiceManager_Factory.create(provideBookPublisherProvider));
  }

  @Override
  public GetBookActivity provideGetBookActivity() {
    return new GetBookActivity(getCatalogDao(), provideRecommendationsServiceClientProvider.get());
  }

  @Override
  public GetPublishingStatusActivity provideGetPublishingStatusActivity() {
    return new GetPublishingStatusActivity(getPublishingStatusDao());
  }

  @Override
  public RemoveBookFromCatalogActivity provideRemoveBookFromCatalogActivity() {
    return RemoveBookFromCatalogActivity_Factory.newRemoveBookFromCatalogActivity(getCatalogDao());
  }

  @Override
  public SubmitBookForPublishingActivity provideSubmitBookForPublishingActivity() {
    return new SubmitBookForPublishingActivity(
        getPublishingStatusDao(), getCatalogDao(), providesBookPublishRequestManagerProvider.get());
  }

  @Override
  public ATAKindlePublishingServiceManager provideATAKindlePublishingServiceManager() {
    return aTAKindlePublishingServiceManagerProvider.get();
  }

  public static final class Builder {
    private DataAccessModule dataAccessModule;

    private ClientsModule clientsModule;

    private PublishingModule publishingModule;

    private Builder() {}

    public ApplicationComponent build() {
      if (dataAccessModule == null) {
        this.dataAccessModule = new DataAccessModule();
      }
      if (clientsModule == null) {
        this.clientsModule = new ClientsModule();
      }
      if (publishingModule == null) {
        this.publishingModule = new PublishingModule();
      }
      return new DaggerApplicationComponent(this);
    }

    public Builder clientsModule(ClientsModule clientsModule) {
      this.clientsModule = Preconditions.checkNotNull(clientsModule);
      return this;
    }

    public Builder dataAccessModule(DataAccessModule dataAccessModule) {
      this.dataAccessModule = Preconditions.checkNotNull(dataAccessModule);
      return this;
    }

    public Builder publishingModule(PublishingModule publishingModule) {
      this.publishingModule = Preconditions.checkNotNull(publishingModule);
      return this;
    }
  }
}
