package com.amazon.ata.kindlepublishingservice.dao;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;
import com.amazon.ata.kindlepublishingservice.exceptions.PublishingStatusNotFoundException;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequest;
import com.amazon.ata.kindlepublishingservice.utils.KindlePublishingUtils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Accesses the Publishing Status table.
 */
public class PublishingStatusDao {

    private static final String ADDITIONAL_NOTES_PREFIX = " Additional Notes: ";
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new PublishingStatusDao object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the publishing status table.
     */
    @Inject
    public PublishingStatusDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Updates the publishing status table for the given publishingRecordId with the provided
     * publishingRecordStatus. If the bookId is provided it will also be stored in the record.
     *
     * @param publishingRecordId The id of the record to update
     * @param publishingRecordStatus The PublishingRecordStatus to save into the table.
     * @param bookId The id of the book associated with the request, may be null
     * @return The stored PublishingStatusItem.
     */
    public PublishingStatusItem setPublishingStatus(String publishingRecordId,
                                                    PublishingRecordStatus publishingRecordStatus,
                                                    String bookId) {
        return setPublishingStatus(publishingRecordId, publishingRecordStatus, bookId, null);
    }

    /**
     * Updates the publishing status table for the given publishingRecordId with the provided
     * publishingRecordStatus. If the bookId is provided it will also be stored in the record. If
     * a message is provided, it will be appended to the publishing status message in the datastore.
     *
     * @param publishingRecordId The id of the record to update
     * @param publishingRecordStatus The PublishingRecordStatus to save into the table.
     * @param bookId The id of the book associated with the request, may be null
     * @param message additional notes stored with the status
     * @return The stored PublishingStatusItem.
     */
    public PublishingStatusItem setPublishingStatus(String publishingRecordId,
                                                    PublishingRecordStatus publishingRecordStatus,
                                                    String bookId,
                                                    String message) {
        String statusMessage = KindlePublishingUtils.generatePublishingStatusMessage(publishingRecordStatus);
        if (StringUtils.isNotBlank(message)) {
            statusMessage = new StringBuffer()
                .append(statusMessage)
                .append(ADDITIONAL_NOTES_PREFIX)
                .append(message)
                .toString();
        }

        PublishingStatusItem item = new PublishingStatusItem();
        item.setPublishingRecordId(publishingRecordId);
        item.setStatus(publishingRecordStatus);
        item.setStatusMessage(statusMessage);
        item.setBookId(bookId);
        dynamoDbMapper.save(item);
        return item;
    }

    public List<PublishingStatusItem> getPublishingStatuses(String publishingStatusId) {
        Map<String, AttributeValue> searchCondition = new HashMap<>();
        searchCondition.put(":publishingRecordId", new AttributeValue().withS(publishingStatusId));
        DynamoDBQueryExpression<PublishingStatusItem> queryExpression = new DynamoDBQueryExpression<PublishingStatusItem>()
                .withKeyConditionExpression("publishingRecordId = :publishingRecordId")
                .withExpressionAttributeValues(searchCondition);
        List<PublishingStatusItem> publishingStatusItem = dynamoDbMapper.query(PublishingStatusItem.class, queryExpression);

        if(publishingStatusItem.isEmpty()) {
            throw new PublishingStatusNotFoundException(String.format("No book status  found for publishingDd: %s", publishingStatusId));
        }

        return publishingStatusItem;
    }

//    public void addPublishingStatus(BookPublishRequest bookPublishRequest, PublishingRecordStatus status) {
//        PublishingStatusItem item = new PublishingStatusItem();
//        item.setBookId(bookPublishRequest.getBookId());
//        item.setPublishingRecordId(bookPublishRequest.getPublishingRecordId());
//        item.setStatus(status);
//
//        dynamoDbMapper.save(item);
//    }
}