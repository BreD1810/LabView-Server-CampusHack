# LabView-Server
This is the webserver for the LabView project by KirkStars, written at CampusHack2018.

The server is written in Java, and is design to work with [Apache Tomcat](http://tomcat.apache.org/).
It receives connections from the clients found [here](https://github.com/BreD1810/LabView-Client/).
It sends and receives data from the webpage found [here](https://github.com/BreD1810/LabView-Webpage).

The server uses port 8080 for communication with clients and the webpage.

The following URIs can be accessed:
1. /addclient
    * Add a new client to the server's database. This is utilised in the admin page.
2. /clientlist
    * Returns a list of client names, and their on and off status.
3. /detailedclientlist
    * Returns more details of the clients. The ID, name, status, last online date, and log data.
4. /logfile
    * Returns the log file for the client.
5. /websocketendpoint
    * Change the status of the client connecting.
