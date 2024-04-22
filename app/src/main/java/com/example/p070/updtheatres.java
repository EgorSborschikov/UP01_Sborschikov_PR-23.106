package com.example.p070;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class updtheatres extends AppCompatActivity {
    TextView tvInfo;
    EditText tvName;
    Button tvButton;
    Button tvButtonU;
    Button tvButtonD;
    updtheatres.MyTask mt;
    //    updtheatres.MyTaskU mtn;
//    updtheatres.MyTaskD mtf;
    ArrayList<updtheatres.MyTaskU>mtu=new ArrayList <updtheatres.MyTaskU>();
    ArrayList<updtheatres.MyTaskD>mtd=new ArrayList <updtheatres.MyTaskD>();
    ListView lvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updtheatres);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvName = (EditText) findViewById(R.id.editTextMask);
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        tvButton = (Button) findViewById(R.id.tvButtonF);
        tvButtonU = (Button) findViewById(R.id.tvButtonU);
        tvButtonD = (Button) findViewById(R.id.tvButtonD);
//        mt = new updtheatres.MyTask();
//        mt.execute();

    }

    public void onclickF(View v) {
        mt = new updtheatres.MyTask();
        mt.execute(tvName.getText().toString());
    }

    class MyTask extends AsyncTask<String, Void, ArrayList<String[]>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Begin");
        }

        @Override
        protected ArrayList<String[]> doInBackground(String... params) {
            ArrayList<String[]> res = new ArrayList<>();
            HttpURLConnection myConnection = null;

            try {
                URL githubEndpoint = new URL("http://10.0.2.2:8080/kino?cod=4&name=" + params[0]);
                myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                tvInfo.setText("1");

            } catch (IOException e) {
                e.printStackTrace();
                tvInfo.setText("2");

            }

            int i = 0;
            try {
                i = myConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i == 200) {
                InputStream responseBody = null;
                try {
                    responseBody = myConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InputStreamReader responseBodyReader = null;
                try {
                    responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                JsonReader jsonReader;
                jsonReader = null;
                jsonReader = new JsonReader(responseBodyReader);
                try {
                    jsonReader.beginArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String key = null;
                String value = null;

                while (true) {
                    try {
                        if (!jsonReader.hasNext()) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonReader.beginObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ;
                    String[] str = new String[3];
                    int n = 0;
                    while (true) {
                        try {
                            if (!jsonReader.hasNext()) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            key = jsonReader.nextName();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        sb.append("\r\n : " +key);
                        try {
                            value = jsonReader.nextString();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
//                        sb.append("\r\n : " +value);
                        str[n] = value;
                        n++;
                    }
                    try {
                        jsonReader.endObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    res.add(str);
                }
                try {
                    jsonReader.endArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            myConnection.disconnect();


            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<String[]> result) {

            super.onPostExecute(result);
            updtheatres.ClAdapter clAdapter = new updtheatres.ClAdapter(tvInfo.getContext(), result);
            lvMain = (ListView) findViewById(R.id.lvMain);
            lvMain.setAdapter(clAdapter);
//            int n=lvMain.getChildCount();
            tvButtonU.setEnabled(true);
            tvButtonD.setEnabled(true);
            tvButton.setEnabled(false);
        }
    }

    class ClAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater lInflater;
        List<String[]>lines;

        ClAdapter(Context context, List<String[]> elines) {
            ctx = context;
            lines = elines;
            lInflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return lines.size();
        }

        @Override
        public Object getItem(int position) {
            return lines.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.itemudp, parent, false);
            }
            ;
            String[] p = (String[]) getItem(position);
            ((TextView) view.findViewById(R.id.tvText)).setText(p[0]);
            ((TextView) view.findViewById(R.id.tvText1)).setText(p[1]);

            return view;
        }

        ;

        public boolean getCheck(int position) {

            return true;
        }
    }

    public void onclickU(View v) {
        int n = lvMain.getChildCount();
        int m = 0;
        for (int i = 0; i < n; i++) {
            String[] st = (String[]) lvMain.getAdapter().getItem(i);
            LinearLayout ll = (LinearLayout) lvMain.getChildAt(i);
            CheckBox ch = (CheckBox) ll.getChildAt(0);
            EditText etn=(EditText) ll.getChildAt(1);
            EditText eta=(EditText) ll.getChildAt(2);
            if (ch.isChecked()) {
                String nm=etn.getText().toString();
                String ad=eta.getText().toString();
                mtu.add(new updtheatres.MyTaskU());
                mtu.get(m).execute(nm,ad,st[2]);
                m++;
            }
            ;
        }
    }

    public void onclickD(View v) {
        int n = lvMain.getChildCount();
        int m = 0;
        for (int i = 0; i < n; i++) {
            String[] st = (String[]) lvMain.getAdapter().getItem(i);
            LinearLayout ll = (LinearLayout) lvMain.getChildAt(i);
            CheckBox ch = (CheckBox) ll.getChildAt(0);
            if (ch.isChecked()) {
                mtd.add(new updtheatres.MyTaskD());
                mtd.get(m).execute(st[2]);
                m++;
            }
            ;
        }

    }

    class MyTaskU extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String line = null;
            String total = null;
            HttpURLConnection myConnection = null;
            try {
                URL githubEndpoint = new URL("http://10.0.2.2:8080/kino/");
                myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                myConnection.setRequestMethod("PUT");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            myConnection.setDoOutput(true);
            try {
                myConnection.getOutputStream().write( ("cod=1&name=" + params[0]+"&address="+params[1]+"&id="+params[2]).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            };
            int i=0;
            try {
                i = myConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                tvInfo.setText(str);
            if (i==200) {
                InputStream responseBody=null;
                try {
                    responseBody = myConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
                while (true) {
                    try {
                        if (!((line = r.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    total=line;
                }

            }
            myConnection.disconnect();
            return params;
        }
        @Override
        protected void onPostExecute(String... params) {
            super.onPostExecute(null);

            tvInfo.setText("End");
        }

    }
    class MyTaskD extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String line = null;
            String total = null;
            HttpURLConnection myConnection = null;
            try {
                URL githubEndpoint = new URL("http://10.0.2.2:8080/kino/");
                myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                myConnection.setRequestMethod("DELETE");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            myConnection.setDoOutput(true);
            try {
                myConnection.getOutputStream().write( ("cod=1&id="+params[0]).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            };
            int i=0;
            try {
                i = myConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                tvInfo.setText(str);
            if (i==200) {
                InputStream responseBody=null;
                try {
                    responseBody = myConnection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
                while (true) {
                    try {
                        if (!((line = r.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    total=line;
                }

            }
            myConnection.disconnect();
            return params;
        }
        @Override
        protected void onPostExecute(String... params) {
            super.onPostExecute(null);

            tvInfo.setText("End");
        }

    }

}
