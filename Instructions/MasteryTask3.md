Mastery Task 3: Class-ified Information
=

You have discovered a task on the backlog indicating that the `Order` class is not properly encapsulated. Demonstrating _Ownership_ and _Bias for Action_, you pick up the task.

Milestone 1: Security Survey
----------------------------

Create a test file called OrderTest at com.amazon.ata.deliveringonourpromise.types.

Write a unit test that fails if the `Order` class has any externally modifiable variables.

You don't write any unit tests that try to reassign variables declared `private` directly (the compiler will prevent you!).

Focus your efforts on writing test(s) that try to modify objects that your class might expose, either by accepting the object as an argument or by returning the object as a method return value. Focus on the object(s) that is/are _mutable_, meaning you can modify the object the variable points to in some way without reassigning the variable itself. (Think of objects that contain other objects).

Milestone 2: Fortify
--------------------

Encapsulate the `Order` class.

Each test should be run separately using the following commands - one command per test:
```
./gradlew -q clean :test --tests "com.amazon.ata.deliveringonourpromise.types.\*"

./gradlew -q clean IRT

./gradlew -q clean MasteryTaskThreeTests
```

Exit Checklist
--------------

*   You have written unit tests that verify encapsulation of the `Order` class
*   Your Mastery Task 3 TCTs are passing locally
*   You have pushed your code
*   Mastery Task 3's TCTs are passing in CodeGrade
