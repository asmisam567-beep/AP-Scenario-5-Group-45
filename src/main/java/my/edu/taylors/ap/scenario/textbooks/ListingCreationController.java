package my.edu.taylors.ap.scenario.textbooks;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import my.edu.taylors.ap.data.DataAccessException;
import my.edu.taylors.ap.scenario.common.RuleViolationException;

/** TOOL 4 CONTROLLER: exposes the create-listing service as an AI tool. */
public class ListingCreationController {
    private final ListingCreationService service;

    public ListingCreationController(ListingCreationService service) {
        this.service = service;
    }

    @Tool("TOOL 4 - Create a textbook listing for the signed-in seller after validating the asking price and condition. Returns the new listing ID.")
    public String createListing(
            @ToolMemoryId String sellerId,
            @P("ISBN-13 of the textbook") String isbn,
            @P("condition grade: A, B, C or D") String condition,
            @P("asking price in Malaysian ringgit, for example 45.00") String askingPriceRm,
            @P("defects description; required for condition D") String defects)
            throws RuleViolationException, DataAccessException {
        return service.createListing(sellerId, isbn, condition, askingPriceRm, defects);
    }
}
