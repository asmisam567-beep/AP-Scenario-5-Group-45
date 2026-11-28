package my.edu.taylors.ap.scenario.textbooks;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import my.edu.taylors.ap.scenario.common.RuleViolationException;

/** TOOL 2 CONTROLLER – AI entry point for suggested-price calculation. */
public class PriceSuggestionController {
    private final PriceSuggestionService service;

    public PriceSuggestionController(PriceSuggestionService service) {
        this.service = service;
    }

    @Tool("TOOL 2 – Calculate the suggested resale price and maximum asking price for a textbook condition grade A, B, C or D.")
    public String suggestPrice(@P("ISBN-13") String isbn,
                               @P("condition grade A, B, C or D") String condition)
            throws RuleViolationException {
        return service.suggestPrice(isbn, condition);
    }
}
