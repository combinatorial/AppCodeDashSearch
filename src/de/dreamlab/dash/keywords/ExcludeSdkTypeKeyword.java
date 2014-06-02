package de.dreamlab.dash.keywords;

import com.intellij.openapi.projectRoots.Sdk;

public class ExcludeSdkTypeKeyword implements IKeyword {
    private String keyword;
    private String sdkType;

    public ExcludeSdkTypeKeyword(String keyword, String sdkType) {
        this.keyword = keyword;
        this.sdkType = sdkType;
    }

    public String getName(Sdk sdk) {
        if ( sdkType != null && !sdkType.equals(sdk.getSdkType().getName()) ) {
            return keyword;
        }
        else {
            return null;
        }
    }
}
