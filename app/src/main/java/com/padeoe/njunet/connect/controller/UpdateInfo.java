package com.padeoe.njunet.connect.controller;

import android.util.Log;

import com.padeoe.nicservice.njuwlan.object.portal.BasicInfo;
import com.padeoe.nicservice.njuwlan.object.portal.ReturnData;
import com.padeoe.nicservice.njuwlan.object.portal.row.BasicInfoRow;
import com.padeoe.nicservice.njuwlan.service.OnlineQueryService;
import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.uihandler.ConnectResultHandle;
import com.padeoe.njunet.connect.uihandler.ErrorHandle;
import com.padeoe.njunet.connect.uihandler.GetOnlineTimeFailHandle;
import com.padeoe.njunet.connect.uihandler.OnlineTimeHandle;
import com.padeoe.njunet.connect.uihandler.ReturnDataHandle;
import com.padeoe.njunet.util.MyObservable;

/**
 * Created by padeoe on 2016/4/26.
 */
public class UpdateInfo extends MyObservable<ConnectResultHandle> {
    public void updateInfo() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OnlineQueryService onlineQueryService = new OnlineQueryService();
                String result = onlineQueryService.getUserInfo();
                ReturnData returnData = ReturnData.getFromJson(result);
                setChanged();
                notifyObservers(returnData != null ? new ReturnDataHandle(returnData) : new ErrorHandle(result));
            }
        }.start();
    }

    public void updateOnlineTime() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OnlineQueryService onlineQueryService = new OnlineQueryService();
                String result = onlineQueryService.getBasicInfo();
                BasicInfo basicInfo = BasicInfo.getFromJson(result);
                int seconds;
                if (basicInfo != null && basicInfo.getBasicInfoRows() != null && basicInfo.getBasicInfoRows().length > 0) {
                    BasicInfoRow basicInfoRow = basicInfo.getBasicInfoRows()[0];
                    try {
                        seconds = Integer.parseInt(basicInfoRow.getTotal_ipv4_volume());
                        setChanged();
                        notifyObservers(new OnlineTimeHandle(seconds));
                    } catch (NumberFormatException e) {
                        setChanged();
                        notifyObservers(new ErrorHandle(App.context.getString(R.string.illegal_result_from_server)));
                    }
                } else {
                    setChanged();
                    notifyObservers(new GetOnlineTimeFailHandle());
                    Log.e("获取时间失败", result);
                }

            }
        }.start();
    }

}
