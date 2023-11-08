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

#### Add recruiter command - `add --rec`

Applies to: <text class="job-application">Recruiter</text> <text class="beginner-difficulty">Beginner</text>

<span class="learning-outcome">:trophy: Able to add contacts of recruiters into Jobby</span>

<text class="information">:information_source: Assumes that you have completed the tutorial</text>

Format: `add --rec --name NAME [-id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...`

Required Fields:
* `NAME` can accept any value, but must not be blank.

Optional Fields:
* `ID` refers to a unique identifier which is used to uniquely identify the recruiter (alphanumeric and basic symbols, i.e. should only be `a-z`, `A-Z`, `0-9`, `-`, `_`).
    * Specifying this sets the `ID`, or a unique one will be derived and generated from the name if not provided.

* `ORG_ID` refers to the unique identifier which is used to uniquely identify the <text class="job-application">Organization</text> the <text class="job-application">Recruiter</text> should be tied to. It is subject to the same validation as the `ID` field.
The value provided must also be the `ID` of an existing <text class="job-application">Organization</text> in the address book.

* `NUMBER` should be a valid phone number.

* `EMAIL` should be a valid email.

* `URL` should be a valid url where each domain is separated by a dot - *domain.com* or *domain.subdomain.com* are accepted. 

* `ADDRESS` can accept any value. It designates the contact’s physical address.

* `TAG` can accept any value and may have multiple inputs.


Examples of valid use of `add --rec` command:
* `add --rec --name John Doe` _Adds a recruiter that is not linked to any organization._
* `add --rec --name John Doe --oid paypal-sg` _Adds a recruiter that is linked to an organization (if it exists in the address book) with the id "paypal-sg"_

Examples of invalid use of `add --rec` command:
* `add --rec` _Missing a name._
* `add --rec --name John Doe --phone` _Optional fields (in this case `--phone`) were used but not specified_
* `add --rec --name John Doe --oid bogus-org` _Given that no organization with the id "bogus-org" exists in the address book._

--------------------------------------------------------------------------------------------------------------------

## Glossary

(Terms that may be difficult to understand here.)

--------------------------------------------------------------------------------------------------------------------

## Issues

(Where to report issues and what bugs currently exist)

