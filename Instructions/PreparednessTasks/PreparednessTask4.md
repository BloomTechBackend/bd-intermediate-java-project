Preparedness Task 4: Document the Classes
==

Milestone 1: Class Overview
---------------------------

The CLI application comprises the following classes and interfaces. Some already exist and you won't need to modify, some exist and you'll need to modify (some lightly, others extensively). You'll also be adding a couple more that aren't in this list based on your design below! We're not providing a class diagram here (you'll be creating one yourself in the project!), but here are some brief descriptions

All these classes are contained in the `com.amazon.ata.deliveringonourpromise` package and its subpackages.

### Initialization and Presentation

These are in the base package:

*   **App**: Instantiates objects that the other classes will depend on. This is a manual version of “dependency injection,” a best practice that we'll get to later in the course. _Most of this will remain as-is, but you might need to modify this as you refactor the class hierarchies._
*   **Shell**: Implements the user interaction and makes calls to the GetPromiseHistoryByOrderID API. _You shouldn't need to modify this._

### Front End Messaging

*   **PromiseHistoryClient** (`promisehistoryservice` subpackage): A “client” is typically the last stop in the front end before requests get sent over the network. We don't have a network for this project, but we included the separation anyway. _You shouldn't need to modify this._

### Back End Logic

*   **GetPromiseHistoryByOrderIdActivity** (`activity` subpackage): An “activity” is typically where requests from the network are first handled by the back end. Usually an activity just translates data from the network into objects and calls a logic class, but we don't have a network for this project, so we combined the activity and logic in one class. _This is one of the classes you'll be modifying._
*   **ReadOnlyDao** (`dao` subpackage): “DAO” stands for “data access object”, and is an object that you use to read data from or write data to some source (might be database, another service, local file system, etc). The Missed Promise CLI service uses two DAOs, and both of them implement ReadOnlyDao, an interface with a single `get()` method. It promises that if you provide an identifier, the DAO will provide you an object. _You shouldn't need to modify this interface._

### Order Logic

*   **OrderDao** (`dao` subpackage); Retrieves order data for order IDs and transforms it into a useful format for CS reps. _This is one of the classes you'll be modifying._
*   **OMAClient** (`ordermanipulationauthority` subpackage): Here's the client / server pattern again. This is the last stop in our back end before we make a request from our OMA dependency. This project _still_ has no networking, but you'll see this separation so often we thought it was important to to emphasize it. _You shouldn't need to modify this._

### Promise Logic

*   **PromiseDao** (`dao` subpackage): Retrieves promise data for order item IDs and transforms it into a useful format for CS reps. _You may need to update this._
*   **DeliveryPromiseServiceClient** (`deliverypromiseservice` subpackage): Last stop in our back end before we request promise data from our DPS dependency. _Normally clients remain as-is, but you may need to update this as you refactor the class hierarchy._

### Data Types

We want to control the data we present to our customers, but our dependencies also want to control the data they present to their customers. Therefore data classes often come in pairs with very similar structures, because we copy data from one or more dependencies into our own format. The results we get from the dependency services often get a suffix like “Result” or a prefix indicating their specialization.

The Missed Promise CLI data types are in the `types` subpackage; others are in dependencies, so you shouldn't try to navigate to their packages.

*   **Order / OrderResult**: Information about an order, including a list of items.
*   **OrderItem / OrderResultItem**: Information about a single item in an order.
*   **Promise / DeliveryPromise**: Information about promised shipment dates.
*   **PromiseHistory**: Collected data about all the promises for all the items in an order.

### Dependencies

*   **OrderManipulationAuthority**: Implementation of OMA for the purposes of this project. All you really need to know is that it turns order IDs into order data. _You shouldn't even be able to modify this._
*   **DeliveryPromiseService**: Implementation of DPS for the purposes of this project. Turns order item IDs into delivery promise data. _You shouldn't even be able to modify this._
*   **OrderFulfillmentService**: Implementation of OFS for the purposes of this project. Turns order item IDs into shipping promise data. _You shouldn't even be able to modify this._

Milestone 2: Diagram the Classes
--------------------------------

