package com.padeoe.njunet.deploy;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.padeoe.njunet.R;
import com.padeoe.njunet.util.PrefFileManager;


public class SettingFinishFragment extends DeployFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button button = (Button) (getActivity().findViewById(R.id.nextStepButton));
        button.setText(getNextStepButtonText());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_finish, container, false);
    }


    @Override
    public void handle(View view) {
        PrefFileManager.getAccountPref().edit().putBoolean("isFirstInstall",false).apply();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public String getNextStepButtonText() {
        return getString(R.string.finish);
    }
}
