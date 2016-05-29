package com.padeoe.njunet.deploy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.util.PrefFileManager;

import java.util.ArrayList;
import java.util.List;

public class FirstSettingActivity extends AppCompatActivity {
    private List<DeployFragment> fragments;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_setting);
        Intent intent = getIntent();
        String action=intent.getAction();
        fragmentManager = getSupportFragmentManager();
        //首次安装，设置fragment
        if(PrefFileManager.getAccountPref().getBoolean("isFirstInstall", true)){
            fragments=new ArrayList<>();
            fragments.add(new AccountTypeFragment());
            fragments.add(new AccountInputFragment());
            fragments.add(new SettingFinishFragment());
            //设置第一屏
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.root, fragments.get(0));
            fragmentTransaction.commit();
        }
        else{
            //不是首次安装，测试是否是设置项跳转来的
            if(action!=null&&action.equals("com.padeoe.njunet.EDIT_ACCOUNT")){
                fragments=new ArrayList<>();
                fragments.add(new AccountInputFragment());
                fragments.add(new SettingFinishFragment());
                //设置第一屏
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.root, fragments.get(0));
                fragmentTransaction.commit();
            }
            else{
                openMainActivity();
                finish();
            }
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // ConnectResultHandle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.disconnect) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onNextStepButtonClicked(View view) {
        Log.d("栈大小", fragmentManager.getBackStackEntryCount() + "");
        int stackSize=fragmentManager.getBackStackEntryCount();
        if(stackSize<fragments.size()-1){
            fragments.get(stackSize).handle(view);
            showNewFragment(fragments.get(stackSize+1));
        }
        else{
            Intent intent = getIntent();
            if(intent.getAction()!=null&&intent.getAction().equals("android.intent.action.MAIN")){
                openMainActivity();
            }
            finish();
        }

    }

    private void openMainActivity() {
        Intent intent = new Intent();
        intent.setClass(FirstSettingActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void showNewFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);
        fragmentTransaction.replace(R.id.root, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
