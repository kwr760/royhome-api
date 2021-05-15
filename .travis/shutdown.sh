#!/bin/bash

repo=${1}
deployed=${2}

echo "shutting down ${repo} as ${deployed}"
pkill -f "java.*${deployed}.*royhome.api"