When you're new to a code base, you might find it helpful to document things as they're currently built, so you really know how the system works and can refer back to the diagrams later. Create a class diagram for the classes in this system by reading the sequence diagram below, reading the code to determine the public methods for the classes/interfaces, and thinking about which member variables are most important to remember.

We recommend using the [PlantUML plugin for IntelliJ](https://plugins.jetbrains.com/plugin/7017-plantuml-integration "https://plugins.jetbrains.com/plugin/7017-plantuml-integration"), in particular so that you don't run the risk of closing your browser and losing your work!

Your job is to create a PlantUML source text for the DeliveringOnOurPromises Application. When you're done, save the PlantUML source text in your package as `src/resources/deliveringonourpromise_CD.puml`.

![OverviewSD.png](https://github.com/LambdaSchool/ebd-unit2-sprint1-challenge-DeliveringOnOurPromises/blob/main/Instructions/PreparednessTasks/OverviewSD.png)

You should test your code

    ./gradlew -q clean :test 

If you wish to run all the Task Completion Tests, the command is:

    ./gradlew -q clean TCT

#### Peer Review

Once the tests pass, request a review from one of your peers. If you have trouble finding an available peer, try slacking your cohort channel.

If you need to make changes to the diagram, remember to update the source in your code package. One approved by your peer, you are ready to commit your code.

Review one of your peer's class diagrams and leave them feedback (as comments) and/or an approval. Remember to follow up on their changes/responses and work with them to get the diagram to an approval.

### Recommendations/tips

*   Here is the [PlantUML reference for class diagrams](http://wiki.plantuml.net/site/class-diagram "http://wiki.plantuml.net/site/class-diagram")
    
*   IntelliJ can display PlantUML diagrams as you type if you install the [PlantUML integration plugin](https://plugins.jetbrains.com/plugin/7017-plantuml-integration "https://plugins.jetbrains.com/plugin/7017-plantuml-integration").
    
    *   If you get an error in the plug-in complaining about GraphViz, surf to this site [https://graphviz.org/download/](https://graphviz.org/download/ "https://graphviz.org/download/") for help.
    *   After installing GraphViz, try closing and reopening IntelliJ.
*   Our Class Diagram analyzing code is still a little finicky, so try to keep your diagrams simple, and conforming to PlantUML standards. In particular:
    
    *   Do not worry about aggregation vs composition (open diamonds vs closed diamonds) for "has-a"/"contains" relationships. You can use either `o--` or `*--` for those.
    *   You should be able to represent relationships with either left- or right-facing arrows (`o--` or `--o`, `..|>` or `<|..` ) to suit your diagram layout. You _will_ need the correct _direction_ of the relationship (from one class/interface to the other).
    *   You should be able to include any number of the appropriate dots (`.`) or dashes (`-`) in your lines (`o-` or `o--` or `o---`) to suit your diagram layout
    *   Avoid adding labels to your relationships between types:
```
|      |    |    |

|-----:|:---|:---|

| YES: | `ClassA o-- ClassB`          | |

| NO:  | `ClassA o-- "1:many" ClassB` | "1:many" label may cause tests to fail |

*   Include spaces between your relationship lines and the types in the relationship:

|      |    |    |

|-----:|:---|:---|

| YES: | `ClassA o-- ClassB` | |

| NO:  | `ClassA o--ClassB`  | line is touching ClassB |

| NO: | `ClassAo--ClassB`    | even worse! line is touching both types |

*   Make your **implements** relationships (if any) use the dotted line notation, and the "closed arrowhead":

|      |    |    |

|-----:|:---|:---|

| YES: | <code>ClassA ..|> TypeB</code> | |

| NO:  | `ClassA ..> TypeB`             | use closed arrowhead instead |

| NO:  | <code>ClassA --|> TypeB</code> | use dotted line instead |
```

### Exit Checklist

*   You understand the roles of the following pieces of the project:
    *   Initialization and Presentation
    *   Front End Messaging
    *   Back End Logic
    *   Order Logic
    *   Promise Logic
    *   Data Types
    *   Dependencies (services)
*   You have created your class diagram
*   You reviewed a peer's class diagram and provided feedback
*   Your Preparedness Task 4 TCTs are passing locally
*   You pushed your code
*   Preparedness Task 4's TCTs are passing on CodeGrade.
