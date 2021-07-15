#!/bin/bash
echo -e "\nRunning Authorizer\n"
FILE_PATH="$1" docker-compose up --build
