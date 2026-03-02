# 📸 Screenshot Reference Guide

## What Each Screenshot Should Show

When you capture these 5 screenshots, here's exactly what you should see:

---

## Screenshot 1: Report Summary (`01-report-summary.png`)

**Where to capture**: Top of the Cucumber HTML report
**What you'll see**:
```
┌─────────────────────────────────────────────────────────┐
│    PGP-UK Genomic Portal - QA Test Automation Suite     │
│                                                           │
│  ✅ 14 Passed                                            │
│  ❌ 0 Failed                                             │
│  ⏭️ 2 Skipped                                            │
│                                                           │
│  Duration: 2m 34s                                        │
│                                                           │
│  [Charts showing pass/fail distribution]                │
│  [List of 4 Feature files below]                        │
└─────────────────────────────────────────────────────────┘
```

**Why it's important**: Shows:
- Overall test success rate (100%)
- Test execution time
- Pass/Fail/Skip breakdown
- Professional report organization

---

## Screenshot 2: Feature Overview (`02-feature-overview.png`)

**Where to capture**: Feature list section of report
**What you'll see**:
```
Features
────────────────────────────────────────────
✅ Role-Based Access Control (RBAC)
   └─ 2 scenarios, 2 passed

✅ UI Integrity and Smoke Suite
   └─ 1 scenario, 1 passed

✅ Genomic Variant Management
   └─ 2 scenarios, 2 passed

⏭️ Analytics Visualization Synchronization
   └─ 1 scenario, 1 skipped (known bug)
```

**Why it's important**: Shows:
- Clear test organization
- Test categorization
- Coverage across multiple feature areas
- Professional BDD structure

---

## Screenshot 3: Admin CRUD Scenario (`03-admin-crud-scenario.png`)

**Where to capture**: Click on "Admin user has full CRUD permissions" scenario
**What you'll see**:
```
✅ Scenario: Admin user has full CRUD permissions (45.2s)

  ✅ Given I select "System Admin" from the user roles (1.234s)

  ✅ Then the "New Analysis" button should be visible (0.567s)

  ✅ When I register a new variant with: (5.123s)
     ┌────────────────────────────────┐
     │ Gene             │ rsID   │ Ancestry │
     ├────────────────────────────────┤
     │ VDR_TO_BE_EDITED │ rs1544410 │ African  │
     └────────────────────────────────┘

  ✅ Then the first row in the table should show: (2.345s)
     [DataTable displayed]

  ✅ When I modify the first row with: (3.456s)
     ┌────────────────────────────────┐
     │ Gene              │ rsID   │ Ancestry    │
     ├────────────────────────────────┤
     │ VDR_TO_BE_DELETED │ rs1544410 │ South Asian │
     └────────────────────────────────┘

  ✅ Then the first row in the table should show: (1.234s)
     [DataTable displayed]

  ✅ When I purge the displayed row (2.123s)

  ✅ Then "VDR_TO_BE_DELETED" should no longer appear in the registry (0.987s)
```

**Why it's important**: Shows:
- Complex test scenario with multiple steps
- CRUD operations (Create, Read, Update, Delete)
- DataTable usage in tests
- Step execution times
- Professional assertion messages

---

## Screenshot 4: Smoke Test Scenario (`04-smoke-test-scenario.png`)

**Where to capture**: Click on "Core Dashboard components are rendered on load" scenario
**What you'll see**:
```
✅ Scenario: Core Dashboard components are rendered on load (22.1s)

  ✅ Given I navigate to the Genomic Portal (2.345s)

  ✅ Then the page title should be "PGP-UK Genomic Portal | Biotech Edition" (0.234s)

  ✅ And I should see the following metric cards (1.567s)

  ✅ And the Ancestry Distribution Chart should be visible (0.456s)

  ✅ And the System Audit Log should be active (0.345s)

  ✅ And the Data Registry Table should display genomic headers (0.567s)

  ✅ And the Table should have 949 rows (0.789s)
```

**Why it's important**: Shows:
- UI smoke test coverage
- Dashboard component validation
- Critical data validation (949 rows = correct data cleanup)
- Component visibility checks
- Professional assertion naming

---

## Screenshot 5: Known Bug Scenario (`05-known-bug-scenario.png`)

**Where to capture**: Scroll down to find "Ancestry Chart dynamically updates" scenario
**What you'll see**:
```
⏭️ Scenario: Ancestry Chart dynamically updates for new populations (SKIPPED)

  Tags: @known-bug @chart-desync

  Status: ⏭️ SKIPPED (Known Issue - Expected Failure)

  Note:
  This scenario is marked as a known bug and is expected to fail.
  The chart visualization does not update when new populations are added,
  even though the data table updates correctly.

  See: Known Issues & Bug Tracking section in README.md for details.
```

**Why it's important**: Shows:
- Professional bug tracking approach
- Transparency about known issues
- Understanding of test risk management
- Documentation of technical debt
- Leadership quality (admitting/tracking bugs)

---

## How to Take These Screenshots

