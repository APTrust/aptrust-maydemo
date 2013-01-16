package org.aptrust.client.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;

import com.yourmediashelf.fedora.client.FedoraClient;
import com.yourmediashelf.fedora.client.FedoraCredentials;

/**
 * A command line utility that can be used to index packages in a fedora
 * repository.
 */
public class PackageIndexer {

    private static String servicePid = "aptrust:indexableSDef";
    private static String serviceMethod = "toSolr";

    public static void main(String args[]) throws HttpException, IOException {
        if (args.length < 3) {
            System.out.println("Usage: IndexAPTrustPackage [fedora-url] [solr-update-url] [pid] ...");
            return;
        }

        // instantiate a read-only FedoraClient
        FedoraClient fc = new FedoraClient(new FedoraCredentials(args[0], null, null));

        try {
            for (int i = 2; i < args.length; i ++) {
                System.out.print("Indexing " + args[i] + "...");
                indexPid(fc, args[i], args[1]);
                System.out.println("DONE");
            }
            commit(args[1]);
        } catch (Exception ex) {
            rollback(args[1]);
            System.out.println("Error encountered, rolling back!");
            ex.printStackTrace();
        }
        
    }
    
    public static void indexPid(FedoraClient fc, String pid, String updateUrl) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(FedoraClient.getDissemination(pid, servicePid, serviceMethod).execute(fc).getEntityInputStream(), baos);
        
        HttpClient client = new HttpClient();

        PostMethod post = new PostMethod(updateUrl);
        Part[] parts = {
                new FilePart("add.xml", new ByteArrayPartSource("add.xml", baos.toByteArray()))
        };
        post.setRequestEntity(
                new MultipartRequestEntity(parts, post.getParams())
            );
        try {
            client.executeMethod(post);
            int status = post.getStatusCode();
            if (status != HttpStatus.SC_CREATED && status != HttpStatus.SC_OK) {
                throw new RuntimeException("REST action \"" + updateUrl + "\" failed: " + post.getStatusLine());
            }
        } finally {
            post.releaseConnection();
        }
        
    }
    
    public static void commit(String updateUrl) throws HttpException, IOException {
        String url = updateUrl + "?stream.body=%3Ccommit/%3E";
        GetMethod get = new GetMethod(url);
        try {
            HttpClient client = new HttpClient();
            client.executeMethod(get);
            int status = get.getStatusCode();
            if (status != HttpStatus.SC_OK) {
                throw new RuntimeException("REST action \"" + url + "\" failed: " + get.getStatusLine());
            }
        } finally {
            get.releaseConnection();
        }
    }
    
    public static void rollback(String updateUrl) throws HttpException, IOException {
        String url = updateUrl + "?stream.body=%3Crollback/%3E";
        GetMethod get = new GetMethod(url);
        try {
            HttpClient client = new HttpClient();
            client.executeMethod(get);
            int status = get.getStatusCode();
            if (status != HttpStatus.SC_OK) {
                throw new RuntimeException("REST action \"" + url + "\" failed: " + get.getStatusLine());
            }
        } finally {
            get.releaseConnection();
        }
    }

}
