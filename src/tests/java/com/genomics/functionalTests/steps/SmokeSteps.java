package com.genomics.functionalTests.steps;

import com.genomics.functionalTests.base.utils.ConfigReader;
import com.genomics.functionalTests.pageObjects.GenomeHome;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SmokeSteps {

    GenomeHome genomeHome = new GenomeHome(Hooks.page);
    @Given("I navigate to the Genomic Portal")
    public void iNavigateToTheGenomicPortal() {
        String url = ConfigReader.get("baseUrl");
        genomeHome.navigateTo(url);
    }

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String arg0) {
        assertThat(Hooks.page).hasTitle("PGP-UK Genomic Portal | Biotech Edition");
    }

    @And("I should see the following metric cards")
    public void iShouldSeeTheFollowingMetricCards() {
        genomeHome.assertTotalVariantCard("Variant Registry", 949);
        genomeHome.assertHighPathogenicityCard("High Pathogenicity", 237);
        genomeHome.assertUniqueCohortsCard("Unique Cohorts", 3);
    }
    @And("the Ancestry Distribution Chart should be visible")
    public void theAncestryDistributionChartShouldBeVisible() {

        assertThat(genomeHome.getAncestryChart()).isVisible();
    }

    @And("the System Audit Log should be active")
    public void theSystemAuditLogShouldBeActive() {
        assertThat(genomeHome.getAuditLog()).isVisible();
        assertThat(genomeHome.getAuditLog()).containsText("Connection established");
    }
    @And("the Data Registry Table should display genomic headers")
    public void theDataRegistryTableShouldDisplayGenomicHeaders() {
        List<String> expectedHeaders = List.of(
                "Locus/Gene",
                "Marker (rsID)",
                "Population",
                "Sequence Tools"
        );

        assertAll(
                () -> assertTrue(genomeHome.isTheadVisible(),                        "thead not visible"),
                () -> assertEquals(expectedHeaders, genomeHome.getTableHeaders(),    "headers mismatch")

        );
    }

    @And("the Table should have {int} rows")
    public void theTableShouldHaveRows(int expectedRows) {
        assertEquals(expectedRows, genomeHome.getTableRowCount(), "row count mismatch");

    }
}
