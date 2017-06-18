## Design-information
### Introduction 
This design is a cryptogram game which uses a simple subsitiution cipher. 

### Design details
1. There is a player class，this class is used to relize four functions: 
(1). Choose a cryptogram to solve;
(2). Solve the cryptograms;
(3). See previously solved crypograms;
(4). View the list of player ratings. In the UML, these four parts is defined as **chooseCryp**, **sloveCryp**,**seeSlovedCrup** and **viewRatingList**. 
2. The function **choose a cryptogram to solve** can be relized by visiting **ExternalWebService** to get the current cryptogram and then choose one. 
3. The function **view the list of player rating** is also can be relized by visiting **ExternalWebService** to get the current rating, in **ExternalWebService**, it is defined as **requestCurrPlayration**.
4. The **administrate** class is used to relize the administrate function. The details of the function includes: (1) add new cryptograms; (2) add new local players. And these two functins was defined as **addNewcrytogram** and **addNewPlayer**. 
5. in order to achieve the function for *external web service utility*, we design the **ExternalWebService** class. This class includes the following four functions: (1). Send updated player ratings; (2). Send new cryptograms and receive a unique identifier for them; (3). Request a list of additional cryptograms; (4). Request the current player ratings. In the class, these four functions are defined as **sendPlayRating**, **sendNewCryp**, **requestAddCrup** and **requestCurrPlayration**. Besides, the sort is used to relize the rate for players.
6. In this game, there should be a database to store the ralative data such as the informaiton of plays and the information of administrator. 
7. In order to achieve the cryptogram function, we design the **cryptogram** class. In this class, there are four members: (1). **bSolved**: this function is used to mark whether it has been solved;(2).**NumberIncorrectcSol**:this is used to mark the number of current incorrect solution.
(3).**solutionPhase**:solution; (4).**matchedPrase**:matching encoded phrase.