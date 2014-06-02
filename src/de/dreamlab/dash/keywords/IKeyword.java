package de.dreamlab.dash.keywords;

import com.intellij.openapi.projectRoots.Sdk;
import com.sun.istack.internal.Nullable;

public interface IKeyword {
    @Nullable
    public String getName(Sdk sdk);
}
