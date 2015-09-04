package com.padeoe.autoconnect;

/**
 * Created by padeoe on 2015/7/2.
 */
public class ConnectResult {
    public boolean isConnected;
    private String reply_msg;
    private String fullname;
    private String username;
    ConnectResult(boolean isConnected,String reply_msg,String fullname,String username){
        this.isConnected=isConnected;
        this.reply_msg=reply_msg;
        this.fullname=fullname;
        this.username=username;
    }
    public String getShowResult(){
        if(isConnected){
            return fullname+username+"\n"+reply_msg;
        }
        else{
            if(reply_msg!=null){
                return reply_msg;
            }
            else{
                return (String) App.context.getResources().getText(R.string.login_fail);
            }
        }
    }
}
