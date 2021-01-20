#!/bin/sh
cd /home/ubuntu/webapp
FILE=/home/ubuntu/webapp/server.js
if test -f "$FILE"; then
    echo "$FILE exists."
    forever stop server.js
    #forever stop server.js
else
    echo "No sever.js running"
fi