from bigquery_loader import load_parquet_to_bigquery


PROJECT_ID = "metadata-ingestion-platform"
DATASET_ID = "metadata_analytics"
TABLE_ID = "accounts"
PARQUET_DIRECTORY = "output/accounts"


row_count = load_parquet_to_bigquery(
    PROJECT_ID,
    DATASET_ID,
    TABLE_ID,
    PARQUET_DIRECTORY
)


print(
    "Verified BigQuery row count:",
    row_count
)