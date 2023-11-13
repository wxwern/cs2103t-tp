---
layout: page
title: Bryan's Project Portfolio Page
---

<div class="reset-page-break-defaults" markdown="1">

### Project: Jobby

Jobby is a desktop app for managing job applications and contacts. It can help you manage the tracking of your job applications and contacts in a more streamlined fashion.

Given below are my contributions to the project.

* **New Feature**: Added the ability to create contacts for recruiters in the AddressBook.
    * What it does: Adds a way for the user to track recruiters' contacts.
    * Justification: Students would need to track the recruiters that recruited them for the internship application.

* **New Feature**: Modified certain fields in organization and recruiter contacts to be optional.
    * What it does: Users are given the option to leave certain fields blank when adding new organization and recruiter contacts.
    * Justification: Users might not have all the information and might not want to specify every detail when creating a new contact. Hence, this improves the user experience by making certain fields optional.

* **New Feature**: Added the ability to associate recruiters with organizations.
    * What it does: Users are able to link recruiters to other organizations added to the AddressBook.
    * Justification: Recruiters are associated with the organization they work for. Giving users the ability to store this relationship in the AddressBook improves the product's functionality in managing contacts.
    * Highlights: Given how AB3 was structured, implementing this association was a challenge. There was a struggle between loosely coupling or creating a stronger relationship between the two types of contacts in code. Going with the latter resulted in overhauls to the command's execution and storing of contacts in json. Proper care also had to be taken when details of the contacts changed as the association had to be updated to ensure that the contacts were linked to the latest versions of each other.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=AY2324S1-CS2103T-W08-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=McNaBry&tabRepo=AY2324S1-CS2103T-W08-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~other~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Forked the team repo and set up team organization.
  * Managed release [v1.2.1](https://github.com/AY2324S1-CS2103T-W08-3/tp/releases/tag/v1.2.1) on GitHub.
  * Contributed to refactoring `Person` to `Contact` class: [#27](https://github.com/AY2324S1-CS2103T-W08-3/tp/pull/27)
  * Renamed `person` package to `contact`: [#56](https://github.com/AY2324S1-CS2103T-W08-3/tp/pull/56)

* **Documentation**:
  * User Guide:
    * Added documentation for adding recruiter: [#165](https://github.com/AY2324S1-CS2103T-W08-3/tp/pull/165)
    * Added glossary and an appendix detailing acceptable features for the command parameters [#163](https://github.com/AY2324S1-CS2103T-W08-3/tp/pull/163)
  * Developer Guide:
    * Added documentation on the Organization-Recruiter link: [#173](https://github.com/AY2324S1-CS2103T-W08-3/tp/pull/173)
    * Added appendix on effort taken to evolve AB3 to Jobby: [#190](https://github.com/AY2324S1-CS2103T-W08-3/tp/pull/190)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [#7](https://github.com/AY2324S1-CS2103T-W08-3/tp/pull/7), [#164](https://github.com/AY2324S1-CS2103T-W08-3/tp/pull/164)
  * Reported [bugs and suggestions](https://github.com/McNaBry/ped/issues) for other teams.

</div>
