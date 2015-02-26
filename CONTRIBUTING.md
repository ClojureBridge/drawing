# How to Contribute

This drawing app is a part of the ClojureBridge
[curriculum](https://github.com/ClojureBridge/curriculum). Updates to
the curriculum are welcome and encouraged! We would not have anything
without the input from the volunteers organizing workshops.

## Getting Started

The curriculum team uses GitHub to review, discuss, and ultimately
accept changes. If you don't have a GitHub account, you'll have to
[sign up](https://github.com/signup/free). If you're new to
[git](http://en.wikipedia.org/wiki/Git_(software)) or collaborating on
shared Git repositories, checkout the
[references](#completely-new-to-git-and-github).

* [**Fork**](https://github.com/ClojureBridge/drawing/fork) this
  repository to your GitHub account.

* **Clone** your fork to your local computer:

  ```shell
  # Run from a terminal environment.
  git clone https://github.com/<your GitHub username>/drawing.git
  cd drawing/
  ```

## Making Changes

With the local fork in hand, you're ready to make some changes.

* First, create a local topic **branch** for your change:

   ```shell
   git checkout -b my-new-feature-branch
   ```

* **Commit** changes to your new branch:

    ```shell
    # Create, edit, remove files, etc.
    git add .
    git commit -m "An informative description of my change."
    ```

## Submitting Changes

After finishing work on your branch, you can submit a
[pull request](https://help.github.com/articles/using-pull-requests/)
for review:

* **Push** your committed changes from your local fork of the
  repository. This sets up a remote branch tracking your local branch:

    ```shell
    git push origin -u my-new-feature-branch
    ```

* Once you're done making your change, you're ready to submit a **pull
  request** (PR) to the
  [ClojureBridge/drawing](https://github.com/ClojureBridge/drawing)
  repository. Pull requests are submitted from your own fork, but will
  appear on the upstream repository. After pushing changes to your
  fork, you fork's GitHub page will automatically prompt you to submit
  a pull request upstream.

* The ClojureBridge curriculum team will **review** and discuss the
  pull request in comments on the PR. Two curriculum team members must
  give a thumbs up, then the PR will be accepted. All pull requests
  will receive a response, but may need to be updated with new commits
  once you've received feedback from the maintainer.

* After your PR is accepted and merged into `master`, you'll have to
  update your own fork. First, set the **upstream** remote repository:

   ```shell
   git remote add upstream git://github.com/ClojureBridge/drawing.git
   ```

  and then update your local copy:

   ```shell
   git checkout master
   git fetch upstream master
   git push origin master
   ```

## Curriculum Team

* Wait, why does the curriculum team get to say which PRs get
  accepted? I'm glad you asked! If you contribute more than two
  patches, you, too, will become part of the curriculum team.
* Curriculum team members are given commit rights to the curriculum.
* Commit rights are meant for approving PRs, not for making direct
  commits.
* There is also a
  [ClojureBridge curriculum group](https://groups.google.com/forum/#!forum/clojurebridge-curriculum)
  for discussing curriculum direction.

## Workshop/Chapter Curriculum Forks

* Workshops or chapters that are using the main ClojureBridge
  curriculum should fork the curriculum to their chapter's GitHub (e.g.,
  [https://github.com/clojurebridge-sf/curriculum](https://github.com/clojurebridge-sf/curriculum))
* Give teachers commit rights to the chapter's fork of the curriculum.
* The submit pull requests to the main curriculum, if you would like
  to contribute the changes back.

## Completely New to Git and GitHub

If this is your first time using Git and GitHub for source code
version control and collaboration, there are some great tutorials on
the web. For Git fundamentals,
[Git Immersion](http://gitimmersion.com/) walks through a lot of basic
usage. [GitHub guides](https://guides.github.com/) also explains
common GitHub collaboration workflows, such as
[forking](https://guides.github.com/activities/forking/). The workflow
described above is sometimes known as the
["GitHub flow"](https://guides.github.com/introduction/flow/). If you
have any questions, don't hesitate to ask questions to the curriculum
[mailing list](https://groups.google.com/forum/#!forum/clojurebridge-curriculum).
