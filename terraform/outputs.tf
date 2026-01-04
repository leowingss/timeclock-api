output "ecr_repository_url" {
  description = "The URL of the ECR repository"
  value       = module.ecr_timeclock.repository_url
}