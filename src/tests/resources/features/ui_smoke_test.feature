@smoke
Feature: UI Integrity and Smoke Suite
  As a user of the PGP-UK Genomic Portal
  I want to ensure the dashboard layout is intact
  So that I can perform clinical analysis without UI failures

  Scenario: Core Dashboard components are rendered on load
    Given I navigate to the Genomic Portal
    Then the page title should be "PGP-UK Genomic Portal | Biotech Edition"
    And I should see the following metric cards
    And the Ancestry Distribution Chart should be visible
    And the System Audit Log should be active
    And the Data Registry Table should display genomic headers
    And the Table should have 949 rows