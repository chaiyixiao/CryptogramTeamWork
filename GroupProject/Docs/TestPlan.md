# Test Plan

*This is the template for your test plan. The parts in italics are concise explanations of what should go in the corresponding sections and should not appear in the final document.*

**Author**: Team 19

This test plan is created for the Cryptogram application developed for Boston Towers. The primary goal of the application is to keep the kids busy by solving cryptograms and comparing their progress. 

## 1 Testing Strategy

### 1.1 Overall strategy

*This section should provide details about your unit-, integration-, system-, and regression-testing strategies. In particular, it should discuss which activities you will perform as part of your testing process, and who will perform such activities.*

We need to design, develop, test and deliver this project in a short time frame. Since we are a gang of four, we have limited resources. Given this, we would like to automate most of the test cases, so that we have a good coverage.
 
Unit Tests - Every individual developers will run an Unit test on their module to ensure that it is fully functional in all respects. Every defect that is identified and fixed in this stage will help us save a of wasted efforts in the future. Creating a cryptogram and verifying, if the cryptogram is working as desired on a handheld device is a very time consuming process. We will create a set of test cases that will be automated to ensure that the cryptogram functionality is good. The developer responsible for cryptogram verification will carry out the task.  

Integration Tests - Once every parts of the application is unit tested, every one will work together on the integration tests. These tests will ensure that all the modules are able to work with each other and successfully deliver the functionality.  

System - We will designate one person as the Test lead. This person will run the functional tests to  ensure that the product meets all the necessary requirements.

Regression - After the code is complete, during the testing phase, defects may be found. Whenever these defects are fixed, it is possible that the related areas were affected. To ensure that the fix did not introduce any new defects, we need to test the areas related to the fix. This will be typically carried out by the same individual who reported the trouble. He will use the input from the person who fixed it, to gain an understanding of the fix to narrow down the areas to test.

### 1.2 Test Selection

*Here you should discuss how you are going to select your test cases, that is, which black-box and/or white-box techniques you will use. If you plan to use different techniques at different testing levels (e.g., unit and system), you should clarify that.*

### 1.3 Adequacy Criterion

*Define how you are going to assess the quality of your test cases. Typically, this involves some form of functional or structural coverage. If you plan to use different techniques at different testing levels (e.g., unit and system), you should clarify that.*

### 1.4 Bug Tracking

*Describe how bugs and enhancement requests will be tracked.*

### 1.5 Technology

*Describe any testing technology you intend to use or build (e.g., JUnit, Selenium).*

## 2 Test Cases

*This section should be the core of this document. You should provide a table of test cases, one per row. For each test case, the table should provide its purpose, the steps necessary to perform the test, the expected result, the actual result (to be filled later), pass/fail information (to be filled later), and any additional information you think is relevant.*

Table title:

| TestCaseId | Requirement No | Scenario                          | Steps                                                                                                                                                                                                                                 | Expected Outcome                                                                                                                                                              | Actual Outcome | Result |
|------------|----------------|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|--------|
| TC-1       | 1              | Login as Administrator            | At the login screen, after entering the correct credentials, click the "Login as Administrator" button.                                                                                                                               | User is logged in as an Administrator                                                                                                                                         |                |        |
| TC-2       | 2              | Provide options for Player        | At the login screen, after entering the correct credentials, click the "Login" button.                                                                                                                                                | You must see 4 choices as specified below.(1) choose a cryptogram to solve, (2) solve cryptograms, (3) see previously solved cryptograms (4) view the list of player ratings. |                |        |
| TC-3       | 3              | Provide options for Administrator | At the login screen, after entering the correct credentials, click the "Login as Administrator" button.                                                                                                                               | You must see 2 choices. (1) add new cryptograms, and (2) add new local players                                                                                                |                |        |
| TC-4       | 4              | Persist data across multiple runs | 1. At the login screen, after entering the correct credentials for Player A, click the "Login" button.2. Choose and solve a cryptogram. 3. Log out. 4. Restart the device. 5. Login as Player A 6. See previously solved cryptograms. | The cryptogram that was solved before restarting must be available now, in the "Solved Cryptograms" list.                                                                     |                |        |
| TC-5       | 5              | Share Updated Player Ratings      |                                                                                                                                                                                                                                       |                                                                                                                                                                               |                |        |
| TC-6       | 5              |                                   |                                                                                                                                                                                                                                       |                                                                                                                                                                               |                |        |
