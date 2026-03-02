package com.genomics.functionaltests.steps;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.genomics.functionaltests.pageobjects.GenomeHome;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;

/**
 * Step definitions for Access Control functionality within the Genomics application.
 * This class handles user role switching and permission-based UI validation.
 */
public class AccessControlSteps {
  /**
   * Page Object for the Genome Home screen, initialized using the current Playwright page.
   */

  GenomeHome genomeHome = new GenomeHome(Hooks.page);

  /**
   * Switches the active user session to a specific role.
   *
   * @param arg0 the role name to select (e.g., "Admin", "Researcher")
   */
  @Given("I select {string} from the user roles")

  public void selectFromTheUserRoles(String arg0) {
    genomeHome.changeUserRole(arg0);
  }

  /**
   * Verifies that a specific button is visible on the interface.
   *
   * @param buttonName the label of the button expected to be visible
   */
  @Then("the {string} button should be visible")
  public void theButtonShouldBeVisible(String buttonName) {
    assertTrue(genomeHome.isButtonVisible(buttonName),
        buttonName + "button is not Visible for admin!");

  }

  /**
   * Validates that action tools are present on every row of the results table.
   *
   * @param editButton  the name/label of the edit tool
   * @param purgeButton the name/label of the purge tool
   */
  @And("every row in the registry should show {string} and {string} tools")
  public void everyRowInTheRegistryShouldShowAndTools(String editButton, String purgeButton) {
    assertAll(
        () -> assertTrue(genomeHome.allRowsHaveTool(editButton),
            "Edit Button is available for all rows"),
        () -> assertTrue(genomeHome.allRowsHaveTool(purgeButton),
            "Purge Button is available for all rows"));

  }

  /**
   * Updates the first available row in the table with values provided in the Feature file.
   *
   * @param dataTable the Cucumber DataTable containing Gene, rsID, and Ancestry data
   */
  @When("I modify the first row with:")
  public void modifyTheFirstRowWith(DataTable dataTable) {
    Map<String, String> data = dataTable.asMaps().getFirst();
    genomeHome.modifyDisplayedRow(data.get("Gene"), data.get("rsID"), data.get("Ancestry"));
  }

  /**
   * Triggers the purge action on the currently displayed row.
   */
  @When("I purge the displayed row")
  public void purgeTheDisplayedRow() {
    genomeHome.purgeDisplayedRow();
  }

  /**
   * Confirms that a specific gene record has been successfully removed from the UI.
   *
   * @param gene the gene identifier to check for absence
   */
  @Then("{string} should no longer appear in the registry")
  public void shouldNoLongerAppearInRegistry(String gene) {
    assertFalse(genomeHome.variantExistsInTable(gene),
        "Expected '" + gene + "' to be removed but it still appears in the table");
  }

  /**
   * Verifies that a specific button is hidden from the user interface.
   *
   * @param buttonLabel the label of the button expected to be hidden
   */
  @Then("the {string} button should be hidden")
  public void theButtonShouldBeHidden(String buttonLabel) {
    assertFalse(genomeHome.isButtonVisible(buttonLabel),
        "Expected button '" + buttonLabel + "' to be hidden but it was visible");
  }

  /**
   * Checks that multiple table tools are hidden simultaneously.
   *
   * @param tool1 the name of the first tool to check
   * @param tool2 the name of the second tool to check
   */
  @And("the sequence tools {string} and {string} should be hidden from the table")
  public void theSequenceToolsShouldBeHidden(String tool1, String tool2) {
    assertAll(() -> assertFalse(genomeHome.isToolVisibleInTable(tool1),
            "Expected '" + tool1 + "' to be hidden but it was visible"),
        () -> assertFalse(genomeHome.isToolVisibleInTable(tool2),
            "Expected '" + tool2 + "' to be hidden but it was visible"));
  }
}
