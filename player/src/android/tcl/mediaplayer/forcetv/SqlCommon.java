package android.tcl.mediaplayer.forcetv;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class SqlCommon {
    /** Called when the activity is first created. */

	 //保存终端是否激活的标志位在数据库中
    public void updateDeviceActiveFlag(String record, ContentResolver resolver){
      	  ContentValues values1 = new ContentValues();
      	 
           values1.put(MyUsers.devicetoken.ACTIVE_FLAG, record);
           
           resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
      }
    //保存设备ID号在数据库中
    public void updateDeviceidRecord(String record, ContentResolver resolver){
      	  ContentValues values1 = new ContentValues();
      	 
           values1.put(MyUsers.devicetoken.DEVICE_ID, record);
           
           resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
      }
    //保存设备编号在数据库中
    public void updateDumRecord(String record,ContentResolver resolver){
     	 ContentValues values1 = new ContentValues();
     	 
          values1.put(MyUsers.devicetoken.DUM, record);
          
          resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
     }
    //保存设备类型在数据库中
    public void updateDeviceModel(String record,ContentResolver resolver){
     	 ContentValues values1 = new ContentValues();
     	 
          values1.put(MyUsers.devicetoken.DEVICE_MODEL, record);
          
          resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
     }
    //保存设备激活码在数据库中
    public void updateActivekeyRecord(String record,ContentResolver resolver){
    	 ContentValues values1 = new ContentValues();
    	 
         values1.put(MyUsers.devicetoken.ACTIVE_KEY, record);
         
         resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
    }
    
    //保存DIDtoken在数据库中
    public void updateDidTokenRecord(String record,ContentResolver resolver){
   	 ContentValues values1 = new ContentValues();
   	 
        values1.put(MyUsers.devicetoken.DIDTOKEN, record);
        
        resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
   }
    //保存用户登录token在数据库中
    public void updateTokenRecord(String record,ContentResolver resolver){
   	 ContentValues values1 = new ContentValues();
   	 
        values1.put(MyUsers.devicetoken.TOKEN, record);
        
        resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
   }
    //保存设备欢网登录的号在数据库中
    public void updateHuanidRecord(String record,ContentResolver resolver){
      	 ContentValues values1 = new ContentValues();
      	 
           values1.put(MyUsers.devicetoken.HUAN_ID, record);
           
           resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
      }
    //保存设备登录许可证类型在数据库中
    public void updateLicenseTypeRecord(String record,ContentResolver resolver){
      	 ContentValues values1 = new ContentValues();
      	 
           values1.put(MyUsers.devicetoken.LICENSE_TYPE, record);
           
           resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
      }
    //保存设备登录许可证数据在数据库中
    public void updateLicenseDataRecord(String record,ContentResolver resolver){
      	 ContentValues values1 = new ContentValues();
      	 
           values1.put(MyUsers.devicetoken.LICENSE_DATA, record);
           
           resolver.update(MyUsers.devicetoken.CONTENT_URI,values1,null,null);
      }

    //获取激活的标志位
    public String getDeviceActiveFlag(ContentResolver resolver) {
        String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID,MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA };
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur =resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
          if(cur!=null){
              if (cur.moveToFirst()) {
                  
                  do {
                      deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.ACTIVE_FLAG));
                 
                 } while (cur.moveToNext());
             }
              cur.close();
          }

        return deviceid;
    } 
   //获取设备ID号 
    public String getDeviceid(ContentResolver resolver) {
    	   String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID ,MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA};
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur =resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
          if(cur!=null){
              if (cur.moveToFirst()) {
                  
                  do {
                      deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.DEVICE_ID));
                 
                 } while (cur.moveToNext());
             }
              cur.close();
          }

        return deviceid;
    } 
    //获取设备编号
    public String getDum(ContentResolver resolver) {
    	   String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID,MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA };
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur = resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
        if(cur !=null){
            if (cur.moveToFirst()) {
                
                do {
                    deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.DUM));
               
               } while (cur.moveToNext());
           }
            cur.close();
        }

        return deviceid;
    } 
    //获取设备类型
    public String getDeviceModel(ContentResolver resolver) {
    	   String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID,MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA };
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur = resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
          if(cur!=null){
              if (cur.moveToFirst()) {
                  
                  do {
                      deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.DEVICE_MODEL));
                 
                 } while (cur.moveToNext());
             }
              cur.close();
          }

        return deviceid;
    } 
    //获取设备激活码
    public String getActiveKey(ContentResolver resolver) {
    	   String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID,MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA };
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur = resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
          if(cur!=null){
              if (cur.moveToFirst()) {
                  
                  do {
                      deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.ACTIVE_KEY));
                 
                 } while (cur.moveToNext());
             }
              cur.close();
          }

        return deviceid;
    } 
    
  //获取DIDTOKEN
    public String getDidtoken(ContentResolver resolver) {
    	   String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID,MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA };
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur = resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
          if(cur!=null){
              if (cur.moveToFirst()) {
                  
                  do {
                      deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.DIDTOKEN));
                 
                 } while (cur.moveToNext());
             }
              cur.close();
          }

        return deviceid;
    } 
    
    //获取登录token
    public String getToken(ContentResolver resolver) {
    	   String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID,MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA };
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur = resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
          
        if (cur.moveToFirst()) {
            
            do {
                deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.TOKEN));
           
           } while (cur.moveToNext());
       }
        cur.close();
        return deviceid;
    } 
    
    //获取欢网帐号
    public String getHuanid(ContentResolver resolver) {
    	   String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID ,MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA};
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur =resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
       if(cur!=null){
           if (cur.moveToFirst()) {
               
               do {
                   deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.HUAN_ID));
              
              } while (cur.moveToNext());
          }
           cur.close();
       }

        return deviceid;
    } 
    //获取终端许可证类型
    public String getLicenseType(ContentResolver resolver) {
    	   String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID, MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA};
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur =resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
          
        if (cur.moveToFirst()) {
            
            do {
                deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.LICENSE_TYPE));
           
           } while (cur.moveToNext());
       }
        cur.close();
        return deviceid;
    } 
    
  //获取终端许可证数据
    public String getLicenseData(ContentResolver resolver) {
    	   String columns[] = new String[] { MyUsers.devicetoken.ACTIVE_FLAG,MyUsers.devicetoken.DEVICE_ID, MyUsers.devicetoken.DUM,MyUsers.devicetoken.DEVICE_MODEL,MyUsers.devicetoken.ACTIVE_KEY,MyUsers.devicetoken.DIDTOKEN,MyUsers.devicetoken.TOKEN,MyUsers.devicetoken.HUAN_ID, MyUsers.devicetoken.LICENSE_TYPE,MyUsers.devicetoken.LICENSE_DATA};
        Uri myUri = MyUsers.devicetoken.CONTENT_URI;
        Cursor cur =resolver.query(myUri, columns,null, null, null );
           String deviceid = null;
          
        if (cur.moveToFirst()) {
            
            do {
                deviceid = cur.getString(cur.getColumnIndex(MyUsers.devicetoken.LICENSE_DATA));
           
           } while (cur.moveToNext());
       }
        cur.close();
        return deviceid;
    } 

}