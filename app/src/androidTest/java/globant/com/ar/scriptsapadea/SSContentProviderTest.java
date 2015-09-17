package globant.com.ar.scriptsapadea;

import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import android.database.Cursor;
import android.content.ContentResolver;

import com.globant.scriptsapadea.contentprovider.SSContentProvider;

import junit.framework.Assert;

/**
 * Created by nicolas.quartieri.
 */
public class SSContentProviderTest extends ProviderTestCase2<SSContentProvider> {

    private static MockContentResolver resolve;

    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */
    public SSContentProviderTest(Class<SSContentProvider> providerClass, String providerAuthority) {
        super(providerClass, providerAuthority);
    }

    @Override
    protected void setUp() throws Exception {
        try {
            super.setUp();
            resolve = this.getMockContentResolver();
        } catch (Exception e) {
            //
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCase () {
        Cursor cursor = getProvider().query(SSContentProvider.CONTENT_URI, new String[]{"id", "name", "avatar"}, null, null, null);

        assertNotNull(cursor);
        assertTrue(false);
    }
}
