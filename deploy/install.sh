#!/bin/bash

# blue/green
deployment=${1}

api=/usr/royhome/api/${deployment}

echo "installing ${api} as ${deployment}"
cd "${api}" || exit
pwd
./gradelw build

echo "installed"
