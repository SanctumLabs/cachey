# Git Hooks

These hooks are installed when `./gradlew check` is run. This will set up a [pre-push](./pre-push.sh) hook which will
run tests before pushing the code to a VCS, a [pre-commit](./pre-commit.sh) hook which will run linting(static analysis)
of the code before committing source code changes(This will only run for Kotlin files) and
a [prepare-commit-msg](./prepare-commit-msg.sh)
hook which will setup commit message template.

These can be skipped with
the [`--no-verify`](https://git-scm.com/docs/git-commit#Documentation/git-commit.txt---no-verify) flag from git command
line tools.