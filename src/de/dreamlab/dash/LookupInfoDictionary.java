package de.dreamlab.dash;

import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class LookupInfoDictionary{
    private Language language;
    private PsiElement psiElement;
    private Project project;
    private PsiFile psiFile;
    private VirtualFile virtualFile;

    private boolean isSdk = false;
    private Sdk sdk = null;

    private boolean isFileSqlLanguage = false;
    private Language fileSqlLanguage;


    public LookupInfoDictionary(Language language, PsiElement psiElement, Project project, PsiFile psiFile, VirtualFile virtualFile) {
        this.language = language;
        this.psiElement = psiElement;
        this.project = project;
        this.psiFile = psiFile;
        this.virtualFile = virtualFile;
    }

    // initially set values
    public Language getLanguage() {
        return language;
    }

    public PsiElement getPsiElement() {
        return psiElement;
    }

    public Project getProject() {
        return project;
    }

    public PsiFile getPsiFile() {
        return psiFile;
    }

    public VirtualFile getVirtualFile() {
        return virtualFile;
    }

    // optional sdk value
    public boolean isSdk() {
        return isSdk;
    }

    public Sdk getSdk() {
        if ( !isSdk) {
            throw new Error("sdk has not been set");
        }

        return sdk;
    }

    public void setSdk(Sdk sdk) {
        if (isSdk) {
            throw new Error("sdk can only be set once");
        }

        this.sdk = sdk;
        this.isSdk = true;
    }

    // optional fileSqlLanguage value
    public boolean isFileSqlLanguage() {
        return isFileSqlLanguage;
    }

    public Language getFileSqlLanguage() {
        if ( !isFileSqlLanguage) {
            throw new Error("fileSqlLanguage has not been set");
        }

        return fileSqlLanguage;
    }

    public void setFileSqlLanguage(Language fileSqlLanguage) {
        if (isFileSqlLanguage) {
            throw new Error("fileSqlLanguage can only be set once");
        }

        this.fileSqlLanguage = fileSqlLanguage;
        this.isFileSqlLanguage = true;
    }


}
