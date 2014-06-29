package de.dreamlab.dash.keywords;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import java.util.HashMap;


public class SdkTypeSpecificKeyword implements IKeyword {
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

    public String getName(Sdk sdk, final Project project, final PsiFile psiFile, final VirtualFile virtualFile)
    {
        if ( sdk != null && sdkTypes.containsKey(sdk.getSdkType().getName()) ) {
            return sdkTypes.get(sdk.getSdkType().getName());
        }

        return defaultKeyword;
    }
}