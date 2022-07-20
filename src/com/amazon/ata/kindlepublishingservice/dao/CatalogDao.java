package com.amazon.ata.kindlepublishingservice.dao;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;

import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequest;
import com.amazon.ata.kindlepublishingservice.publishing.KindleFormattedBook;
import com.amazon.ata.kindlepublishingservice.utils.KindlePublishingUtils;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class CatalogDao {

    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new CatalogDao object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the catalog table.
     */
    @Inject
    public CatalogDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the latest version of the book from the catalog corresponding to the specified book id.
     * Throws a BookNotFoundException if the latest version is not active or no version is found.
     *
     * @param bookId Id associated with the book.
     * @return The corresponding CatalogItem from the catalog table.
     */
    public CatalogItemVersion getBookFromCatalog(String bookId) {
        CatalogItemVersion book = getLatestVersionOfBook(bookId);

        if (book == null || book.isInactive()) {
            throw new BookNotFoundException(String.format("No book found for id: %s", bookId));
        }

        return book;
    }

    // Returns null if no version exists for the provided bookId
    private CatalogItemVersion getLatestVersionOfBook(String bookId) {
        CatalogItemVersion book = new CatalogItemVersion();
        book.setBookId(bookId);

        DynamoDBQueryExpression<CatalogItemVersion> queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(book)
                .withScanIndexForward(false)
                .withLimit(1);

        List<CatalogItemVersion> results = dynamoDbMapper.query(CatalogItemVersion.class, queryExpression);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

//    public Event cancelEvent(String eventId) {
////        // PARTICIPANTS: replace this implementation to perform a soft delete
////        Event canceledEvent = new Event();
////        canceledEvent.setId(eventId);
////        mapper.delete(canceledEvent);
////        return null;
//        Event canceledEvent = this.getEvent(eventId); // this mean using getEvent() IN THIS CLASS***
//        canceledEvent.setCanceled(true);
//        mapper.save(canceledEvent);
//        return canceledEvent;
//    }

    public CatalogItemVersion removeBookFromCatalog(String bookId) {
        CatalogItemVersion catalogItemVersion = this.getBookFromCatalog(bookId);
        catalogItemVersion.setInactive(true);
        dynamoDbMapper.save(catalogItemVersion);
        return catalogItemVersion;
    }

    public boolean validateBookExists(String bookId) {
        Map<String, AttributeValue> searchCondition = new HashMap<>();
        searchCondition.put(":bookId", new AttributeValue().withS(bookId));
        DynamoDBQueryExpression<CatalogItemVersion> queryExpression = new DynamoDBQueryExpression<CatalogItemVersion>()
                .withKeyConditionExpression("bookId = :bookId")
                .withExpressionAttributeValues(searchCondition);
        List<CatalogItemVersion> catalogItemVersion = dynamoDbMapper.query(CatalogItemVersion.class, queryExpression);

        if(catalogItemVersion.isEmpty()) {
            return false;
        }

        return true;
    }

    public void setInactiveForOlderBookVersion(String bookId) {
        CatalogItemVersion book = new CatalogItemVersion();
        book.setBookId(bookId);

        DynamoDBQueryExpression<CatalogItemVersion> queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(book)
                .withScanIndexForward(false)
                .withLimit(1);

        List<CatalogItemVersion> results = dynamoDbMapper.query(CatalogItemVersion.class, queryExpression);
        if (results.isEmpty()) {
            System.out.println("There is no book with this bookId");;
        }
        results.get(0).setInactive(true);
        dynamoDbMapper.save(results.get(0));
    }

    public CatalogItemVersion createOrUpdate(KindleFormattedBook book) {
       // Updating a book
        if(book.getBookId() != null) {              // Book id is not null
            if(!validateBookExists(book.getBookId())) {
                throw new BookNotFoundException("Book id not found: " + book.getBookId());
            }
            CatalogItemVersion newBook = getLatestVersionOfBook(book.getBookId());                       // Creating a newer version book
            removeBookFromCatalog(book.getBookId());

            newBook.setAuthor(book.getAuthor());
            newBook.setGenre(book.getGenre());
            newBook.setTitle(book.getTitle());
            newBook.setBookId(book.getBookId());
            newBook.setInactive(false);                                                    // new book is going to be active
            newBook.setVersion(getLatestVersionOfBook(book.getBookId()).getVersion() + 1); // version = old version +  1
            dynamoDbMapper.save(newBook);
            return newBook;
        }
        // Create a new book

        CatalogItemVersion newBook = new CatalogItemVersion();                             // Completely new book didnt exist in the Catalog table
        newBook.setAuthor(book.getAuthor());
        newBook.setGenre(book.getGenre());
        newBook.setTitle(book.getTitle());
        newBook.setBookId(KindlePublishingUtils.generateBookId());
        newBook.setInactive(false);                                                        // new book is going to be active
        newBook.setVersion(1);
        newBook.setText(book.getText());
        dynamoDbMapper.save(newBook);

        return newBook;
    }
}
