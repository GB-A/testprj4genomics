package com.genomics.functionaltests.steps;

import static com.genomics.functionaltests.steps.Hooks.page;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.genomics.functionaltests.base.utils.Log;
import com.genomics.functionaltests.pageobjects.GenomeHome;
import com.microsoft.playwright.options.LoadState;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;

/**
 * Step definitions for Data Visualisation features.
 * This class handles validations for the Ancestry Chart and data consistency
 * between the table and graphical components.
 */
public class DataVisualisationSteps {
  /**
   * Page Object for the Genome Home screen, initialized via the Hooks page instance.
   */
  GenomeHome genomeHome = new GenomeHome(Hooks.page);

  /**
   * Verifies the number of distinct population segments currently visible in the Ancestry Chart.
   *
   * @param expectedCount the expected number of population segments
   */
  @Given("the current Ancestry Chart shows {int} distinct populations")
  public void theCurrentAncestryChartShows(int expectedCount) {
    int initialSegmentCount = genomeHome.getAncestryChartSegmentCount();
    assertEquals(expectedCount, initialSegmentCount,
        "Expected " + expectedCount + " populations but chart shows: " + initialSegmentCount + " "
            + genomeHome.getAncestryChartSegments());
  }

  /**
   * Adds a new genetic variant with a specific ancestry to the system.
   *
   * @param ancestry the ancestry type to be assigned to the new variant
   */
  @When("I add a new variant with {string} ancestry")
  public void addNewVariantWith(String ancestry) {
    genomeHome.createNewVariant("TEST_GENE", "rs9999999", ancestry);
    Log.info("Added new variant with ancestry: {}", ancestry);
  }

  /**
   * Confirms that a specific gene record is successfully displayed in the results table.
   *
   * @param gene the name of the gene record to search for and verify
   */
  @Then("the {string} record should appear in the table")
  public void theRecordShouldAppearInTable(String gene) {
    // search for the newly added ancestry
    genomeHome.searchFor(gene);
    assertTrue(genomeHome.getTableRowCount() > 0, "No records found for ancestry: " + gene);
    Log.info("Records found for ancestry: {}", gene);
  }

  /**
   * Validates that the Ancestry Chart has correctly updated its segments after data changes.
   * Performs a deep comparison and logs a detailed bug report if the UI state is stale.
   *
   * @param expectedCount the total number of segments expected after the update
   */
  @And("the Ancestry Chart should now show {int} distinct population segments")
  public void theAncestryChartShouldNowShow(int expectedCount) {
    page.waitForLoadState(LoadState.NETWORKIDLE);
    int actualCount = genomeHome.getAncestryChartSegmentCount();
    List<String> segments = genomeHome.getAncestryChartSegments();
    if (actualCount != expectedCount) {
      Log.warn("========================================");
      Log.warn("BUG DETECTED: Ancestry chart has not updated");
      Log.warn("Expected segments : {}", expectedCount);
      Log.warn("Actual segments   : {}", actualCount);
      Log.warn("Visible segments  : {}", segments);
      Log.warn("========================================");

      fail("\n========================================" + "\nUI DESYNC BUG DETECTED"
          +
          "\n========================================" + "\nExpected : " + expectedCount
          +
          " segments" + "\nActual   : " + actualCount + " segments" + "\nVisible  : "
          + segments
          +
          "\nCause    : Chart has not re-rendered after 'American' population was added"
          +
          "\nImpact   : Data Scientist is analysing stale population data"
          +
          "\n========================================");
    }
  }
}
