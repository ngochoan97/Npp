package com.example.nppproject.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nppproject.Globals;
import com.example.nppproject.MainWT;
import com.example.nppproject.R;
import com.example.nppproject.Wearther;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {
    String json = "";
    String city = "hanoi";
    String JsonAPI = Globals.URL_LINKWT + city + Globals.URL_KEYAPI;
    TextView tvId, tvMain, tvDes, tvIcon, tvTemp, tvPre, tvHumi, tvTemMin, tvTempMax;
    MainWT main;
    ArrayList<Wearther> listWeather;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wearther, container, false);
        // Inflate the layout for this fragment
        tvId = view.findViewById(R.id.tvId);
        tvMain = view.findViewById(R.id.tvMain);
        tvDes = view.findViewById(R.id.tvDescription);
        tvIcon = view.findViewById(R.id.tvIcon);
        tvTemp = view.findViewById(R.id.tvTemp);
        tvPre = view.findViewById(R.id.tvPressure);
        tvHumi = view.findViewById(R.id.tvHumidity);
        tvTemMin = view.findViewById(R.id.tvMin);
        tvTempMax = view.findViewById(R.id.tvMax);
        new getJson2().execute();
        return view;
    }

    public void getData() {
        try {
            URL url = new URL(JsonAPI);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            int byteChar;
            while ((byteChar = inputStream.read()) != -1) {
                json += (char) byteChar;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class getJson2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            getData();
            getMain();
            getWeather();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            int TempC,TemMin,TemMax;
           TempC = Integer.parseInt(   Math.round(((Double.parseDouble(main.getTemp()) - 273)*10)/10)+"");
           TemMin= Integer.parseInt(   Math.round(((Double.parseDouble(main.getTemp_min()) - 273)*10)/10)+"");
           TemMax=Integer.parseInt(   Math.round(((Double.parseDouble(main.getTemp_max()) - 273)*10)/10)+"");
           tvId.setText("ID: "+listWeather.get(0).getId());
           tvMain.setText("Main: "+listWeather.get(0).getMain());
           tvDes.setText("Description: "+listWeather.get(0).getDescription());
           tvIcon.setText("Icon: "+listWeather.get(0).getIcon());
           tvTemp.setText(TempC+"°C");
           tvTempMax.setText("Max: "+TemMax+"°C");
           tvTemMin.setText("Min: "+TemMin+"°C");
            tvHumi.setText("Humidity: "+main.getHumidity());
            tvPre.setText("Pressure: "+main.getPressure());
        }
    }

    private static final String TAG = "WeatherFragment";
    public void getMain() {
        main = new MainWT();
        try {
           // String JsonNew=json.substring(4);
            JSONObject object=new JSONObject(json);

            JSONObject objectMain = object.getJSONObject("main");
            main.setTemp(objectMain.getString("temp"));
            //     Toast.makeText(this, ""+main.getTemp(), Toast.LENGTH_SHORT).show();
            main.setPressure(objectMain.getString("pressure"));
            main.setHumidity(objectMain.getString("humidity"));
            main.setTemp_min(objectMain.getString("temp_min"));
            main.setTemp_max(objectMain.getString("temp_max"));
            Log.d(TAG, "getMain: "+main.getTemp());
        } catch (Exception e) {
            Log.d(TAG, "getMain: "+main.getTemp());
            e.printStackTrace();
        }
    }

    public void getWeather() {
        listWeather = new ArrayList<>();
        try {
            String JsonNew=json.substring(4);
            JSONObject object = new JSONObject(json);
            JSONArray jsonArray = object.getJSONArray("weather");
            for (int i = 0; i <= jsonArray.length(); i++) {
                String id = jsonArray.getJSONObject(i).getString("id");
                String main = jsonArray.getJSONObject(i).getString("main");
                String description = jsonArray.getJSONObject(i).getString("description");
                String icon = jsonArray.getJSONObject(i).getString("icon");
                Wearther weather = new Wearther();
                weather.setId(id);
                weather.setMain(main);
                weather.setDescription(description);
                weather.setIcon(icon);
                listWeather.add(weather);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
