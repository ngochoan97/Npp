package com.example.nppproject.Fragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.nppproject.Adapter.VideoAdapter;
import com.example.nppproject.Entity.VideoEntity;
import com.example.nppproject.Globals;
import com.example.nppproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoHomeFragment extends Fragment {
    static String urlHotvideo;
    ProgressBar pgBar;
    VideoAdapter videoAdapter;
    RecyclerView rvListVideo;
    ArrayList<VideoEntity> mListVideo = new ArrayList<>();
    ArrayList<VideoEntity> mListSend = new ArrayList<>();
    String jsonCate = "";

    public static VideoHomeFragment newInstance(String urlHotvideo) {

        Bundle args = new Bundle();
        args.putString("urlHotvideo", urlHotvideo);
        Log.d(TAG, "newInstance: " + urlHotvideo);
        VideoHomeFragment fragment = new VideoHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public VideoHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hotvideo, container, false);

        try {
            urlHotvideo = getArguments().getString("urlHotvideo");
       //     Log.d(TAG, "onCreateView: " + urlHotvideo);
        } catch (Exception e) {
            urlHotvideo = Globals.URL_HOTVIDEO;
        }
        rvListVideo = view.findViewById(R.id.rvPostVD);
        // Inflate the layout for this fragment
        new JsonReader().execute();

        return view;
    }

    private static final String TAG = "VideoHomeFragment";

    public void getJson(String urlHotvideo) {

        try {
            URL url = new URL(urlHotvideo);
            Log.d(TAG, "getJsonItemCategory: " + url);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            int byteChar;
            while ((byteChar = is.read()) != -1) {
                jsonCate += (char) byteChar;
               // Log.d(TAG, "getJson: " + jsonCate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListVideo() {
        try {
            JSONArray jsonArray = new JSONArray(jsonCate);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                VideoEntity videoEntity = new VideoEntity();
                videoEntity.setId(jsonObject1.getString("id"));
                videoEntity.setProvider_id(jsonObject1.getString("provider_id"));
                videoEntity.setCategory_id(jsonObject1.getString("category_id"));
                videoEntity.setAvater(jsonObject1.getString("avatar"));
                videoEntity.setTitle(jsonObject1.getString("title"));
                videoEntity.setPrice(jsonObject1.getString("price"));
                videoEntity.setStatus(jsonObject1.getString("status"));
                videoEntity.setCoppyright(jsonObject1.getString("copyright"));
                videoEntity.setArtist_name(jsonObject1.getString("artist_name"));
                videoEntity.setAlbum_name(jsonObject1.getString("album_name"));
                videoEntity.setFile_mp4(jsonObject1.getString("file_mp4"));
                videoEntity.setDate_published(jsonObject1.getString("date_published"));
                videoEntity.setYoutube_url(jsonObject1.getString("youtube_url"));
                videoEntity.setDownload_status(jsonObject1.getString("download_status"));
                mListVideo.add(videoEntity);
                mListSend.add(videoEntity);
            }
            Log.d(TAG, "onCreateView: " + mListVideo.get(0).getFile_mp4());
            Log.d(TAG, "getListVideo: "+mListVideo.get(0).getTitle());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class JsonReader extends AsyncTask<ArrayList<VideoEntity>, Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<VideoEntity>... arrayLists) {
            if (mListVideo.size() == 0) {
                getJson(urlHotvideo);
                Log.d(TAG, "doInBackground12321: " + urlHotvideo);
                getListVideo();

            }
            return null;
        }

        private static final String TAG = "JsonReader";
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            videoAdapter = new VideoAdapter(mListVideo);
            //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false)
            rvListVideo.setAdapter(videoAdapter);
            videoAdapter.setOnClickItemListener(new VideoAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v, boolean check) {
                    String url = "";

                    VideoEntity videoEntity = mListVideo.get(position);

                    url = videoEntity.getFile_mp4();

                    mListSend.remove(position);
                   PlayVideoFragment playVideoFragment = PlayVideoFragment.newInstance(url,mListVideo,mListSend,videoEntity,position);
                   getFragmentManager().beginTransaction().replace(R.id.container,playVideoFragment).addToBackStack("stack").commit();
                    Log.d(TAG, "onItemClick: "+url);

                }

            });
            videoAdapter.notifyDataSetChanged();
        }
    }
}
