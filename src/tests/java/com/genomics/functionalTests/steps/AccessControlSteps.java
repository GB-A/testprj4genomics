package com.genomics.functionalTests.steps;

import com.genomics.functionalTests.pageObjects.GenomeHome;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AccessControlSteps {

    GenomeHome genomeHome=new GenomeHome(Hooks.page);
    @Given("I select {string} from the user roles")
    public void iSelectFromTheUserRoles(String arg0) {
        genomeHome.changeUserRole(arg0);
    }

    @Then("the {string} button should be visible")
    public void theButtonShouldBeVisible(String buttonName) {
        assertTrue(genomeHome.isButtonVisible(buttonName),
                        buttonName+"button is not Visible for admin!");

    }

    @And("every row in the registry should show {string} and {string} tools")
    public void everyRowInTheRegistryShouldShowAndTools(String editButton, String purgeButton) {
        genomeHome.allRowsHaveTool(editButton);
        genomeHome.allRowsHaveTool(purgeButton);

    }
    @When("I modify the first row with:")
    public void iModifyTheFirstRowWith(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        genomeHome.modifyDisplayedRow(
                data.get("Gene"),
                data.get("rsID"),
                data.get("Ancestry")
        );
    }

    @When("I purge the displayed row")
    public void iPurgeTheDisplayedRow() {
        genomeHome.purgeDisplayedRow();
    }
    @Then("{string} should no longer appear in the registry")
    public void shouldNoLongerAppearInRegistry(String gene) {
        assertFalse(
                genomeHome.variantExistsInTable(gene),
                "Expected '" + gene + "' to be removed but it still appears in the table"
        );
    }
    @Then("the {string} button should be hidden")
    public void theButtonShouldBeHidden(String buttonLabel) {
        assertFalse(
                genomeHome.isButtonVisible(buttonLabel),
                "Expected button '" + buttonLabel + "' to be hidden but it was visible"
        );
    }
    @And("the sequence tools {string} and {string} should be hidden from the table")
    public void theSequenceToolsShouldBeHidden(String tool1, String tool2) {
        assertAll(
                () -> assertFalse(
                        genomeHome.isToolVisibleInTable(tool1),
                        "Expected '" + tool1 + "' to be hidden but it was visible"
                ),
                () -> assertFalse(
                        genomeHome.isToolVisibleInTable(tool2),
                        "Expected '" + tool2 + "' to be hidden but it was visible"
                )
        );
    }
}
