---
layout: page
title: Wern's Project Portfolio Page
---

### Project: Jobby

Jobby is a desktop application used for tracking job applications.

Given below are my contributions to the project.

* **New Feature**:

  * **Command Autocompletion**

    * This allows users to autocomplete their commands, just like command terminals or programming IDEs, such as by pressing **TAB**.

    * Like an IDE, it does a _subsequence_ match, so typing `-dsp` then **TAB** can select `--description`, allowing for fast disambiguation from another similar term like `--descending`.

    * As a bonus, it also supports undoing with **BACKSPACE**. It keeps a complete undo history, so you can do multiple undos for the same command as long if you did not move your text cursor from the end point.

    * **Rationale:**

      * When developing Jobby, we started having too many command parameters with clashing prefixes, so it no longer make sense to offer single letter parameter names.

      * Memorizing multi-letter abbreviations for a bunch of parameter is more difficult than a full name.

      * However, a full parameter name takes much longer to type, which means it's no longer optimized for fast typists.

      * Hence, autocompletion was implemented to resolve all of these concerns.

    * **Implementation:** The internal implementation is written completely from scratch. This allows it to:

      * specifically parse our custom command structure, and

      * dynamically determine what should or should not be suggested next based on a given set of rules, or available data in the app.

    * **UI:** The autocompletion UI is adapted from [@floralvikings's AutoCompleteTextBox.java](https://gist.github.com/floralvikings/10290131) with significant enhancements. These include:

      * overhauling the implementation in modern Java syntax and JavaFX method calls,

      * modified the implementation idea to support for our custom `Stream`-based autocompletion result suppliers,

      * integrating it directly within FXML,

      * improved the UI to support adding indicators for invoking the best (first-in-list) autocomplete result,

      * added support for auto-highlighting only the postfix term that is suggested for improved glancability,

      * added support for external parties to invoke autocompletion (e.g., via preferred key events or UI buttons), and

      * added support for undoing autocompleted results (and similarly for external parties to invoke undo).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=AY2324S1-CS2103T-W08-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=wxwern&tabRepo=AY2324S1-CS2103T-W08-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~other~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:

  * To be added...

* **Enhancements to existing features**:

  * Revamped the parameter syntax to use a prefix of `--param`.

    * This allows for improved autocompletion UX as compared to `param/`, since we can immediately determine if the user intends to type it based on the first character.

    * It is also much less likely to clash with an existing user input.

  * Swapped out random ID generation with an implementation to derive IDs from an input name.

    * This allows for improved UX when editing details that require an ID, combined with autocomplete integration.

    * Previously, it was implemented by another team member by generating random RFC 4122 UUIDs instead.

    * Now, random IDs are derived from the name, e.g., `google-sg-a8b3fe` from an input of `Google SG`.

* **Documentation**:

  * User Guide:

    * Added a simpler command syntax introduction.

    * Improved explanation of command symbols used in the guide.

    * Added usage guides for command autocompletion.

    * Styling the website for improved overall glancability and support for fully-automated print formatting, including:

      * Styling headers with improved spacing, typography and color for increased readability.

      * Tweaking the page-break rules for different elements, such as preventing page breaks on crucial boxes or enforcing page breaks immediately after certain headers.

      * Styling custom unified UG elements for readability, like the following:\
<span class="learning-outcome pill">:trophy: How to perform a task</span>\
<span class="information pill">:information_source: An info message pill</span>\
<span class="warning pill">:warning: A warning message pill</span>\
<span class="danger pill">:warning: A danger message pill</span>\
<span class="beginner pill">Beginner</span> <span class="intermediate pill">Intermediate</span> <span class="expert pill">Expert</span>\
<div class="applies-to pill"><span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span> <span class="jobby-data-class pill">Job Application</span></div>

  * Developer Guide:

    * Added a quick explanation of how "Parser classes" may obtain the properties of our custom `Flag`s (like `--name`) from a command via `ArgumentMultimap` and `ArgumentTokenizer`.

    * Added a high-level end-to-end explanation of Jobby's internal autocomplete implementation and interactions.

* **Community**:
  * To be added...

