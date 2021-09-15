Preparedness Task 1: Ready, Set, Code!
=================================
Step zero to making a code change is always obtaining the code locally. Once you pull down the code, you should always ensure the service works as expected. This will give you confidence that the service is set up properly. If you dive right into editing code and then encounter an issue, you won't be able to be sure if the issue is caused by your change or the code not being set up locally correctly. We'll walk you through how to do this below, after we introduce you to the main characters.

This task has no related Task Completion Tests (more on TCTs in Milestone 1). There is no test to run locally or that is executed that corresponds to this task.

Before beginning the Sprint Challenge and its related Preparedness Tasks, please take a moment to read about the Sprint Challenge found in [Sprint Assessment: Sprint Project](https://lambdaschool.instructure.com/courses/1097/pages/sprint-project "Sprint Project"). The three Sprint Challenges for this Unit do build on each other so a thorough understanding of the project is critical to success!

Milestone 1: The Artifacts
--------------------------

Let's take a look at what artifacts exist for this project:

There are six types of automated checks your project must pass for approval:

1.  **Style Checks** \- These validate that your conforms to establish coding styles and practices. You must following the coding standards presented in the course in order for these Style Checks to pass and for your project to succeed.
    
2.  **Coding Checks** \- Besides style, certain programming constructs can introduce bugs into your code. This process will check for over 400 bug patterns that cause problems.
    
3.  **User Tests** \- These are test you develop specifically for your application. These are tests you will be developing on the job as well!
    
4.  **Code Coverage Check** \- This validates that your code has the appropriate amount of testing based off the user tests from the previous step. All user tests must pass before test code coverage can be calculated. This checks to make sure that a certain percentage of all lines of code in your project have tests to cover them. Don't worry about this too much right now. This will make more sense after the lesson on Unit Testing, but know that if it fails, it probably means you need to write more unit tests. That will also be part of Mastery Task 3.
    
5.  **Integration Regression Tests** \- These tests are specific for Lambda School's Grading Policy. These are tests that verify no existing code in the project breaks. These tests should all be passing now and they should always pass when you make changes to the project. If these tests start failing, it probably means that your change had unintended consequences and accidentally changed existing features. If these tests start to fail, you should run them locally to see which integration regression tests are now failing and get some information that should help you figure out why they are failing (more on how to do that in milestone four).
    
6.  **Task Completion Tests** \- These tests are specific for Lambda School's Grading Policy. These are the tests that verify you are successfully completing the project tasks. They are represented as multiple checks in the workflow - one for each of the project mastery tasks and a couple for the project preparedness tasks. Currently all of these tests should be failing, since you haven't yet completed any of the project tasks. Each time you finish a task, commit your code, and push your solution, you should view these checks and ensure that the one corresponding to the task you just completed is now succeeding. If a test you were expecting to start passing isn't, you should run the test locally to get more information (more on that in milestone four). Once you have completed the project, all of the task tests should succeed.
    

Milestone 2: Setup Your Local Workspace
---------------------------------------

### Project Setup

*    Fork and clone this repository.  [https://github.com/LambdaSchool/ebd-unit2-sprint1-challenge-DeliveringOnOurPromises.git](https://github.com/LambdaSchool/ebd-unit2-sprint1-challenge-DeliveringOnOurPromises.git)
*    Create a new working branch: git checkout -b `<firstName-lastName>`.
*    Implement the project on your newly created `<firstName-lastName>` branch, committing changes regularly.
*    Push commits: git push origin `<firstName-lastName>`.
*    From the root folder of the newly cloned project, issue the following commands: `Bash gradle wrapper ./gradlew clean build`

### CodeGrade Setup

*    Open the assignment in Canvas and following along with the attached document [here.](https://www.notion.so/lambdaschool/Submitting-an-assignment-via-Code-Grade-A-Step-by-Step-Walkthrough-07bd65f5f8364e709ecb5064735ce374 "https://www.notion.so/lambdaschool/Submitting-an-assignment-via-Code-Grade-A-Step-by-Step-Walkthrough-07bd65f5f8364e709ecb5064735ce374").
*    Follow instructions to set up Codegrade's Webhook and Deploy Key, making sure your deployment is set to your `<firstName-lastName>`.
*    Push your first commit: `git commit --allow-empty -m "first commit" && git push`
*    Check to see that Codegrade has accepted your git submission.

Milestone 3: Interaction with the Missed Promise CLI
----------------------------------------------------

When a user starts up the Missed Promise CLI, an interactive terminal session will begin. The CLI will ask the user for an order ID and will then return information about the order and the promise history of the order. The following example shows what the CLI interaction will look like _**at the end**_ of building your project. A sample interaction with the CLI (where the app is waiting for user response are lines beginning with a `>`):

Please enter the orderId you would like to view the Promise History for.

\> 111-7497023-2960775

 -------------------------------------------------------------------------------------------------------------

| ORDER DATE          | ORDER ID            | MARKETPLACE | TIMEZONE | CONDITION  | SHIP OPTION | CUSTOMER ID |

 =============================================================================================================

| 2018-07-16T15:04:11 | 111-7497023-2960775 | 1 - US      | UTC      | 4 - Closed | second      | 375944434   |

 -------------------------------------------------------------------------------------------------------------

 ------------------------------------------------------------------------------------------------------------------------------------------------------------

| EFFECTIVE DATE      | ASIN       | ITEM ID        | ACTIVE | PROMISED SHIP DATE  | PROMISED DELIVERY DATE | DELIVERY DATE       | PROVIDED BY | CONFIDENCE |

 ============================================================================================================================================================

| 2018-07-16T16:04:11 | B01MZEEFNX | 20655079937755 | N      | 2018-07-17T22:04:11 | 2018-07-18T16:04:11    | 2018-07-21T06:04:11 | DPS         | -94        |

 ----------------------------------------------------------- ------------------------------------------------------------------------------------------------

Would you like to enter another orderId? (y/n)

\> n

*   About date/time formats: `ZonedDateTime` uses the [ISO-8601 standard for date/time representation](https://en.wikipedia.org/wiki/ISO_8601#Combined_date_and_time_representations "https://en.wikipedia.org/wiki/ISO_8601#Combined_date_and_time_representations") by default, as shown in the ORDER DATE column. It separates the date from the time with a 'T', as in "2018-07-16T15:04:11". We specify time zones as an offset from UTC (Coordinated Universal Time, or Universal Time Coordinated), the primary time standard, to eliminate the ambiguities of other time zones. When interacting with customers, we print times in their locale; for instance, we would print "2018-07-16T07:04:11 UTC-08:00" to specify 7:04 AM in Pacific Standard Time. [DateTimeFormatter](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html "https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html") allows you to use alternate date time formats, including custom ones (don't change it in the CLI though, as it'll likely break some unit tests!).

Try running a completed CLI so you can play with it! Run this command from your project's root directory to invoke the CLI:

`java -jar cli.jar`

(NOTE: the first time you run, it'll take a few minutes, be patient).

When it asks for an orderId, enter:

900-3746402-0000002

You should see the following output:

\------------------------------------------------------------------------------------------------------------------

| ORDER DATE        | ORDER ID            | MARKETPLACE | TIMEZONE  | CONDITION      | SHIP OPTION | CUSTOMER ID   |

\================================================================================================================== |

2019-06-03T04:20:18 | 900-3746402-0000002 | 1 - US      | UTC-08:00 | 3 - Authorized | second      | 375944469     |

\------------------------------------------------------------------------------------------------------------------

\------------------------------------------------------------------------------------------------------------------------------------------------------

| EFFECTIVE DATE      | ASIN       | ITEM ID        | ACTIVE | PROMISED SHIP DATE  | PROMISED DELIVERY DATE | DELIVERY DATE | PROVIDED BY | CONFIDENCE | ======================================================================================================================================================

| 2019-06-03T05:20:18 | B07MVQL5RT | 20655079937950 | Y      | 2019-06-04T11:20:18 | 2019-06-05T05:20:18    |               | DPS         | 72         |

\------------------------------------------------------------------------------------------------------------------------------------------------------

Your cli should now be prompting for another orderId.

Would you like to enter another orderId? (y/n) >

Answer `y` and provide:

900-3746401-0000003

There is an assignment in Canvas called, "Ready, Set, Code!" Submit the "PROMISED DELIVERY DATE" for this orderId.

Milestone 4: Run the Tests
--------------------------

But there is more that you can do than just run the cli! Let's run the available tests!

1.  **Style Checks**
    Checkstyle is a code analysis tool for checking if Java source code complies with coding rules. We have a special set of Amazon inspired Checkstyle rules we'll be using throughout the course, though they will evolve and change as we move from unit to unit. These checks range from stylistic items like not having a space before a curly braces to code structure like having four or more if statements nested inside each other. The formatter you set up in IntelliJ should help your code comply with the stylistic ATA Checkstyle rules. Unfortunately, it may miss some of the stylistic rules and it can't help at all with the code structure rules. If you do happen to fail a Checkstyle rule, no worries! Checkstyle will point out to you in which class and on which line the error is occurring. You may see something like:  
      
    \[ant:checkstyle\] \[ERROR\] <folder location on your local computer>/DeliveringOnOurPromise/src/com/amazon/ata/deliveringonourpromise/Shell.java:71:9: '{' at column 9 should be on the previous line. \[LeftCurly\]  
    \[ant:checkstyle\] \[ERROR\] <folder location on your local computer>/DeliveringOnOurPromise/src/com/amazon/ata/deliveringonourpromise/dao/ReadOnlyDao.java:6:1: Type Javadoc comment is missing @param \<I\> tag. \[JavadocType\]  
    
    You can do a web search for Checkstyle and the rule that is in brackets at the end, e.g. "LeftCurly" in this example, to learn more about the particular Checkstyle rule you are breaking. (Here, we've got the opening curly bracket on it own line.)
    
    To run the Checkstyle tool on your code, from your project's root directory, issue the following command
    
    `./gradlew clean :checkstyleMain`
    
    To see an HTML formatted report of your errors, open the file `<folder location on your local computer>/DeliveringOnOurPromise/build/reports/checkstyle/main.html`
    
2.  **Code Checks**
    
    SpotBugs is a program which uses static analysis to look for bugs in Java code. SpotBugs checks for more than 400 bug patterns. Bug descriptions can be found [here](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html "https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html").
    
    To run the SpotBugs tool on your code, from your project's root directory, issue the following command
    
    `./gradlew clean :spotbugsMain`
    
    To see an HTML formatted report of your errors, open the file `<folder location on your local computer>/DeliveringOnOurPromise/build/reports/spotbugs/main.html`
    
3.  **User Tests**
    
    These are unit and integration tests written for you to automatically check that your code preforms correctly. To run these user provided tests
    
    `./gradlew clean :test`
    
    You will see a report on your screen about whether the tests passed or not and if the test failed, a description on what might be wrong. You will also receive a summary of how many tests run, passed, failed, or where skipped. This will look similar to the following
    
    \----------------------------------------------------------------------
    
    | Results: SUCCESS (68 tests, 68 successes, 0 failures, 0 skipped)   |
    
    \----------------------------------------------------------------------
    
    Exit Result for Scoring: 1
    
    You can find a nicely formatted HTML report at `<folder location on your local computer>/DeliveringOnOurPromise/build/reports/test/index.html`.
    
4.  **Code Coverage Check**
    
    Using a tool called JaCoCo, how much of your code is covered, tested, by your automated unit tests is determined. We have a set minimum of how much code must be tested before moving to the next milestone. For now, let's just check our current coverage. Don't worry; the number will be really low and will fail the test. This will be fixed are you progress through the project.
    
    To run the JaCoCo tool on your code, from your project's root directory, issue the following command
    
    `./gradlew build :jacocoTestCoverageVerification`
    
    You get back `BUILD SUCCESSFUL` or `BUILD FAILED` depending on if your test coverage is enough according to JaCoCo.
    
5.  **Integration Regression Tests**
    
    The tests verify the integrity of your project. They should always pass. If they do not, a major part of your project has been changed with dire results. Fix it now! These IRTs are actually located in a separate application found on your computer. The application was saved on your computer when you clone the initial repository.
    
    To run the integration regression tests on your code, from your project's root directory, issue the following command
    
    `./gradlew -q clean IRT`
    
    These should ALWAYS pass. You will get a report of all the test results and a summary that tells you how many tests were run, how many tests were successful, failed, or skipped. So something like the following:
    
    \----------------------------------------------------------------------
    
    | Results: SUCCESS (10 tests, 10 successes, 0 failures, 0 skipped)   |
    
    \----------------------------------------------------------------------
    
    You can find a nicely formatted HTML report at `<folder location on your local computer>/DeliveringOnOurPromise/build/reports/test/IRT/index.html`.
    
6.  **Task Completion Tests**
    
    The standard testing library for Java is JUnit 5. We will be using this library throughout the course. We are also using the testing framework TestNG. These are the test that will determine if the work you have done on your project is correct or not. These are similar to the unit tests that will be discussed throughout the course. You can run these tests on your current code. However, realize that the tests will fail because you have not written any code yet! As you write more code to solve the mastery tasks, the tests related to that master task should pass. Don't worry. As we go along we will be very clear about which tests should be passing!
    
    To run the task completion tests on your code, from your project's root directory, issue the following command
    
    `./gradlew -q clean TCT`
    
    You will see a report of all the test results and a summary of what happened. This time you have failing tests. For right now that is GOOD!
    
    You can find a nicely formatted HTML report at `<folder location on your local computer>/DeliveringOnOurPromise/build/reports/test/TCT/index.html`. Open that HTML file.
    
    In that file, look for the test `preparednessTaskTwo_preparednessTaskTwoFile_existsAndItIsNotEmpty` and follow its link. For the final question in the "Ready, Set, Code!" assignment, submit the error message you receive following `java.lang.IllegalArgumentException:`. You will need this test's result for PreparednessTask2!
    

Milestone 5: Debugging the CLI in IntelliJ
------------------------------------------

You are able to run the CLI directly in your workspace/IntelliJ without doing any deploying. So when you are doing your own testing, go ahead and invoke `Shell`'s `main()` method within IntelliJ. You can click on the green triangle next to the declaration of the `main()` method to and select "Run".

When you want to **debug**, instead of selecting "Run", select "Debug". Remember that in order to start stepping through code, you will need to set a breakpoint in your code (before clicking "Debug"!). If the execution ends up reaching the line where you have placed the breakpoint, then execution will halt, ready for you to start stepping through code.

### Try it out!

1.  Set a breakpoint on the first line inside the `main()` method
2.  Start `main()` in debug mode and make sure the code stops at your breakpoint
3.  Step into/over a few lines of code
4.  When you're done, click the green arrow in the debugger to "Resume Program". The code should continue running (note: it might be waiting for you to enter input inside the Console tab!)

If you run into any problems, ask in Support Hours / Office Hours

Exit Checklist
--------------

*   You read the above introduction to the project and its infrastructure
*   You have created a workspace for your project and imported your project package
*   You connected the project to CodeGrade
*   You were able to run the Missed Promise CLI
*   You ran the Preparedness Task 2 TCT tests (and they failed)
*   You were able to run the CLI in debug mode, and step through a few lines of code
