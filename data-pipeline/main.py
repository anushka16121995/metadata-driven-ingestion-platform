import os

from metadata_client import MetadataClient

from scanner import scan_file
from loader import create_spark_session, load_csv

from validator import validate_data

from enricher import enrich_data

from writer import write_data


metadata_service_url = os.getenv(
    "METADATA_SERVICE_URL",
    "http://localhost:8080"
)

client = MetadataClient(metadata_service_url)


spark = create_spark_session()


file_path = "input/accounts.csv"


job_id = scan_file(
    file_path,
    client
)


print("Scanner finished successfully.")
print("Job ID:", job_id)


dataframe = load_csv(
    file_path,
    spark,
    client,
    job_id
)


print("Loader finished successfully.")

dataframe.show()

dataframe.printSchema()

valid_dataframe, rejected_dataframe = validate_data(
    dataframe,
    client,
    job_id
)


print("Validator finished successfully.")


print("VALID RECORDS:")
valid_dataframe.show()


print("REJECTED RECORDS:")
rejected_dataframe.show()

enriched_dataframe = enrich_data(
    valid_dataframe,
    client,
    job_id
)

print("Enricher finished successfully.")

print("ENRICHED RECORDS:")
enriched_dataframe.show(truncate=False)

output_path = write_data(
    enriched_dataframe,
    rejected_dataframe,
    client,
    job_id
)

print("Writer finished successfully.")
print("Output path:", output_path)

spark.stop()

print("Pipeline completed successfully.")

