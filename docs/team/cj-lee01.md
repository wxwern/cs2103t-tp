---
layout: page
title: Bryan's Project Portfolio Page
---

<div class="reset-page-break-defaults" markdown="1">

### Project: Jobby

Jobby is a desktop address book and job application tracking tool. The user interacts with it using a CLI, and it has a GUI created in JavaFX. It is written in Java.

Given below are my contributions to the project.

* **New Feature**: Apply command
    * What it does: Allows users to add job applications.
    * Justification: For job application tracking, since one person can apply to multiple positions within a company, a separate class is required for job applications.
    * Highlights: Created necessary classes for storing information related to job applications.

* **New Feature**: UI for showing job applications
    * What it does: Allows users to view their job applications.
    * Justification: To let them see what job applications they have added.

* **New Feature**: Storage of job applications
    * What it does: Stores information on job applications in JSON file.
    * Justification: For users to save their job applications.
    * Highlights: Decided to store the job applications instead the organization JSON to ensure that the job application is truly associated to it.

* **New Feature**: Editing of job applications
    * What it does: Allows users to edit job applications.
    * Justification: In case their job application status changes or they made a mistake while adding the job application.

* **New Feature**: Deleting of job applications
    * What it does: Allows users to delete job applications.
    * Justification: Allows users to delete old job applications.

    
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=AY2324S1-CS2103T-W08-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=CJ-Lee01&tabRepo=AY2324S1-CS2103T-W08-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~other~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:

* **Enhancements to existing features**: Delete recursive
  * Allows users to delete recruiters linked to an organization when deleting the organization.
  * This is due to the parent-child relationship between recruiters and organization.


* **Documentation**:
    * User Guide:
        * Apply, Edit applications and delete command.
        * Enhancements to Introduction section and Navigating the Guide section.
    * Developer Guide:
        * Implementation details for apply command, added planned enhancements
        * Add more user stories
        * Add more use cases
        * Add some non-functional requirements
        * Updating manual testing guide

* **Community**:
    * Contributed to forum discussions (examples: #371)
    * Reviewed PRs: #39, #42, #60, #67, #99, #147, #150,  

</div>
