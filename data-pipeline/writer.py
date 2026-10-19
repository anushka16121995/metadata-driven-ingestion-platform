from pathlib import Path


def write_data(
    enriched_dataframe,
    rejected_dataframe,
    metadata_client,
    job_id
):

    writer_execution = metadata_client.start_stage(
        job_id,
        "WRITER"
    )

    writer_execution_id = writer_execution["stageExecutionId"]

    output_path = "output/accounts"
    rejected_path = "rejected/accounts"

    try:

        enriched_dataframe.write \
            .mode("overwrite") \
            .parquet(output_path)

        rejected_dataframe.write \
            .mode("overwrite") \
            .parquet(rejected_path)

        processed_record_count = enriched_dataframe.count()
        rejected_record_count = rejected_dataframe.count()

        metadata_client.update_stage(
            job_id,
            writer_execution_id,
            "COMPLETED",
            input_record_count=(
                processed_record_count
                + rejected_record_count
            ),
            processed_record_count=processed_record_count,
            rejected_record_count=rejected_record_count
        )

        return str(Path(output_path).absolute())

    except Exception as error:

        metadata_client.update_stage(
            job_id,
            writer_execution_id,
            "FAILED",
            error_message=str(error)
        )

        raise