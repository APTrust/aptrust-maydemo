# Ingest Utilities

This codebase will be the home of the ingest client, ingest processing service and other utilities associated with ingest of content into AP Trust.

## DSPace AIP manifest generation utility

A simple utility that walks through a directory of DSPace AIP packages and creates an ingest manifest describing the ingest of all of the content in that directory.  Upon transfer of that manifest and the AIP zip files to the staging space in DuraCloud, the materials will be ingested.

	mvn exec:java -Dexec.mainClass=org.aptrust.ingest.dspace.ManifestGenerator -Dexec.args="(aip directory) (manifest filename) (duracloud username) (access conditions) (dpn bound)"

* aip directory is the directory in which the AIP zip files are present 
* manifest filename is the path and filename that you wish the manifest file to be written (ie, "manifest.xml")
* duracloud username your username in duracloud (not for security purposes, but just to identify the ingest with you in the user interface)
* access conditions (optional) either "world", "institution" or "restricted" (default)
* dpn bound (optional) either "true" or "false", defaulting to "false"

## Ingest Client
This application, run at partner institutions moves content into the DuraCloud staging space where it will be processed for ingest.

Early design documentation can be found on the AP Trust development wiki here: https://wiki.duraspace.org/display/aptrust/Ingest+Client

## Ingest Processing Service
This service, running in the cloud, will respond to the addition or modification of files in any of the AP Trust staging spaces and process them.  Early design documentation can be found on the AP Trust development wiki here: https://wiki.duraspace.org/display/aptrust/Ingest+Processing+Service