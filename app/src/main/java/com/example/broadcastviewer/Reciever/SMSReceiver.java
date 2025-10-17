package com.example.broadcastviewer.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.broadcastviewer.MainActivity;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // What we want to do when the SMS happens
        final Bundle bundle = intent.getExtras();
        if(intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)){
            if(bundle != null){
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                String format = bundle.getString("format").toString();
                String message = "";
                for (Object o : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) o, format);
                    String sender = currentMessage.getDisplayOriginatingAddress();
                    message = currentMessage.getDisplayMessageBody();
                    String printMessage = "Sender: " + sender + " Message: " + message;
                }
                Intent launchIntent = new Intent(context, MainActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                String prefix = "Ticker:<<";
                String suffix = ">>";
                if(message.startsWith(prefix) && message.endsWith(suffix)){
                    String ticker = message.substring(prefix.length(), message.length() - suffix.length());
                    if(ticker.matches("[a-zA-Z]+"))
                    launchIntent.putExtra("TICKER",ticker.toUpperCase());
                        else
                        launchIntent.putExtra("INVALID TICKER", true);
                }else{
                    launchIntent.putExtra("INVALID FORMAT", true);
                }
                context.startActivity(launchIntent);
            }
        }
    }



}