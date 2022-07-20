package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.converters.CatalogItemConverter;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;
import com.amazon.ata.kindlepublishingservice.models.Book;
import com.amazon.ata.kindlepublishingservice.models.requests.SubmitBookForPublishingRequest;
import com.amazon.ata.kindlepublishingservice.models.response.RemoveBookFromCatalogResponse;
import com.amazon.ata.kindlepublishingservice.models.response.SubmitBookForPublishingResponse;
import com.amazon.ata.kindlepublishingservice.converters.BookPublishRequestConverter;
import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequest;

import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequestManager;
import com.amazon.ata.kindlepublishingservice.utils.KindlePublishingUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of the SubmitBookForPublishingActivity for ATACurriculumKindlePublishingService's
 * SubmitBookForPublishing API.
 * <p>
 * This API allows the client to submit a new book to be published in the catalog or update an existing book.
 */
public class SubmitBookForPublishingActivity {

    private PublishingStatusDao publishingStatusDao;
    private CatalogDao catalogDao;
    private BookPublishRequestManager bookPublishRequestManager;
    /**
     * Instantiates a new SubmitBookForPublishingActivity object.
     *
     * @param publishingStatusDao PublishingStatusDao to access the publishing status table.
     */
    @Inject
    public SubmitBookForPublishingActivity(PublishingStatusDao publishingStatusDao, CatalogDao catalogDao, BookPublishRequestManager bookPublishRequestManager) {
        this.publishingStatusDao = publishingStatusDao;
        this.catalogDao = catalogDao;
        this.bookPublishRequestManager = bookPublishRequestManager;
    }

    /**
     * Submits the book in the request for publishing.
     *
     * @param request Request object containing the book data to be published. If the request is updating an existing
     *                book, then the corresponding book id should be provided. Otherwise, the request will be treated
     *                as a new book.
     * @return SubmitBookForPublishingResponse Response object that includes the publishing status id, which can be used
     * to check the publishing state of the book.
     */
    public SubmitBookForPublishingResponse execute(SubmitBookForPublishingRequest request) {
        // TODO: If there is a book ID in the request, validate it exists in our catalog
        // TODO: Submit the BookPublishRequest for processing

        System.out.println("Memory address of BookPublishRequestManager in SubmitActivity: " + this.bookPublishRequestManager.toString());
        if (request.getBookId() != null) {                                                                              // bookId is present in the request
            if (!catalogDao.validateBookExists(request.getBookId())) {                                                  // validate if the bookId exist in the table
                throw new BookNotFoundException(String.format("No book found for id: %s", request.getBookId()));
            }
        }


        final BookPublishRequest bookPublishRequest = BookPublishRequestConverter.toBookPublishRequest(request);        // create a new BookPublishRequest

        this.bookPublishRequestManager.addBookPublishRequest(bookPublishRequest);                                       // add the current BookPublishRequest to BookPublishRequestManager

//        System.out.println("From Activity, request in  the queue" + bookPublishRequestManager.getBookPublishRequestToProcess());
        PublishingStatusItem item = publishingStatusDao.setPublishingStatus(bookPublishRequest.getPublishingRecordId(), // set publishingRecordId in dynamoDB
                PublishingRecordStatus.QUEUED,                                                                          // set status as Queued in DynamoDB
                bookPublishRequest.getBookId());                                                                        // set the bookId in DynamoDB

        return SubmitBookForPublishingResponse.builder()                                                                // Building a new SubmitBookForPublishingResponse
                .withPublishingRecordId(item.getPublishingRecordId())                                                   // with a new PublishingRecordId generated when converting BookPublishRequest from the "request"
                .build();                                                                                               // finish build
    }
}
