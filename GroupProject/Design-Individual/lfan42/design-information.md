## Requirements and Solutions

1. _A user shall be able to choose to log in as the administrator or as a specific player when starting the application. For simplicity, authentication is optional._

	I added the `Administrator` and the `Player` classes to the design. The log in choice is not shown and will be implemented in the GUI.

2. _The application shall allow players to (1) choose a cryptogram to solve, (2) solve cryptograms, (3) see previously solved cryptograms, and (4) view the list of player ratings._

    I added four methods to the class of `Player` to implement these actions, namely `chooseCryptogram()`, `solveCryptogram`, `seeSolvedCryptogram`, and `viewPlayerRatings()`. These methods can associate the `Palyer` class with the `PalyerCryptogram` and the `AvailableCryptograms` classes, which will be explainted later.

3. _The application shall allow the administrator to (1) add new cryptograms, and (2) add new local players._

	I added the `AddNewCryptogram()` and the `AddLocalPlayer()` methods to the class of `Administrator`. These two methods can associate the `Administrator` class with the `Player` and the `Cryptogram` classes.

4. _The application shall maintain an underlying database to save persistent information across runs (e.g., cryptograms, players, solutions)._

	The implementation of database is not able to shown explicitly in the class diagram. They will be stored in the server. `Player`, `PlayerRating`, and `AvailableCryptogram` can be requested from and updated to the server by the `ExternalWebService`.

5. _Cryptograms and player ratings will be shared with other instances of the application.An external web service utility will be used by the application to communicate with a central server to:_

    a. _Send updated player ratings._
    b. _Send new cryptograms and receive a unique identifier for them._
    c. _Request a list of additional cryptograms._
    d. _Request the current player ratings._

    _You should represent this utility as a utility class called " ExternalWebService " that (1) is connected to the other classes in the system that use it and (2) explicitly lists relevant methods used by those classes._

    a. I associated the `Player` class with the `PlayerRating` class. The rating can be updated through the `ExternalWebService`.
    b. The `AddCryptogram` interface was added. It will send new cyptogram to the server and obtain the assigned indentifier through the `ExternalWebService`.
    c. The `requestAdditionalCryptogram()` method was added to the `AvailableCryptograms` class. It can request additinal cryptograms through the `ExternalWebService`.
    d. I associated the `Player` class with the `PlayerRating` class. The rating can be requested through the `ExternalWebService`.

6. _A cryptogram shall have an encoded phrase (encoded with a simple substitution cipher), and a solution._

	I added the `Cryptogram` class with the `encodedPhrase` and the `solutionPhrase` attributes.

7. _A cryptogram shall only encode alphabetic characters, but it may include other characters (such as punctuation, numbers, or white spaces)._

	This restriction is not able to shown explicitly in the class diagram. It will be implemented in the actual code.

8. _To add a player, the administrator will enter the following player information:_
    a. _A first name_
    b. _A last name_
    c. _A unique username_

    I added the `userName`, the `lastName`, and the `firstName` attributes to the `Player` class. The uniqueness of a `userName` will be verified by the server through the `ExternalWebService`.

9. _To add a new cryptogram, an administrator will:_
    a. _Enter a solution phrase._
    b. _Enter a matching encoded phrase._
    c. _Edit any of the above information as necessary._
    d. _Save the complete cryptogram._
    _After doing so, the administrator shall see a confirmation message. The message shall contain the unique identifier assigned to the cryptogram by the external web service utility._

    I added the `AddCryptogram` interface with four methods, namely `enterSolutionPhrase()`, `enterEcondedPhrase()`, `editEnteredPhrases()`, and `saveCryptogram()`. It will send new cyptograms to the server and show the assigned indentifier through the `ExternalWebService`.

10. _To choose and solve a cryptogram, a player will:_
    a. _Choose a cryptogram from a list of all available cryptograms (see also Requirement 11)._
    b. _View the chosen cryptogram (including any prior solution, complete or in progress, in case he or she already worked on the same cryptogram earlier)._
    c. _Assign (or reassign) replacement letters to the encrypted letters and view the effects of these assignments in terms of resulting potential solution._
    d. _Submit the current solution when he or she has replaced all letters in the puzzle and is satisfied with such solution._
    _At this point, the player shall get a result indicating whether the solution was correct. At any point, the player may return to the list of cryptograms to try another one._

    To implement all these requirements, I first added the `PlayerCryptogram` and the `AvailabelCryptograms` classes. The `PlayerCryptogram` is inherited from the `Cryptogram` class. Three additional attributes were added to it, namely `priorSolutions[]`, `solvedOrNot`, and the `numIncorrecSub`. The `AvailableCryptograms` is a class which contains a list of `PlayerCryptogram`. Each `Player` instance will have its own `AvailableCryptograms` instance, which will be requested from the server first when it is initialized.

    a. I associated the `Player` with the `AvaiableCryptograms`. Then a player can choose cryptogram from its `cryptograms[]` attribute.
    b. I associated the `Player` with the `PlayerCryptograms`. Then a player can view its three attributes through the `viewCryptogram()` method, namely `priorSolutions[]`, `solvedOrNot`, and the `numIncorrectSub`.
    c. I added the `assignLetters()` method to the `PlayerCryptogram` class. As the `Player` is associated with `PlayerCryptogram`. A player can assign letters and see the effects of these assignments.
    d. I added the `submitSolution()` method to the `PlayerCryptogram` class. A player submit solutions through this method.

11. _The list of available cryptograms shall show, for each cryptogram, its identifier, whether the player has solved it, and the number of incorrect solution submissions, if any._

	I added the `AvailableCryptograms` class with the `cryptograms[]` attribute, which is a list of instances of `PlayerCryptogram`. The latter has the required information as its attributes, namely `priorSolutions[]`, `solvedOrNot`, and the `numIncorrecSub`.

12. _The list of player ratings shall display, for each player, his or her name, the number of cryptograms solved, the number of cryptograms started, and the total number of incorrect solutions submitted. The list shall be sorted in descending order by the number of cryptograms solved._

	I added the `PlayerRating` class with the requeire information as its attributes, namely `firstName`, `lastName`, `numStarted`, `numSolved`, and `totalIncorrectSub`. Each palyer will has his/her own instance of `PlayerRating`. Then I  added the `PlayerRatingList` class which has a sorted list of instances of `PlayerRating` in descending order by the number of cryptograms solved.

13. _The User Interface (UI) shall be intuitive and responsive._

	The UI is not able to be shown in the class diagram. It will be implemented in actual code.