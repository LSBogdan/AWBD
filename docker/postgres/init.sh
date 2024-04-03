#!/bin/bash

set -e
set -u

if [ -n "$POSTGRES_DATABASE_CREATION" ]; then
    echo "  Creating user and database "


    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        CREATE USER "$POSTGRES_DATABASE_CREATION";
        CREATE DATABASE "$POSTGRES_DATABASE_CREATION";
        GRANT ALL PRIVILEGES ON DATABASE "$POSTGRES_DATABASE_CREATION" TO "$POSTGRES_DATABASE_CREATION";
EOSQL
    echo "Database created successfully"

fi
