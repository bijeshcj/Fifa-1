package com.prokarma.fifa.webservice;
/*package com.pk.restrequesttest.webservice;



import android.content.Context;
import android.util.Log;

import com.calldropdetect.MLog;
import com.calldropdetect.User;
import com.calldropdetect.localdb.LocalDBStorage;

public class Registration {
    private static final String TAG = Registration.class.getCanonicalName();
	public void registration(Context context, User user){
		Log.d(TAG,"$$$ registration");
		try{
			LocalDBStorage localDBStorage = new LocalDBStorage(context);
		String userId = localDBStorage.checkIsNewUser(context, user);
		Log.i(TAG,"userId from db "+userId);
		if((userId == null) || (localDBStorage.doesUserInformationNeedToBeUpdated(context, user))){
			new RequestManager().sendReg(context,user);
		}
	
		}catch(Exception e){
			MLog.e("","Exception In the Registration");
			e.printStackTrace();
		}
	}
	
}
*/