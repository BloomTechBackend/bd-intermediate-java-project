Preparedness Task 3: Understanding the Current Design
=====================================================

Let's explore the current design of this project through diagrams.

Milestone 1: Architecture Diagram
---------------------------------

The Missed Promise CLI accepts an order ID from the CS representative and returns the order's information and promise history. Right now, the Missed Promise CLI will call the OrderingManipulationAuthority to get order information and the DeliveryPromiseService to get the Order Promise at the time of checkout.

**One of your tasks will be to update the Missed Promise CLI so that it gets the Order Promise at the time of shipping from OrderFulfillmentService.**

![ProjectArchitecture.png](https://lambdaschool.instructure.com/courses/1097/files/342074/preview)

(This document will use these abbreviations going forward)

**Missed Promise CLI**: The command line interface to the CS rep that you'll be updating.

**Delivery Promise Service (DPS)**: Service providing the delivery promise made to the customer at checkout. The Missed Promise CLI already uses the GetDeliveryPromise API to fetch the checkout delivery promise.

**Order Fulfillment Service (OFS)**: Service providing the delivery promise made at the time of packaging the order in a fulfillment center. You will use the GetFulfillmentPromise API to retrieve the promise(s) at the time of shipping. See the Appendix for a sample OFS Fulfillment Promise Result.

**Ordering Manipulation Authority (OMA)**: Service that creates and stores customer's orders. The Missed Promise CLI already uses the GetCustomerOrderByOrderId API to fetch a full order object by order ID. See the Appendix in the README file for a sample Order and OrderItem.

Use this diagram to answer the first questions in the "Delivering on Our Promise Diagram" quiz in Canvas. Try to answer the questions by just looking at this diagram and not looking at the code yet.

Milestone 2: Learn about Sequence Diagrams
------------------------------------------

Sequence diagrams help us to understand and discuss processes. Watch this video: [how to make a UML sequence diagram](https://www.youtube.com/watch?v=pCK6prSq8aw "https://www.youtube.com/watch?v=pCK6prSq8aw") 

You do not need to create a LucidChart account. (9 mins)

Milestone 3: The Delivering on our Promise Sequence Diagram
-----------------------------------------------------------

There are two components to this application:

1.  The **interactive shell** that receives and presents information to the user
2.  **Get Promise History By Order ID API**: The back-end logic to collect the order's promise history from an order ID

### 1\. Interactive Shell

The interactive shell begins in the `main()` method inside the `Shell` class. A `main()` method is where Java begins executing an application; it's the entry-point to the program. The method declaration for main is a special incantation; it must be written: `public static void main(String[] args)`. For now you don't need to understand all of the pieces of this declaration, just understand that `main()` is always written in this way. If you open the `Shell` class in IntelliJ, you'll notice a special feature of `main()` methods: in the margin with the line numbers to the left of your code, you'll see a green “play” button. You can use this to run a main method directly within IntelliJ. Build tools can also be configured to run main methods; we often use the gradle wrapper (gradlew) to execute our applications.

The main method starts the `Shell` class, which interacts directly with the user, forwarding calls to the API you'll be working on. The `Shell` class collects the order ID from the user through the terminal, calls the API to get the order's promise history, and displays it on the screen. It repeats this until the user is done, at which point the program will terminate. The `Shell` class is implemented for you; you should not need to modify it. You will modify the API that it calls to fetch the order history.

### 2\. Get Promise History by Order Id API

You will be modifying this API to provide the interactive shell with the OrderPromiseHistory. This sequence diagram shows the current interaction between the relevant classes.

![OverviewSD.png](https://lambdaschool.instructure.com/courses/1097/files/342073/preview)

The “front end” requests data from the “back end” and reformats it to be useful to a CS rep. While you may find some of the front end code educational, most of your tasks will involve understanding and enhancing the back end code.

The back end makes calls to the dependencies to assemble the necessary data. Right now, when the front end makes a request, it comes to the `GetPromiseHistoryByOrderIdActivity`, which proceeds to:

1.  Use the `OrderDao` to get the full `Order` information from OMA, including the list of items that were ordered. Items within an order may be shipped separately and thus have different delivery promises.
2.  Use the `PromiseDao` to retrieve the order's shipment dates from OMA, and delivery promises from DPS.
3.  Returns the promises to `Shell` for display to the user.

You'll be modifying the back end to include shipping promises from OFA.

See the Sprint Project for sample [PromiseHistory and Promise objects](https://lambdaschool.instructure.com/courses/1097/pages/sprint-project#promise_history "Sprint Project").

Use this sequence diagram to answer the questions in the “Delivering on Our Promise Sequence Diagram” quiz in Canvas. Try to answer the questions by just looking at this diagram and not looking at the code yet.

Once you have completed the quiz, create a file called `preparedness-task3.txt` in `src/resources`. Put a link in this file to the Canvas quiz. Now test your progress

Each test should be run separately using the following commands - one command per test:

    `./gradlew -q clean :test `

If you wish to run all the Task Completion Tests, the command is:

    `./gradlew -q clean TCT`

Exit Checklist
--------------

*   You completed the "Delivering on Our Promise Diagram" quiz in Canvas and submitted it
*   You understand the correct answers to all of the questions in the quiz (including those you initially missed)
*   Your Preparedness Task 3TCTs are passing locally
*   You pushed your code
*   Preparedness Task 3's TCTs are passing on CodeGrade
