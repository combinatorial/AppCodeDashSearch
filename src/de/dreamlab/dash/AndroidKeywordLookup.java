package de.dreamlab.dash;

public class AndroidKeywordLookup extends KeywordLookup {

    public AndroidKeywordLookup(final DashLauncher dashLauncher) {
        super(dashLauncher);
    }

    @Override
    protected void setUpLanguages() {
        super.setUpLanguages();

        // Android Studio 0.5.7
        addLanguage("JAVA", "android", "javadoc", "cvj", "processing");
    }
}
