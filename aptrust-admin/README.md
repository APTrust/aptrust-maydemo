aptrust-admin Overview
======================

This subproject is a Spring application to run as an administrative front end for the APTrust
preservation repository.

Building
========

This project depends on building the root project. See aptrust/README.md for more information.

Running the Web Admin in Development
====================================

Once you've built the whole project: you can run the web admin by simply running the following:

  mvn clean jetty:run

on the command line and then open your browser to 

  http://localhost:8888/aptrust-admin/

Default Development Accounts
============================

try uva-admin/password  to login.
other usernames with the same password will also work: 
ncsu-admin and jhu-admin

for super user access:   aptrust-admin/password

**NOTE** Change these account details in production!

Configuring user accounts
=========================

TODO Directions for adding user accounts.
