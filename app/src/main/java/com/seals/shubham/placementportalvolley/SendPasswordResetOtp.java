package com.seals.shubham.placementportalvolley;

import android.telephony.SmsManager;

import java.util.Random;

/**
 * Created by shubham on 1/5/2017.
 */

public class SendPasswordResetOtp {
    SmsManager mSmsManager;
    private String mobile_No;
    Random mRandom;
    int rndm_No;

    //Constructor to Set Mobile_No
    public SendPasswordResetOtp(){
        mRandom = new Random();
    }

    public void set_Mobile_No(String mobile_No){
        this.mobile_No = mobile_No;
    }

    public int numGenerator(){
        int val;
        val = mRandom.nextInt(1000000);
        return val;
    }

    public int getOtp(){
        return rndm_No;
    }

    public void sendMessage(){
        mSmsManager = SmsManager.getDefault();
        rndm_No = numGenerator();
        mSmsManager.sendTextMessage(mobile_No,null,"Otp for resetting Password is "+rndm_No,null,null);
    }

}
