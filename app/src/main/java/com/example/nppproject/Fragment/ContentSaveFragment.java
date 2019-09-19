package com.example.nppproject.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nppproject.Adapter.ContentSaveAdapter;
import com.example.nppproject.Entity.ContentEntity;
import com.example.nppproject.Entity.PostEntity;
import com.example.nppproject.R;
import com.example.nppproject.SQL.SQLHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentSaveFragment extends Fragment {
    TextView tvTitle, tvShortTitle, tvTime, tvContent;
    ImageView imgContent;
    RecyclerView rvDetailSave;
    private static ArrayList<PostEntity> mListPost = new ArrayList<>();
    public static ContentEntity contentEntity = new ContentEntity();

    public ContentSaveFragment() {
        // Required empty public constructor
    }

    public static ContentSaveFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("url", url);
        ContentSaveFragment fragment = new ContentSaveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_save, container, false);
        tvContent = view.findViewById(R.id.tvContent);
        tvTitle = view.findViewById(R.id.tvTitle);
        url = getArguments().getString("url");
        tvShortTitle = view.findViewById(R.id.tvShortTitle);
        tvTime = view.findViewById(R.id.tvTime);
        rvDetailSave = view.findViewById(R.id.rvDetailSave);
        //Toast.makeText(getActivity(), "" + url, Toast.LENGTH_SHORT).show();
        mListPost= (ArrayList<PostEntity>) getArguments().getSerializable("mListPost");
        new HtmlReader().execute();
        return view;
    }

    String s;


    public class HtmlReader extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            getHtml(getArguments().getString("url"));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
          // tvContent.setText(contentEntity.getContent());
           tvShortTitle.setText(contentEntity.getShortTittle());
           tvTitle.setText(contentEntity.getTittle());
           tvTime.setText(contentEntity.getPubDate());
            ContentSaveAdapter contentSaveAdapter= new ContentSaveAdapter(contentEntity.getContent());
            rvDetailSave.setAdapter(contentSaveAdapter);
            contentSaveAdapter.notifyDataSetChanged();
        }
    }

    public void getHtml(String url) {

        //Document document;
        try {
            Document document = Jsoup.parse(url);

            //document= Jsoup.connect(url).get();
            s = document.title();


            Log.d("dcm", s);
            if (s.contains(" - ")) {
                contentEntity.setTittle(s.substring(0, s.indexOf(" - ")));
            } else {
                contentEntity.setTittle(s);
            }

            Elements description = document.select("p.description");
            if (description.size() > 0) {
                contentEntity.setShortTittle("\t\t" + description.first().text());
            }
            Elements time = document.select("span.time.left");
            if (time.size() > 0) {
                contentEntity.setPubDate(time.first().text());
            }
            Elements listContent = document.select("p.Normal,p.MsNormal,table.tplCaption,article.content_detail.fck_detail.width_common.block_ads_connect,img");
            if (listContent.size() > 0) {
                ArrayList<String> mListContent = new ArrayList<>();
                //  ArrayList<String> mListP = new ArrayList<>();
                for (int i = 0; i < listContent.size(); i++) {

                    if (listContent.get(i).is("p.Normal") || listContent.get(i).is("p.MsNormal")) {
                        mListContent.add(listContent.get(i).text());
                    } else if (listContent.get(i).is("table.tplCaption")) {

                        //  Elements table = document.select("table.tplCaption");
                        Elements td = listContent.get(i).select("td");
                        Elements img = td.select("img");
                        for (int y = 0; y < img.size(); y++) {
                            mListContent.add(img.get(y).attr("src"));
                        }
                        // Elements srcImg=img.select("img.src");
                        // mListContent.add(img.attr("src"));

                    } else if (listContent.get(i).is("article.content_detail.fck_detail.width_common.block_ads_connect")) {

                        Elements article = listContent.get(i).select("article.content_detail.fck_detail.width_common.block_ads_connect");
                        Elements p = article.select("p");

                        //  mListContent.add();
                        for (int j = 0; j < p.size(); j++) {

                            mListContent.add(p.get(j).text());
                        }
                    }
//                    else if(listContent.get(i).is("img.vne_lazy_image.lazyLoaded")){
//                        Elements img2=listContent.get(i).select("img.vne_lazy_image.lazyLoaded");
//                        for(int b=0;b<img2.size();b++){
//                            mListContent.add(img2.get(b).attr("src.vne_lazy_image.lazyLoaded"));
//                        }
//                    }

                }
                contentEntity.setContent(mListContent);


                Log.d("arr", "getHtml: " + mListContent);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
