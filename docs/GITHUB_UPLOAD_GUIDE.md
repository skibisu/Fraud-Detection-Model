# Upload this project to a public GitHub repository

## 1. Extract and open the project

Unzip the downloaded project. Open Terminal on macOS/Linux or Git Bash on Windows, then move into the project folder:

```bash
cd path/to/credit-card-fraud-backend
```

For example, if it is in Downloads on a Mac:

```bash
cd ~/Downloads/credit-card-fraud-backend
```

## 2. Check that Git is installed

```bash
git --version
```

If the command is not found, install Git from <https://git-scm.com/downloads>.

## 3. Create an empty public repository on GitHub

1. Sign in at <https://github.com>.
2. Click the **+** button in the upper-right corner.
3. Select **New repository**.
4. Enter `credit-card-fraud-backend` as the repository name.
5. Optionally add: `Student-friendly fraud detection backend using Spring Boot, PostgreSQL, Python, scikit-learn, and Docker.`
6. Choose **Public**. Public means anyone can see the repository.
7. Do not select README, `.gitignore`, or license because they are already in the project.
8. Click **Create repository**.

## 4. Initialize the local Git repository

Run these commands from inside the project folder:

```bash
git init
git add .
git commit -m "Initial commit: credit card fraud detection backend"
git branch -M main
```

If Git asks for your identity, run the following with your own information, then repeat the commit:

```bash
git config --global user.name "Your Name"
git config --global user.email "your-email@example.com"
```

## 5. Connect the project to GitHub

Copy the repository URL shown by GitHub. It will look like:

```text
https://github.com/YOUR-USERNAME/credit-card-fraud-backend.git
```

Replace `YOUR-USERNAME` below with your GitHub username:

```bash
git remote add origin https://github.com/YOUR-USERNAME/credit-card-fraud-backend.git
git push -u origin main
```

GitHub may open a browser and ask you to sign in. GitHub does not accept an account password for command-line pushes. Use the browser sign-in flow or a personal access token if prompted.

## 6. Confirm that everyone can see it

1. Refresh your repository page on GitHub.
2. Open the repository in a private/incognito browser window while signed out.
3. If the files are visible, the repository is public.

If it is private, open **Settings**, scroll to **Danger Zone**, select **Change repository visibility**, choose **Make public**, and complete GitHub's confirmation.

## Upload future changes

After editing the project, run:

```bash
git status
git add .
git commit -m "Describe what changed"
git push
```

Never commit passwords, API keys, real card numbers, or customer data. `.env` is ignored for this reason.

