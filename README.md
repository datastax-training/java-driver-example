# Some Cassandra Java Examples

All of this were originally written by [@beccam](https://github.com/beccam) and merely remixed by me for a live-coding exercise at UberConf 2015.

To make these work, you'll have to have an instance of DataStax Enterprise running somewhere, and edit the `clusterAddress` at the top of the `Cassandra` class to point to a node in that cluster. The instance will need this table in the `killr_video` keyspace:

~~~sql
CREATE TABLE users (
    user_id uuid,
    first_name text,
    last_name text,
    email text,
    created_date timestamp,
    PRIMARY KEY (user_id)
);
~~~

Run `gradlew dataStax` to execute the `main()` method.
