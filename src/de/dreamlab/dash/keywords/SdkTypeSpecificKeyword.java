package de.dreamlab.dash.keywords;

import com.intellij.openapi.projectRoots.Sdk;
import de.dreamlab.dash.LookupInfoDictionary;

import java.util.HashMap;


public class SdkTypeSpecificKeyword extends AbstractSdkKeyword implements IKeyword {
    private String defaultKeyword = null;
    private HashMap<String, String> sdkTypes;

    public SdkTypeSpecificKeyword(String defaultKeyword, String... sdkTypesAndKeywords) {
        if ( (sdkTypesAndKeywords.length % 2) != 0 ) {
            throw new Error("Missing keyword for sdk: " + sdkTypesAndKeywords[sdkTypesAndKeywords.length - 1]);
        }

        this.defaultKeyword = defaultKeyword;

        sdkTypes = new HashMap<String, String>();

        for ( int i = 0; i < sdkTypesAndKeywords.length; i += 2 ) {
            sdkTypes.put(sdkTypesAndKeywords[0], sdkTypesAndKeywords[1]);
        }
    }

    public SdkTypeSpecificKeyword(String keyword, String sdkType) {
        sdkTypes = new HashMap<String, String>();
        sdkTypes.put(sdkType, keyword);
    }

    public String getName(final LookupInfoDictionary dict)
    {
        Sdk sdk = getSdk(dict);

        if ( sdk != null && sdkTypes.containsKey(sdk.getSdkType().getName()) ) {
            return sdkTypes.get(sdk.getSdkType().getName());
        }

        return defaultKeyword;
    }
}