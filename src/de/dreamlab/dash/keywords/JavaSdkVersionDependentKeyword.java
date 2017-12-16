package de.dreamlab.dash.keywords;

import com.intellij.openapi.projectRoots.Sdk;
import de.dreamlab.dash.LookupInfoDictionary;

public class JavaSdkVersionDependentKeyword extends AbstractSdkKeyword implements IKeyword {
    private String java6Keyword;
    private String java7Keyword;
    private String java8Keyword;
    private String java9Keyword;

    public JavaSdkVersionDependentKeyword(String java6Keyword, String java7Keyword, String java8Keyword, String java9Keyword) {
        this.java6Keyword = java6Keyword;
        this.java7Keyword = java7Keyword;
        this.java8Keyword = java8Keyword;
        this.java9Keyword = java9Keyword;
    }

    @Override
    public String getName(LookupInfoDictionary dict) {
        Sdk sdk = getSdk(dict);

        if ( sdk != null && sdk.getSdkType().getName().equals("JavaSDK") ) {
            String versionString = sdk.getVersionString();

            if ( versionString != null ) {
                if ( versionString.contains("1.6.") ) {
                    return java6Keyword;
                }
                else if ( versionString.contains("1.7.") ) {
                    return java7Keyword;
                }
                else if ( versionString.contains("1.8.") ) {
                    return java8Keyword;
                }
                else if ( versionString.contains("1.9.") ) {
                    return java9Keyword;
                }
            }
        }

        return null;
    }
}
