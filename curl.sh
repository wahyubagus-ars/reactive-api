#!/bin/bash

GET_TRANSACTION_API="http://localhost:8080/api/transaction"
SUBMIT_TRANSACTION_API="http://localhost:8080/api/transaction/submit"

# Get the start time
start_time=$(date '+%Y-%m-%d %H:%M:%S')
start_epoch=$(date +%s)

echo "Start time: $start_time"

for i in {1..1000}
do
   if [ $((i % 2)) -eq 0 ]; then
        curl --location "$SUBMIT_TRANSACTION_API" \
              --header 'Content-Type: application/json' \
              --data '{
                  "userId": 2,
                  "paymentMethod": "QRIS"
              }' &
       echo -e "\nRequest $i to SUBMIT_TRANSACTION_API completed."
   else
       curl -X GET "$GET_TRANSACTION_API" &
       echo -e "\nRequest $i to GET_TRANSACTION_1 completed."
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
