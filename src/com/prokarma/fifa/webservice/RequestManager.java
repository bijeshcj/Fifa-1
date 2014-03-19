package com.prokarma.fifa.webservice;

import java.util.Iterator;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;

import com.prokarma.fifa.contentprovider.DatabaseHandler;
import com.prokarma.fifa.models.soccer.SeasonType;
import com.prokarma.fifa.models.soccer.Soccer;


public class RequestManager {
	private static final String TAG = RequestManager.class.getCanonicalName();
	private static final String MTAG = RequestManager.class.getSimpleName();
	Context context = null;
	ProgressDialog progDia = null;
	Soccer soccerResponse = null;
	public boolean isWithInOneMileReceived = false;
	
	public RequestManager(Context context) {
		this.context = context;
	}
	
	public void getSoccerResp(){
		RestClient client = new RestClient("http://api.espn.com/v1/sports/soccer/fifa.world",1);
		client.addParam("apikey", "wncdqb5f6tnt2h3yyraf7bvv"); 
		try{
			client.execute(RestClient.RequestMethod.GET, new ResponseListener() {

				@Override
				public void onResponseReceived(Object responseObj, int requestType) {

					if(responseObj != null){
						if(responseObj instanceof Soccer){
							Soccer soccerResp = (Soccer)responseObj;
							DatabaseHandler dbHandler = new DatabaseHandler(context);
							
							List<SeasonType> sTypes = soccerResp.getSports().get(0).getLeagues().get(0).getSeason().getSeasonTypes();
							
							Iterator<SeasonType> it = sTypes.iterator();
							
							while(it.hasNext()) {
								SeasonType stype = it.next();
								dbHandler.addSeasonType(stype);
							}
							setServerResponse(soccerResp);
						}
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Soccer getSoccerResponse(){
		return soccerResponse;
	}
	public void setServerResponse(Soccer resp){
		soccerResponse = resp;
	}

	/*public void sendReg(Context context,User user) throws Exception{}


	public void sendSignalDetail(Context context,User user) throws Exception {
		this.context = context;
		// Generate req
		Log.d(TAG,"sendSignalDetail... ");
		LocalDBStorage db = new LocalDBStorage(context);
		RestClient client = new RestClient("http://"+AppState.getCurrentServerIp(context)+":8080/signalrx/signalDetail",2);
		final String callIds = db.callIdForWebService(context);
		Log.d(TAG,"$$$ callIDs "+callIds);

		if(callIds.trim().length() <= 0){
			//			Toast toast = Toast.makeText(RequestManager.this.context , " No Call Detail ", Toast.LENGTH_LONG);
			//			toast.show();
			MLog.d(MTAG,"No Call Detail");
			return;
		}
		MLog.e("", "Call Detail callids "+callIds);
		ArrayList<CallDetails> list = db.fetchTableValues(context, false, callIds,null);
		if(list.size() < 1){
			//			Toast toast = Toast.makeText(RequestManager.this.context , " No Records Selected ", Toast.LENGTH_LONG);
			//			toast.show();
			return;
		}
		MLog.e("", "Call Detail sent size "+list.size());
		String signalDetString =new CreateJSON().getJSON(list);
		client.addParam("signalDetail",signalDetString);
		client.addParam("userId", user.getUserId());
		client.execute(RestClient.RequestMethod.POST, new ResponseListener(){
			@Override
			public void onResponseReceived(Object responseObj, int requestType) {
				if(requestType == 2 && responseObj != null){
					if(responseObj instanceof ServerResponse){
						ServerResponse resp = (ServerResponse) responseObj;
						if (resp.getErrorCode().equals("0")){
							//							new LocalDBStorage(RequestManager.this.context).deleteCallsFromDB(RequestManager.this.context,callIds);
							//							Toast toast = Toast.makeText(RequestManager.this.context , " Call Detail Successfully sent ", Toast.LENGTH_LONG);
							//toast.show();
							MLog.e("", "Call Detail Successfully sent");
							//RequestManager.this.sendVelocityDetail();

						}else if (resp.getErrorCode().equals("1")){
							new LocalDBStorage(RequestManager.this.context).revertServerFlag(RequestManager.this.context,callIds);
							//							Toast toast = Toast.makeText(RequestManager.this.context , " SignalRx Server Error ", Toast.LENGTH_LONG);
							//toast.show();
							MLog.e("", " SignalRx Server Error");

						}else if(resp.getErrorCode().equals("2")){
							new LocalDBStorage(RequestManager.this.context).revertServerFlag(RequestManager.this.context,callIds);
							//							Toast toast = Toast.makeText(RequestManager.this.context , " Parameter Missing", Toast.LENGTH_LONG);
							//toast.show();
							MLog.e("", "Parameter Missing");

						}else {//if(resp.getErrorCode().equals("3")){
							new LocalDBStorage(RequestManager.this.context).revertServerFlag(RequestManager.this.context,callIds);//Toast toast = Toast.makeText(RequestManager.this.context , " Invailed User", Toast.LENGTH_LONG);
							//toast.show();
							MLog.e("", " Invailed User");

						}
					}
					else {
						new LocalDBStorage(RequestManager.this.context).revertServerFlag(RequestManager.this.context,callIds);//Toast toast = Toast.makeText(RequestManager.this.context , " Invailed User", Toast.LENGTH_LONG);
						//toast.show();
						MLog.e("", " Not Instance of ServerResponse");

					}
				}else{
					new LocalDBStorage(RequestManager.this.context).revertServerFlag(RequestManager.this.context,callIds);
					//					Toast toast = Toast.makeText(RequestManager.this.context , " Call Detail upload fail ", Toast.LENGTH_LONG);
					//toast.show();
					MLog.e("", "Call Detail upload fail");

				}
			}

		});
	}

	public void sendVelocityDetail(){
		String logfilePath = LocationState.logFile;
		final File f = new File(logfilePath);
		if(f.exists()){
			StringBuffer str = new StringBuffer();
			try{
				FileReader read = new  FileReader(f);
				BufferedReader bufre = new BufferedReader(read);

				String temp=null;
				while((temp = bufre.readLine()) != null){
					str.append(temp);
					str.append('\n');
				}
				RestClient client = new RestClient("http://"+AppState.getCurrentServerIp(context)+":8080/signalrx/signalDetail",3);
				client.addParam("veloLog",str.toString());
				client.execute(RestClient.RequestMethod.POST, new ResponseListener(){
					@Override
					public void onResponseReceived(Object respObj,int requestType) {
						if(respObj != null && respObj instanceof ServerResponse){
							ServerResponse resp = (ServerResponse) respObj;
							if (resp.getErrorCode().equals("0")){
								//							Toast toast = Toast.makeText(RequestManager.this.context , " Velocity details Sent ", Toast.LENGTH_LONG);
								//							toast.show();
								f.delete();
							}else if (resp.getErrorCode().equals("1")){
								//							Toast toast = Toast.makeText(RequestManager.this.context , " SignalRx Server Error ", Toast.LENGTH_LONG);
								//							toast.show();
							}
						}

					}

				});

			}catch(Exception e){
				MLog.e("","Exception In the sendVelocityDetail");
				e.printStackTrace();
			}

		}

	}

	public void sendWifiSignalDetail(Context context,User user) throws Exception {
		this.context = context;
		// Generate req
		final LocalDBStorage db = new LocalDBStorage(context);
		RestClient client = new RestClient("http://"+AppState.getCurrentServerIp(context)+":8080/signalrx/sendWifi", 2);

		ArrayList<WifiSignals> list=db.fetchWifiSignalDetail(context);

		if((list != null)&&list.size() < 1){
			Log.e("Wifi Signal", "No Rows Selected");
			return;
		}
		String signalDetString =new CreateJSON().getwifiJSON(list);
		
		System.out.println("$$$ signalDetString "+signalDetString);

		client.addParam("wifiDetails",signalDetString);
		client.addParam("userId", user.getUserId());
		client.execute(RestClient.RequestMethod.POST, new ResponseListener(){
			@Override
			public void onResponseReceived(Object responseObj, int requestType) {
				if(requestType == 2 && responseObj != null){
					if(responseObj instanceof ServerResponse){
						ServerResponse resp = (ServerResponse) responseObj;
						if (resp.getErrorCode().equals("0")){
							MLog.i("wifi signal",  " Wifi Signal Detail Successfully sent "+new Date());
							db.deleteWifiSignalDetail(RequestManager.this.context);
						}else if (resp.getErrorCode().equals("1")){
							Log.i("wifi signal",  " SignalRx Server Error ");

						}else if(resp.getErrorCode().equals("2")){
							Log.i("wifi signal",  " Parameter Missing");

						}else if(resp.getErrorCode().equals("3")){

							Log.i("wifi signal",  " Invailed User");

						}
					}
				}else{

					Log.i("wifi signal",  " Wifi Signal Detail upload fail ");
				}
			}

		});
	}
	public void sendDataConnectionReport(final Context context) {
		final LocalDBStorage db = new LocalDBStorage(context);
		User user = db.getUser(context);
		try {
			String userId = db.checkIsNewUser(context, user);
			if(userId != null){
				user.setUserId(userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<ConnectionReport> list=db.fetchDataReport(context);

		if((list != null) && list.size() < 1){
			Log.e("Data Report", "No Rows Selected");
			return;
		}
		String reportString =new CreateJSON().getDataConnectionreport(list);
		RestClient client = new RestClient("http://"+AppState.getCurrentServerIp(context)+":8080/signalrx/signalDetail", 6); //change url
		client.addParam("mobileData", "mobileData");
		client.addParam("dataReport",reportString);
		client.addParam("userId", user.getUserId());
		try {
			client.execute(RestClient.RequestMethod.POST, new ResponseListener(){
				@Override
				public void onResponseReceived(Object responseObj, int requestType) {
					if(requestType == 6 && responseObj != null){
						if(responseObj instanceof ServerResponse){
							ServerResponse resp = (ServerResponse) responseObj;
							if (resp.getErrorCode().equals("0")){
								MLog.i("DataConnectionReport",  " Data Connection Report Successfully sent "+new Date());
								db.deleteDataConnectionReport(context);
							}else if (resp.getErrorCode().equals("1")){
								Log.i("Data Report",  " SignalRx Server Error ");
							}else if(resp.getErrorCode().equals("2")){
								Log.i("Data Report",  " Parameter Missing");
							}else if(resp.getErrorCode().equals("3")){
								Log.i("Data Report",  " Invailed User");
							}
						}
					}else{
						Log.i("DataConnectionReport",  " Data Connection Report upload fail ");
					}
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
