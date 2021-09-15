Mastery Task 4: Inevitable
=

Change is inevitable, according to famous British Prime Minister [Benjamin Disraeli](https://en.wikipedia.org/wiki/Benjamin_Disraeli), and the important question is how we carry it out. It's time to bring the big change to the Missing Promise CLI.

The service already has a flow to retrieve the `Promise` made to a customer at checkout by calling the `DeliveryPromiseService`. We know we can improve it by getting the `Promise` at the time of packaging from `OrderFulfillmentService` (OFS). Knowing that change is inevitable, we anticipate that more improvements will be required in the future: maybe promises from shipping companies, from drone dispatchers, from Prime Now, from Fresh... who knows?

Milestone 1
-----------

The current design only supports one client returning a promise. Redesign the `PromiseDao` to support multiple clients, including the OFS client we know about and future clients we can't anticipate right now.

Find the `PromiseDaoDesignReview` template to your `src/resources`. Answer each of the questions to help you better grasp how the code should change. 

After the updating that document, create a file named `mastery-task4.txt` in `src/resources` folder of the project.

Milestone 2
-----------

Implement your `OrderFulfillmentServiceClient` class to get the packaging promise from the `OrderFulfillmentService`.

Create the new `OrderFulfillmentServiceClient` class, inside a new Java package, `com.amazon.ata.deliveringonourpromise.orderfulfillmentservice`, following the pattern of the other service clients.

Before you write unit tests for it, consider how error-prone code would be if every class that needed your client instantiated a new one -- especially if the client had a lot of configuration options. It would be much more robust to build a single, correct client when the program started, then _provide_ that client to every class that needed it. This is known as "Dependency Injection", and it's similar to what our `App` class does.

Add a method in the `App` class to create your client. Write unit tests that get their `OrderFulfillmentServiceClient` by calling the `App` method, instead of calling a constructor directly.

You will find that `Promise` objects are populated using a bunch of concatenated `with***(value)` calls, followed by one `build()`. This is an application of the "Builder" pattern for constructing objects with many fields. Use the existing `DeliveryPromiseServiceClient` as a reference to implement the OFS client similarly.

Milestone 3
-----------

Update the `PromiseDao` to match your design and retrieve the packaging promise from your `OrderFulfillmentServiceClient`. It should also use the `App` class to get its clients, rather than instantiating them itself. Make sure the existing unit tests continue to pass.

Each of the following tests should be run separately and pass using the following commands - one command per test:

  
```
./gradlew -q clean :test

./gradlew -q clean IRT

./gradlew -q clean MasteryTaskFourTests
```

Exit Checklist
--------------

*   You created `mastery-task4.txt` in the `src/resources` folder of the project
*   You implemented `OrderFulfillmentServiceClient`
*   You updated the PromiseDao to retrieve the packaging promise from OFS
*   Your Mastery Task 4 TCTs are passing locally
*   You have pushed your code
*   Mastery Task 4's TCTs are passing in CodeGrade
