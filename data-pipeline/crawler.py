from pyhive import hive


def register_accounts_table(hive_host):

    connection = hive.Connection(
        host=hive_host,
        port=10000,
        database="default"
    )

    cursor = connection.cursor()

    create_table_sql = """
        CREATE EXTERNAL TABLE IF NOT EXISTS accounts (
            account_id STRING,
            customer_id STRING,
            customer_name STRING,
            country STRING,
            balance DOUBLE,
            account_type STRING,
            balance_category STRING,
            ingestion_timestamp TIMESTAMP
        )
        STORED AS PARQUET
        LOCATION 'file:///data/output/accounts'
    """

    cursor.execute(create_table_sql)

    cursor.close()
    connection.close()

    print("Crawler registered accounts table successfully.")