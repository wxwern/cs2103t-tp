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

#### Adding an organization contact: `add --org`

Format: `add --org --name NAME [--id ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]... `

Acceptable Parameters:
* `NAME` can accept any value, but must not be blank.
* `ID` refers to a unique identifier which is used to uniquely identify the organization (alphanumeric and basic symbols, i.e. should only be `a-z`, `A-Z`, `0-9`, `-`, `_`).
    * Specifying this sets the ID, or one unique one will be derived and generated from the name if not provided.
* `NUMBER` should be a valid phone number.
* `EMAIL` should be a valid email.
* `URL` should be a valid url-like format.
* `ADDRESS` can accept any value. It designates the contact’s physical address.
* `TAG` can accept any value and may have multiple inputs.


Examples:
* `add --org --name J&J`
* `add --org --name Google --id g-sg --phone 98765432 `
* `add --org --name Hoyoverse --email mihoyo@example.com --tag example1 --tag example2`
* `add --org --name Example --url www.organization.org --tag freelance`
* `add --org --name Examinations NUS --phone 65166269 --email examinations@nus.edu.sg --url https://luminus.nus.edu.sg/`

### Editing a contact: `edit`

Current: Edit contacts whose names contain any of the given keywords or ids.

Format: `INDEX/ID [--name NAME] [--id ID] [--phone PHONE] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...`

* Names, index and id are being searched.
* For id, the search is case-insensitive, e.g. `hans` will match `Hans`
* For Index, the search will match with the index as listed on the GUI e.g. `1` will match with the first item in the GUI.
* You can change the parameter of any of the `[--variable NEW VALUE]`, e.g. `edit 1 --name Google --phone 91241412 --email google@gmail.sg`, which changes the name, phone number and email of the contact

Examples:
* `edit google --phone 91292951` changes the phone number of google to `91292951`
* `edit 1 --name Jane Street` changes the name of the contact at index 1 in the GUI to `Jane Street`
* `edit 1 --name Google --phone 91241412 --email google@gmail.sg`, which changes the name, phone number and email of the contact to `Google`, `91241412` and `google@gmail.sg` respectively.

### Locating contacts by name and id: `find`

Current: Finds contacts whose names contain any of the given keywords or ids.

Format: `find KEYWORD/ID...`

* Name & ID are searched
* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* You can match partial keywords. e.g. searching for `ha` will match with `hamburger`.
* Partial IDs can match the entire ID. e.g. searching for `1234` will match with `id_12345`.
* Persons matching at least one keyword will be returned (i.e. `OR` search)
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Can match with multiple ids `12345 id_51231` will match with `id_12345` and `id_51231`

Examples:
* `find John` returns `john` and `John Doe`
* `find id_12345` returns `john` and whose id is `id_12345`
* `find Jo` returns `john`, `John Doe`, `Josh` and every other keyword with `jo` in its substring
* `find alex david` returns `Alex Yeoh`, `David Li`

--------------------------------------------------------------------------------------------------------------------

## Glossary

(Terms that may be difficult to understand here.)

--------------------------------------------------------------------------------------------------------------------

## Issues

(Where to report issues and what bugs currently exist)

