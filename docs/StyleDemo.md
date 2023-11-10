---
layout: page
title: Style Demo
---

**WARNING: TEXT HERE IS NOT A USER GUIDE. IT SERVES TO DEMONSTRATE STYLES.**

**Lorem ipsum** dolor _sit amet_, `consectetur` adipiscing ~~elit~~, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Installation formatting example

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `jobby.jar` from [here](https://github.com/AY2324S1-CS2103T-W08-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Jobby Application.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar jobby.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type a **command** in the command box and press Enter to execute it - e.g., typing **`help`** and pressing Enter will open the help window.<br>

1. Refer to the [Features](#) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Advanced formatting

<div class="h2-summary" markdown="1">
Welcome to Jobby's documentation!

> ### You will learn
> - How to add contact into Jobby
> - How to edit contact in Jobby
> - How to add application in Jobby
> - How to use core features like find, remove
> - How to delete data in Jobby
> - And more...
</div>

### GUI Legends

Below is a labelled diagram of our GUI

![Label](images/starter-guide/initial-ui.jpg)


### Pill Demo

<span class="learning-outcome pill">:trophy: How to perform a task</span>

<span class="beginner pill">Beginner</span> <span class="intermediate pill">Intermediate</span> <span class="expert pill">Expert</span>

<span class="information pill">:information_source: An info pill</span>

<span class="warning pill">:warning: A warning pill</span>

<span class="danger pill">:warning: A danger pill</span>

### Jobby class labelling

#### Adding contacts
Jobby can create contacts in the form of <span class="jobby-data-class">Organization</span> and <span class="jobby-data-class">Recruiter</span>, which you can add into your database with simple CLI commands.

For example, to add an <span class="jobby-data-class">Organization</span> **"Woogle"** with the ID **"woogle_id"** into Jobby, simply do so by typing a simple command:

```
add --org --name Woogle --id woogle_id
```

![Add Organization](images/starter-guide/add-woogle.jpg)


### Popup boxes

<span class="learning-outcome pill">:trophy: How to use Jobby's command autocompletion</span> <span class="beginner pill">Beginner</span>

Command autocompletion allows you to type commands in Jobby at unimaginable speeds.

As you type your command, you may see a list of suggested completions pop up.
Just press **TAB** or **SPACE** to select the first suggestion to fill in that text!

![Autocomplete Screenshot](images/autocomplete.png)

<div markdown="block" class="alert alert-info">

**:bulb: Additional tips:** <br>

* Any extra tips.

* <span class="expert pill">Expert</span> Inline pills.

</div>

<div markdown="block" class="alert alert-warning">

**:warning: Limitations:**<br>

* Autocomplete is not autocorrect.

* Autocomplete does not verify that the command will run.

</div>

-------------------------------------------------------------------------------------------------

## Feature Display Demo


### Adding contacts - `add`

<div class="applies-to pill">**Applies to:** <span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span></div>

<span class="learning-outcome pill">:trophy: How to add contacts into Jobby</span> <span class="beginner pill">Beginner</span>

##### Format
```
add --org/--rec <additional parameters and values...>
```

Adds a contact to the address book of the given class type: <span class="jobby-data-class">Organization</span> or <span class="jobby-data-class">Recruiter</span>.

Supplying `--org` adds an <span class="jobby-data-class">Organization</span> while supplying `--rec` adds a <span class="jobby-data-class">Recruiter</span> to the address book.

Details on adding an [organization]() contact and a [recruiter](#add-recruiter-command---add---rec) contact are specified in the next sections

#### Adding recruiters - `add --rec`

<div class="applies-to pill">**Applies to:** <span class="jobby-data-class pill">Recruiter</span></div>

<span class="learning-outcome pill">:trophy: How to add recruiter contacts into Jobby</span> <span class="beginner pill">Beginner</span>


##### Format
```
add --rec --name NAME [-id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...
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
<span class="learning-outcome pill">:trophy: How to choose contacts to list in Jobby</span>

##### Format
```
list [--org/--rec]
```

Shows a list of all contacts in the address book.

Supplying `--org` lists only Organizations while supplying `--rec` lists only Recruiters.

##### Examples
* `list`
* `list --org`
* `list --rec`

[SCREENSHOT HERE]

### Sorting - `sort`
<div class="applies-to pill">**Applies to:** <span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span> <span class="jobby-data-class pill">Job Application</span></div>

<span class="learning-outcome pill">:trophy: How to sort contacts and job applications in Jobby</span> <span class="intermediate pill">Intermediate</span>

<span class="information pill">:information_source: You need to have contacts or job applications to make use of sorting.</span>


##### Format
```
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

<div class="applies-to pill">**Applies to:** <span class="jobby-data-class pill">Organization</span> <span class="jobby-data-class pill">Recruiter</span> <span class="jobby-data-class pill">Job Application</span></div>

#### Deleting contacts - `delete`

<span class="learning-outcome pill">:trophy: How to delete organizations and recruiters in Jobby</span> <span class="intermediate pill">Intermediate</span>

<span class="danger pill">:warning: Deletion is permanent and there is no way to undo it.</span>

##### Format
```
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

<span class="learning-outcome pill">:trophy: How to delete job applications in Jobby</span> <span class="intermediate pill">Intermediate</span>

<span class="danger pill">:warning: Deletion is permanent and there is no way to undo it.</span>

##### Format
```
delete --application INDEX
```

##### Valid use examples
* `delete --application 1` _Given that there is at least one job application in the list._

##### Invalid use examples
* `delete --application 0` _Invalid index._


--------------------------------------------------------------------------------------------------------------------

## Glossary

(Terms that may be difficult to understand here.)

--------------------------------------------------------------------------------------------------------------------

## Issues

(Where to report issues and what bugs currently exist)

