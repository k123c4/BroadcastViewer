package com.example.broadcastviewer.Receiver;

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


                // correct format

                if (message.startsWith(prefix) && message.endsWith(suffix)) {
                    String ticker = message.substring(prefix.length(), message.length() - suffix.length()).trim().toUpperCase();

                    if (ticker.matches("^[a-zA-Z]{1,5}$")) {
                        ticker = ticker.toUpperCase();  // capitalize before using

                        // Valid ticker format
                        Toast.makeText(context, "Received valid ticker: " + ticker, Toast.LENGTH_SHORT).show();
                        launchIntent.putExtra("TICKER", ticker);
                    } else {
                        // Valid SMS format but invalid ticker characters
                        Toast.makeText(context, "Invalid ticker: " + ticker, Toast.LENGTH_LONG).show();
                        launchIntent.putExtra("INVALID_TICKER", true);
                    }

                } else {
                    // Invalid SMS format
                    Toast.makeText(context, "No valid watchlist entry found", Toast.LENGTH_LONG).show();
                    launchIntent.putExtra("INVALID_FORMAT", true);
                }

                context.startActivity(launchIntent);
            }
        }
    }
}