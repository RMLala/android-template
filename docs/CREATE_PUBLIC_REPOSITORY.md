# Automatic public repository creation — RMLala

## Purpose
Use the workflow **Create public Android repository** in Actions of `RMLala/android-template` to generate a new public repository from this template and create `work/initial-development`.

## One-time authorization
An organization administrator must create a dedicated, restricted credential and store it in the organization Actions secrets as `RMLALA_REPO_CREATOR_TOKEN` with access to `android-template`.

A classic PAT with `public_repo` may allow creation of public repositories under an organization when its owner has the appropriate organization permissions. Because such a token can have broad privileges, use a dedicated service account with the minimum organization role, short expiration, rotation, and appropriate organization approval. Never paste a token into chat, code, README, or workflow inputs. Verify organization policy and any SSO requirements. A GitHub App with narrowly scoped permissions is preferable if supported by the organization creation workflow.

## Manual test
1. Merge the workflow PR after review.
2. Go to Actions → **Create public Android repository** → **Run workflow**.
3. Enter a unique repository name and optional description.
4. Inspect the workflow logs and the new public repository.
5. Verify `work/initial-development` exists and the new repository has access to the intended organization secrets.

## Limits
- The workflow does not upload the application source, open an application PR, or compile a complete application. These steps must be performed by the application's own development workflow.
- This workflow is invoked from GitHub Actions; a chat message alone cannot trigger it unless a connected tool is granted permission to dispatch the workflow.
- A generated repository uses the template's `main` content. Change its package ID and application code before publishing any APK.
- Existing repositories are never overwritten.
- Restrict who can run the workflow and who can modify its YAML; the creation credential is sensitive.
