#!/usr/bin/env bash

mysql -h127.0.0.1 -uroot -proot1234 -e "drop database zhongkexie_server;"
mysql -h127.0.0.1 -uroot -proot1234 -e "create database zhongkexie_server;"
