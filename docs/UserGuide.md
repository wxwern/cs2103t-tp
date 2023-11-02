---
layout: page
title: User Guide
---

**Jobby** is a **desktop app for managing job applications and contacts, optimized for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). Jobby can help you manage tracking your job applications and contacts in a more streamlined fashion. If you can type fast, Jobby can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `jobby.jar` from [here](https://github.com/AY2324S1-CS2103T-W08-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Jobby Application.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar jobby.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type a command in the command box and press Enter to execute it - e.g., typing **`help`** and pressing Enter will open the help window.<br>

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Usage

This section guides you on understanding and typing commands in the app, like formatting and autocomplete.

If you're instead looking for available commands, check out the [Features](#features) section instead. 

### Command Structure

At a basic level, we write commands in the command box at the top of Jobby's window.

Commands are made up of a few parts: The **command**, **parameter names** and **input values**.

A command like "`edit google --name Google SG --id google-sg`" would refer to:
* the command `edit`,
* with a command value `google`,
* with a parameter `--name`,
  * which has the parameter value `Google SG`,
* with a parameter `--id`,
  * which has the parameter value `google-sg`.

Parameters may be in any order, and are of the form `-a` or `--abc123`.

Any extra values to commands that don't accept them will be ignored (or if ambiguous, throws an error).

### Command Format

Throughout this guide, you will find symbols and placeholders used to describe a command format. They are:

* **Words in `UPPER_CASE`**
  * The parts where you should be typing your parameter values.
  * e.g., `--name NAME` means inputting names along the lines of `--name Alice`.

* **Terms separated by `/`**
  * Exactly one of the given options
  * e.g., `--a/--b` means either `--a` or `--b` but not `--a --b`.

* **Terms surrounded by `[` and `]`**
  * An optional parameter or option that may be omitted.

* **Terms ending with `...`**
  * The parameter is multivalued.
  * e.g., `[--tag TAG]...` means `--tag` can be repeated from 0 to any number of times.

### Command Autocomplete

Command autocompletion allows you to type commands in Jobby at unimaginable speeds.

As you type your command, you may see a list of suggested completions pop up like below. 
Just press **TAB** or **SPACE** to select the first suggestion to fill in!

![Autocomplete Screenshot](images/autocomplete.png)

To temporarily hide all suggestions, press **ESC**. This temporarily disables autocompletion for the next keystroke. 

If suggestions were hidden or aren't shown when they should, press **TAB** to prompt Jobby to bring it back.

<div markdown="block" class="alert alert-info">

**:bulb: Additional tips:**<br>

* Autocomplete checks for fuzzy matches - it sorts by the best *subsequence* prefix match first.

  * For example, you can type `-nm` to get the autocompletion result of `--name`.

  * This allows you to quickly choose between parameter names with similar prefixes e.g., by typing
    `-dsp` to select `--description` instead of `--descending`.

* If you rather choose from the list instead of typing out the prefix, it is possible to use the **UP** and **DOWN**
  arrow keys to navigate through the menu, then press **ENTER** to select them.

* Accidentally triggered autocomplete when you didn't intend to? Don't worry, just press **BACKSPACE** to immediately 
  revert to your previously typed text.

</div>

## Features

We can see a list of features available in Jobby here.

<div markdown="block" class="alert alert-info">

**:information_source: Note:**<br>

* If you are using a PDF version of this document, be careful when copying and pasting commands
  that span multiple lines as space characters surrounding line-breaks may be omitted when copied over
  to the application.

</div>

### Viewing help: `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a contact: `add`

Format: `add --org/--rec <additional parameters and values...>`

Adds a contact to the address book of the given class type: Organization or Recruiter.

Supplying `--org` adds an Organization while supplying `--rec` adds a Recruiter to the address book.

Details specifically on organization and recruiter level are specified in the next sections.


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


#### Adding a recruiter contact: `add --rec`

Format: `add --rec --name NAME [-id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...`


Acceptable Parameters:
* `NAME` can accept any value, but must not be blank.

* `ID` refers to a unique identifier which is used to uniquely identify the recruiter (alphanumeric and basic symbols, i.e. should only be `a-z`, `A-Z`, `0-9`, `-`, `_`).
    * Specifying this sets the ID, or one unique one will be derived and generated from the name if not provided.

* `ORG_ID` refers to the unique identifier which is used to uniquely identify the organization the recruiter should be tied to. It is subject to the same validation as the ID field.
The value provided must also be the ID of an existing organization in the address book.

* `NUMBER` should be a valid phone number.

* `EMAIL` should be a valid email.

* `ADDRESS` can accept any value. It designates the contact’s physical address.

* `TAG` can accept any value and may have multiple inputs.


Examples:
* `add --rec --name John Doe --oid paypal-sg` links the recruiter `John Doe` to an organization with the id `paypal-sg`


### Listing all contacts: `list`

Format: `list [--org/--rec]`

Shows a list of all contacts in the address book.
Supplying `--org` lists only Organizations while supplying `--rec` lists only Recruiters.

Examples:
* `list`
* `list --org`
* `list --rec`


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

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Name & ID are searched
* The partial keyword can be matched with `ha` will match with `hambarger`
* Partial ID can match with the entire id as well `12345` will match with `id_12345`, however good to be more specific
* Persons matching at least one keyword will be returned (i.e. `OR` search)
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Can match with multiple ids `12345 id_51231` will match with `id_12345` and `id_51231`

Examples:
* `find John` returns `john` and `John Doe`
* `find id_12345` returns `john` and whose id is `id_12345`
* `find Jo` returns `john`, `John Doe`, `Josh` and every other keyword with `jo` in its substring
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)


