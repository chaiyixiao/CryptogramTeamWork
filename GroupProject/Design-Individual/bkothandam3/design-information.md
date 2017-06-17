Design Information
--------------------




**Requirement # 1**: A user shall be able to choose to log in as the administrator or as a specific player when
starting the application. For simplicity, authentication is optional.

**Design**: The classes Administrator and Player will be derived from the class User. The way a user logs in will be an action/operation. Authentication, being optional, is not covered.  

<br>

**Requirement # 2**: The application shall allow players to (1) choose a cryptogram to solve, (2) solve
cryptograms, (3) see previously solved cryptograms, and (4) view the list of player
ratings.

**Design**: This requirement does not affect the design of classes. This is an action/operation that can be performed and is more suitable to be described in the sequence diagram. A simple Menu will be provided listing all the above 4 choices.  

<br>

**Requirement # 3**: The application shall allow the administrator to (1) add new cryptograms, and (2) add new local players.

**Design**: This requirement does not affect the design of classes. This is an action/operation that can be performed when a user is logged in as an admministrator. A Menu will be provided listing all the above 2 choices.  

<br>

**Requirement # 4**: The application shall maintain an underlying database to save persistent information across runs (e.g., cryptograms, players, solutions).

**Design**: A database will be used to persist information across runs. This is not a part of the Class Model.  


<br>

**Requirement # 5**: Cryptograms and player ratings will be shared with other instances of the application.
An external web service utility will be used by the application to communicate with a
central server to:
* a. Send updated player ratings.
* b. Send new cryptograms and receive a unique identifier for them.
* c. Request a list of additional cryptograms.
* d. Request the current player ratings.

You should represent this utility as a utility class called " ExternalWebService " that (1) is
connected to the other classes in the system that use it and (2) explicitly lists relevant
methods used by those classes.

**Design**: We will follow the suggestion provided in the requirement. We will have an utility class to represent the above functionalty provided by the external web service utility.  

<br>

**Requirement # 6**: A cryptogram shall have an encodedphrase (encoded with a simple substitution cipher), and a solution.

**Design**: The class Cryptogram will have 2 elements encodedPhrase and solution to meet this requirement.  

<br>

**Requirement # 7**: A cryptogram shall only encode alphabetic characters, but it may include other characters (such as punctuation, numbers, or white spaces).

**Design**: This will be achieved by having a validation function that will validate that the non-alpha characters are retained as-is, between the encodedPhrase and solution .  

<br>

**Requirement # 8**: To add a player, the administrator will enter the following player information:
* a. A first name
* b. A last name
* c. A unique username.

**Design**: This will be achieved by having members firstName, lastName and userName in the User class. The uniqueness will be validated when the admin makes a request to add and will be noted in the sequence diagram.    

<br>

**Requirement # 9**: To add a new cryptogram, an administrator will:
* a. Enter a solution phrase.
* b. Enter a matching encoded phrase.
* c. Edit any of the above information as necessary.
* d. Save the complete cryptogram.

After doing so, the administrator shall see a confirmation message. The message shall
contain the unique identifier assigned to the cryptogram by the external web service
utility .

**Design**: This will be achieved by having members elements encodedPhrase and solution in the Cryptogram class. The save functionality will be provided by the utility class. 

<br>

**Requirement # 10**: To choose and solve a cryptogram, a player will:
* a. Choose a cryptogram from a list of all available cryptograms (see also
Requirement 11).
* b. View the chosen cryptogram (including any prior solution, complete or in
progress, in case he or she already worked on the same cryptogram earlier).
* c. Assign (or reassign) replacement letters to the encrypted letters and view the
effects of these assignments in terms of resulting potential solution.
* d. Submit the current solution when he or she has replaced all letters in the puzzle
and is satisfied with such solution.

At this point, the player shall get a result indicating whether the solution was correct. At
any point, the player may return to the list of cryptograms to try another one.


**Design**: This is a dynamic behavior showing action of the user, that can be represented in a sequence diagram.  

<br>

**Requirement # 11**: The list of available cryptograms shall show, for each cryptogram, its identifier, whether
the playerhas solved it, and the number of incorrect solution submissions, if any.


**Design**: This requirement states that every cryptogram needs to be tracked if a player attempts to solve it. We will have class SolutionAttempt to meet this.  

<br>

**Requirement # 12**: The list of player ratings shall display, for each player, his or her name, the number of
cryptograms solved, the number of cryptograms started, and the total number of
incorrect solutions submitted. The list shall be sorted in descending order by the number
of cryptograms solved.


**Design**: This is a display/GUI functionality. The data required for this page is available in the SolutionAttempt class. The sorting functionality will be provided by the display utility.

<br>

**Requirement # 13**: The User Interface (UI) shall be intuitive and responsive.

**Design**: This does not impact the design, and is not considered.  
<br>


**Scratch Pad**: 

CLASS - administrator, player , user , cryptogram, ExternalWebService<utility>
		- SolutionAttempt

MEMBERS - cryptogram - encoded phrase, solution
	- cryptogram - validation - only encode alphabetic characters ?? utility - check, replace 
	- player - fn, ln, un, rating

ASSOCIATION - Administrator and Players are subset of User

OPERATION - administrator can add new cryptograms, new local players
	 - player can rate a cryptograms
	 - User can login as admin/player
	  - ExternalWebService - save functionality
	  - ExternalWebService - username uniqueness 
	 
	 
