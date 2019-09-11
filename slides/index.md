% Endpoints
% A Quest for the Right Level of Coupling
   
  [http://julienrf.github.io/2019/endpoints](http://julienrf.github.io/2019/endpoints)

### Agenda {.unnumbered}

- Motivation
- Demo
- Design

### {.unnumbered}

<div style="text-align: center; margin-top: -4em;"><p style="margin: 0;">
<img src="bestmile-sponsor.png" alt="bestmile sponsor" style="max-width: 100%; max-height: 26em;">
</p></div>

# Motivation

## Context

### Monolith {.unnumbered}

![](monolith.svg)

### Monolith: Pros & Cons {.unnumbered}

#### Pros {.unnumbered}

- Simplicity

#### Cons {.unnumbered}

- Rigidity
- Scalability

### Distributed {.unnumbered}

![](distributed.svg)

### Distributed: Pros {.unnumbered}

- Flexibility
    - Independent teams, tech stacks, deployments
- Scalability
    - Scale out just the critical nodes

### Distributed: Cons {.unnumbered}

- Complexity
    - Communication across program boundaries
    - Dependencies between nodes

### Crossing Program Boundaries {.unnumbered}

<div style="text-align: center">
<img src="microservices.png" style="height: 15em">
</div>

## How Do We Cross Program Boundaries in Practice?

### Java RMI {.unnumbered}

~~~ scala
val b: B = service.compute(a)
~~~

> - Remote invocation as an `A => B` function!
> - Compile-time errors

### Java RMI: Cons {.unnumbered}

> - Blocking, inefficient
> - No streaming
> - Network failures surface as runtime exceptions

### Illusion of Transparency {.unnumbered}

> “Distribution transparency is impossible to achieve in practice. Precisely
> because of that impossibility, it is **dangerous** to provide the **illusion of
> transparency**”. [Rachid Guerraoui et al., 1999](http://www.engr.sjsu.edu/fayad/column/CACM/ACMPub/p101-guerraoui.pdf)

### Akka {.unnumbered}

~~~ scala
import akka.actor.typed.scaladsl.AskPattern._

val eventualB: Future[B] = serviceRef.ask(ref => Compute(a, ref))
~~~

- Non-blocking, efficient
- Streaming (with Akka Streams)
- Same guarantees for local and remote invocations
    - Location transparency is not a lie!

### Akka: Cons {.unnumbered}

> - Remote invocation as an `(A, ActorRef[B]) => Unit` function
>     - Tedious plumbing
> - All the nodes have to use the Akka communication protocol
>     - Not appropriate to expose a public API

### HTTP Services {.unnumbered}

~~~ scala
val eventualB: Future[B] =
  httpClient.post("http://service/compute", a.toJson)
    .filter(_.status == 200)
    .flatMap(_.entity.asJson.to[B])
~~~

- Clients and servers can have their own tech stack

### HTTP Services: Cons {.unnumbered}

> - Building HTTP requests is tedious
> - Documenting an HTTP API is tedious
> - Untyped
>     - No compile-time guarantee that requests are well-formed
>     - No IDE assistance

### WSDL {.unnumbered}

~~~ scala
val eventualB: Future[B] = service.compute(a)
~~~

(assuming that you have generated `service` implementation class with
a tool like scalaxb)

- Brings back type-safety
- Makes invocation simple

### WSDL: Cons {.unnumbered}

> - No streaming
> - XML service definitions are tedious to read or write
> - Relies on code generation
>     - Requires extra step in build
>     - Generated code sometimes does not compile
>     - Generated code may impose constraints on the way it can be used
>     - Limited expressivity of the service definition language

### gRPC {.unnumbered}

> - Service definitions written in a human-readable format
> - Better performance (HTTP2, binary serialization)
> - Streaming
> - But still, relies on code generation!

### Meta-Programming {.unnumbered}

Idea: mitigate code generation issues by synthesizing code
with macros instead

~~~ scala
trait Service {
  def compute(a: A): B
}

val service = synthesized.Client[Service]
val eventualB: Future[B] = service.compute(a)
~~~

### Meta-Programming: Cons {.unnumbered}

> - Poor tooling support
> - Second-class citizen

# Demo

# Design

### Just a Boring Library {.unnumbered}

### First-Class Scala Values {.unnumbered}

- No code generation
- No new language
- Seamless integration in your project

### No Macros {.unnumbered}

- No surprising semantics
- IDE friendly

### Modularity {.unnumbered}

![](modularity.svg)

### Modularity² {.unnumbered}

![](modularity-bis.svg)

### Extensibility {.unnumbered}

![](extensibility.svg)

# The End

### Thanks for your attention {.unnumbered}

Any questions?

