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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nppproject.Adapter.ContentAdapter;
import com.example.nppproject.Entity.ContentEntity;
import com.example.nppproject.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    TextView tvTitle, tvShortTitle, tvTime, tvContent;
    ImageView imgContent;
    RecyclerView rvContent;
    ProgressBar progressBar;
  //  private static ArrayList<ContentEntity> mListContent = new ArrayList<>();

    ContentEntity contentEntity = new ContentEntity();
    Element element;

    public static DetailFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("url", url);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Toast.makeText(getActivity(), "" + getArguments().getString("url"), Toast.LENGTH_SHORT).show();
        tvContent = view.findViewById(R.id.tvContent);
        imgContent = view.findViewById(R.id.imgContent);
        rvContent = view.findViewById(R.id.rvDetail);
        progressBar = view.findViewById(R.id.process);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvShortTitle = view.findViewById(R.id.tvShortTitle);
        tvTime = view.findViewById(R.id.tvTime);

        new HtmlReader().execute();

        // Inflate the layout for this fragment
        return view;
    }

    String s;


    public class HtmlReader extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            getHtml(getArguments().getString("url"));
            Log.d("mll", "doInBackground: "+getArguments().getString("url"));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(View.GONE);
            tvShortTitle.setText(contentEntity.getShortTittle());
            tvTitle.setText(contentEntity.getTittle());
            tvTime.setText(contentEntity.getPubDate());

            ContentAdapter contentAdapter = new ContentAdapter(contentEntity.getContent());
            rvContent.setAdapter(contentAdapter);
            contentAdapter.notifyDataSetChanged();

        }
    }


    public void getHtml(String url) {

        //Document document;
        try {
            Document document = Jsoup.connect(url).get();
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
            Elements listContent = document.select("p.Normal,p.MsNormal,table.tplCaption");
            if (listContent.size() > 0) {
                ArrayList<String> mListContent = new ArrayList<>();
                for (int i = 0; i < listContent.size(); i++) {

                    if (listContent.get(i).is("p.Normal") || listContent.get(i).is("p.MsNormal")) {
                        mListContent.add(listContent.get(i).text());
                    } else {
                        if (listContent.get(i).is("table.tplCaption")) {
                            Elements table = document.select("table.tplCaption");
                            Elements td = table.select("td");
                            Elements img = td.select("img");
                            // Elements srcImg=img.select("img.src");
                            mListContent.add(img.attr("src"));



                        }
                    }
                }
                contentEntity.setContent(mListContent);
                Log.d("arr", "getHtml: "+mListContent);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("logd", "getHtml: ");


    }
//    public void parserHtml() {
//        mListContent = new ArrayList<>();
//
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Element element = (Element) nodeList.item(i);
//            ContentEntity contentEntity = new ContentEntity();
//            contentEntity.setTittle();
//            contentEntity.setPubDate();
//            contentEntity.setShortTittle();
//            mListContent.add(contentEntity);
//        }
//
//        int count = 0;
//        for (int i = 0; i < nodeDes.getLength(); i++) {
//            if (i < 1) {
//            } else {
//                Node node = nodeDes.item(i);
//                if (node.getTextContent().trim().equals("No Description")) {
//                    continue;
//                } else {
//                    String des = node.getTextContent();
//                    mListContent.get(count).setDescription(des);
//                    mListContent.get(count).setUrlImg(getUrlImgFromDes(des));
//                    count++;
//                }
//            }
//        }
//    }


}
