package com.amazon.ata.kindlepublishingservice.converters;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.Book;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;

import java.util.List;

public class PublishingItemConverter {

    private PublishingItemConverter() {}

    /**
     * Converts the given {@link com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem} object into the corresponding Coral Book object.
     *
     * @param publishingStatusItem CatalogItem item to convert.
     * @return Book Coral Book object.
     */
    public static PublishingStatusRecord toPublishingStatusRecord(PublishingStatusItem publishingStatusItem) {
        return PublishingStatusRecord.builder()
                .withStatus(publishingStatusItem.getStatus().name())
                .withBookId(publishingStatusItem.getBookId())
                .withStatusMessage(publishingStatusItem.getStatusMessage())
                .build();

    }
}
