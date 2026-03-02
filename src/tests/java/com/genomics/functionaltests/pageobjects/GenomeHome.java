package com.genomics.functionaltests.pageobjects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.genomics.functionaltests.base.utils.Log;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Page Object Model for the Genome Home dashboard.
 * Handles variant registry interactions and dashboard statistics.
 */
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

  /**
   * Initializes locators for the Genome Home page.
   *
   * @param page the Playwright Page instance
   */
  public GenomeHome(Page page) {
    this.page = page;
    this.recordCountLabel = page.locator("#recordCount");
    this.newAnalysisBtn = page.locator("#addVariantBtn");
    this.roleSelector = page.locator("#userRole");
    // variant-registry card elements
    this.totalVariantCard = page.getByTestId("card-variant-registry");
    this.totalVariantLabel = page.getByTestId("card-variant-registry-label");
    this.totalVariantsStat = page.locator("#stat-total");
    // hp card elements
    this.highPathogenicityCard = page.getByTestId("card-high-pathogenicity");
    this.highPathogenicityLabel = page.getByTestId("card-high-pathogenicity-label");
    this.highPathogenicityStat = page.locator("#stat-high");
    // uniCoh card elementss
    this.uniqueCohortsCard = page.getByTestId("card-unique-cohorts");
    this.uniqueCohortsLabel = page.getByTestId("card-unique-cohorts-label");
    this.uniqueCohortsStat = page.locator("#stat-unique");


    this.searchBar = page.locator("#searchBar");
    this.variantTableBody = page.locator("#tableBody");

    this.modalContainer = page.locator("#modal");
    this.geneInput = page.locator("#geneInput");
    this.rsidInput = page.locator("#rsidInput");
    this.ancestrySelect = page.locator("#ancestryInput");
    this.commitChangesBtn = page.locator("button:has-text('Commit Changes')");
    this.ancestryChart = page.locator("#ancestryChart");
    this.ancestryChartSegment = page.locator("#ancestryChart.segment");
    this.auditLog = page.locator("#auditLog");
    this.thead = page.locator("table thead");
    this.tableHeaders = page.locator("table thead th");
    this.tableRows = page.locator("table tbody#tableBody tr");
    this.tableBody = page.locator("table tbody#tableBody");


  }

  /**
   * Navigates to the application URL.
   *
   * @param url destination address
   */
  public void navigateTo(String url) {
    page.navigate(url);
  }

  /**
   * Asserts the visibility and values of a dashboard stat card.
   *
   * @param card locator for the card
   * @param label locator for the label
   * @param value locator for the numeric value
   * @param expectedLabel text expected in label
   * @param expectedValue integer expected in value
   */
  public void assertCard(Locator card, Locator label, Locator value, String expectedLabel,
                         int expectedValue) {
    int actualValue = Integer.parseInt(value.textContent().trim());
    // all assertions run without skipping after any one failure
    assertAll(() -> assertTrue(card.isVisible(), "card not visible"),
        () -> assertEquals(expectedLabel, label.textContent().trim(), "label mismatch"),
        () -> assertEquals(expectedValue, actualValue, "value mismatch"));
  }

  // convenience method so the test doesn't need to pass locators manually also
  // as there's only 3 cards ,using 3 methods for readability
  /**
   * Asserts the High Pathogenicity card state.
   *
   * @param expectedLabel the expected label text
   * @param expectedValue the expected stat value
   */
  public void assertHighPathogenicityCard(String expectedLabel, int expectedValue) {
    assertCard(highPathogenicityCard, highPathogenicityLabel, highPathogenicityStat, expectedLabel,
        expectedValue);
  }

  /**
   * Asserts the Total Variant card state.
   *
   * @param expectedLabel the expected label text
   * @param expectedValue the expected stat value
   */
  public void assertTotalVariantCard(String expectedLabel, int expectedValue) {
    assertCard(totalVariantCard, totalVariantLabel, totalVariantsStat, expectedLabel,
        expectedValue);
  }

  /**
   * Asserts the Unique Cohorts card state.
   *
   * @param expectedLabel the expected label text
   * @param expectedValue the expected stat value
   */
  public void assertUniqueCohortsCard(String expectedLabel, int expectedValue) {
    assertCard(uniqueCohortsCard, uniqueCohortsLabel, uniqueCohortsStat, expectedLabel,
        expectedValue);
  }

  /**
   * Checks if the table header is visible.
   *
   * @return true if the thead element is visible
   */
  public boolean isTheadVisible() {
    return thead.isVisible();
  }

  /**
   * Retrieves the text contents of all table headers.
   *
   * @return a list of header strings
   */
  public List<String> getTableHeaders() {
    return tableHeaders.allTextContents();
  }

  /**
   * Returns the count of rows currently in the table.
   *
   * @return the number of table rows
   */
  public int getTableRowCount() {
    return tableRows.count();
  }

  /**
   * Checks if all visible rows contain a specific gene name.
   *
   * @param gene the gene name to check for
   * @return true if all rows contain the gene string
   */
  public boolean allRowsContainGene(String gene) {
    return tableRows.allTextContents().stream().allMatch(row -> row.contains(gene));
  }

  /**
   * Retrieves data from a specific row by index.
   *
   * @param rowIndex the zero-based index of the row
   * @return a map of column names to cell values
   */
  public Map<String, String> getRowData(int rowIndex) {
    // nth() is zero based so row 1 = index 0
    Locator row = page.locator("table tbody#tableBody tr").nth(rowIndex);
    List<String> cells = row.locator("td").allTextContents();

    return Map.of("Gene", cells.get(0).trim(), "rsID", cells.get(1).trim(), "Ancestry",
        cells.get(2).trim());
  }

  /**
   * Gets the numeric value displayed for registry variants.
   *
   * @return the current total variants count as an integer
   */
  public int getRegistryVariantsValue() {
    return Integer.parseInt(totalVariantsStat.textContent().trim());
  }

  /**
   * Enters the search text to filter the table.
   *
   * @param text string to be entered
   */
  public void searchFor(String text) {
    searchBar.fill(text);
    searchBar.press("Enter");
  }

  /**
   * Select or change the user role.
   *
   * @param role role name to be selected
   */
  public void changeUserRole(String role) {
    roleSelector.selectOption(role);
    page.waitForLoadState();
    Log.info("Role changed to: {}", role);
  }

  /**
   * Gets the currently selected role.
   *
   * @return the role name string
   */
  public String getMyRole() {
    return roleSelector.inputValue();
  }

  /**
   * Create a new variant entry via the modal.
   *
   * @param gene     gene name to be entered
   * @param rsid     rsid to be entered
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

  /**
   * Modifies the first visible row in the table.
   *
   * @param gene     new gene name
   * @param rsid     new rsid
   * @param ancestry new ancestry selection
   */
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

  /**
   * Purges (deletes) the first visible row in the table.
   */
  public void purgeDisplayedRow() {
    // after search there should be exactly one row visible
    Locator displayedRow = page.locator("table tbody#tableBody tr").first();
    String gene = displayedRow.locator("td:first-child").textContent().trim();

    displayedRow.getByText("PURGE").click();
    page.waitForLoadState();
    Log.info("Purged variant: {}", gene);
  }

  /**
   * Checks if a variant with a specific gene name exists in the table.
   *
   * @param gene the gene name to look for
   * @return true if found
   */
  public boolean variantExistsInTable(String gene) {
    return page.locator("table tbody#tableBody tr td:first-child").allTextContents().stream()
        .anyMatch(cell -> cell.trim().equals(gene));
  }

  /**
   * Returns the Ancestry Chart locator.
   *
   * @return the chart locator
   */
  public Locator getAncestryChart() {
    return ancestryChart;
  }

  /**
   * Returns the Audit Log locator.
   *
   * @return the audit log locator
   */
  public Locator getAuditLog() {
    return auditLog;
  }

  /**
   * Checks if a button with a specific label is visible using ARIA roles.
   *
   * @param buttonLabel the text label of the button
   * @return true if visible
   */
  public boolean isButtonVisible(String buttonLabel) {
    return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(buttonLabel))
        .isVisible();
  }

  /**
   * Validates that every row in the table contains a specific tool link.
   *
   * @param tool the tool name/text to verify
   * @return true if tool count matches row count
   */
  public boolean allRowsHaveTool(String tool) {
    // checks every row in the table has the tool link visible
    Locator toolLinks = page.locator("table tbody#tableBody tr td:last-child").getByText(tool);
    int rowCount = page.locator("table tbody#tableBody tr").count();
    int toolCount = toolLinks.count();
    return toolCount == rowCount;
  }

  /**
   * Checks if a specific tool is visible anywhere in the table body.
   *
   * @param tool the tool text to search for
   * @return true if at least one instance is visible
   */
  public boolean isToolVisibleInTable(String tool) {
    // check if any instance of the tool link exists and is visible in the table
    Locator toolLinks = tableBody.getByText(tool, new Locator.GetByTextOptions().setExact(true));
    return toolLinks.count() > 0 && toolLinks.first().isVisible();
  }

  /**
   * Checks if the ancestry chart contains a specific population segment.
   *
   * @param ancestry the ancestry name to check
   * @return true if the segment exists in the chart
   */
  public boolean ancestryChartHasSegment(String ancestry) {
    return page.locator("#ancestryChart")
        .getByText(ancestry, new Locator.GetByTextOptions().setExact(true)).count() > 0;
  }

  /**
   * Retrieves the segment count attribute from the ancestry chart.
   *
   * @return the number of segments as an integer
   */
  public int getAncestryChartSegmentCount() {
    page.waitForLoadState(LoadState.NETWORKIDLE);
    String count = page.locator("#ancestryChart").getAttribute("data-segment-count");
    Log.info("Ancestry chart segment count: {}", count);
    return Integer.parseInt(count);
  }

  /**
   * Retrieves a list of all population segments from the chart's data attribute.
   *
   * @return a list of segment name strings
   */
  public List<String> getAncestryChartSegments() {
    String segments = page.locator("#ancestryChart").getAttribute("data-segments");
    Log.info("Ancestry chart segments: {}", segments);
    return Arrays.asList(segments.split(","));
  }
}
