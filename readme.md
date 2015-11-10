Simple Java Event Store
=======================

Proof of concept of a simple, pretty low performing event store with multiple backends for Java or
any languages on the JVM capable of consuming Java libraries.


Design goals
------------

 - Java 8 only. Should not be a problem given that Java 7 is end-of-live already.
 - Don't force consumers to extend or implement any of our types for events or aggregates.


Some basic assumptions
----------------------

 - Aggregates are identified by a string type
