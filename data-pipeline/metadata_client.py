import requests


class MetadataClient:

    def __init__(self, base_url):
        self.base_url = base_url

    def create_job(self, file_name, input_file_path, file_size_bytes):
        url = self.base_url + "/api/v1/jobs"

        payload = {
            "fileName": file_name,
            "inputFilePath": input_file_path,
            "fileSizeBytes": file_size_bytes
        }

        response = requests.post(url, json=payload)

        response.raise_for_status()

        return response.json()


    def start_stage(self, job_id, stage):
        url = self.base_url + "/api/v1/jobs/" + str(job_id) + "/stages"

        payload = {
            "stage": stage
        }

        response = requests.post(url, json=payload)

        response.raise_for_status()

        return response.json()

    def update_stage(
            self,
            job_id,
            stage_execution_id,
            status,
            input_record_count=None,
            processed_record_count=None,
            rejected_record_count=None,
            error_message=None
        ):
            url = (
                self.base_url
                + "/api/v1/jobs/"
                + str(job_id)
                + "/stages/"
                + str(stage_execution_id)
            )

            payload = {
                "status": status,
                "inputRecordCount": input_record_count,
                "processedRecordCount": processed_record_count,
                "rejectedRecordCount": rejected_record_count,
                "errorMessage": error_message
            }

            response = requests.patch(url, json=payload)

            response.raise_for_status()

            return response.json()