Endpoints
=========

> a quest for the right level of coupling

## Abstract

Distributed systems (e.g., microservices, web apps, or web services) promise more flexibility to developers
because each node is loosely coupled with each other. Indeed, each node evolves on its own and is free to use its own
technology stack (e.g., JavaScript, Python, JVM, etc.). However, I argue that this loose coupling also introduces
more maintenance burden, which negatively impacts the developers productivity. Typically, when a server is modified
to require a new request parameter, the clients and the documentation may not automatically catch up on that change.
This results in additional work on the client and the documentation to carry that change forward, or, even worse,
in a failure at run-time if developers didn’t notice that they introduced an incompatible change.

To address this problem, I’ve created the [endpoints](http://julienrf.github.io/endpoints) library. It introduces
just enough coupling between servers, clients and documentation such that they are automatically consistent regarding
the communication protocol, while keeping the flexibility of using different technology stacks on servers and clients.

In this talk, I will show a demo of *endpoints*, explain the design decisions I’ve made, and compare the library
with other approaches.

## Setup

This project contains both the slides and the demo code. To build the demo you only need sbt.
To build the slides you need Pandoc, and to show them you need Python 3.

### Run the Demo

~~~
$ sbt
> server/reStart
~~~

### Show the slides

Use the `makeSlides` sbt task to build the slides (into the `target/slides/` directory). Or the `showSlides` task
to run an HTTP server showing the slides. Typical developer workflow:

~~~
$ sbt
> ~showSlides
~~~
