---
layout: page
title: Style Demo
---

**WARNING: TEXT HERE IS NOT A USER GUIDE. IT SERVES TO DEMONSTRATE STYLES.**

**Lorem ipsum** dolor _sit amet_, `consectetur` adipiscing ~~elit~~, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

**Table of Contents**

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Installation formatting example

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `jobby.jar` from [here](https://github.com/AY2324S1-CS2103T-W08-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Jobby Application.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar jobby.jar` command to run the application.<br><br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type a **command** in the command box and press Enter to execute it - e.g., typing **`help`** and pressing Enter will open the help window.<br>

1. Refer to the [Features](#) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Advanced formatting

<div class="h2-summary" markdown="1">
Welcome to fancy formatting.

### You will learn
{: .no_toc}
> - How to omit a header from kramdown (like this "You will learn" heading)
> - How to ensure the `h2` before this entire summary and the next `h3` header is not forced to auto break (using `h2-summary`)
> - How to use classes and style elements
> - And more...
</div>

### 3rd-level headings for subtopics

High level topics here

#### 4th-level headings for subtopics within subtopics

This should still be part of an important topic

##### Format
```
triple-backtick code block for format stuff
```
##### Examples

* Whitespace will be appropriately auto-added above headings for visual split
* More lines...

##### 5th-level headings for intra-subtopic organization

These are automatically omitted from the Table of Contents, and should be just there for organization.

Use them for better visual separation and styling consistency


### Another sub-subtopic

##### Format
```
...
```

### Class Overrides
{: .allow-page-break}

#### Page breaking

* `allow-page-break` resets the page breaking rules, like for **Class Overrides** heading here.

```md
### Class Overrides
{: .allow-page-break}
```

* `no-toc` removes a header from table of contents.

```md
### Header to hide
{: .no_toc}
```

* To ensure the h2-summary-h3 stays in one page during printing, wrap class `h2-summary` for the summary segment

```md
## h2 header

<div class="h2-summary" markdown="1">
Pretend this is a long summary with lots of elements

> ### You will learn
{: .no_toc}
> - something...
</div>

### h3 header
```

* `page-break` forces a page break:

```md
<div class="page-break"></div>
```
<div class="page-break"></div>



### Pill Demo
Use the pills appropriately.
```md
<span class="learning-outcome pill">:trophy: How to perform a task</span>
```
<span class="learning-outcome pill">:trophy: How to perform a task</span>

```md
<span class="beginner pill">Beginner</span> <span class="intermediate pill">Intermediate</span> <span class="expert pill">Expert</span>
```
<span class="beginner pill">Beginner</span> <span class="intermediate pill">Intermediate</span> <span class="expert pill">Expert</span>

```md
<span class="information pill">:information_source: An info pill</span>
```
<span class="information pill">:information_source: An info pill</span>

```md
<span class="warning pill">:warning: A warning pill</span>
```
<span class="warning pill">:warning: A warning pill</span>

```md
<span class="danger pill">:warning: A danger pill</span>
```
<span class="danger pill">:warning: A danger pill</span>

```md
<div class="applies-to pill"><span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span> <span class="jobby-data-class pill">Job Application</span></div>
```
<div class="applies-to pill"><span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span> <span class="jobby-data-class pill">Job Application</span></div>


### Jobby class labelling

Just use
```md
<span class="jobby-data-class">Jobby class</span>
```

#### Adding contacts
Jobby can create contacts in the form of <span class="jobby-data-class">Organization</span> and <span class="jobby-data-class">Recruiter</span>, which you can add into your database with simple CLI commands.

For example, to add an <span class="jobby-data-class">Organization</span> **"Woogle"** with the ID **"woogle_id"** into Jobby, simply do so by typing a simple command:

```
add --org --name Woogle --id woogle_id
```

![Add Organization](images/starter-guide/add-woogle.jpg)


### Use the `sh` formatter for syntax highlighting
```sh
add --rec --name NAME [--id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...
```

### Popup boxes

<span class="learning-outcome pill">:trophy: How to use Jobby's command autocompletion</span> <span class="beginner pill">Beginner</span>

Command autocompletion allows you to type commands in Jobby at unimaginable speeds.

As you type your command, you may see a list of suggested completions pop up.
Just press **TAB** or **SPACE** to select the first suggestion to fill in that text!

![Autocomplete Screenshot](images/autocomplete.png)

```md
<div markdown="block" class="alert alert-info">
...
</div>
```
<div markdown="block" class="alert alert-info">

**:bulb: Additional tips:** <br>

* Any extra tips.

* <span class="expert pill">Expert</span> Inline pills.

</div>

```md
<div markdown="block" class="alert alert-warning">
...
</div>
```
<div markdown="block" class="alert alert-warning">

**:warning: Limitations:**<br>

* Autocomplete is not autocorrect.

* Autocomplete does not verify that the command will run.

</div>

-------------------------------------------------------------------------------------------------

## Feature Display Demo


### Adding contacts - `add`

<div class="applies-to pill"><span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span></div>


#### Adding organizations - `add --org`

<span class="learning-outcome pill">:trophy: How to add organization contacts into Jobby</span> <span class="beginner pill">Beginner</span>


##### Format
...


#### Adding recruiters - `add --rec`

<span class="learning-outcome pill">:trophy: How to add recruiter contacts into Jobby</span> <span class="beginner pill">Beginner</span>


##### Format
```sh
add --rec --name NAME [--id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...
```

##### Valid use examples
* `add --rec --name John Doe` _Adds a recruiter that is not linked to any organization._
* `add --rec --name John Doe --tag friendly --tag woogle` _Adds a recruiter with two tags - friendly and woogle._
* `add --rec --name John Doe --oid paypal-sg` _Adds a recruiter that is linked to an organization (if it exists in the address book) with the id "paypal-sg"_

##### Invalid use examples
* `add --rec` _Missing a name._
* `add --rec --name John Doe --phone` _Optional fields (in this case `--phone`) were used but not specified_
* `add --rec --name John Doe --oid bogus-org` _Given that no organization with the id "bogus-org" exists in the address book._

##### Sample demonstration
* If you execute the command: `add --rec --name Ryan Koh --oid job_seeker_plus`, you should see a new contact being added to the list of contacts on the left panel.

* Since the `--oid` flag was provided, the newly added recruiter contact will have a special label _from organization (job\_seeker\_plus)_ to indicate that the recruiter is linked to the organization with that particular id.

![add-recruiter](images/add_recruiter_success.png)

### List contacts - `list`
<div class="applies-to pill"><span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span> <span class="jobby-data-class pill">Job Application</span></div>
<span class="learning-outcome pill">:trophy: How to choose contacts to list in Jobby</span>

##### Format
```sh
list [--org/--rec/--toapply]
```

Shows a list of all contacts in the address book.

Supplying `--org` lists only Organizations while supplying `--rec` lists only Recruiters.

Supplying `--toapply` lists Organizations you have not applied to.

##### Examples
* `list`
* `list --org`
* `list --rec`
* `list --toapply`

[SCREENSHOT HERE]

### Sorting - `sort`
<div class="applies-to pill"><span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span> <span class="jobby-data-class pill">Job Application</span></div>

<span class="learning-outcome pill">:trophy: How to sort contacts and job applications in Jobby</span> <span class="intermediate pill">Intermediate</span> <br>
<span class="information pill">:information_source: You should have contacts or job applications present to make full use of sorting functions.</span>


##### Format
```sh
sort --FLAG_TO_SORT [--ascending/--descending]
```

##### Supported flags <sub>(only 1 must be provided)</sub>

**Flags for contacts:**
* `--address` - The address of the <span class="jobby-data-class">Contact</span>. Will sort alphabetically.
* `--email` - The email address of the <span class="jobby-data-class">Contact</span>. Will sort alphabetically.
* `--id` - The identification string of the <span class="jobby-data-class">Contact</span>. Will sort alphabetically.
* `--name` - The name of the <span class="jobby-data-class">Contact</span>. Will sort alphabetically.
* `--phone` - The phone number of the <span class="jobby-data-class">Contact</span>. Will sort alphabetically.
* `--url` - The web address of the <span class="jobby-data-class">Contact</span>. Will sort alphabetically.

**Flags for job applications:**
* `--by` - The deadline of the <span class="jobby-data-class">Job Application</span>. Will sort chronologically.
* `--stage` - The stage of the <span class="jobby-data-class">Job Application</span>. Will sort by stage order.
* `--stale` - The time of last update of the <span class="jobby-data-class">Job Application</span>. Will sort chronologically.
* `--status` - The status of the <span class="jobby-data-class">Job Application</span>. Will sort by status order.
* `--title` - The title of the <span class="jobby-data-class">Job Application</span>. Will sort alphabetically.

**Flags for resetting the sort order:**
* `--none` - Will reset the sorting order of <span class="jobby-data-class">Contacts</span> and <span class="jobby-data-class">Job Applications</span>.

**Flags for specifying the sort order:**
* `--ascending` - The specified flag will sort in ascending order.
* `--descending` - The specified flag will sort in descending order.

If neither `--ascending` or `--descending` are provided, the list will be sorted in ascending order by default.

Neither `--ascending` nor `--descending` may be specified if the flag is `--none`.

`sort` will work even if no <span class="jobby-data-class">Contacts</span> or <span class="jobby-data-class">Job Applications</span> exist.

[SCREENSHOT HERE]

##### Valid use examples
* `sort --title --ascending`
* `sort --url`
* `sort --stale --descending`
* `sort --none`

##### Invalid use examples
* `sort` _No flag provided._
* `sort --organization` _Invalid flag._
* `sort --none --ascending` _Flags `--none` and `--ascending` both specified._


### Deleting - `delete`

<div class="applies-to pill"><span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span> <span class="jobby-data-class pill">Job Application</span></div>

#### Deleting contacts - `delete`

<span class="learning-outcome pill">:trophy: How to delete organizations and recruiters in Jobby</span> <span class="intermediate pill">Intermediate</span> <br>
<span class="danger pill">:warning: Deletion is permanent and there is no way to undo it.</span>

##### Format
```sh
delete INDEX/ID [--recursive]
```

If the contact to delete is an organization, it will delete the job applications associated with it.

If `--recursive` is specified, deletes the associated recruiter contacts if the contact to delete is an organization.

##### Valid use examples

* `delete 1` _Given that there is at least 1 contact in the list._
* `delete 1 --recursive` _Given that there is at least 1 contact in the list._

##### Invalid use examples
* `delete 0` _Invalid index._

#### Deleting job applications - `delete --application`

<span class="learning-outcome pill">:trophy: How to delete job applications in Jobby</span> <span class="intermediate pill">Intermediate</span> <br>
<span class="danger pill">:warning: Deletion is permanent and there is no way to undo it.</span>

##### Format
```sh
delete --application INDEX
```

##### Valid use examples
* `delete --application 1` _Given that there is at least one job application in the list._

##### Invalid use examples
* `delete --application 0` _Invalid index._


--------------------------------------------------------------------------------------------------------------------

## Tables

### Example Table 1

Term | Definition
-----|-----------
**Top Level Domain** | A Top Level Domain (TLD) is the part of the website address where it comes after the last dot (i.e. ".com", ".org", ".net") and before the first slash. (E.g. www.example.**com**/path)
**Whitespace** | In the context of this application, a whitespace is any number of spaces or tabs that is in the input.

### Example Table 2

Parameter | Requirements | Examples
----------|--------------|---------
`INDEX` | A valid index can accept any positive integer up to the number of items in the contact or job application list where applicable. | `1`<br>`10`
`NAME` | A valid name can accept any non-empty value. | `Ryan Koh`<br>`小明`
`ID` | A valid ID has to start with a letter.<br><br>It can consist of alphanumeric and basic symbols (i.e. `a-z`, `A-Z`, `0-9`, `-`, `_`) | `woogle123`<br>`ryan_soc-rec`
`NUMBER` | A valid phone number can consist of only numbers with no whitespace.<br><br>It must be at least 3 digits. | `999`<br>`91824137`
`EMAIL` | A valid email should be in the form of `local-part@domain` where the `local-part` and `domain` must be separated by a single **@**.<br><br>The `local-part` can consist of any character except whitespace.<br><br>The `domain` name can comprise of one or more labels separated by periods, and each label can include any character except whitespace. The last `domain` label must be a minimum of two characters long. | `ryankoh@nus`<br>`ryan-koh@nus.edu.sg`
`URL` | A valid url should include a part in the form of `domain.tld` where the `domain` and the `tld` (top level domain) must be separated by a period. | `example.com`<br>`example.more.com`<br>`https://example.com`<br>`example.com/more`
`ADDRESS` | A valid address can accept any non-empty value.<br><br>For a contact, it designates its physical address. | `21 Lower Kent Ridge Rd` 
`TAG` | A valid tag can consist of only alphanumeric characters. | `internship`<br>`network`<br>`parttime`<br>`jobPortal` 
`ORG_ID` | A valid organization ID is subject to the same requirements as the ID parameter.<br><br>It must belong to an <span class="jobby-data-class">Organization</span> contact in the address book. | `woogle123`<br>`meta_sg-1`
`TITLE` | A valid title can accept multiple words separated with spaces, as long as the characters are alphanumeric. | `Software Engineer`<br>`Level 3 Engineer`
`DESCRIPTION` | A valid description can accept any non-empty value. | `Senior Role`<br>`Hourly rate: $25`
`DEADLINE` | A valid deadline should be a date in the form of `DD-MM-YYYY`.<br><br>The day (`DD`) and month (`MM`) can be either single or double digits. | `09-02-2022`<br>`9-2-2022`<br>`19-11-2022`
`STAGE` | A valid job application stage can accept only one of the three values: `resume`, `online assessment`, `interview`.<br><br>The values are ranked in the order shown. | `resume`<br>`online assessment`<br>`interview`
`STATUS` | A valid job application status can accept only one of the four values: `pending`, `offered`, `accepted`, `turned down`.<br><br>The values are ranked in the order shown. | `pending`<br>`offered`<br>`accepted`<br>`turned down`
`KEYWORD` | A valid keyword is a single word that can accept any non-empty value. | `software`<br>`Ryan`

--------------------------------------------------------------------------------------------------------------------

## Issues

(Where to report issues and what bugs currently exist)

