#!/bin/bash

# Define your friend's IP or domain
FRIEND_IP="172.30.248.209"

# Infinite loop to continuously ping
while true; do
  # Ping your friend 1 time
  ping -c 1 $FRIEND_IP
  
  # Check if the ping was successful
  if [ $? -eq 0 ]; then
    echo "Ping successful!"
  else
    echo "Ping failed!"
  fi
  
  # Wait for 5 seconds before pinging again
done
