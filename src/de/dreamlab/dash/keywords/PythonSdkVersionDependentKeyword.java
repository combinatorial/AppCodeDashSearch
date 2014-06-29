package de.dreamlab.dash.keywords;

import com.intellij.openapi.projectRoots.Sdk;
import de.dreamlab.dash.LookupInfoDictionary;

public class PythonSdkVersionDependentKeyword extends AbstractSdkKeyword implements IKeyword {
    private String python2Keyword;
    private String python3Keyword;

    public PythonSdkVersionDependentKeyword(String python2Keyword, String python3Keyword) {
        this.python2Keyword = python2Keyword;
        this.python3Keyword = python3Keyword;
    }

    @Override
    public String getName(LookupInfoDictionary dict) {
        Sdk sdk = getSdk(dict);

        if ( sdk != null && sdk.getSdkType().getName().equals("Python SDK") ) {
            String versionString = sdk.getVersionString();

            if ( versionString != null ) {
                if ( versionString.contains("Python 2") ) {
                    return python2Keyword;
                }
                else if ( versionString.contains("Python 3") ) {
                    return python3Keyword;
                }
            }
        }

        return null;
    }
}
