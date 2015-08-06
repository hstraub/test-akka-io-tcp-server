# Description

Testing Akka TCP IO Server and listening on 0.0.0.0:4444.

# Documentations

* [Akka Documentation Using TCP](http://doc.akka.io/docs/akka/snapshot/scala/io-tcp.html)
* [My Github project test-akka-io-tcp-client](https://github.com/hstraub/test-akka-io-tcp-client)

# Build one jar file

```
sbt assembly
```

and run the program:
```
java -jar target/scala-2.11/test-akka-io-tcp-server-assembly-0.1.jar
```

# Run Application direct

With

```
sbt run
```

# Testcases

with netcat:

```
cat test.txt | nc localhost 4444
```

with telnet

```
telnet localhost 4444
```

