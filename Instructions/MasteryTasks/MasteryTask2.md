Mastery Task 2: Beyond Bobby McFerrin
=

Bobby McFerrin is famous for _Don't Worry, Be Happy_, a song in which he made _all_ the sounds with his voice and body. Apparently, the QA team never heard of it, or else they just don't share his carefree outlook: they aren't content that the code _collectively_ passes the unit test coverage threshold in the pipeline, they want _every class_ to pass.

You will improve coverage on the OrderDao, ensuring quality both now _and_ in the future.

Milestone 1: Make a Plan
------------------------

Plan what you will test in the OrderDao. Inside of your codes `src/resources/` you will find a file `OrderDaoTestPlan.md`. This is a template on how to write test plans. The text file uses "Markdown" syntax, which is a way to format text for display as HTML without a lot of intrusive HTML tags. Markdown is handled in IntelliJ and many online websites; if this is your first encounter, [the wiki has a short guide](https://w.amazon.com/index.php/Markdown "https://w.amazon.com/index.php/Markdown").

Milestone 2: Execute the Plan
-----------------------------

Implement the planned unit tests you made in OrderDaoTestPlan.md. Make sure they all pass. Commit your changes.

All of the following test commands should pass:

`./gradlew -q clean :test --tests "com.amazon.ata.deliveringonourpromise.dao.*"`  
`./gradlew -q clean IRT  
``./gradlew -q clean PreparednessTaskTwoTests`

In addition, you should check your code coverage report (`build/jacocoHtml/index.html`) and make sure the OrderDoa has at least 80% test coverage.

Exit Checklist
--------------

*   You created an OrderDao test plan
*   You implemented the unit tests in your plan
*   Your code coverage is above 80%
*   Your Mastery Task 3 TCTs are passing locally
*   You have pushed your code
*   Mastery Task 3's TCTs are passing on CodeGrade
