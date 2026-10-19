import os
import sys

from pyspark.sql import SparkSession

os.environ["PYSPARK_PYTHON"] = sys.executable
os.environ["PYSPARK_DRIVER_PYTHON"] = sys.executable


spark = (
    SparkSession.builder
    .appName("ParquetWriteTest")
    .master("local[1]")
    .getOrCreate()
)


data = [
    (1, "Alice"),
    (2, "Bob")
]


columns = [
    "id",
    "name"
]


dataframe = spark.createDataFrame(
    data,
    columns
)


dataframe.show()


dataframe.write \
    .mode("overwrite") \
    .parquet("output/parquet_test")


print("Parquet write successful.")


spark.stop()