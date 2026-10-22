from bigquery_client import create_bigquery_client


PROJECT_ID = "metadata-ingestion-platform"


client = create_bigquery_client(
    PROJECT_ID
)


query = f"""
    SELECT COUNT(*) AS total_accounts
    FROM `{PROJECT_ID}.metadata_analytics.accounts`
"""


query_job = client.query(query)


results = query_job.result()


for row in results:
    print("Total accounts:", row.total_accounts)