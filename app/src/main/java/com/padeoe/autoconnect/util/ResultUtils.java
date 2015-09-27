package com.padeoe.autoconnect.util;


import com.padeoe.autoconnect.R;
import com.padeoe.autoconnect.activity.App;
import com.padeoe.nicservice.njuwlan.object.portal.ReturnData;

/**
 * Created by padeoe on 2015/9/17.
 */
public class ResultUtils {
    public static String getShowResult(String connectResult, boolean isLogin) {
        if (connectResult == null) {
            return App.context.getResources().getString(R.string.unknownfault);
        }
        ReturnData returnData;
        try {
            returnData = ReturnData.getFromJson(connectResult);
        } catch (Exception e) {
            return connectResult;
        }
        if (returnData == null) {
            return connectResult;
        }
        if (isLogin & returnData.getUserInfo() != null) {
            return returnData.getUserInfo().getFullname() + returnData.getUserInfo().getUsername() + returnData.getReply_message();
        }
        if (isLogin & returnData.getUserInfo() == null) {
            return returnData.getReply_message();
        }
        if (!isLogin) {
            return returnData.getReply_message();
        }
        return connectResult;
    }

/*    public static ReturnData getReturnDataObject(String connectResult) {
        if (connectResult == null) {
            return null;
        }
        return ReturnData.getFromJson(connectResult);
    }
    public static boolean isLoginSuccess(String result){
        if(result.startsWith("{\"reply_code\":1")||result.startsWith("{\"reply_code\":6")){
            return true;
        }
        return false;
    }*/
}
