Mastery Task 1: Grace Under Pressure - Milestone 2-4
=

A CS representative has filed a bug report, stating that when they request the promise history for order ID `111-749023-7630574`, the Missed Promise CLI prints a weird message and exits:

Running CLI! Please enter the orderId you would like to view the Promise History for. > 111-749023-7630574 Error encountered. Exiting. Thank you for using the Promise History CLI. Have a great day!

Milestone 2: Be Kind to “Future You”
------------------------------------

Write a unit test (with a descriptive name!) that fails whenever the bug is triggered. If "Future You" accidentally re-introduces this bug, the unit test will fail and you'll know you have to fix something before you can CR.

*   **Which order IDs should I use in my tests?**  
    You _could_ try a bunch of different order IDs until you find an order that's suitable for your test. However, most order IDs are not guaranteed to be consistent, and therefore aren't suitable for testing.
    
    To get a list of orders that _are_ guaranteed to never change, run `java -jar cli.jar --show-fixtures`. This prints the known consistent orders, along with a description of their attributes, before proceeding to the CLI.
    

Milestone 3: _Grace Hopper_ That Bug
------------------------------------

Fix the bug! Then run your code and verify that your new unit test passes.

Milestone 4: Commit It
----------------------

`./gradlew -q clean :test`

`./gradlew -q clean IRT`

`./gradlew -q clean MasteryTaskOneTests`

If you wish to run all the Task Completion Tests, the command is:

`./gradlew -q clean TCT`

When all tests run successfully, commit and push your code!

Exit Checklist
--------------

*   You used the debugger to walk through the code (whether you used it to actually find the bug or not--if you see the bug, still practice with the debugger!)
*   You added a new unit test that catches the original bug
*   You fixed the bug
*   Your Mastery Task 1 TCTs are passing locally
*   You have pushed your code
*   Mastery Task 1's TCTs are passing on CodeGrade
