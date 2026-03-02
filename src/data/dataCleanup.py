import json
import os
import pandas as pd


def process_and_clean():
    # 1. Load Raw Data
    try:
        participants = pd.read_csv('raw/participants.csv')
        variants = pd.read_csv('raw/variants_raw.csv')
    except FileNotFoundError:
        print("❌ Error: Raw files not found. Run generate_raw_data.py first.")
        return

    # 2. Identify Errors (Lead QA Reporting)
    orphans = variants[~variants['participant_id'].isin(participants['participant_id'])]
    print(f"🔍 Audit: Found {len(orphans)} rows with invalid Foreign Keys.")

    # 3. Clean & Join (Inner Join drops orphans)
    cleaned_df = pd.merge(variants, participants, on='participant_id', how='inner')

    # 4. Final Validation
    expected_count = 949
    if len(cleaned_df) == expected_count:
        print(f"✅ Validation Passed: {len(cleaned_df)} clean records ready.")
    else:
        print(f"⚠️ Warning: Row count is {len(cleaned_df)}, expected {expected_count}.")

    # 5. Export for WebApp
    if not os.path.exists('../app'): os.makedirs('../app')

    result = cleaned_df.to_dict(orient='records')
    with open('../app/cleaned_data.json', 'w') as f:
        json.dump(result, f, indent=4)

    print("🚀 Step 2 Complete: 'app/cleaned_data.json' created.")


if __name__ == "__main__":
    process_and_clean()
