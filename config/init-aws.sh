#!/bin/bash

echo "Creating S3 BUCKET..."
awslocal s3 mb s3://transactions_reports
echo "Bucket created successfully."

echo "Creating SES..."
awslocal ses verify-email-identity --email-address example@example.com
