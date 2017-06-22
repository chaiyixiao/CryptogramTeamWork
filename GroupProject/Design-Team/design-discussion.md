# Individual Designs

## Design 1 (xliang70)

![Screen Shot 2017-06-22 at 10.39.46 A](media/Screen%20Shot%202017-06-22%20at%2010.39.46%20AM.png)

There are several good aspects in this design.

The UML diagram is very simple and concise capturing the requirements in detail. It shows the relationships between the entities as specified by the requirements in a detailed manner. Every attribute that has been mentioned in the requirement has been added at the appropriate entity, so that the solution can be implemented without any gaps.

Here are some of the improvements that can be made:

Classes can be created in a hierarchical manner if it can be abstracted out into a common class. Distinct classes can be used for User, player and administrator. A cryptogram can be solved by all players, and this data needs to be captured for every player. Association classes has to be used to capture this data. 

Even though, there are minor enhancements that can be made, this is a very good design.

## Design 2 (lfan42)
![Screen Shot 2017-06-22 at 10.39.14 A](media/Screen%20Shot%202017-06-22%20at%2010.39.14%20AM.png)

**Pros:**
On the whole, it is a very straightforward and precise design. It covers almost every requirement(attribute and operation) from the instructions. The relationships between classes make good sense. The quantitative relations between classes are also described clearly.

**Cons:**
There are also some improvements can be made. First, Duplicate *ExternalWebService* can be reduced to one instance. Secondly, an extra utility class *CentralServer* can be used to assign identifiers to cryptograms, keeping the responsibility of *ExternalWebService* simple. Likewise, a *LocalServerWithStorage* can be store local data and used for user rankings calculation. Besides, similar to the Library System in lecture video, *PlayerCryptogram* can be an association class.

## Design 3


## Design 4


# Team Design


# Summary


