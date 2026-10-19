from pathlib import Path


def scan_file(file_path, metadata_client):
    path = Path(file_path)

    file_name = path.name
    file_size_bytes = path.stat().st_size

    job = metadata_client.create_job(
        file_name,
        str(path.absolute()),
        file_size_bytes
    )

    job_id = job["jobId"]

    scanner_execution = metadata_client.start_stage(
        job_id,
        "SCANNER"
    )

    scanner_execution_id = scanner_execution["stageExecutionId"]

    metadata_client.update_stage(
        job_id,
        scanner_execution_id,
        "COMPLETED"
    )

    return job_id