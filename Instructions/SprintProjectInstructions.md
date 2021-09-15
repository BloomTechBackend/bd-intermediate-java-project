# Sprint Challenge: Unit 2

**_Read these instructions carefully. Understand exactly what is expected before starting this Sprint Challenge_**

This challenge allows you to practice the concepts and techniques learned during this sprint and apply them in a concrete project. This sprint explores

#### Sprint 1

Writing and Testing Functional Requirements
Debugging
Unit Testing
Memory

#### Sprint 2

Encapsulation
Polymorphism and Interfaces
Primitive Wrapper Classes
Using a Generic Type

#### Sprint 3

Lists
Big O Part A
Comparable
Comparators and Using sort()

In your challenge this week, you will demonstrate your mastery of these skills by creating a full Java Application entitled **Delivering on Our Promises.**

This is an individual assessment. All work must be your own. All projects will be submitted to CodeGrade for automated review. 

You are not allowed to collaborate directly on work for this sprint challenge. However, you are encouraged to follow the twenty-minute rule and seek support through the help channels, support hours, and office hours.

Each module that has a mastery task should be completed that same day. As you complete each task, you should commit and push your code to your repository so it can be graded and recorded by CodeGrade. **Assignments that are not pushed to their repository will not be graded. **




## Introduction: The README.md

We'll start by taking a look at the project's `README.md` file using the instructions below. A `README` is a text file within a code package that contains helpful information about the package. It can include instructions on how to get things set up, an explanation of the package structure, how to run things, or just helpful documentation that explains the code. The `README` file uses the `.md` extension. This means the file is markdown, a lightweight markup language with plain text formatting syntax. Here is a great markdown cheatsheet for creating markdown.

The `README` for this project will describe the business problem, the service you will be working on, how to successfully complete the project, and contains the tasks you will be working on.




## Introduction: Delivering on our Promise!

Rose Kidder ordered a soccer ball from Amazon, expecting it would arrive in time for her daughter, Kela's first soccer practice. When she checked out on (amazon.com)[amazon.com], she was promised on her order confirmation page that it would arrive on Thursday. At the dinner table on Thursday, Kela was so excited about starting soccer the next day and asked Rose where the ball was. Rose then realized that her order didn't arrive when she expected it. After Kela headed off to brush her teeth, Rose called Amazon and asked, “Why didn't my package arrive when you said it would?” The Customer Service (CS) representative who answered Rose's call couldn't tell where in the process between ordering and delivery the problem came from that the order delivery fell behind schedule. In fact, she might've been wondering if Rose was making up the story to try to game the system. It led to a frustrating experience for Rose, but she eventually convinced the CS representative that a promise had been broken, and the CS representative refunded Rose's money.

Unfortunately, this scenario is a common question for Amazon CS representatives. One piece of the complexity is that Amazon promises a delivery date up front to deliver customers, but there are several different stages before an order is finally delivered where the order can fall behind schedule. CS representatives want to be able to see where the breakdown was, in order to explain to customers what has happened, as well as to quickly compensate the customer for the broken promise.

When a customer checks out, Amazon makes a promise for when their order will arrive. This is called a **_Contract Promise_**. The customer sees this promise on the Thank You Page, the Order Confirmation Email, and in their Your Orders page.

  

### Examples




#### (Thank You Page) Estimated Delivery: April 11, 2019:







#### (Order Confirmation Email) Arriving: Thursday, April 11:







#### (Orders Page) Arriving Thursday [by 8pm]:







CS representatives are using our team's Missed Promise Command Line Interface (CLI) to verify when Amazon promised the order at checkout, and when the order was actually delivered. They can use this information to verify a customer's claim and then take the appropriate action. This has greatly improved CS's ability to investigate and help customer issues. However, Amazon hasn't been able to diagnose where the issues are occurring. Is Amazon having trouble fulfilling the order or is the carrier unable to deliver in time? Knowing this can help Amazon make more accurate promises to customers in the future.

After investigating further, we have learned that in addition to the promise made to the customer at checkout (by the DeliveryPromiseService), the OrderFulfillmentService also makes a promise when a package is shipped. If everything went well fulfilling the order, the OrderFulfillmentService promise's date will remain the same as the DeliveryPromiseService's original promise's. However, if there was a delay in pulling together the shipment in any way, the new promise will have a new, later date. With this additional data, CS reps can see if Amazon had to alter the promise date due to issues in fulfillment.

Your assignment is to take the initial implementation of the Missed Promise CLI and to provide the full picture of the promises made, fulfilled and broken to the customer. The CLI currently fetches order details from Order Manipulation Authority (OMA), and fetches promises from the Delivery Promise Service (DPS); you will integrate it with the Order Fulfillment Service (OFS), improving the system design along the way.

By the way, this project is based on a real tool (a web version in this case) that the Amazon Fulfillment Exception team currently owns and that CS reps use!

  

## Instructions




### Task 1: Project Setup

