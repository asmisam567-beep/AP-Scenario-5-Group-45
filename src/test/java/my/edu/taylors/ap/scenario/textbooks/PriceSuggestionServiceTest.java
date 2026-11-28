package my.edu.taylors.ap.scenario.textbooks;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PriceSuggestionServiceTest {

    private TextbookService createService() {
        TextbookRules group45Rules = new TextbookRules(9, 20, 500, 4);
        Clock clock = Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"), ZoneOffset.UTC);
        Book book = new Book("9781234567890", "Test Textbook", 2, 2026, 10000, null);
        return new TextbookService(List.of(book), group45Rules, clock);
    }

    @Test
    void returnsSuggestedAndMaximumAskingPriceForConditionA() throws Exception {
        PriceSuggestionService service = new PriceSuggestionService(createService());

        String result = service.suggestPrice("9781234567890", "A");

        assertEquals("Test Textbook grade A: suggested RM100.00, maximum asking RM120.00.", result);
    }

    @Test
    void appliesConditionMultiplierAndMaximumMarkupForConditionB() throws Exception {
        PriceSuggestionService service = new PriceSuggestionService(createService());

        String result = service.suggestPrice("9781234567890", "B");

        assertEquals("Test Textbook grade B: suggested RM85.00, maximum asking RM102.00.", result);
    }

    @Test
    void normalisesLowercaseAndWhitespaceInCondition() throws Exception {
        PriceSuggestionService service = new PriceSuggestionService(createService());

        String result = service.suggestPrice("9781234567890", " b ");

        assertEquals("Test Textbook grade B: suggested RM85.00, maximum asking RM102.00.", result);
    }

    @Test
    void rejectsUnknownIsbn() {
        PriceSuggestionService service = new PriceSuggestionService(createService());

        assertThrows(TextbookExceptions.UnknownBookException.class,
                () -> service.suggestPrice("0000000000000", "A"));
    }

    @Test
    void rejectsInvalidCondition() {
        PriceSuggestionService service = new PriceSuggestionService(createService());

        assertThrows(TextbookExceptions.InvalidConditionException.class,
                () -> service.suggestPrice("9781234567890", "E"));
    }

    @Test
    void appliesGroup45MinimumSuggestedPriceForOldBooks() throws Exception {
        TextbookRules rules = new TextbookRules(9, 20, 500, 4);
        Clock clock = Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"), ZoneOffset.UTC);
        Book oldBook = new Book("9781111111111", "Old Textbook", 1, 1900, 1000, null);
        TextbookService textbooks = new TextbookService(List.of(oldBook), rules, clock);
        PriceSuggestionService service = new PriceSuggestionService(textbooks);

        String result = service.suggestPrice("9781111111111", "D");

        assertEquals("Old Textbook grade D: suggested RM5.00, maximum asking RM6.00.", result);
    }
}
