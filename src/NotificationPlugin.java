package avishayhajbi.notificationplugin;
//notification
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.os.PowerManager;

//import com.avishayhajbi.finance.R;

//calander
import java.lang.System;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
//json parse
import org.json.JSONObject;
//icon
/*import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.InputStream;
import android.content.res.Resources;
import java.io.IOException;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;*/

public class NotificationPlugin extends CordovaPlugin{

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if(action.equals("noti")){
          JSONObject jObject = args.optJSONObject(0);
          final int id = jObject.optInt("id");
          final String title = jObject.optString("title");
          final String message = jObject.optString("description");  
		  final String image = jObject.optString("image");  
          	cordova.getActivity().runOnUiThread(new Runnable() { //cordova.getThreadPool().execute(new Runnable() {
	        	public void run() {
		            activateNotification(id,title,message,image);
		       		callbackContext.success("java callback test");
	            }
            });
            return true;
        }
      	callbackContext.error("unknown");
      	return false;
    }
    
    public void activateNotification(int id,String title,String message,String image){
     
        System.out.println("creating notification");
		//Context context = webView.getContext();
        Context context = super.cordova.getActivity().getApplicationContext();
        // Get taskId & message
        if (id < 0) id *=-1;
        int taskId = id;
        String notificationText = message;

        Intent intent = new Intent(context,NotificationPlugin.class);
        intent.setAction(""+taskId);
        intent.putExtra("taskId", id);
        intent.putExtra("notificationText", message );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		int icon = cordova.getActivity().getResources().getIdentifier(image, "drawable", cordova.getActivity().getPackageName());
		Notification notification = new Notification(icon, notificationText ,System.currentTimeMillis());
		/*Bitmap bmp = null;
		Uri iconUri = null;
		try{
			iconUri = Uri.parse(image);
			bmp = getIconFromUri(iconUri);
		} catch (Exception e){
			bmp = getIconFromRes(image);
		}*/
        PendingIntent pendingIntent = PendingIntent.getActivity(context, taskId, intent,  0);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
       /* Builder notification = new NotificationCompat.Builder(context)
            .setDefaults(0) // Do not inherit any defaults
            .setContentTitle(message)
            .setContentText(title)
            .setNumber(id)
            .setTicker(message
            .setLargeIcon(bmp);
            //.setLargeIcon(options.getIcon())
            //.setAutoCancel(options.getAutoCancel())
            //.setOngoing(options.getOngoing())
            //.setLights(options.getColor(), 500, 500)
            //.setDeleteIntent(dpi);*/
        notification.setLatestEventInfo( context,title, notificationText, pendingIntent);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(taskId, notification); // to execute
    }
	/*private Bitmap getIconFromUri (Uri uri) throws IOException {
        Bitmap bmp = null;
          
        InputStream input = cordova.getActivity().getApplicationContext().getContentResolver().openInputStream(uri);
        bmp = BitmapFactory.decodeStream(input);

        return bmp;
    }
	private Bitmap getIconFromRes (String icon) {
        Resources res = cordova.getActivity().getResources();
        int iconId = 0;

        iconId = getIconValue(cordova.getActivity().getPackageName(), icon);

        if (iconId == 0) {
            iconId = getIconValue("android", icon);
        }

        if (iconId == 0) {
            iconId = android.R.drawable.ic_menu_info_details;
        }

        Bitmap bmp = BitmapFactory.decodeResource(res, iconId);

        return bmp;
    }
	private int getIconValue (String className, String iconName) {
        int icon = 0;

        try {
            Class<?> klass  = Class.forName(className + ".R$drawable");

            icon = (Integer) klass.getDeclaredField(iconName).get(Integer.class);
        } catch (Exception e) {}

        return icon;
    }*/
}
