from pathlib import Path

from google.cloud import bigquery

from bigquery_client import create_bigquery_client


def load_parquet_to_bigquery(
    project_id,
    dataset_id,
    table_id,
    parquet_directory
):

    client = create_bigquery_client(project_id)

    table_reference = (
        f"{project_id}.{dataset_id}.{table_id}"
    )

    parquet_files = list(
        Path(parquet_directory).glob("*.parquet")
    )

    if not parquet_files:
        raise FileNotFoundError(
            f"No Parquet files found in {parquet_directory}"
        )

    job_config = bigquery.LoadJobConfig(
        source_format=bigquery.SourceFormat.PARQUET,
        write_disposition=bigquery.WriteDisposition.WRITE_TRUNCATE
    )

    for parquet_file in parquet_files:

        print(
            f"Loading Parquet file: {parquet_file}"
        )

        with open(parquet_file, "rb") as source_file:

            load_job = client.load_table_from_file(
                source_file,
                table_reference,
                job_config=job_config
            )

            load_job.result()

    table = client.get_table(table_reference)

    print(
        f"BigQuery load completed. "
        f"Table contains {table.num_rows} rows."
    )

    return table.num_rows