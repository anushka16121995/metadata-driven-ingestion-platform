from datetime import datetime, timedelta

from airflow import DAG
from airflow.operators.python import PythonOperator


def run_ingestion_pipeline():
    print("Starting PySpark ingestion pipeline")

    print(
        "Pipeline stages: "
        "SCANNER -> LOADER -> VALIDATOR -> ENRICHER -> WRITER"
    )

    print("PySpark ingestion pipeline completed")


def register_hive_catalog():
    print("Starting Hive crawler")

    print(
        "Registering processed Parquet dataset "
        "as Hive external table"
    )

    print("Hive crawler completed")

default_args = {
    "owner": "data-engineering",
    "retries": 2,
    "retry_delay": timedelta(minutes=1)
}

with DAG(
    dag_id="metadata_ingestion_pipeline",
    description="Orchestrates PySpark ingestion and Hive catalog registration",
    default_args=default_args,
    start_date=datetime(2026, 1, 1),
    schedule=None,
    catchup=False,
    tags=["data-engineering", "pyspark", "hive"]
) as dag:

    ingestion = PythonOperator(
        task_id="run_ingestion_pipeline",
        python_callable=run_ingestion_pipeline
    )

    crawler = PythonOperator(
        task_id="register_hive_catalog",
        python_callable=register_hive_catalog
    )

    ingestion >> crawler