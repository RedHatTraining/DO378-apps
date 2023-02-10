#!/usr/bin/env bash

if [ $# -le 1 ]
  then
    echo "Incorrect arguments provided"
    echo "    Usage: $0 HOST AMOUNT"
    echo ""
    echo "    Example: $0 localhost:8080 10"
    exit 1
fi

HOST="${1}"
AMOUNT="${2}"
URL="http://${HOST}/expense"

EXPENSE='{
    "name":"My Expense",
    "paymentMethod":"CASH",
    "amount":"'${AMOUNT}'"
}'

echo "Posting expense to $URL"
echo ""
curl "$URL" -H "Content-Type: application/json" -d "$EXPENSE"
echo ""
