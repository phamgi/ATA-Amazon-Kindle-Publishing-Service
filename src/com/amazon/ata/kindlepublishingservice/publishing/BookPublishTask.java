package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;

import javax.inject.Inject;

public class BookPublishTask implements Runnable {
    BookPublishRequestManager bookPublishRequestManager;
    PublishingStatusDao publishingStatusDao;
    CatalogDao catalogDao;

    @Inject
    public BookPublishTask(BookPublishRequestManager bookPublishRequestManager, CatalogDao catalogDao, PublishingStatusDao publishingStatusDao) {
        this.bookPublishRequestManager = bookPublishRequestManager;
        this.publishingStatusDao = publishingStatusDao;
        this.catalogDao = catalogDao;
    }

    @Override
    public void run() {
        System.out.println("BookPublishingTask is running!!!");

        CatalogItemVersion itemVersion = new CatalogItemVersion();
        BookPublishRequest request = bookPublishRequestManager.getBookPublishRequestToProcess();


        try {
            if (request == null) {   // Check if there is anything in BookPublishRequestManagerToProcess
                return;
            }

            String publishingRecordId = request.getPublishingRecordId();                // Get the publishingRecordId from the request
            String bookId = request.getBookId();                                        // Get the bookId from the request


            PublishingStatusItem item = publishingStatusDao                             // Updating the PublishingStatusItem in DynamoDB
                    .setPublishingStatus(publishingRecordId,                            // Set publishingRecordId
                            PublishingRecordStatus.IN_PROGRESS,                         // Set status as IN_PROGRESS
                            bookId);                                                    // Set bookId

            KindleFormattedBook kindleBook = KindleFormatConverter.format(request);     // Convert the request to a KindleFormattedBook

            itemVersion = catalogDao.createOrUpdate(kindleBook);                        // Add a new book to CatalogItem table

            publishingStatusDao
                    .setPublishingStatus(publishingRecordId,                            // Now we have finished adding new book to Catalog => Set publishingRecordId (In Publishing table)
                            PublishingRecordStatus.SUCCESSFUL,                          // Set status as SUCCESS (In Publishing table)
                            itemVersion.getBookId());                                                    // Set bookId (In Publishing table)


        } catch (Exception e) {                                                         // Catch any exception occur during processing
            publishingStatusDao
                    .setPublishingStatus(request.getPublishingRecordId(),               // Set publishingRecordId
                            PublishingRecordStatus.FAILED,                              // Set status as FAILED
                            request.getBookId(),                                        // Set  bookId
                            "something went wrong");                           // A message
        }


    }
}

//        try {
//            if (bookPublishRequestManager.check() == null) { // get the 1st item in the queue, if null
//                return; // exit the program
//            } else {  // we got a non null request to process
//                if (bookPublishRequestManager.check().getBookId() != null) { // bookId is here
//                    BookPublishRequest bookPublishRequest = bookPublishRequestManager.getBookPublishRequestToProcess();
//                    PublishingStatusItem item = publishingStatusDao.setPublishingStatus(bookPublishRequest.getPublishingRecordId()
//                            , PublishingRecordStatus.IN_PROGRESS, bookPublishRequest.getBookId());
//                    // publishingStatusDao.addPublishingStatus(bookPublishRequest, item.getStatus());
//                } else { // bookId is not here
//                    BookPublishRequest publishRequest = bookPublishRequestManager.getBookPublishRequestToProcess();
//                    // catalogDao.addNewBook(publishRequest); // add the new book to DynamoDB
//                }
//
//                BookPublishRequest bookPublishRequest = bookPublishRequestManager.getBookPublishRequestToProcess();
//                PublishingStatusItem item = publishingStatusDao.setPublishingStatus(bookPublishRequest.getPublishingRecordId()
//                        , PublishingRecordStatus.SUCCESSFUL, bookPublishRequest.getBookId());
//            }
//        } catch (Exception e) {
//            BookPublishRequest bookPublishRequest = bookPublishRequestManager.getBookPublishRequestToProcess();
//            catalogDao.setInactiveForOlderBookVersion(bookPublishRequest.getBookId());
//            PublishingStatusItem item = publishingStatusDao.setPublishingStatus(bookPublishRequest.getPublishingRecordId()
//                    , PublishingRecordStatus.FAILED, bookPublishRequest.getBookId(), "an error has occurred");
//
//        }
