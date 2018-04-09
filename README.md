# visuals-and-patterns
To explore some visual index of D3.js

Data Source:
Fetched data from Twitter.
Used Tweepy, a python library, to fetch Tweets related to topic: "MarchMadness" using Jupyter Notebook.

Installed Hadoop in Oracle VM VirtualBox to run the map reduce code.

Map Reduce:
Code written in Java. 
Considered all the words in the file (data fetched from Tweepy tweets).

Jar dependencies:
commons-cli-1.2.jar
slf4j-simple-1.7.25.jar
slf4j-api-1.7.25.jar
hadoop-mapreduce-client-core-3.1.0.jar
hadoop-common-3.1.0.jar


Exported it as a jar file and using drive transferred the jar file and the tweets output file to the VM.

Ran the map reduce jar file:
start-hadoop.sh
hadoop -fs put twitter-ncaa twitter-ncaa // twitter-ncaa is the name of the file containing the tweets
hadoop jar WordCountTwitter.jar WordCountPackage.WordCount twitter-ncaa TwitterNcaa //Name of the jar - PackageName - Java class name - the file containing tweets - name of the output of reducer
hadoop fs -cat TwitterNcaa/part-r-00000
hadoop fs -get TwitterNcaa/part-r-00000 twitter-ncaa-output

The reducer output file is placed in the folder containing the code for D3.js

D3.js
Used https://github.com/jasondavies/d3-cloud
Modified the code a little to display according to the outputs I wanted to show.

Used httpster, a light weight server, to run the code locally to use GET method to call the file containing reducer output.

Node.js should be installed in the system
npm install httpster

httpster -d /word-cloud -p 3000

Starts the code in localhost:3000








