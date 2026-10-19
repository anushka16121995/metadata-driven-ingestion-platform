from metadata_client import MetadataClient


client = MetadataClient("http://localhost:8080")


job = client.create_job(
    "python_pipeline_test.csv",
    "/incoming/python_pipeline_test.csv",
    5000
)

print("Created job:")
print(job)


job_id = job["jobId"]


scanner_execution = client.start_stage(
    job_id,
    "SCANNER"
)

print("Started Scanner:")
print(scanner_execution)


scanner_execution_id = scanner_execution["stageExecutionId"]


completed_scanner = client.update_stage(
    job_id,
    scanner_execution_id,
    "COMPLETED"
)

print("Completed Scanner:")
print(completed_scanner)