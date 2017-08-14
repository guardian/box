Box
===

Put something in, get it out again

Box reproduces much of the, now deprecated, `akka.Agent` API, but does not 
reproduce the same concurrency model as it is a simple wrapper round 
`AtomicReference`. 

It has been designed to provide a simple migration path for systems already 
using Agents that simply use it as a convenient way to share cross-thread
state. New applications should probably use `AtomicReference` or some other
concurrency abstraction directly.