- Fork and clone [the project's repository](https://github.com/LambdaSchool/ebd-unit2-sprint1-challenge-DeliveringOnOurPromises.git)
- Create a new working branch: `git checkout -b <firstName-lastName>.`
- Implement the project on your newly created <firstName-lastName> branch, committing changes regularly. 
- Push commits: `git push origin <firstName-lastName>`.
- From the root folder of the newly cloned project, issue the following commands: `bash gradle wrapper ./gradlew clean build`
  



### Task 2: CodeGrade Verification

  - Push your first commit: `git commit --allow-empty -m "first commit" && git push`
  - Check to see that Codegrade has accepted your git submission.




### Project Requirements Set 1: Project Preparedness Tasks

The projects are intended to be worked on throughout the Sprint. You should be able to start these tasks on the first day of each unit, and start applying the lessons you've learned that week. Projects will often start with “project preparedness tasks” to get oriented with the problem domain, do some preliminary design and get comfortable with the existing project code. You might do a little development during these steps, but they're really about making sure you understand what your job is on the project and getting ready to dive into the project mastery tasks within the next week. Project Preparedness Tasks can be completed prior to any module in the Sprint but need to be completed before any Project Mastery Tasks are attempted.

  - Project Preparedness Task 1: Ready, Set, Code!
  - Project Preparedness Task 2: Hello, Project Buddy
  - Project Preparedness Task 3: Understanding the Current Design
  - Project Preparedness Task 4: Document the Classes

### Project Requirements Set 2: Project Mastery Tasks

Complete each task AFTER the listed Module is completed

- Project Mastery Task 1: Grace Under Pressure
  Debugging

- Project Mastery Task 2: Beyond Bobby McFerrin
  Writing and Testing Functional Requirements

- Project Mastery Task 3: Class-ified Information
  Encapsulation

- Project Mastery Task 4: Inevitable
  Interfaces

- Project Mastery Task 5: Devil in the Details
  Lists, Big O Part A, Comparable, and Comparators and Using sort()




### Additional Comments
  - You are welcome to create additional files but **do not move or rename existing files or folders.** Throughout the project you may need to update, edit existing files or folders. This is allowed and encouraged!
  - In your solution, it is essential that you follow best practices and produce clean and professional results. Your coding style is graded against school norms.
  - Schedule time to review, refine, and assess your work and perform basic professional polishing including spell-checking and grammar-checking on your work.
  - It is better to submit a challenge that meets MVP than one that attempts too much and does not.




### Reference Materials - Sample Data Objects

#### Sample Order and OrderItem 

(`OrderDao` converts OMA responses into an `Order` containing a list of `OrderItem`)

order:
  ```
    orderId: 111-7497023-2960775

    marketplaceId: 1 - US

    condition: 4 - Closed

    customerOrderItemList:

        # Sample OrderItem

        - quantity: 1

          customerOrderItemId: 20655079937481

          orderId: 111-7497023-2960775

          merchantId: 14263472715

          asin: B0019H32G2

          title: "Bob's Red Mill, Organic Whole Grain Buckwheat Groats, Gluten Free, 16 Ounce (453 g)"

          isConfidenceTracked: true

          confidence: 94

    - ...

    customerId: 375944378

    orderDate: 2018-10-14 14:41:53

    shipOption: second
  ```

#### Sample PromiseHistory and Promise

`PromiseDao` converts DPS and OFS responses into `Promise`s, and `GetPromiseHistoryByOrderIdActivity` creates a `PromiseHistory` relating them with their `Order`)

PromiseHistory:
  ```
    order:

        # See sample Order and OrderItemList

        orderId: 111-7497023-2960775

        ...

    promises:

        # Sample Promise

        - promiseLatestArrivalDate: 2018-10-16 23:59:59

          customerOrderItemId: 20655079937521

          promiseEffectiveDate: 2018-10-14 22:31:35

          active: Y

          promiseLatestShipDate: 2018-10-15 23:59:59

          promiseProvidedBy: OFS

          asin: B07C9JYF2W

          deliveryDate: 2018-10-16 17:23:19

          confidence: 94

        - ...
  ```


#### Sample DPS Delivery Promise Result

`DeliveryPromiseServiceClient` converts a `DeliveryPromise` from DPS to a `Promise`)

DeliveryPromise:
  ```
    fulfillmentSvcSubclassId: 2

    promiseLatestArrivalDate: 2018-10-16 23:59:59

    customerOrderItemId: 20655079937521

    promiseQuantity: 1

    customerOrderId: 114-1315199-1937807

    promiseDataSource: SLAM 

    promiseEffectiveDate: 2018-10-14 22:31:35

    planQualityTypeCode: Normal

    isActive: Y

    promiseLatestShipDate: 2018-10-15 23:59:59

    promiseProvidedBy: OFS

    asin: B07C9JYF2W

    source: OFS

```


#### Sample Fulfillment Promise Result

You will write the class the converts the `PromiseResult` object that OFS will return.

OrderPromise:
  ```
    fulfillmentSvcSubclassId: 1

    intPrmsLatestArrivalDate: 2017-05-25 23:59:59

    extPrmsLatestArrivalDate: 2017-05-25 20:00:00

    customerOrderItemId: 14418401775681

    promiseQuantity: 1

    customerOrderId: 113-4938334-2196208

    promiseDataSource: SLAM

    promiseEffectiveDate: 2017-05-25 11:38:04

    planQualityTypeCode: Normal

    isActive: Y

    promiseLatestShipDate: 2017-05-25 11:15:00

    promiseProvidedBy: OFS

    asin: 0596009208

    source: OFS

```


## Submission format
 - Submit via Codegrade by commiting and pushing any new changes.
 - Check codegrade for automated feedback.
 - Any changes pushed to your branch will resubmited to codegrade if pushed before the sprint challenge deadline. Changes after the deadline will not be reviewed.




## You are DONE when
 Your project passes ALL tests in CodeGrade
