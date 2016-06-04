package com.padeoe.njunet.deploy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.controller.ConnectService;
import com.padeoe.njunet.util.PrefFileManager;

public class AccountInputFragment extends DeployFragment {
    EditText usernameEdit;
    EditText passwordEdit;
    View view;

    public AccountInputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button button = (Button) (getActivity().findViewById(R.id.nextStepButton));
        button.setText(getNextStepButtonText());
        view = inflater.inflate(R.layout.fragment_account_input, container, false);
        usernameEdit = ((EditText) (view.findViewById(R.id.input_username)));
        passwordEdit = ((EditText) (view.findViewById(R.id.input_password)));
        usernameEdit.setText(PrefFileManager.getAccountPref().getString("username", ""));
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getActivity().findViewById(R.id.nextStepButton).setEnabled(hasInput(usernameEdit) && hasInput(passwordEdit));
            }
        };
        usernameEdit.addTextChangedListener(textWatcher);
        passwordEdit.addTextChangedListener(textWatcher);

        passwordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (hasInput(passwordEdit)) {
                        ((FirstSettingActivity) getActivity()).onNextStepButtonClicked(getActivity().findViewById(R.id.nextStepButton));
                    }
                    handled = true;
                }
                return handled;
            }
        });
        return view;
    }


    @Override
    public void handle(View view) {
        ConnectService.setUsername(getUserName());
        ConnectService.setPassword(getPassword());
        view.setEnabled(true);
    }

    public String getUserName() {
        return ((TextView) (view.findViewById(R.id.input_username))).getText().toString();
    }

    public String getPassword() {
        return ((TextView) (view.findViewById(R.id.input_password))).getText().toString();
    }


    public boolean hasInput(EditText editText) {
        return editText.getText().toString().length() > 0;
    }

    public String getNextStepButtonText() {
        return getString(R.string.nextStep);
    }
}
