Feature: Genomic Variant Management
  As a Clinical Researcher
  I want to manage and filter the variant database
  So that I can identify specific markers for study

  Scenario: Search for a specific gene
    Given the registry contains "BRCA1" and "APOE" variants
    When I search for "APOE" in the search bar
    Then the table should only display "APOE" records
    And the "Registry Variants" count should still reflect the full dataset

  Scenario: Add a new clinical variant
    Given I am an authorized Admin
    When I register a new variant with:
      | Gene | rsID      | Ancestry |
      | VDR  | rs1544410 | African  |
    Then the first row in the table should show:
      | Gene | rsID      | Ancestry |
      | VDR  | rs1544410 | African  |
    And the "Registry Variants" count should increase full dataset by one
    And the System Audit Log should record "SUCCESS: Created rs1544410"
