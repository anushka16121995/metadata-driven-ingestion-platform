from pyspark.sql.functions import col


def validate_data(dataframe, metadata_client, job_id):

    validator_execution = metadata_client.start_stage(
        job_id,
        "VALIDATOR"
    )

    validator_execution_id = validator_execution["stageExecutionId"]

    valid_condition = (
        col("customer_id").isNotNull()
        & (col("balance") >= 0)
    )

    valid_dataframe = dataframe.filter(valid_condition)

    rejected_dataframe = dataframe.filter(~valid_condition)

    input_record_count = dataframe.count()
    processed_record_count = valid_dataframe.count()
    rejected_record_count = rejected_dataframe.count()

    metadata_client.update_stage(
        job_id,
        validator_execution_id,
        "COMPLETED",
        input_record_count=input_record_count,
        processed_record_count=processed_record_count,
        rejected_record_count=rejected_record_count
    )

    return valid_dataframe, rejected_dataframe