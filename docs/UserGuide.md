---
layout: page
title: User Guide
---

INSERT INTRO TO JOBBY HERE
**Jobby** is a **desktop app for managing job applications and contacts, optimized for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). Jobby can help you manage tracking your job applications and contacts in a more streamlined fashion. If you can type fast, Jobby can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Installation

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `jobby.jar` from [here](https://github.com/AY2324S1-CS2103T-W08-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Jobby Application.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar jobby.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type a command in the command box and press Enter to execute it - e.g., typing **`help`** and pressing Enter will open the help window.<br>

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. ... (Go through the basic features like a tutorial)

--------------------------------------------------------------------------------------------------------------------

## Using Jobby

This section explains the details of how we can interact with Jobby.

If you're looking for the list of available commands, check out the [Features](#features) section instead.

### Command Structure

<span class="learning-outcome pill">:trophy: How to understand and write Jobby commands</span> <span class="beginner pill">Beginner</span>

In Jobby, we write commands in the command box at the top of Jobby's window.

Commands are made up of a few parts: The **command**, **parameter names** and **input values**.

A command like "`edit google --name Google SG --id google-sg`" would refer to:
* the **command** `edit`,
* with a **command value** `google`,
* with a **parameter** `--name`,
  * which has the **parameter value** `Google SG`,
* with a **parameter** `--id`,
  * which has the **parameter value** `google-sg`.

Parameters may be in any order, whose names are of the form `-a` or `--abc123`, and must be surrounded by whitespace.

Any extra parameters and values to commands that don't accept them will either be ignored or throw an error.

<div markdown="block" class="alert alert-info">

**:bulb: Additional information:**<br>

* Parameter names are restricted to the `-`/`--` prefix, contain only letters and numbers, and must begin with a letter.

* Any parameter names not following the required format will be treated as data input, so an input like *"-5 degrees"* will work.

* Although Jobby's syntax resembles the usual Unix syntax, you should not quote your text, and you should not leave a trailing `=`.

</div>

### Command Explanations

<span class="learning-outcome pill">:trophy: How to interpret this guide's command explanations</span> <span class="beginner pill">Beginner</span>

Throughout this guide, you will find symbols and placeholders used to describe a command format. They are:

* **Words in `UPPER_CASE`**

  * The parts where you should be typing your parameter values.

  * e.g., `--name NAME` means inputting names along the lines of `--name Alice`.

* **Terms separated by `/` or `|`**

  * Exactly one of the given options.

  * These may be included in the parameter names or value description.

  * e.g., `--a/--b` means either `--a` or `--b` but not `--a --b`.

* **Terms surrounded by `[` and `]`**

  * An optional parameter or option that may be omitted.

  * e.g., `[--id ID]` means you may omit setting an ID for the command.

* **Terms ending with `...`**

  * The parameter is multivalued.

  * e.g., `[--tag TAG]...` means `--tag` and its value can be repeated from 0 to any number of times.



### Command Autocomplete

<span class="learning-outcome pill">:trophy: How to use Jobby's command autocompletion</span> <span class="beginner pill">Beginner</span>

Command autocompletion allows you to type commands in Jobby at unimaginable speeds.

As you type your command, you may see a list of suggested completions pop up.
Just press **TAB** or **SPACE** to select the first suggestion to fill in that text!

![Autocomplete Screenshot](images/autocomplete.png)

To temporarily hide all suggestions, press **ESC**. This temporarily disables autocompletion for the next keystroke.

If suggestions were hidden or aren't shown when they should, press **TAB** to prompt Jobby to bring it back.

<div markdown="block" class="alert alert-info">

**:bulb: Additional tips:** <br>

* If you rather choose from the list instead of typing out the prefix, it is possible to use the **UP** and **DOWN**
  arrow keys to navigate through the menu, then press **ENTER** to select them.

* Accidentally triggered autocomplete when you didn't intend to? Don't worry, just press **BACKSPACE** to immediately
  revert to your previously typed text.

* <span class="expert pill">Expert</span> Autocomplete checks for fuzzy matches - it sorts by the best *subsequence* prefix match first.

  * For example, you can type `-nm` to get the autocompletion result of `--name`.

  * This allows you to quickly choose between parameter names with similar prefixes, e.g., by typing
    `-dsp` to select `--description` instead of `--descending`.

</div>

<div markdown="block" class="alert alert-warning">

**:warning: Limitations:**<br>

* Autocomplete is not autocorrect. It will not attempt to correct mistyped details.

* Autocomplete suggests plausible values you may want to add onto your partially typed command. It does not verify that the command will run.

</div>

-------------------------------------------------------------------------------------------------

## Features

(Detailed information on each command with the constraints, possible errors and feature flaws)

### Add command - `add`

Applies to: <span class="jobby-data-class">Organization</span> <span class="jobby-data-class">Recruiter</span>

<span class="learning-outcome pill">:trophy: How to add contacts into Jobby</span> <span class="beginner pill">Beginner</span>

Format: `add --org/--rec <additional parameters and values...>`

Adds a contact to the address book of the given class type: <span class="jobby-data-class">Organization</span> or <span class="jobby-data-class">Recruiter</span>.

Supplying `--org` adds an <span class="jobby-data-class">Organization</span> while supplying `--rec` adds a <span class="jobby-data-class">Recruiter</span> to the address book.

Details on adding an [organization]() contact and a [recruiter](#add-recruiter-command---add---rec) contact are specified in the next sections

#### Add recruiter command - `add --rec`

Applies to: <span class="jobby-data-class">Recruiter</span>

<span class="learning-outcome pill">:trophy: How to add recruiter contacts into Jobby</span> <span class="beginner pill">Beginner</span>

<span class="information pill">:information_source: Assumes that you have completed the tutorial</span> <br>

Format: `add --rec --name NAME [-id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...`

Required Fields:
* `NAME` - The name of the <span class="jobby-data-class">Recruiter</span>. A valid name can be of any value, but must not be blank.

Optional Fields:
* `ID` - The unique identifier of the <span class="jobby-data-class">Recruiter</span>. A valid `ID` must start with a letter and can consist of alphanumeric and basic symbols, i.e. should only be `a-z`, `A-Z`, `0-9`, `-`, `_`.
    * Specifying this sets the `ID`, or a unique one will be derived and generated from the `NAME` if not provided. <br>

* `ORG_ID` - The unique identifier of the <span class="jobby-data-class">Organization</span> linked to this <span class="jobby-data-class">Recruiter</span>. It is subjected to the same validation as the `ID` field. The value provided must be the `ID` of an existing <span class="jobby-data-class">Organization</span> in the address book.

* `NUMBER` - The phone number of the <span class="jobby-data-class">Recruiter</span>. A valid phone number contains only numbers and must be at least 3 digits long. E.g. 999 or 87263614

* `EMAIL` - The email of the <span class="jobby-data-class">Recruiter</span>. A valid email consists of a _local-part_ and _domain_ and should be in the form of *local-part@domain*. E.g. johndoe@example.com

* `URL` - The url of the <span class="jobby-data-class">Recruiter</span>. A valid url should be a path that contains _domain.tld_. E.g. example.com, subdomain.example.com/path, https://example.com

* `ADDRESS` - The address of the <span class="jobby-data-class">Recruiter</span>. A valid address can be any non-empty value. It designates the <text class="job-application">Recruiter's</text> physical address.

* `TAG` - The tag(s) of the <span class="jobby-data-class">Recruiter</span>. A valid tag consists of only alphanumeric characters. Multiple tags can also be specified.

Examples of valid use of `add --rec` command:
* `add --rec --name John Doe` _Adds a recruiter that is not linked to any organization._
* `add --rec --name John Doe --tag friendly --tag woogle` _Adds a recruiter with two tags - friendly and woogle._
* `add --rec --name John Doe --oid paypal-sg` _Adds a recruiter that is linked to an organization (if it exists in the address book) with the id "paypal-sg"_

Examples of invalid use of `add --rec` command:
* `add --rec` _Missing a name._
* `add --rec --name John Doe --phone` _Optional fields (in this case `--phone`) were used but not specified_
* `add --rec --name John Doe --oid bogus-org` _Given that no organization with the id "bogus-org" exists in the address book._

Sample demonstration with the expected visual outcome:
* If you execute the command: `add --rec --name Ryan Koh --oid job_seeker_plus`, you should see a new contact being added to the list of contacts on the left panel.

* Since the `--oid` flag was provided, the newly added recruiter contact will have a special label _from organization (job\_seeker\_plus)_ to indicate that the recruiter is linked to the organization with that particular id.

![add-recruiter](images/add_recruiter_success.png)

#### List contacts - `list`
<span class="learning-outcome">:trophy: Able to list contacts, organizations, and recruiters in Jobby</span>

<span class="information">:information_source: Assumes that you have completed the tutorial</span>

Format: `list [--org/--rec]`

Shows a list of all contacts in the address book.
Supplying `--org` lists only Organizations while supplying `--rec` lists only Recruiters.

Examples:
* `list`
* `list --org`
* `list --rec`

[SCREENSHOT HERE]

#### Sorting contacts and job applications - `sort`
<span class="learning-outcome">:trophy: Able to sort contacts and job applications in Jobby</span>

<span class="information">:information_source: Assumes that you have completed the tutorial</span>

Format: `sort --FLAG_TO_SORT [--ascending/--descending]`


Supported flags (only 1 must be provided): [This should be a title font, I think]

Flags for contacts:
* `--address` - The address of the <span class="job-application">Contact</span>. Will sort alphabetically.
* `--email` - The email address of the <span class="job-application">Contact</span>. Will sort alphabetically.
* `--id` - The identification string of the <span class="job-application">Contact</span>. Will sort alphabetically.
* `--name` - The name of the <span class="job-application">Contact</span>. Will sort alphabetically.
* `--phone` - The phone number of the <span class="job-application">Contact</span>. Will sort alphabetically.
* `--url` - The web address of the <span class="job-application">Contact</span>. Will sort alphabetically.

Flags for job applications:
* `--by` - The deadline of the <span class="job-application">Job Application</span>. Will sort chronologically.
* `--stage` - The stage of the <span class="job-application">Job Application</span>. Will sort by stage order.
* `--stale` - The time of last update of the <span class="job-application">Job Application</span>. Will sort chronologically.
* `--status` - The status of the <span class="job-application">Job Application</span>. Will sort by status order.
* `--title` - The title of the <span class="job-application">Job Application</span>. Will sort alphabetically.

Flags for resetting the sort order:
* `--none` - Will reset the sorting order of <span class="job-application">Contacts</span> and <span class="job-application">Job Applications</span>.

Flags for specifying the sort order:
* `--ascending` - The specified flag will sort in ascending order.
* `--descending` - The specified flag will sort in descending order.

If neither `--ascending` or `--descending` are provided, the list will be sorted in ascending order by default.

Neither `--ascending` nor `--descending` may be specified if the flag is `--none`.

`Sort` will work even if no <span class="job-application">Contacts</span> or <span class="job-application">Job Applications</span> exist.

[SCREENSHOT HERE]

Examples of valid use of `sort` command:
* `sort --title --ascending`
* `sort --url`
* `sort --stale --descending`
* `sort --none`

Examples of invalid use of `sort` command:
* `sort` _No flag provided._
* `sort --organization` _Invalid flag._
* `sort --none --ascending` _Flags `--none` and `--ascending` both specified._


### Reminding about deadlines: `remind`
Reminds the user of upcoming deadlines for job applications.

Format: `remind --earliest/--latest`

Specifying `--earliest` will list the application deadlines in order of urgency, from earliest to latest.

Specifying `--latest` will list the application deadlines in order of reverse urgency, from latest to earliest.

Examples:
* `remind --earliest`
* `remind --latest`

--------------------------------------------------------------------------------------------------------------------

## Command Summary

### Commands for Handling Contacts

 Action                | Format, Examples                                                                                                                                                                                                                                                                            
-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 **Add Organization**  | `add --org --name <NAME> [--id ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...`<br> e.g., `add --org --name NUS --phone 0123456789 --email example@nus.edu.sg --url https://www.nus.edu.sg/` 
 **Add Recruiter**     | `add --rec --name <NAME> [--id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...`<br> e.g., `add --rec --name John Doe --oid paypal-sg`                                                                                                       
 **Delete Contact**    | `delete INDEX/ID [--recursive]` <br> e.g., `delete 3`, `delete id-55tg`                                                                                                                                                                               
 **Edit Contact**      | `edit INDEX ...` or <br>`edit ID ...`                                                                                                                                                                                                                                                                              
 **List**              | `list [--org/--rec]`
 **Sort Contacts**     | `sort --address/--email/--id/--name/--phone/--url [--ascending/--descending]`

### Commands for Handling Recruiters

 Action                | Format, Examples                                                                                                                                                                                                                                                                            
-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 **Delete Application**| `delete --application INDEX/ID` <br> e.g., `delete --application 2`                                                                                                                                                                              
 **Edit Application**  | `edit --application INDEX ...` <br> e.g., `edit --application 2 --title Analyst`                                                                                                                                                                                                                                                                             
 **Apply**             | `apply INDEX/ID --title TITLE [--description DESCRIPTION] [--by DEADLINE] [--stage STAGE] [--status STATUS]`
 **Sort Applications** | `sort --by/--stage/--stale/--status/--title [--ascending/--descending]`

### Other Commands

 Action               | Format, Examples                                                                                                                                                                                                                                                                            
----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 **Clear**            | `clear`                                                                                                                                                                                                                                                                                     
 **Find**             | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
 **Help**             | `help`

--------------------------------------------------------------------------------------------------------------------

## Glossary

(Terms that may be difficult to understand here.)

--------------------------------------------------------------------------------------------------------------------

## Issues

(Where to report issues and what bugs currently exist)

