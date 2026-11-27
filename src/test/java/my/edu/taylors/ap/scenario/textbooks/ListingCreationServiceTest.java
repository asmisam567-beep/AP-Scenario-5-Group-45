package my.edu.taylors.ap.scenario.textbooks;

import my.edu.taylors.ap.data.JsonStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListingCreationServiceTest {
    @TempDir Path tempDir;

    private TextbookService textbookService() {
        Book book = new Book("9781234567890", "Test Java Textbook", 1, 2026, 10000, null);
        return new TextbookService(List.of(book), new TextbookRules(9, 20, 500, 4),
                Clock.fixed(Instant.parse("2026-06-01T00:00:00Z"), ZoneOffset.UTC));
    }

    @Test
    void createsAndPersistsValidListingWithoutSellerContactDetails() throws Exception {
        JsonStore<Listing> store = JsonStore.of(tempDir.resolve("listings.json"), Listing.class);
        ListingCreationService service = new ListingCreationService(textbookService(), store);

        String result = service.createListing("STU-999", "9781234567890", "A", "100.00", "");

        assertTrue(result.contains("TX-0001"));
        List<Listing> saved = store.findAll();
        assertEquals(1, saved.size());
        assertEquals("9781234567890", saved.get(0).isbn());
        assertEquals("STU-999", saved.get(0).sellerId());
        assertEquals("A", saved.get(0).condition());
        assertEquals(10000L, saved.get(0).askingSen());
        assertNull(saved.get(0).sellerEmail());
        assertNull(saved.get(0).sellerPhone());
    }

    @Test
    void nextListingIdUsesHighestExistingId() throws Exception {
        JsonStore<Listing> store = JsonStore.of(tempDir.resolve("listings.json"), Listing.class);
        store.saveAll(List.of(new Listing("TX-0003", "9781234567890", "STU-1", null, null, "A", 10000, "")));
        ListingCreationService service = new ListingCreationService(textbookService(), store);

        String result = service.createListing("STU-999", "9781234567890", "A", "100.00", "");

        assertTrue(result.contains("TX-0004"));
    }

    @Test
    void rejectsUnknownBook() {
        ListingCreationService service = new ListingCreationService(textbookService(),
                JsonStore.of(tempDir.resolve("listings.json"), Listing.class));
        assertThrows(TextbookExceptions.UnknownBookException.class,
                () -> service.createListing("STU-999", "0000000000000", "A", "100.00", ""));
    }

    @Test
    void rejectsAskingPriceAboveTwentyPercentLimit() {
        ListingCreationService service = new ListingCreationService(textbookService(),
                JsonStore.of(tempDir.resolve("listings.json"), Listing.class));
        assertThrows(TextbookExceptions.OverpricedListingException.class,
                () -> service.createListing("STU-999", "9781234567890", "A", "120.01", ""));
    }

    @Test
    void rejectsConditionDWithoutDefectsDisclosure() {
        ListingCreationService service = new ListingCreationService(textbookService(),
                JsonStore.of(tempDir.resolve("listings.json"), Listing.class));
        assertThrows(TextbookExceptions.MissingDisclosureException.class,
                () -> service.createListing("STU-999", "9781234567890", "D", "40.00", " "));
    }

    @Test
    void rejectsMissingSellerIdentity() {
        ListingCreationService service = new ListingCreationService(textbookService(),
                JsonStore.of(tempDir.resolve("listings.json"), Listing.class));
        assertThrows(my.edu.taylors.ap.scenario.common.RuleViolationException.class,
                () -> service.createListing(" ", "9781234567890", "A", "100.00", ""));
    }
}
