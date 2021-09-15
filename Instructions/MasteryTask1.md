Mastery Task 1: Grace Under Pressure - Milestone 1
===

[Admiral Grace Hopper](https://en.wikipedia.org/wiki/Grace_Hopper "https://en.wikipedia.org/wiki/Grace_Hopper") is famously known for being a real-life, literal debugger thanks to her logbook entry describing how she found and removed a moth from a computer relay in one of the early room-sized computers. The Missed Promise CLI system also has bugs (although in this case they are not literally insects), and in this task you will find and eliminate one of them.

A CS representative has filed a bug report, stating that when they request the promise history for order ID `111-749023-7630574`, the Missed Promise CLI prints a weird message and exits:

Running CLI! Please enter the orderId you would like to view the Promise History for. > 111-749023-7630574 Error encountered. Exiting. Thank you for using the Promise History CLI. Have a great day!

Over the course of this sprint, you will determine why this happens, replace the error message with an informative one, prevent the tool from exiting when this error occurs, _and_ make sure it never happens again.

Milestone 1: Find the Bug
-------------------------

Reproduce the problem in your workspace by running the code and verifying that it prints the message the CS representative reports. Then run the CLI in IntelliJ and use IntelliJ's debugger and breakpoints to determine why the error message occurs. Add a comment where you think the fix should go (start with "// FIXME...") so you can return to it in Milestone 2.
