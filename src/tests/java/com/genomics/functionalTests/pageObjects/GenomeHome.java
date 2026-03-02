package com.genomics.functionalTests.pageObjects;
import com.genomics.functionalTests.base.utils.Log;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class GenomeHome {
    private final Page page;
    private final Locator recordCountLabel;
    private final Locator newAnalysisBtn;
    private final Locator roleSelector;

    // 2. Dashboard Stats
    private final Locator totalVariantCard;
    private final Locator totalVariantLabel;
    private final Locator totalVariantsStat;

    private final Locator highPathogenicityCard;
    private final Locator highPathogenicityLabel;
    private final Locator highPathogenicityStat;

    private final Locator uniqueCohortsCard;
    private final Locator uniqueCohortsLabel;
    private final Locator uniqueCohortsStat;


    private final Locator ancestryChart;
    private final Locator ancestryChartSegment;
    private final Locator auditLog;
    private final Locator tableHeaders;
    private final Locator tableRows;
    private final Locator tableBody;
    private final Locator thead;

    // 3. Table & Search
    private final Locator searchBar;
    private final Locator variantTableBody;

    // 4. Modal (New Analysis / Edit)
    private final Locator modalContainer;
    private final Locator geneInput;
    private final Locator rsidInput;
    private final Locator ancestrySelect;
    private final Locator commitChangesBtn;


    public
    GenomeHome(Page page) {
        this.page = page;
        this.recordCountLabel = page.locator("#recordCount");
        this.newAnalysisBtn = page.locator("#addVariantBtn");
        this.roleSelector = page.locator("#userRole");
        //variant-registry card elementss
        this.totalVariantCard =page.getByTestId("card-variant-registry");
        this.totalVariantLabel =page.getByTestId("card-variant-registry-label");
        this.totalVariantsStat = page.locator("#stat-total");
        //hp card elementss
        this.highPathogenicityCard =page.getByTestId("card-high-pathogenicity");
        this.highPathogenicityLabel=page.getByTestId("card-high-pathogenicity-label");
        this.highPathogenicityStat = page.locator("#stat-high");
        //uniCoh card elementss
        this.uniqueCohortsCard=page.getByTestId("card-unique-cohorts");
        this.uniqueCohortsLabel=page.getByTestId("card-unique-cohorts-label");
        this.uniqueCohortsStat=page.locator("#stat-unique");


        this.searchBar = page.locator("#searchBar");
        this.variantTableBody = page.locator("#tableBody");

        this.modalContainer = page.locator("#modal");
        this.geneInput = page.locator("#geneInput");
        this.rsidInput = page.locator("#rsidInput");
        this.ancestrySelect= page.locator("#ancestryInput");
        this.commitChangesBtn = page.locator("button:has-text('Commit Changes')");
        this.ancestryChart = page.locator("#ancestryChart");
        this.ancestryChartSegment = page.locator("#ancestryChart.segment");
        this.auditLog = page.locator("#auditLog");
        this.thead        = page.locator("table thead");
        this.tableHeaders = page.locator("table thead th");
        this.tableRows    = page.locator("table tbody#tableBody tr");
        this.tableBody      = page.locator("table tbody#tableBody");


    }

    public void navigateTo(String url) {
        page.navigate(url);
    }

    public void assertCard(Locator card, Locator label, Locator value,  String expectedLabel, int expectedValue ){
        int actualValue = Integer.parseInt(value.textContent().trim());
        //all assertions run without skipping after any one failure
        assertAll(
                () -> assertTrue(card.isVisible(),                            "card not visible"),
                () -> assertEquals(expectedLabel, label.textContent().trim(), "label mismatch"),
                () -> assertEquals(expectedValue, actualValue,                "value mismatch")
        );
    }

    // convenience method so the test doesn't need to pass locators manually also as theres only 3 cards ,
    // using 3 methods for readability
    public void assertHighPathogenicityCard(String expectedLabel, int expectedValue) {
        assertCard(highPathogenicityCard, highPathogenicityLabel, highPathogenicityStat, expectedLabel, expectedValue);
    }

    public void assertTotalVariantCard(String expectedLabel, int expectedValue){
        assertCard(totalVariantCard, totalVariantLabel, totalVariantsStat, expectedLabel, expectedValue);
    }

    public void assertUniqueCohortsCard(String expectedLabel, int expectedValue){
        assertCard(uniqueCohortsCard, uniqueCohortsLabel, uniqueCohortsStat, expectedLabel, expectedValue);
    }

    public boolean isTheadVisible() {
        return thead.isVisible();
    }

    public List<String> getTableHeaders() {
        return tableHeaders.allTextContents();
    }

    public int getTableRowCount() {
        return tableRows.count();
    }

    public boolean allRowsContainGene(String gene) {
        return tableRows.allTextContents()
                .stream()
                .allMatch(row -> row.contains(gene));
    }

    public Map<String, String> getRowData(int rowIndex) {
        // nth() is zero based so row 1 = index 0
        Locator row = page.locator("table tbody#tableBody tr").nth(rowIndex);
        List<String> cells = row.locator("td").allTextContents();

        return Map.of(
                "Gene",     cells.get(0).trim(),
                "rsID",     cells.get(1).trim(),
                "Ancestry", cells.get(2).trim()
        );
    }

    public int getRegistryVariantsValue() {
        return Integer.parseInt(totalVariantsStat.textContent().trim());
    }


    /**
     * Enters the search text to filter the table
     * @param text string to be entered
     */
    public void searchFor(String text) {
        searchBar.fill(text);
        searchBar.press("Enter");
    }

    /**
     * Select or change the role
     * @param role role name to be selected from the select eelement
     */
    public void changeUserRole(String role) {
        roleSelector.selectOption(role);
        page.waitForLoadState();
        Log.info("Role changed to: {}", role);
    }

    public String getMyRole(){
        return roleSelector.inputValue();
    }

    /**
     *Create a new variant
     * @param gene gene name to be entered
     * @param rsid rsid to be entered
     * @param ancestry ancestry to be selected
     */
    public void createNewVariant(String gene, String rsid, String ancestry) {
        newAnalysisBtn.click();
        assertTrue(modalContainer.isVisible());
        geneInput.fill(gene);
        rsidInput.fill(rsid);
        ancestrySelect.selectOption(ancestry);
        commitChangesBtn.click();
    }

    public void modifyDisplayedRow(String gene, String rsid, String ancestry) {
        // after search there should be exactly one row visible
        Locator displayedRow = page.locator("table tbody#tableBody tr").first();
        displayedRow.getByText("MODIFY").click();
        geneInput.clear();
        geneInput.fill(gene);
        rsidInput.clear();
        rsidInput.fill(rsid);
        ancestrySelect.selectOption(ancestry);

        commitChangesBtn.click();
        page.waitForLoadState();
        Log.info("Modified variant to: {} {} {}", gene, rsid, ancestry);
    }

    public void purgeDisplayedRow() {
        // after search there should be exactly one row visible
        Locator displayedRow = page.locator("table tbody#tableBody tr").first();
        String gene = displayedRow.locator("td:first-child").textContent().trim();

        displayedRow.getByText("PURGE").click();
        page.waitForLoadState();
        Log.info("Purged variant: {}", gene);
    }

    public boolean variantExistsInTable(String gene) {
        return page.locator("table tbody#tableBody tr td:first-child")
                .allTextContents()
                .stream()
                .anyMatch(cell -> cell.trim().equals(gene));
    }

    public Locator getAncestryChart() {
        return ancestryChart;
    }

    public Locator getAuditLog() {
        return auditLog;
    }
    public boolean isButtonVisible(String buttonLabel) {
        return page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(buttonLabel)).isVisible();
    }

    public boolean allRowsHaveTool(String tool) {
        // checks every row in the table has the tool link visible
        Locator toolLinks = page.locator("table tbody#tableBody tr td:last-child")
                .getByText(tool);
        int rowCount  = page.locator("table tbody#tableBody tr").count();
        int toolCount = toolLinks.count();
        return toolCount == rowCount;
    }

    public boolean isToolVisibleInTable(String tool) {
        // check if any instance of the tool link exists and is visible in the table
        Locator toolLinks = tableBody.getByText(tool,
                new Locator.GetByTextOptions().setExact(true));
        return toolLinks.count() > 0 && toolLinks.first().isVisible();
    }


    public boolean ancestryChartHasSegment(String ancestry) {
        return page.locator("#ancestryChart")
                .getByText(ancestry, new Locator.GetByTextOptions().setExact(true))
                .count() > 0;
    }
    public int getAncestryChartSegmentCount() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
        String count = page.locator("#ancestryChart").getAttribute("data-segment-count");
        Log.info("Ancestry chart segment count: {}", count);
        return Integer.parseInt(count);
    }

    public List<String> getAncestryChartSegments() {
        String segments = page.locator("#ancestryChart").getAttribute("data-segments");
        Log.info("Ancestry chart segments: {}", segments);
        return Arrays.asList(segments.split(","));
    }
}
