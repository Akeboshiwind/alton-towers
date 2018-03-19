# Alton towers web scraper

Scrapes the alton towers queue times for all the rides and stores them in a database.

Docker link:

## Environment variables

Variable | Description
-------- | -----------
THREADS | The number of threads to run the web scraper on. (Default: 1)
BASE_URL | The base url to scrape the ride times from. (Default: http://ridetimes.co.uk/queue-times-new.php?r=)
DB_HOST | The host of the database. (Default: localhost)
DB_PORT | The port of the database. (Default: 3306)
DB_NAME | The MySQL database name. (Default: alton-towers)
DB_USER | The username for the database. (Default: admin)
DB_PASSWORD | The password for the database.

## License

Copyright © 2017 Oliver Marshall

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
