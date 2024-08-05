#!/bin/bash

API_ENDPOINT="http://localhost:8080/api/transaction/"
API_ENDPOINT_2="http://localhost:8080/api/transaction/submit"

# Get the start time
start_time=$(date '+%Y-%m-%d %H:%M:%S')
start_epoch=$(date +%s)

echo "Start time: $start_time"

for i in {1..1000}
do
   if [ $((i % 2)) -eq 0 ]; then
       curl -X GET "$API_ENDPOINT_2" &
       echo -e "\nRequest $i to API_ENDPOINT_2 completed."
   else
       curl -X GET "$API_ENDPOINT" &
       echo -e "\nRequest $i to API_ENDPOINT_1 completed."
   fi
done

# Wait for all background processes to finish
wait

# Get the end time
end_time=$(date '+%Y-%m-%d %H:%M:%S')
end_epoch=$(date +%s)

echo "End time: $end_time"

# Calculate the time difference
time_diff=$((end_epoch - start_epoch))

minutes=$(( (time_diff % 3600) / 60))
seconds=$((time_diff % 60))

echo "Time needed for the loop process: ${minutes} minutes, ${seconds} seconds"
