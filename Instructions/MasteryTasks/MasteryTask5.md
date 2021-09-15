Mastery Task 5: Devil in the Details
=

The Missing Promise CLI displays promises for the first item in an order. That's not the whole story, though: Amazon often breaks orders into multiple shipments with different delivery speeds, or separates Amazon items from the items fulfilled by different vendors. Therefore, each _item_ has a delivery promise.

You will modify the tool to show each item's promises. Then you'll sort the promises associated with the order to make it easier for CS reps to use the tool.

Milestone 1
-----------

Modify the code so that it prints the promise(s) for each item in an order. Write unit tests covering the different cases that could occur in order promises. Make sure all the existing unit tests still pass. Run the tool with order id `900-3746403-0000002` to view an order with many different promises.

Milestone 2
-----------

Customers often ask our CS representatives about specific products that they're worried about delivery for. Our CS representatives have asked that the items in the promise history be listed by ASIN (alphabetically, from 'A' to 'Z') to make this lookup more efficient for orders with more than one item.

Modify the code so that the item promises are sorted **alphabetically by their ASINs**, ascending ('A' to 'Z'). Think about these questions as you come up with your recommendation:

*   Can we use `sort()` to accomplish this?
*   Should we use a `Comparable<T>` to accomplish this? Is there a natural ordering here?
*   Or should we use a `Comparator<T>`? Is this just _one_ ordering that might make sense, among others?
    *   Hint: CS representatives also suggest that it might be helpful to sort by promise dates in the future... How would you extend your design in the future?

After consulting with your Sr Engineer, you both agree that ASIN isn't a _natural_ ordering for `Promise`s, there are many other orderings that make sense. Following this line of thinking, create a `PromiseAsinComparator` in a new package, `com.amazon.ata.deliveringonourpromise.comparators`.

Now that you've decided to use a `Comparator`, what will be required?

*   What interface (exactly, including generic parameter) will your class need to implement?
    *   What type should `T` be?
*   What method (and argument types) do we need to implement to satisfy the interface?
*   What will the comparison method logic look like? Is it comparing a single attribute from each argument? You can probably use some existing `String`\-comparing logic somewhere....

Write a unit test that ensures that the `PromiseHistory` returned by `getPromiseHistoryByOrderId()` contains `Promise`s sorted by ASIN in ascending order.

Run each test as follows. They should all pass

Each test should be run separately using the following commands - one command per test:

  
```
./gradlew -q clean :test

./gradlew -q clean IRT

./gradlew -q clean MasteryTaskFiveTests
```

Nicely done!

**Now go back to the README to see what few remaining tasks you have to be done-done-done with the project**
