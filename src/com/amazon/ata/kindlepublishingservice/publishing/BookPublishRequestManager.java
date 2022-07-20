package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Singleton
public class BookPublishRequestManager {
    public Queue<BookPublishRequest> bookPublishRequests;


    @Inject
    public BookPublishRequestManager(ConcurrentLinkedQueue<BookPublishRequest> bookPublishRequests) {
        this.bookPublishRequests = bookPublishRequests;
    }

    public void addBookPublishRequest(BookPublishRequest bookPublishRequest) {
        bookPublishRequests.add(bookPublishRequest);
    }

    public BookPublishRequest getBookPublishRequestToProcess()  {
        return this.bookPublishRequests.poll();
    }

    public BookPublishRequest peek()  {
        return this.bookPublishRequests.peek();
    }
}
