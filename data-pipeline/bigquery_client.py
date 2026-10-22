from google.cloud import bigquery


def create_bigquery_client(project_id):

    client = bigquery.Client(
        project=project_id
    )

    return client