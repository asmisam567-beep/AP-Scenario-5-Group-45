package my.edu.taylors.ap.scenario.textbooks;

import my.edu.taylors.ap.scenario.common.Money;
import my.edu.taylors.ap.scenario.common.RuleViolationException;

/** TOOL 2 SERVICE – calculates suggested resale and maximum asking prices. */
public class PriceSuggestionService {
    private final TextbookService textbooks;

    public PriceSuggestionService(TextbookService textbooks) {
        this.textbooks = textbooks;
    }

    public String suggestPrice(String isbn, String condition) throws RuleViolationException {
        Book book = textbooks.book(isbn);
        long suggested = textbooks.suggestedSen(book, condition);
        return book.title() + " grade " + TextbookService.normaliseCondition(condition)
                + ": suggested " + Money.rm(suggested)
                + ", maximum asking " + Money.rm(textbooks.maxAskingSen(suggested)) + ".";
    }
}
