package my.edu.taylors.ap.scenario.textbooks;

import my.edu.taylors.ap.data.DataAccessException;
import my.edu.taylors.ap.data.JsonStore;
import my.edu.taylors.ap.scenario.common.Money;
import my.edu.taylors.ap.scenario.common.RuleViolationException;

import java.util.List;

/** TOOL 4 SERVICE: validates and persists a new textbook listing. */
public class ListingCreationService {
    private final TextbookService textbooks;
    private final JsonStore<Listing> listings;

    public ListingCreationService(TextbookService textbooks, JsonStore<Listing> listings) {
        this.textbooks = textbooks;
        this.listings = listings;
    }

    public String createListing(String sellerId, String isbn, String condition,
                                String askingPriceRm, String defects)
            throws RuleViolationException, DataAccessException {
        if (sellerId == null || sellerId.isBlank()) {
            throw new RuleViolationException("SELLER", "A signed-in seller is required to create a listing.");
        }

        Book book = textbooks.book(isbn);
        long askingSen = TextbookService.parseRinggit(askingPriceRm);
        String grade = TextbookService.normaliseCondition(condition);
        textbooks.validateListing(book, grade, askingSen, defects);

        List<Listing> existing = listings.findAll();
        int nextNumber = existing.stream()
                .map(Listing::listingId)
                .filter(id -> id != null && id.matches("TX-[0-9]+"))
                .mapToInt(id -> Integer.parseInt(id.substring(3)))
                .max().orElse(0) + 1;

        Listing listing = new Listing(String.format("TX-%04d", nextNumber),
                book.isbn(), sellerId, null, null, grade, askingSen,
                defects == null ? "" : defects.trim());
        listings.append(listing);

        return "Listing " + listing.listingId() + " created at " + Money.rm(askingSen) + ".";
    }
}
