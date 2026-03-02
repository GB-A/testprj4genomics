Feature: Role-Based Access Control (RBAC)
  As a system administrator
  I want to restrict data modification to authorized users
  So that the genomic dataset remains untampered

  Scenario: Admin user has full CRUD permissions
    Given I select "System Admin" from the user roles
    Then the "New Analysis" button should be visible
    And every row in the registry should show "Modify" and "Purge" tools
    And I register a new variant with:
      | Gene | rsID       | Ancestry |
      | VDR_TO_BE_EDITED  | rs1544410  | African  |
    And the first row in the table should show:
      | Gene          | rsID      | Ancestry |
      | VDR_TO_BE_EDITED | rs1544410 | African  |
    When I modify the first row with:
      | Gene       | rsID      | Ancestry   |
      | VDR_TO_BE_DELETED  | rs1544410 | South Asian|
    Then the first row in the table should show:
      | Gene      | rsID      | Ancestry    |
      | VDR_TO_BE_DELETED | rs1544410 | South Asian |
    When I purge the displayed row
    Then "VDR_TO_BE_DELETED" should no longer appear in the registry


  Scenario: Researcher user has read-only permissions
    Given I select "Clinical Researcher" from the user roles
    Then the "New Analysis" button should be hidden
    And the sequence tools "Modify" and "Purge" should be hidden from the table