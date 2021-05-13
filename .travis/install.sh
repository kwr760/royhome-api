#!/bin/bash

scripts=/var/scripts
repo=${1}
deployment=${2}
type=${3}

source ${scripts}/${repo}/env/${type}

echo "installing ${repo} as ${deployment}"
cd ${LOCATION}/${deployment}
pwd

echo "completed"
