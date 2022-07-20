package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.converters.BookPublishRequestConverter;
import com.amazon.ata.kindlepublishingservice.converters.PublishingItemConverter;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatus;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetBookResponse;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;
import com.amazonaws.services.lambda.runtime.Context;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetPublishingStatusActivity {
    private PublishingStatusDao publishingStatusDao;



    @Inject
    public GetPublishingStatusActivity(PublishingStatusDao publishingStatusDao) {
        this.publishingStatusDao = publishingStatusDao;
    }

    public GetPublishingStatusResponse execute(GetPublishingStatusRequest publishingStatusRequest) {
        /*
         {
        CatalogItemVersion catalogItem = catalogDao.getBookFromCatalog(request.getBookId());
        List<BookRecommendation> recommendations = recommendationServiceClient.getBookRecommendations(
            BookGenre.valueOf(catalogItem.getGenre().name()));
        return GetBookResponse.builder()
            .withBook(CatalogItemConverter.toBook(catalogItem))
            .withRecommendations(RecommendationsCoralConverter.toCoral(recommendations))
            .build();
        }
         */

        List<PublishingStatusItem> publishingStatusItems = publishingStatusDao.getPublishingStatuses(publishingStatusRequest.getPublishingRecordId());
        List<PublishingStatusRecord> publishingStatusRecords = new ArrayList<>();
        for(PublishingStatusItem item : publishingStatusItems) {
            PublishingStatusRecord record = PublishingItemConverter.toPublishingStatusRecord(item);
            publishingStatusRecords.add(record);
        }
        return GetPublishingStatusResponse
                .builder()
                .withPublishingStatusHistory(publishingStatusRecords)
                .build();


    }
}
