from pyspark.sql import SparkSession


def create_spark_session():
    spark = (
        SparkSession.builder
        .appName("MetadataIngestionPipeline")
        .master("local[*]")
        .getOrCreate()
    )

    return spark

def load_csv(file_path, spark, metadata_client, job_id):
    loader_execution = metadata_client.start_stage(
        job_id,
        "LOADER"
    )

    loader_execution_id = loader_execution["stageExecutionId"]

    dataframe = (
        spark.read
        .option("header", True)
        .option("inferSchema", True)
        .csv(file_path)
    )

    input_record_count = dataframe.count()

    metadata_client.update_stage(
        job_id,
        loader_execution_id,
        "COMPLETED",
        input_record_count=input_record_count,
        processed_record_count=input_record_count,
        rejected_record_count=0
    )

    return dataframe