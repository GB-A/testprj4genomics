package com.genomics.functionalTests.steps;

import com.genomics.functionalTests.base.utils.Log;
import com.genomics.functionalTests.pageObjects.GenomeHome;
import com.genomics.functionalTests.steps.Hooks;
import com.microsoft.playwright.options.LoadState;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static com.genomics.functionalTests.steps.Hooks.page;
import static org.junit.jupiter.api.Assertions.*;

public class DataVisualisationSteps {

    GenomeHome genomeHome = new GenomeHome(Hooks.page);

    @Given("the current Ancestry Chart shows {int} distinct populations")
    public void theCurrentAncestryChartShows(int expectedCount) {
        int initialSegmentCount = genomeHome.getAncestryChartSegmentCount();
        assertEquals(
                expectedCount,
                initialSegmentCount,
                "Expected " + expectedCount + " populations but chart shows: " +
                        initialSegmentCount + " " + genomeHome.getAncestryChartSegments()
        );
    }

    @When("I add a new variant with {string} ancestry")
    public void iAddANewVariantWith(String ancestry) {
        genomeHome.createNewVariant("TEST_GENE", "rs9999999", ancestry);
        Log.info("Added new variant with ancestry: {}", ancestry);
    }

    @Then("the {string} record should appear in the table")
    public void theRecordShouldAppearInTable(String gene) {
        // search for the newly added ancestry
        genomeHome.searchFor(gene);

        assertTrue(
                genomeHome.getTableRowCount() > 0,
                "No records found for ancestry: " + gene
        );
        Log.info("Records found for ancestry: {}", gene);
    }

    @And("the Ancestry Chart should now show {int} distinct population segments")
    public void theAncestryChartShouldNowShow(int expectedCount) {
        page.waitForLoadState(LoadState.NETWORKIDLE);

        int actualCount        = genomeHome.getAncestryChartSegmentCount();
        List<String> segments  = genomeHome.getAncestryChartSegments();
        if (actualCount != expectedCount) {
            Log.warn("========================================");
            Log.warn("BUG DETECTED: Ancestry chart has not updated");
            Log.warn("Expected segments : {}", expectedCount);
            Log.warn("Actual segments   : {}", actualCount);
            Log.warn("Visible segments  : {}", segments);
            Log.warn("========================================");

            fail(
                    "\n========================================" +
                            "\nUI DESYNC BUG DETECTED" +
                            "\n========================================" +
                            "\nExpected : " + expectedCount + " segments" +
                            "\nActual   : " + actualCount + " segments" +
                            "\nVisible  : " + segments +
                            "\nCause    : Chart has not re-rendered after 'American' population was added" +
                            "\nImpact   : Data Scientist is analysing stale population data" +
                            "\n========================================"
            );
        }
    }
}
