package de.dreamlab.dash.keywords;

import com.intellij.openapi.projectRoots.Sdk;
import de.dreamlab.dash.LookupInfoDictionary;

public class ExcludeSdkTypeKeyword extends AbstractSdkKeyword implements IKeyword {
    private String keyword;
    private String sdkType;

    public ExcludeSdkTypeKeyword(String keyword, String sdkType) {
        this.keyword = keyword;
        this.sdkType = sdkType;
    }

    public String getName(final LookupInfoDictionary dict) {
        Sdk sdk = getSdk(dict);

        if ( sdkType == null ) {
            return null;
        }
        else if ( sdk != null && !sdkType.equals(sdk.getSdkType().getName()) ) {
            return keyword;
        }
        else {
            return null;
        }
    }
}
