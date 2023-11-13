---
layout: page
title: Wern's Project Portfolio Page
---

<div class="reset-page-break-defaults" markdown="1">

### Project: Jobby

Jobby is a desktop application used for managing your job applications and associated organization and recruiter contacts.

Given below are my contributions to the project.

* **New Feature**: Command Autocompletion

    * This allows users to autocomplete their commands, just like command terminals or programming IDEs, such as by pressing **TAB**, and even undo when you **BACKSPACE**.

    * Like an IDE, it does a _subsequence_ match, so typing `-dsp` then **TAB** can select `--description`, allowing for fast disambiguation from another similar term like `--descending`.

    * **Rationale:** It allows for faster command input, and reduces command syntax memorization.

    * **Implementation:** The internal implementation is written completely from scratch to support our custom formats - more information available in the Developer Guide.

    * **UI:** The autocompletion UI is adapted from [@floralvikings's AutoCompleteTextBox.java](https://gist.github.com/floralvikings/10290131). The reference text box only has a barebones context menu, but significant enhancements has been made to the styling and behavior to improve readability and UX (like partial term highlighting and undo support), which can be seen when using Jobby.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=AY2324S1-CS2103T-W08-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=wxwern&tabRepo=AY2324S1-CS2103T-W08-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~other~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:

  * Configuring the Issue Tracker Tags and Milestones for the project.
  * Setting up GitHub Actions for the project, including Java CI, CodeCov and GitHub Pages.
  * Styling the website for optimal readability and print formatting, including:

      * Styling headers with improved spacing, typography and color for increased readability.

      * Tweaking the page-break rules for different elements, such as preventing page breaks on crucial boxes or enforcing page breaks immediately after certain headers.

      * Styling custom unified UG elements, like the following:
        * <span class="learning-outcome pill">:trophy: How to perform a task</span> <span class="information pill">:information_source: An info pill</span> <span class="warning pill">:warning: A warning pill</span> <span class="danger pill">:warning: A danger pill</span>
        * <span class="applies-to pill"><span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span> <span class="jobby-data-class pill">Job Application</span></span>
        * <span class="beginner pill">Beginner</span> <span class="intermediate pill">Intermediate</span> <span class="expert pill">Expert</span><br><br>

* **Enhancements to existing features**:

  * Revamped the parameter syntax to use a prefix of `--param`.

    * This allows for improved autocompletion UX as compared to `param/`, since we can immediately determine if the user intends to type a parameter based on the first character.

    * It is also much less likely to clash with an existing user input.

  * Swapped out random ID generation with an implementation to derive IDs from an input name.

    * This allows for improved UX when editing details that require an ID, combined with autocomplete integration.

    * e.g., `google-sg-a8b3fe` can be derived from an input of `Google SG`.

* **Documentation**:

  * User Guide:

    * Added a structured command syntax introduction, and instructions to interpret command formats in the UG and app.

    * Added usage guides for command autocompletion.

    * Styling the website for improved overall readability and automated print formatting (see above in Project Management).

  * Developer Guide:

    * Integrated explanations of how "Autocomplete classes" work in the context of the `Logic` package.

    * Updated how `AppParser` (formerly `AddressBookParser`) operates in the context of our app, since we now dynamically look up details and also support autocompletion.

    * Added a complete high-level explanation of Jobby's internal autocomplete implementation and interactions.

    * Added use cases for autocompletion.

* **Community**:
  * Detailed PR Reviews (e.g., )
  * Forum contributions (e.g., )
  * Reported bugs and suggestions for other teams (e.g., during PE-D)

</div>
