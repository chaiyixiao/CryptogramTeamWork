# Design Information

1. *A user shall be able to choose to log in as the administrator  or as a specific  player when starting the application. For simplicity, authentication is optional.*

    To realize this requirement, I added two classes `Player` and `Administrator`. They both extends from a superclass `User`. There can be _many_ players, but _only one_ administrator in the system. All users should `login` to the system first.
   
2. *The application shall allow  players to (1) choose a cryptogram to solve, (2) solve cryptograms, (3) see previously solved cryptograms, and (4) view the list of player ratings.*

    To realize this requirement, I added operations `chooseCryptogramToSolve`, `solveCryptograms`, and `viewPlayerRatings` to `Player` class. To see previously solved cryptograms, I added a association class `PlayCryptogram`, with an attribute `completed` to indicate if a player has solved a certain cryptogram. The attribute `previouslySolvedCryptograms` of `Player` is calculated by `completed` of all the `PlayCryptogram`.
    
3. *The application shall allow the administrator  to (1) add new cryptograms, and (2) add new local players.*

    Requirement#1 associates `Administrator` and `Cryptogram`, thus connect with `addCryptogramWithSolution`. Requirement#2 associates `Administrator` and `Player`, thus connect with `AddLocalPlayer`. 

4. *The application shall maintain an underlying database to save persistent information across runs (e.g., cryptograms, players, solutions).*

    For this requirement, I added utility class `OperatingSystemWithStorage` to deal with local calculations and local data storage/update.

5. *Cryptograms and player ratings will be shared with other instances of the application.*

    To realize this requirement, I added utility class `ExternalWebService`. It is responsible for communication between central server and local system. And I added operations `sendUpdatedPlayerRatingsToServer`, `sendNewCryptogramsToSever`, `requestAdditionalCryptograms`, `requestCurrentPlayerRatings` to class `OperatingSystemWithStorage`, so that local system can sync with central server. For 5(b), I added operation `assignUniqueIDToCryptogramsReceived` to `CentralServer`.

6. *A cryptogram shall have an encoded phrase (encoded with a simple substitution cipher), and a solution.*

    Thus, I added attributes `encodedPhrase` and `solution` to class `Cryptogram`.

7. *A cryptogram shall only encode alphabetic characters, but it may include other characters (such as punctuation, numbers, or white spaces).*

    I don't consider it requirement, but detail realization.

8. *Add a player*

    To realize this requirement, I added three properties(`firstName`, `lastName`, `uniqueUsername`) to `Player` class. And `storePlayerInfo` in class `OperatingSystemWithStorage`.

9. *Add a new cryptogram*

    Since (c), I add relationships `addCryptogramWithSolution` and `editCryptogramAndSolution` between `Administrator` and `Cryptogram`.
class `OperatingSystemWithStorage` saves the complete cryptogram (d), and sends it to `CentralServer` via `ExternalWebService`. `CentralServer` assigns unique identifier to cryptograms received (`assignUniqueIDToCryptogramsReceived`) ,and sends it back to `OperatingSystemWithStorage` via `ExternalWebService`. I don't see confirmation message a requirement but more like a friendly UI design.

10. *Choose and solve a cryptogram*

    To view chosen cryptogram with detail info, I added attributes `priorSolution`, `started` to class `PlayCryptogram`. When `Player` assign placement letters, `priorSolution` changes accordingly for `Player` to view the effects, and `storeProgress` in `OperatingSystemWithStorage`. If `Player` submits his/her solution, `matchSubmittedSolution` with the `solution` of the cryptogram, then returns a result, update `completed` of class `PlayCryptogram`, and `storeProgress` in `OperatingSystemWithStorage`. `Player` can always `quitGame` (with progress stored).

11. *Display list of available cryptograms*

    To realize this requirement, I added attribute `numOfIncorrectSolutionSubmissions` to class `PlayCryptogram`. every time a `Player` submits his/her solution of the associate cryptogram, attribute `numOfIncorrectSolutionSubmissions` increments if `matchSubmittedSolution` returns false.

12. *The list of player ratings*

    To realize this requirement, I added attributes `totalNumberOfIncorrectSolutionsSubmitted`, `totalNumberOfCryptogramsSolved` and `totalNumberOfCryptogramsStarted` to class `Player`. They are computed by all the `PlayCryptogram` instances. Attribute `rating` of `Player` is `sort` by operation `ratePlayers` of class `OperatingSystemWithStorage`.


