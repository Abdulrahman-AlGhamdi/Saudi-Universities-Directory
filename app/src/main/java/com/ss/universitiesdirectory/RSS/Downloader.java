package com.ss.universitiesdirectory.RSS;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class Downloader extends AsyncTask<Void,Void,Object> {

    private Context context;
    private String Address;
    private ListView listView;
    private ProgressDialog progressDialog;

    public Downloader(Context context, String Address, ListView listView) {
        this.context = context;
        this.Address = Address;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog =new ProgressDialog(context);
        progressDialog.setTitle("Fetch data");
        progressDialog.setMessage("Fetching Data...Please wait");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Void... params) {
        return this.downloadData();
    }

    @Override
    protected void onPostExecute(Object data) {
        super.onPostExecute(data);
        progressDialog.dismiss();

        if(data.toString().startsWith("Error")) {
            Toast.makeText(context,data.toString(),Toast.LENGTH_SHORT).show();
        }else{
            new RSSParser(context, (InputStream) data, listView).execute();
        }
    }

    private Object downloadData() {
        Object connection=Connector.connect(Address);
        if(connection.toString().startsWith("Error")) {
            return connection.toString();
        }

        try{
            HttpURLConnection con= (HttpURLConnection) connection;
            int responseCode=con.getResponseCode();
            if(responseCode==con.HTTP_OK){
                InputStream stream = new BufferedInputStream(con.getInputStream());
                return stream;
            }
            return ErrorTracker.RESPONSE_ERROR+con.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return ErrorTracker.IO_ERROR;
        }
    }
}
