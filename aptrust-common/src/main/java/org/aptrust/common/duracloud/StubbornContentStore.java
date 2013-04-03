package org.aptrust.common.duracloud;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.duracloud.client.ContentStore;
import org.duracloud.common.model.AclType;
import org.duracloud.domain.Content;
import org.duracloud.domain.Space;
import org.duracloud.error.ContentStoreException;
import org.duracloud.error.InvalidIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * A ContentStore implementation that wraps another ContentStore implementation
 * but attempts to perform operations three times (with longer delays between
 * attempts) when exceptions are encountered that suggest temporary network
 * connectivity issues.
 * </p>
 */
public class StubbornContentStore implements ContentStore {

    final Logger logger = LoggerFactory.getLogger(StubbornContentStore.class);
    
    private ContentStore cs;

    /**
     * The delays (in ms) before each subsequent attempt.  The number of
     * total attempts for each call (assuming failure) will be the length of
     * this array plus one (for the initial call).
     */
    private int retryDelays[];

    public StubbornContentStore(ContentStore contentStore) {
        cs = contentStore;
        retryDelays = new int[] { 5000, 30000, 120000 };
    }
    
    /**
     * Determines whether the provided exception merits another attempt based
     * on the type of exception and number of attempts.  If it does, this 
     * method sleeps for the preconfigured amount of time and returns, otherwise
     * it throws the provided exception.
     * @param ex the original exception
     * @param attempt the number of retries performed already
     * @throws ContentStoreException the passed exception if appropriate either
     * because we've already tried enough times or the type of exception is
     * unlikely to be resolved with a subsequent attempt.
     */
    private void handleException(ContentStoreException ex, int retries) throws ContentStoreException {
        if (retries < retryDelays.length && ex.getMessage() != null 
                && (ex.getMessage().toLowerCase().contains("timeout") 
                    || ex.getMessage().toLowerCase().contains("no route to host")
                    || ex.getMessage().toLowerCase().contains("connection reset")
                    || ex.getMessage().toLowerCase().contains("response code was 500"))) {
            try {
                logger.warn("Request failed with message, \"" + ex.getMessage() + "\" attempting again in " + retryDelays[retries] + "ms.", ex);
                Thread.sleep(retryDelays[retries]);
            } catch (InterruptedException e) {
                logger.info("Interrupted while waiting before subsequent attempt!");
            }
        } else {
            throw ex;
        }
    }

    public String addContent(String arg0, String arg1, InputStream arg2,
            long arg3, String arg4, String arg5, Map<String, String> arg6)
            throws ContentStoreException {
        String result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.addContent(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public String copyContent(String arg0, String arg1, String arg2, String arg3)
            throws ContentStoreException {
        String result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.copyContent(arg0, arg1, arg2, arg3);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public String copyContent(String arg0, String arg1, String arg2,
            String arg3, String arg4) throws ContentStoreException {
        String result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.copyContent(arg0, arg1, arg2, arg3, arg4);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public void createSpace(String arg0) throws ContentStoreException {
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                cs.createSpace(arg0);
                return;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
    }

    @Override
    public void deleteContent(String arg0, String arg1)
            throws ContentStoreException {
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                cs.deleteContent(arg0, arg1);
                return;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
    }

    @Override
    public void deleteSpace(String arg0) throws ContentStoreException {
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                cs.deleteSpace(arg0);
                return;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
    }

    @Override
    public String getBaseURL() {
        return cs.getBaseURL();
    }

    @Override
    public Content getContent(String arg0, String arg1)
            throws ContentStoreException {
        Content result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.getContent(arg0, arg1);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public Map<String, String> getContentProperties(String arg0, String arg1)
            throws ContentStoreException {
        Map<String, String> result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.getContentProperties(arg0, arg1);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public Space getSpace(String arg0, String arg1, long arg2, String arg3)
            throws ContentStoreException {
        Space result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.getSpace(arg0,  arg1, arg2, arg3);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public Map<String, AclType> getSpaceACLs(String arg0)
            throws ContentStoreException {
        Map<String, AclType> result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.getSpaceACLs(arg0);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public Iterator<String> getSpaceContents(String arg0)
            throws ContentStoreException {
        Iterator<String> result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.getSpaceContents(arg0);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public Iterator<String> getSpaceContents(String arg0, String arg1)
            throws ContentStoreException {
        Iterator<String> result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.getSpaceContents(arg0, arg1);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public Map<String, String> getSpaceProperties(String arg0)
            throws ContentStoreException {
        Map<String, String> result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.getSpaceProperties(arg0);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public List<String> getSpaces() throws ContentStoreException {
        List<String> result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.getSpaces();
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public String getStorageProviderType() {
        return cs.getStorageProviderType();
    }

    @Override
    public String getStoreId() {
        return cs.getStoreId();
    }

    @Override
    public List<String> getSupportedTasks() throws ContentStoreException {
        List<String> result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.getSupportedTasks();
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public String moveContent(String arg0, String arg1, String arg2, String arg3)
            throws ContentStoreException {
        String result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.moveContent(arg0, arg1, arg2, arg3);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public String moveContent(String arg0, String arg1, String arg2,
            String arg3, String arg4) throws ContentStoreException {
        String result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.moveContent(arg0, arg1, arg2, arg3, arg4);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public String performTask(String arg0, String arg1)
            throws ContentStoreException {
        String result = null;
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                result = cs.performTask(arg0, arg1);
                break;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
        return result;
    }

    @Override
    public void setContentProperties(String arg0, String arg1,
            Map<String, String> arg2) throws ContentStoreException {
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                cs.setContentProperties(arg0, arg1, arg2);
                return;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
    }

    @Override
    public void setSpaceACLs(String arg0, Map<String, AclType> arg1)
            throws ContentStoreException {
        for (int i = 0; i <= retryDelays.length; i ++) {
            try {
                cs.setSpaceACLs(arg0, arg1);
                return;
            } catch (ContentStoreException ex) {
                handleException(ex, i);
            }
        }
    }

    @Override
    public void validateContentId(String arg0) throws InvalidIdException {
        cs.validateContentId(arg0);
    }

    @Override
    public void validateSpaceId(String arg0) throws InvalidIdException {
        cs.validateSpaceId(arg0);
        
    }

}
