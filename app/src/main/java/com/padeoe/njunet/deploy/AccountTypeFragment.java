package com.padeoe.njunet.deploy;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.padeoe.njunet.R;

public class AccountTypeFragment extends DeployFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button button=(Button)(getActivity().findViewById(R.id.nextStepButton));
        button.setText(getNextStepButtonText());
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account_type, container, false);
        RadioGroup accountKind=(RadioGroup) view.findViewById(R.id.AccountKindRadio);
        accountKind.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        Log.d("被点击","点击");
                        getActivity().findViewById(R.id.nextStepButton).setEnabled(true);
/*                        NextStepEnable nextStepEnable=new NextStepEnable();
                        nextStepEnable.addObserver();
                        nextStepEnable.enableNextStep();*/
                    }
                }
        );
        RadioButton portalradioButton=(RadioButton) view.findViewById(R.id.radio_pnju);
        RadioButton oaRadioButton=(RadioButton) view.findViewById(R.id.radio_oa);
        setRadioButtonTextColor(portalradioButton);
        setRadioButtonTextColor(oaRadioButton);
        return view;
    }

    @Override
    public void handle(View view) {
        view.setEnabled(false);
    }

    public String getNextStepButtonText(){
        return getString(R.string.nextStep);
    }

    private void setRadioButtonTextColor(RadioButton radioButton){
        String pnjuRadioText=radioButton.getText().toString();
        SpannableString spannableString=new SpannableString(pnjuRadioText);
        int index_newline=pnjuRadioText.indexOf("\n");
        if(index_newline!=-1){
            spannableString.setSpan(new ForegroundColorSpan(Color.GRAY),index_newline,pnjuRadioText.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            radioButton.setText(spannableString);
        }
        else{
            Log.e("内部错误","first setting fragment中radio button文字不包含换行符");
        }
    }
}
