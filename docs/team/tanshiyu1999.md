<div class="reset-page-break-defaults" markdown="1">

### Project: Jobby

Jobby is a desktop address book and job application tracking tool. The user interacts with it using a CLI, and it has a GUI created in JavaFX. It is written in Java.

Given below are my contributions to the project.

* **New Feature**: Add Organization
    * What it does: This adds an organization contact into Jobby
    * Justification: We want our Jobby application to be able to track both Organization Contact and Recruiter Contact with different parameters, hence we added different functions in order to add these 2 different type of contacts.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=AY2324S1-CS2103T-W08-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=tanshiyu1999&tabRepo=AY2324S1-CS2103T-W08-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~other~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Forked the team repo.

* **Enhancements to existing features**: Modified find command
  * What it does: Find now is able to find substring instead of the entire keyword as specified by AB3
  * Justification: The initial find function can only look for the keyword if it is we input the entire keyword. Which is unrealistic as users might not be able to remember the entire keyword. Hence, being able to look up a substring is useful.

* **Enhancements to existing features**: Modified edit command
  * What it does: Edit target can be selected via both index and the substring for name.
  * Justification: Initially, edit only works via selection by index. However, we want it to be flexible enough that we can select the target by its name, hence this is being implemented.

* **Documentation**:
    * User Guide:
        * Added documentation for Add Organization in the User Guide.
        * Created the starter guide for the documentation.
        * Help to create GUI Breakdown, and edit the UG for find, edit, apply and delete.
    * Developer Guide:
        * Documented the implementation of Add Organization into the Developer Guide.
        * Added more user stories.

* **Community**:
    * Reported bugs and suggestions for other teams in the class (examples: During PE-D)
    * Reviewed PR from Teammates

</div>
