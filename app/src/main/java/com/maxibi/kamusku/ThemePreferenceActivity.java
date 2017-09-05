package com.maxibi.kamusku;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

/**
 * Created by faiz-faizal on 9/1/2017.
 */

public class ThemePreferenceActivity extends PreferenceActivity{
    public static final int RESULT_CODE_THEME_UPDATE = 1;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        findPreference("theme").setOnPreferenceChangeListener(new RefreshActivityOnPreferenceChangeListener(RESULT_CODE_THEME_UPDATE));
    }

    private class RefreshActivityOnPreferenceChangeListener implements Preference.OnPreferenceChangeListener {

        private final int resultCode;

        public RefreshActivityOnPreferenceChangeListener(int resultCodeThemeUpdate) {
        resultCode = resultCodeThemeUpdate;

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            setResult(resultCode);
            return true;

        }
    }
}
