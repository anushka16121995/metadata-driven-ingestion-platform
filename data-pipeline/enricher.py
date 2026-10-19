from pyspark.sql.functions import col, when, current_timestamp


def enrich_data(dataframe, metadata_client, job_id):

    enricher_execution = metadata_client.start_stage(
        job_id,
        "ENRICHER"
    )

    enricher_execution_id = enricher_execution["stageExecutionId"]

    enriched_dataframe = (
        dataframe
        .withColumn(
            "balance_category",
            when(col("balance") == 0, "ZERO")
            .when(col("balance") >= 5000, "HIGH_VALUE")
            .otherwise("STANDARD")
        )
        .withColumn(
            "ingestion_timestamp",
            current_timestamp()
        )
    )

    record_count = enriched_dataframe.count()

    metadata_client.update_stage(
        job_id,
        enricher_execution_id,
        "COMPLETED",
        input_record_count=record_count,
        processed_record_count=record_count,
        rejected_record_count=0
    )

    return enriched_dataframe