resource "aws_s3_bucket" "tf_lab_bucket" {
  bucket = var.bucket_name
}