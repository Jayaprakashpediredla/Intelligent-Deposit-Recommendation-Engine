# Deposit Recommendation Engine

This repository contains the components for the Deposit Recommendation Engine project.

Folder structure

- backend/         # API server, business logic, services
- frontend/        # UI (web/mobile) sources
- ai-engine/       # Model training, inference, notebooks
- datasets/        # Raw and processed datasets
- docs/            # Project documentation and user guides
- architecture/    # Diagrams, architecture notes
- docker/          # Dockerfiles, compose files
- scripts/         # Utility scripts (setup, data ingestion, etc.)
- postman/         # Postman collections and environments
- deployment/      # IaC, deployment manifests (K8s / Helm / Terraform)

How to use

- Add your code to the appropriate subfolder (e.g. put Spring Boot code in `backend/`).
- Keep datasets out of version control if large; use `datasets/README.md` to document external storage.

Generated: July 16, 2026

