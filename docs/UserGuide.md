---
layout: page
title: User Guide
---

SteveJobs++ (SJ++) is a **desktop app for managing job applications and contacts, optimized for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). SJ++ can help you manage tracking your job applications and contacts in a more streamlined fashion. If you can type fast, SJ++ can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `sjobs.jar` from [here](https://github.com/AY2324S1-CS2103T-W08-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your SteveJobs Application.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar sjobs.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type a command in the command box and press Enter to execute it - e.g., typing **`help`** and pressing Enter will open the help window.<br>

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. if the command format is `add --name NAME`, `NAME` is a parameter which can be used like `add --name John Doe`.

* Items in square brackets are optional.<br>
  e.g `--name NAME [--tag TAG]` can be used as `--name John Doe --tag friend` or as `--name John Doe`.

* Items in angled brackets describe what the content should be.<br>
  e.g. `--date <yyyymmdd formatted DATE>` means the parameter `DATE`, which is supplied after the `--date` prefix, should be formated as `yyyymmdd`.

* Items with `...` after them can be used multiple times including zero times.<br>
  e.g. `[--tag TAG]...` can be used as ` ` (i.e. 0 times), `--tag friend`, `--tag friend --tag family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `--name NAME --phone PHONE_NUMBER`, `--phone PHONE_NUMBER --name NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Note:**
This is a pending update. Command formats aren't updated as of the current build, and are still using the legacy `parameter/value` syntax instead of this new `--parameter value` syntax. Additionally, commands are in the middle of being revamped.
</div>

### Viewing help: `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a contact: `add`

Format: `add --type CLASS <additional parameters and values...>`

Adds a contact to the address book of the given class type: Organization or Recruiter.

Notice that `CLASS` must be either _Organization_ or _Recruiter_ (or any case-insensitive unambiguous prefix match, so “org” will match “Organization” automatically)

Details specifically on organization and recruiter level are specified in the next sections.


#### Adding an organization contact: `add --type organization`

_{Work in progress...}_

Format: `add --type o[rganization] --name NAME [--id ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--addr ADDRESS] [--stat STATUS] [--pos POSITION] [--tag TAG]... `


Acceptable Parameters:
* `NAME` can accept any value, but must not be blank.

* `ID` refers to a unique identifier which is used to uniquely identify the organization (alphanumeric and basic symbols, i.e. should only be `a-z`, `A-Z`, `0-9`, `-`, `_`).
    * Specifying this sets the ID, or one unique one will be derived and generated from the name if not provided.

* `NUMBER` should be a valid phone number.

* `EMAIL` should be a valid email.

* `URL` should be a valid url-like format.

* `ADDRESS` can accept any value. It designates the contact’s physical address.

* `STATUS` must be one of _interested, applied, pending, offered, rejected, current_ (case-insensitive, prefix-only match allowed).

* `POSITION` may be any value. It designates the position you intend to apply to.

* `TAG` can accept any value and may have multiple inputs.


Examples:
* `add --name J&J --type Organization`

* `add --name Google --type Org --id g-sg --phone 98765432 `

* `add --name Hoyoverse --type organization --email mihoyo@example.com --tag example1 --tag example2`

* `add --name Example --type organi --url www.organization.org --tag freelance work`

* `add --type o --name Examinations NUS --phone 65166269 --email examinations@nus.edu.sg --url https://luminus.nus.edu.sg/ --stat pending`


### Adding a recruiter: `add --type recruiter`

_{Work in progress...}_

Format: `add --type r[ecruiter] --name NAME [-id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--addr ADDRESS] [--tag TAG]...`


Acceptable Parameters:
* `NAME` can accept any value, but must not be blank.

* `ID` refers to a unique identifier which is used to uniquely identify the recruiter (alphanumeric and basic symbols, i.e. should only be `a-z`, `A-Z`, `0-9`, `-`, `_`).
    * Specifying this sets the ID, or one unique one will be derived and generated from the name if not provided.

* `ORG_ID` refers to the unique identifier which is used to uniquely identify the organization the recruiter should be tied to.

* `NUMBER` should be a valid phone number.

* `EMAIL` should be a valid email.

* `ADDRESS` can accept any value. It designates the contact’s physical address.

* `TAG `can accept any value and may have multiple inputs.


Examples:
* `add --name John Doe --type Recruiter --oid paypal-sg`


### Listing all contacts: `list`

_{To be updated...}_

Shows a list of all contacts in the address book.

Format: `list`


### Editing a contact: `edit`

_{To be updated...}_


### Locating contacts by name: `find`

_{To be updated...}_


Current: Finds contacts whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)


### Deleting a contact: `delete`

_{To be updated...}_

Deletes the specified contact from the address book.

Format: `delete INDEX`


* Deletes the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list` followed by `delete 2` deletes the 2nd contact in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st contact in the results of the `find` command.


### Clearing all entries: `clear`

Clears all data from the app.

Format: `clear`


### Exiting the program: `exit`

Exits the program.

Format: `exit`


### Saving the data

SJ++ data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


### Editing the data file

SJ++ data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, SJ++ will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SJ++ home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add Organization** | `add --type o[rganization] --name <NAME> [--id ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--addr ADDRESS] [--stat STATUS] [--pos POSITION] [--tag TAG]...`<br> e.g., `add --type o --name NUS --phone 0123456789 --email example@nus.edu.sg --url https://www.nus.edu.sg/ --stat pending --pos Research`
**Add Recruiter** | `add --type r[ecruiter] --name <NAME> [--id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--addr ADDRESS] [--tag TAG]...`<br> e.g., `add --name John Doe --type Recruiter --oid paypal-sg`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | Coming soon...
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
