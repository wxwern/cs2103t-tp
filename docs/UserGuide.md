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

## Understanding Jobby's command syntax

_Explanation of how Jobby's commands are structured_

--------------------------------------------------------------------------------------------------------------------

## Command documentation

(Detailed information on each command with the constraints, possible errors and feature flaws)

### Apply command - `apply`
Applies to: <text class="job-application">Job Application</text> \
<text class="learning-outcome">🏆 Able to add job applications associated with an organization in Jobby</text>
<text class="intermediate-difficulty">Intermediate</text>\
<text class="information">:information_source: Assumes that you have completed the tutorial</text>

Format: `apply INDEX/ID --title TITLE [--description DESCRIPTION] [--by DEADLINE: DD-MM-YYYY] [--stage APPLICATION STAGE: resume | online assessment | interview] [--status STATUS: pending | offered | accepted | turned down]`

Required fields:
* `INDEX/ID` - The index or the id of the <text class="job-application">Organization</text>
  in the list to be applied to. Must be a valid and existing index or id.
* `TITLE` - The job title of the position. Accepts multiple words separated with spaces, as long as characters are alphanumeric.

Optional fields:
* `DESCRIPTION` - The description of the <text class="job-application">Job Application</text>. If specified, it should not be empty.
* `DEADLINE` - The deadline of the current stage of the <text class="job-application">Job Application</text>. If specified, it should be in the format **DD-MM-YYYY**. If not specified, it is set to 14 days from the current date.
* `APPLICATION STAGE` - The stage of the <text class="job-application">Job Application</text>. If specified, it must be one of the 3 options: **resume, online assessment, interview**. If not specified, it is set to **resume**.
* `STATUS` - The status of the <text class="job-application">Job Application</text>. If specified, it must be one of the 4 options: **pending, offered, accepted, turned down**. If not specified, it is set to **pending**.

Examples of valid use of `apply` command:
* `apply 1 --title SWE` _Given that the first item in the list of contacts is an organization._
* `apply id_12345_1 --title Unit Tester --by 12-12-2023` _Given that id_12345_1 is an id belonging to an organization._
* `apply id_12345_1 --title Level 7 Engineer --description Senior role, Pay: $100 --by 12-12-2023 --stage resume --status pending`

Examples of invalid use of `apply` command:
* `apply 0 --title SWE` _Invalid index._
* `apply 10 --title SWE` _Given that there are only 9 contacts in the list and the 10th contact does not exist._
* `apply 1 --title SWE` _Given that the first contact is a recruiter and not an organization._
* `apply 1` _Job title not specified._
* `apply 1 --title SWE --description` _Optional fields were used but not specified._
* `apply 1 --title SWE --by 31-31-2023` _Invalid date for deadline._
* `apply 1 --title SWE --by tomorrow` _Invalid format for deadline._

--------------------------------------------------------------------------------------------------------------------

## Glossary

(Terms that may be difficult to understand here.)

--------------------------------------------------------------------------------------------------------------------

## Issues

(Where to report issues and what bugs currently exist)

