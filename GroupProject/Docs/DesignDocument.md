# Design Document

**Author**: Team 19

## 1 Design Considerations

*The subsections below describe the issues that need to be addressed or resolved prior to or while completing the design, as well as issues that may influence the design process.*

### 1.1 Assumptions

*Describe any assumption, background, or dependencies of the software, its use, the operational environment, or significant project issues.*

- A **central server** will be available for 24 hours 7 days.
- Android phones satisfying the minimum operating system requirement **(Android 4.4)** will be available to run the application.
- Suitable **external libraries** can be used for communication between phones and the central server.
- **Internet access** will be available when specific features require data exchange between phones and the central server.
- The users will have to grant **storage permission** to the system before they can use it.
- **User authentication** will **not** be available. Only the unique user names will be used for login. Game progress will be fetched according to the logged user name.
- No loss will be compensated due to **disclosure of user names** to third parties.
- There will be **only one administrator**. Only normal players can be created. No other users with administration privileges can be created.
- The administrator **must not** include any **explicit content** in neither user names nor cryptograms (encoded phrases or solution phrases), as **No filtering feature** will be available.
- Parent control feature will not be available. The application will not prevent non-adults from using it in inappropriate time.
- **Temporary game process lost** due to forced crash will **not** be recovered.

### 1.2 Constraints

*Describe any constraints on the system that have a significant impact on the design of the system.*

- The system will be developed using **Java programming language**, **standard Android API**, and **free open source libraries**.
- The system must run on an Android phone with **Android 4.4 and higher** version OS installed.
- The system UI language will be shown in **English only**. The cryptograms will only have English phrases.
- As limited budget, the system will only be tested on **Android Virtual Devices** (AVDs) created in Android Studio.
- As limited time, the functionality of **filtering explicit content** in cryptograms will **not** be available.

### 1.3 System Environment

*Describe the hardware and software that the system must operate in and interact with.*

- **Operating System**: Android 4.4 and higher (Android Watch and Android TV are not supported)
- **CPU**: 1.2 GHz and higher
- **RAM**: 1.0 GB and larger
- **Internet access** through cellular or WLAN is available when necessary.


## 2 Architectural Design

### 2.1 Component Diagram
![ComponentDiagram](images/ComponentDiagram.png)

### 2.2 Deployment Diagram

![Deployment Diagram](images/Deployment%20Diagram.png)

## 3 Low-Level Design

### 3.1 Class Diagram
![design-team](images/Class%20Diagram.png)


### 3.2 Other Diagrams

![Add Cryptogram Sequence Diagram](images/Add%20Cryptogram%20Sequence%20Diagram.jpg)


## 4 User Interface Design
*For GUI-based systems, this section should provide the specific format/layout of the user interface of the system (e.g., in the form of graphical mockups).*

