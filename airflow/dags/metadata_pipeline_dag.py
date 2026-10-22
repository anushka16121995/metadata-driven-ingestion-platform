from datetime import datetime, timedelta

from airflow import DAG
from airflow.operators.bash import BashOperator


default_args = {
    "owner": "data-engineering",
    "retries": 2,
    "retry_delay": timedelta(minutes=1)
}


with DAG(
    dag_id="metadata_ingestion_pipeline",
    description="Orchestrates Dockerized PySpark ingestion and Hive catalog registration",
    default_args=default_args,
    start_date=datetime(2026, 1, 1),
    schedule=None,
    catchup=False,
    tags=["data-engineering", "pyspark", "hive", "docker"]
) as dag:

    ingestion = BashOperator(
        task_id="run_ingestion_pipeline",
        bash_command="""
        docker run --rm \
        -e METADATA_SERVICE_URL=http://host.docker.internal:8080 \
        metadata-pipeline
        """
    )

    crawler = BashOperator(
        task_id="register_hive_catalog",
        bash_command="""
        docker run --rm \
        metadata-pipeline \
        python test_crawler.py
        """
    )

    ingestion >> crawler