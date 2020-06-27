package com.ss.universitiesdirectory.RSS;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import com.ss.universitiesdirectory.Adapter.NewsAdapter;
import com.ss.universitiesdirectory.Model.NewsModel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RSSParser extends AsyncTask<Void,Void,Boolean> {

    private Context context;
    private ListView listView;
    private InputStream stream;
    private ProgressDialog progressDialog;
    private ArrayList<NewsModel> newsModels = new ArrayList<>();

    public RSSParser(Context context, InputStream stream, ListView listView) {
        this.context = context;
        this.stream = stream;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog =new ProgressDialog(context);
        progressDialog.setTitle("Parse data");
        progressDialog.setMessage("Parsing Data...Please wait");
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return this.parseRSS();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);

        progressDialog.dismiss();
        if(isParsed){
            listView.setAdapter(new NewsAdapter(context, newsModels));
        }else{
            Toast.makeText(context,"Unable To Parse",Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean parseRSS() {
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser parser=factory.newPullParser();

            parser.setInput(stream,null);
            int event = parser.getEventType();

            String value=null;
            newsModels.clear();
            NewsModel model = new NewsModel();

            do {

                String name=parser.getName();

                switch (event) {

                    case XmlPullParser.START_TAG:
                        if(name.equals("item")) {
                            model = new NewsModel();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        value=parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("title")) {
                            model.setTitle(value);
                        }else if(name.equals("description")) {
                            model.setDescription(value);
                        }else if(name.equals("pubDate")) {
                            model.setDate(value);
                        }else if(name.equals("link")) {
                            model.setLink(value);
                        }

                        if(name.equals("item")) {
                            newsModels.add(model);
                        }
                        break;
                }

                event=parser.next();

            }while (event != XmlPullParser.END_DOCUMENT);

            return true;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
