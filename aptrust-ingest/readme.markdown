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

### Fedora Content Ingest
For ingesting content in fedora repositories that has already been marked as APTrust materials:

	mvn exec:java -Dexec.mainClass=org.aptrust.ingest.IngestClient -Dexec.args="--fedora-packages http://hostname:8080/fedora/ fedoraUsername fedoraPassword --name \"ingest operation name\" --dry-run"

* name and dry-run are optional

### DSpace Content Ingest
For ingesting DSPace content that has already been exported to a filesystem directory.

	mvn exec:java -Dexec.mainClass=org.aptrust.ingest.IngestClient -Dexec.args="--aip-dir [aip directory] -name \"ingest operation name\" --dpn --dry-run"
* name and dry-run are optional
* dpn : when present flags all ingested content as DPN bound

## Ingest Processing Service
This service listens to will respond to the addition or modification of files in any of the AP Trust staging spaces.  Early design documentation can be found on the AP Trust development wiki here: https://wiki.duraspace.org/display/aptrust/Ingest+Processing+Service

To build and run this service:
    mvn clean package

To install the service:
1. Extract the distribution.tar.gz that was created in the target directory
2. Add a ingest-client-config.properties file that points to the appropriate servers

To start or stop the service on linux:
    bin/ips start
    bin/ips stop

*Warning* - The first thing the IPS does when it successfully connects to DuraCloud is to process any files present in the staging spaces.  If there are many files, this may take a long time.
