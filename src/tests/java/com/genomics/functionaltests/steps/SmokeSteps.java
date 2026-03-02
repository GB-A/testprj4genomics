package com.genomics.functionaltests.steps;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.genomics.functionaltests.base.utils.ConfigReader;
import com.genomics.functionaltests.pageobjects.GenomeHome;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.List;

/**
 * Step definitions for Smoke Tests.
 * Verifies the core UI components, metrics, and basic connectivity of the Genomic Portal.
 */
public class SmokeSteps {
  /**
   * Page Object for the Genome Home screen, initialized via the Hooks page instance.
   */
  GenomeHome genomeHome = new GenomeHome(Hooks.page);

  /**
   * Navigates the browser to the Genomic Portal base URL.
   */
  @Given("I navigate to the Genomic Portal")
  public void inavigateToTheGenomicPortal() {
    String url = ConfigReader.get("baseUrl");
    genomeHome.navigateTo(url);
  }

  /**
   * Validates that the browser page title matches the expected value.
   *
   * @param expectedTitle the title string expected in the browser tab
   */
  @Then("the page title should be {string}")
  public void thePageTitleShouldBe(String expectedTitle) {
    assertThat(Hooks.page).hasTitle(expectedTitle);
  }

  /**
   * Verifies the visibility and initial values of the dashboard metric cards.
   */
  @And("I should see the following metric cards")
  public void shouldSeeTheFollowingMetricCards() {
    genomeHome.assertTotalVariantCard("Variant Registry", 949);
    genomeHome.assertHighPathogenicityCard("High Pathogenicity", 237);
    genomeHome.assertUniqueCohortsCard("Unique Cohorts", 3);
  }

  /**
   * Confirms the visibility of the Ancestry Distribution Chart.
   */
  @And("the Ancestry Distribution Chart should be visible")
  public void theAncestryDistributionChartShouldBeVisible() {

    assertThat(genomeHome.getAncestryChart()).isVisible();
  }

  /**
   * Validates that the System Audit Log is visible and indicates an active connection.
   */
  @And("the System Audit Log should be active")
  public void theSystemAuditLogShouldBeActive() {
    assertThat(genomeHome.getAuditLog()).isVisible();
    assertThat(genomeHome.getAuditLog()).containsText("Connection established");
  }

  /**
   * Validates that the System Audit Log is visible and indicates an active connection.
   */
  @And("the Data Registry Table should display genomic headers")
  public void theDataRegistryTableShouldDisplayGenomicHeaders() {
    List<String> expectedHeaders =
        List.of("Locus/Gene", "Marker (rsID)", "Population", "Sequence Tools");

    assertAll(() -> assertTrue(genomeHome.isTheadVisible(), "thead not visible"),
        () -> assertEquals(expectedHeaders, genomeHome.getTableHeaders(), "headers mismatch")

    );
  }

  /**
   * Checks that the Data Registry Table contains the correct genomic column headers.
   */
  @And("the Table should have {int} rows")
  public void theTableShouldHaveRows(int expectedRows) {
    assertEquals(expectedRows, genomeHome.getTableRowCount(), "row count mismatch");
  }
}
