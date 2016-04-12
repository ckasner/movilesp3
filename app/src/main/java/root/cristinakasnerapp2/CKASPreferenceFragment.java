package root.cristinakasnerapp2;

import android.os.Bundle;
import android.preference.PreferenceFragment;
public class CKASPreferenceFragment extends android.preference.PreferenceFragment {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }



}