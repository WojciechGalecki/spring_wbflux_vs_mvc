# Spring WebFlux vs Spring MVC

## Services
- blocking-service
- reactive-service
- http-server
- rsocket-server
- rsocket-client
- models (shared models with Java Record, MongoDB Document, Cassandra Table and mapper)

## Local environment
- run MongoDB and Apache Cassandra: `start_env.sh`
- stop env: `stop_env.sh`

## Performance Tests

### K6
- documentation: https://k6.io/docs
- run tests: `run_perf_tests.sh`
- metrics output doc: https://k6.io/docs/using-k6/metrics

## Apache Cassandra
> docker exec -it <container-id> bash

> cqlsh

> CREATE KEYSPACE IF NOT EXISTS demo WITH REPLICATION = {'class' : 'NetworkTopologyStrategy', 'datacenter1' : 1};

> USE demo;

> CREATE TABLE demo.comments ( id int PRIMARY KEY, postId int, name text, email text, body text );

> INSERT INTO demo.comments(id,postid,name,email,body) VALUES (1,1,'name','email','body');

## RSocket CLI
- documentation: https://github.com/making/rsc
- example: `rsc --stream --route=comment-stream --debug tcp://localhost:7000`