### Method 1: macOS Native Screenshot (Easiest)
```bash
# Press Cmd + Shift + 4
# Cursor changes to crosshair
# Click and drag to select the area you want
# Screenshot saves to Desktop automatically
# Move to docs/screenshots/ folder
```

### Method 2: Using Chrome DevTools
```bash
1. Open report in Chrome
2. Press Cmd + Option + I (to open DevTools)
3. Press Cmd + Shift + P (Command Palette)
4. Type: "Capture full page screenshot"
5. Press Enter
6. File saves to Downloads
7. Move to docs/screenshots/
```

### Method 3: Using Firefox
```bash
1. Open report in Firefox
2. Right-click on page
3. Select "Take Screenshot"
4. Click "Save full page"
5. File saves to Downloads
6. Move to docs/screenshots/
```

---

## Naming Convention

Save screenshots with this naming:
```
01-report-summary.png
02-feature-overview.png
03-admin-crud-scenario.png
04-smoke-test-scenario.png
05-known-bug-scenario.png
```

**Why numbered prefixes?**
- Makes file organization clear
- GitHub displays files in alphabetical order
- Numbered order maintains logical flow
- Professional presentation

---

## Quality Checklist for Each Screenshot

After capturing, verify each screenshot has:

### ✅ For Report Summary (01)
- [ ] Title visible: "PGP-UK Genomic Portal"
- [ ] Summary metrics visible (14 passed, 0 failed, 2 skipped)
- [ ] Duration visible (2m 34s)
- [ ] Pass rate calculation visible (100%)
- [ ] Feature list below the summary

### ✅ For Feature Overview (02)
- [ ] All 4 feature files visible
- [ ] Check marks for passed features
- [ ] Skip indicator for known bug feature
- [ ] Scenario counts per feature

### ✅ For Admin CRUD (03)
- [ ] All 8 steps visible
- [ ] DataTables showing properly formatted
- [ ] Step execution times visible (1.234s format)
- [ ] Green checkmarks on all steps

### ✅ For Smoke Test (04)
- [ ] All 7 steps visible
- [ ] Includes "949 rows" assertion
- [ ] Metric cards validation visible
- [ ] Chart validation visible

### ✅ For Known Bug (05)
- [ ] Shows SKIPPED status
- [ ] @known-bug tag visible
- [ ] @chart-desync tag visible
- [ ] Note about expected failure visible

---

## File Size & Resolution Tips

**Optimal Screenshot Settings**:
- Resolution: 1280x800 or higher
- Format: PNG (automatic with macOS)
- File size: Usually 200-800 KB per screenshot
- Quality: 100% (no compression)

**Quick Size Check**:
```bash
ls -lh docs/screenshots/
# Each file should be 200-800 KB

# If files are too large (>5 MB):
# - Zoom in browser to 100% (not 125% or 150%)
# - Avoid ultra-high resolution monitors
# - Take smaller crops of just the relevant area
```

---

## Embedding in README (Optional)

Once you have screenshots, you can update README with:

```markdown
### Test Report Screenshots

#### Execution Summary
![Test Report Summary](docs/screenshots/01-report-summary.png)
*Figure 1: Complete test run showing 14 passing scenarios (2 known bugs skipped)*

#### Feature Overview
![Feature Overview](docs/screenshots/02-feature-overview.png)
*Figure 2: Four feature files with comprehensive test coverage*

#### Admin User CRUD Operations
![Admin CRUD](docs/screenshots/03-admin-crud-scenario.png)
*Figure 3: Create, Read, Update, Delete workflow demonstrating role-based permissions*

#### Dashboard Smoke Tests
![Smoke Tests](docs/screenshots/04-smoke-test-scenario.png)
*Figure 4: 949 clean records in data registry after ETL validation*

#### Known Issue Tracking
![Known Bug](docs/screenshots/05-known-bug-scenario.png)
*Figure 5: Chart synchronization bug properly documented and tracked*
```

---

## Troubleshooting

**Q: Can't find the report?**
A: Run `mvn clean test` first. Report generates to `target/cucumber-reports/report.html`

**Q: Report shows different numbers?**
A: That's OK! Your report will show actual test results. The values in this guide are examples.

**Q: Screenshot looks blurry?**
A: Make sure browser zoom is at 100% (Cmd+0 to reset)

**Q: File is too large?**
A: Don't worry, GitHub handles it fine. 2-3 MB total is normal.

**Q: How do I view the HTML report?**
A: `open target/cucumber-reports/report.html` or drag file into browser

---

## Timeline

```
Now:           ✅ Understand what to capture (you're reading this!)
This hour:     ⏳ Generate fresh test reports (5 mins)
This hour:     ⏳ Capture 5 screenshots (10 mins)
This hour:     ⏳ Commit to GitHub (2 mins)
Today:         ✅ Portfolio piece complete!
Next week:     🎯 Show to Genomics England hiring team
```

---

**Ready? Start here:**
1. Terminal 1: `cd genomic-qa-pgp && mvn clean test`
2. Once done: `open target/cucumber-reports/report.html`
3. Use Cmd+Shift+4 to capture screenshots
4. Save to `docs/screenshots/`
5. Commit and push!

Good luck! 🚀

