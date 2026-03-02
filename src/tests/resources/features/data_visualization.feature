Feature: Analytics Visualization Synchronization
  As a Data Scientist
  I want the charts to accurately reflect the live dataset
  So that my population analysis is based on current data
  @known-bug @chart-desync
  Scenario: Ancestry Chart dynamically updates for new populations
    Given the current Ancestry Chart shows 3 distinct populations
    When I add a new variant with "American" ancestry
    Then the "TEST_GENE" record should appear in the table
    And the Ancestry Chart should now show 4 distinct population segments
    # NOTE: This scenario IS EXPECTED to fail - demonstrates UI desync bug
    # Chart segments do not update dynamically when new populations are added
    # The table updates correctly but the chart remains stale
