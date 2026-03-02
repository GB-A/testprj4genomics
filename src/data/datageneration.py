import os
import pandas as pd


def create_raw_files():
    if not os.path.exists('raw'):
        os.makedirs('raw')

    # 1. Create Participant Table (Primary Key Source)
    participants = pd.DataFrame({
        'participant_id': ['uk1', 'uk2', 'uk3'],
        'ancestry': ['European', 'African', 'South Asian'],
        'blood_type': ['O+', 'A-', 'B+']
    })

    # 2. Create Variants Table (with intentional errors)
    # 1000 total rows: 949 valid, 51 orphans
    variants = pd.DataFrame({
        'variant_id': range(1, 1001),
        'participant_id': (['uk1', 'uk2', 'uk3'] * 316 + ['uk1']) + (['uk_ORPHAN'] * 51),
        'rsid': [f'rs{1000 + i}' for i in range(1000)],
        'gene': ['HERC2', 'ABO', 'BRCA1', 'APOE'] * 250
    })

    # 3. Save to CSV
    participants.to_csv('raw/participants.csv', index=False)
    variants.to_csv('raw/variants_raw.csv', index=False)

    print("📂 Step 1 Complete: Raw CSVs generated in src/data/raw/")
    print(f"⚠️ Note: variants_raw.csv contains 51 'uk_ORPHAN' records.")


if __name__ == "__main__":
    create_raw_files()