### Deleting a contact: `delete`

_{To be updated...}_

Deletes the specified contact from the address book and its associated contacts if specified.
Format: `delete INDEX/ID [--recursive]`

* `INDEX` refers to the index number shown on the list and must be a positive integer.
* Deletes the person with id `ID` if specified, ignoring if the contact is shown in the list.
* Deletes other contacts associated under the selected contact if `--recursive` is used, ignoring if the contact is shown in the list.

Examples:
* `delete 1` deletes the 1st contact in the list of contacts shown.
* `delete amazon-sg` deletes the contact with id `amazon-sg` in the address book.
* `delete 1 --recursive` deletes the 1st contact along with other contacts associated under it.



#### Applying to an Organization: `apply`

Format: `apply INDEX/ID --title TITLE [--description DESCRIPTION] [--by DEADLINE: DD-MM-YYYY] [--stage APPLICATION STAGE: resume | online assessment | interview] [--status STATUS: pending | offered | accepted | turned down]`

Acceptable Parameters:
* `TITLE` can accept any value.

* `DESCRIPTION` refers to the description of the internship application.

* `DEADLINE` should be a valid date in the format DD-MM-YYYY.

* `APPLICATION STAGE` should be 1 of 3 pre-determined stage: resume | online assessment | interview.

* `STATUS` should be 1 of 4 pre-determined status: pending | offered | accepted | turned down.



Examples:
* `apply 1 --title SWE`

* `apply id_12345_1 --title Unit Tester --by 12-12-2023`

* `apply id_12345_1 --title Unit Tester --description Unit testing for Google --by 12-12-2023 --stage resume`

* `apply id_12345_1 --title Junior Engineer --description Junior role --by 12-12-2023 --stage resume --status pending`


### Deleting a job application
Deletes the specified job application from the list.
Format: `delete --application INDEX`

* `INDEX` refers to the index number shown on the list and must be a positive integer.

Examples:
* `delete --application 1` deletes the first job application in the list.


### Updating/Editing a job application
Updates the job applications with the input fields.

Format: `edit --application INDEX [--title TITLE] [--description DESCRIPTION] [--by DEADLINE] [--status STATUS] [--stage STAGE]`

* `INDEX` refers to the index number shown on the list and must be a positive integer.
* At least one of the optional fields must be specified.
* `STATUS` is one of `pending`, `offered`, `accepted`, `turned down`
* `STAGE` is one of `resume`, `online assessment`, `interview`

Examples:
* `edit --application 1 --title SWE --description Pay: $100 per hour`
* `edit --application 1 --status rejected`
* `edit --application 1 --stage interview`

### Sorting contacts/job applications: `sort`
Sorts contacts or applications by the specified flag.

Format: `sort --FLAG_TO_SORT`

The following sorting flags are supported:
* For contacts:
  * `--address`
  * `--email`
  * `--name`
  * `--id`
  * `--phone`
  * `--url`
* For job applications:
  * `--deadline`: Sorts by application deadline
  * `--stage`: Sorts by application stage
  * `--stale`: Sorts by last updated applications
  * `--status`: Sorts by application status
  * `--title`: Sorts by job title
* To reset the sorting arrangement:
  * `--none`

Supplying `--ascending` or `--descending` sorts the contacts or applications in the specified order. 
If not specified, the default order is used:
* Chronological (for deadlines)
* According to the stage/status order (for application stages and statuses)
* Alphabetical (for the rest)
Neither order flag may be supplied if `-none` is the specified sorting flag.

Examples:
* `sort --name`
* `sort --deadline --descending`
* `sort --title --ascending`
* `sort --none`

### Reminding about deadlines: `remind`
Reminds the user of upcoming deadlines for job applications.

Format: `remind --earliest/--latest`

Specifying `--earliest` will list the application deadlines in order of urgency, from earliest to latest.
Specifying `--latest` will list the application deadlines in order of reverse urgency, from latest to earliest.

Examples:
* `remind --earliest`
* `remind --latest`



### Clearing all entries: `clear`

Clears all data from the app.

Format: `clear`


### Exiting the program: `exit`

Exits the program.

Format: `exit`


### Saving the data

Jobby data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


### Editing the data file

Jobby data are saved automatically as a JSON file `[JAR file location]/data/jobby.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, Jobby will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Jobby home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

 Action               | Format, Examples                                                                                                                                                                                                                                                                            
----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 **Add Organization** | `add --org --name <NAME> [--id ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...`<br> e.g., `add --org --name NUS --phone 0123456789 --email example@nus.edu.sg --url https://www.nus.edu.sg/` 
 **Add Recruiter**    | `add --rec --name <NAME> [--id ID] [--oid ORG_ID] [--phone NUMBER] [--email EMAIL] [--url URL] [--address ADDRESS] [--tag TAG]...`<br> e.g., `add --rec --name John Doe --oid paypal-sg`                                                                                                       
 **Clear**            | `clear`                                                                                                                                                                                                                                                                                     
 **Delete**           | `delete INDEX/ID [--recursive]` <br> e.g., `delete 3`, `delete id-55tg`                                                                                                                                                                               
 **Edit**             | `edit INDEX ...` or <br>`edit ID ...` or <br>`edit --application INDEX ...`                                                                                                                                                                                                                                                                              
 **Find**             | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
 **Apply**            | `apply INDEX/ID --title TITLE [--description DESCRIPTION] [--by DEADLINE] [--stage STAGE] [--status STATUS]` 
 **List**             | `list [--org/--rec]`
 **Sort**             | `sort --FLAG_TO_SORT`
 **Help**             | `help`                                                                                                                                                                                                                                                                                      


