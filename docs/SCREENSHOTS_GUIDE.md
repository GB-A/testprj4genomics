# Capturing & Adding Test Report Screenshots

This guide walks you through capturing your test report HTML and adding screenshots to GitHub.

## Step 1: Generate Fresh Test Report

```bash
# Navigate to project root
cd /Users/satsaugeeth/IdeaProjects/genomic-qa-pgp

# Clean previous builds
mvn clean

# Run all tests to generate fresh report
mvn test

# ✅ Report generated at: target/cucumber-reports/report.html
```

## Step 2: Open Report in Browser

```bash
# Open the HTML report in your default browser
open target/cucumber-reports/report.html
```

## Step 3: Capture Screenshots

### Using macOS Built-in Screenshot Tool

```bash
# Press Cmd + Shift + 4
# Then click and drag to select area
# Image will save to Desktop automatically
# Move to screenshots folder:
mv ~/Desktop/Screenshot*.png docs/screenshots/
```

### Using Chrome DevTools

1. Open DevTools: `Cmd + Option + I`
2. Click `Cmd + Shift + P` to open Command Palette
3. Type "Capture full page screenshot"
4. Press Enter
5. Save file to `docs/screenshots/`

### Using Firefox

1. Right-click anywhere on page
2. Select "Take Screenshot"
3. Choose "Save full page"
4. Save to `docs/screenshots/`

## Step 4: Screenshots to Capture

### Screenshot 1: Report Summary Page
**What to capture**: Top of the report showing:
- Title: "PGP-UK Genomic Portal - QA Test Automation Suite"
- Summary box with:
  - Total Scenarios: 16
  - Passed: 14
  - Failed: 0
  - Skipped: 2
  - Duration: 2m 34s
- Feature list

**File name**: `01-report-summary.png`

```bash
# Run tests, open report, take screenshot of top portion
```

### Screenshot 2: Feature Overview
**What to capture**: The feature file section showing:
- Feature titles (access_control, ui_smoke_test, etc.)
- Number of scenarios per feature
- Pass/fail indicators per feature

**File name**: `02-feature-overview.png`

### Screenshot 3: Detailed Scenario - Admin CRUD
**What to capture**: Scroll down and click on the "Admin user has full CRUD permissions" scenario
- Show all 8 steps
- Show DataTable with variant data:
  | Gene             | rsID      | Ancestry |
  | VDR_TO_BE_EDITED | rs1544410 | African  |
- Show step execution times

**File name**: `03-admin-crud-scenario.png`

### Screenshot 4: Smoke Test Scenario
**What to capture**: Click on "Core Dashboard components are rendered on load" scenario
- Show all 7 steps
- Show execution times
- Highlight the "949 rows" assertion

**File name**: `04-smoke-test-scenario.png`

### Screenshot 5: Known Bug Scenario
**What to capture**: Show the skipped scenario with @known-bug tag
- "Ancestry Chart dynamically updates for new populations"
- Show SKIP status
- Show reason/note about expected failure

**File name**: `05-known-bug-scenario.png`

## Step 5: Create Directory Structure

```bash
# Create docs/screenshots directory
mkdir -p docs/screenshots

# You should have:
# docs/screenshots/01-report-summary.png
# docs/screenshots/02-feature-overview.png
# docs/screenshots/03-admin-crud-scenario.png
# docs/screenshots/04-smoke-test-scenario.png
# docs/screenshots/05-known-bug-scenario.png
```

## Step 6: Optional - Create Index Document

Create `docs/REPORT_OVERVIEW.md`:

```markdown
# Test Report Overview

This document shows sample outputs from the Cucumber HTML test report.

## Summary Dashboard
![Report Summary](screenshots/01-report-summary.png)
*All 14 executable scenarios passing (2 known bugs skipped)*

## Feature Overview
![Feature Overview](screenshots/02-feature-overview.png)
*Test suite organized into 4 feature files with comprehensive coverage*

## Admin CRUD Scenario
![Admin CRUD](screenshots/03-admin-crud-scenario.png)
*Complex scenario demonstrating Create, Read, Update, Delete operations*

## Smoke Tests
![Smoke Tests](screenshots/04-smoke-test-scenario.png)
*UI integrity and component rendering validation*

## Known Bug Tracking
![Known Bug](screenshots/05-known-bug-scenario.png)
*Documented chart synchronization issue with skipped scenario*
```

## Step 7: Update Main README (Optional)

Add to main README.md in the "Test Execution & Reporting" section:

```markdown
### Sample Report Screenshots

#### Execution Summary
![Test Report Summary](docs/screenshots/01-report-summary.png)

#### Detailed Scenarios
- [Admin CRUD Operations](docs/screenshots/03-admin-crud-scenario.png)
- [UI Smoke Tests](docs/screenshots/04-smoke-test-scenario.png)
- [Known Issues](docs/screenshots/05-known-bug-scenario.png)
```

## Step 8: Commit to Git

```bash
# Add all screenshots
git add docs/screenshots/

# Commit changes
git commit -m "docs: Add test report screenshots for visual documentation"

# Push to GitHub
git push origin main
```

## Step 9: Verify on GitHub

1. Go to your GitHub repo
2. Navigate to `docs/screenshots/` folder
3. Verify all PNG files are visible
4. Check that README displays embedded images correctly

---

## Tips for Better Screenshots

✅ **Do**:
- Capture at full browser width (1280px or more)
- Include browser tabs/address bar for context
- Zoom to 100% (no zooming in/out)
- Use clean, well-organized report layouts
- Include complete scenario steps

❌ **Don't**:
- Capture at 4K/ultrawide resolution (too large)
- Include sensitive data (though test data is fine)
- Crop important context
- Use low-quality compression

---

## Automation (Optional)

You can automate screenshot capture with tools like:

- **Playwright** - `page.screenshot()` (already in use!)
- **Puppeteer** - Node.js automation
- **Cypress** - Has built-in screenshot capability
- **GitHub Actions** - Auto-capture and commit

For this project, manual screenshots are sufficient and show you understand the test output.

---

## Troubleshooting

**Q: Report not generating?**
A: Run `mvn clean test` - make sure no tests are failing unexpectedly

**Q: Can't find target/cucumber-reports/report.html?**
A: Run `mvn test` first, then check target directory exists

**Q: Image quality is poor?**
A: Use native OS screenshot tools, avoid online converters

**Q: How do I embed images in markdown?**
A: Use: `![Alt text](path/to/image.png)`

---

**Last Updated**: March 2, 2026

