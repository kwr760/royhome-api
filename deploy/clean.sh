#!/bin/bash

deployed=${1}

echo "shutting down api service as ${deployed}"
pkill -f "java.*royhome-api.${deployed}.*bootRun"
