package com.example.nppproject.Fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.nppproject.Adapter.ContentAdapter;
import com.example.nppproject.Adapter.PostAdapter;
import com.example.nppproject.Adapter.RelateAdapter;
import com.example.nppproject.Entity.ContentEntity;
import com.example.nppproject.Entity.PostEntity;
import com.example.nppproject.R;
import com.example.nppproject.SQL.SQLHelper;

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
    TextView tvTitle, tvShortTitle, tvTime, tvContent, tvRelate;
    ImageView imgContent;
    PostAdapter postAdapter;
    RelateAdapter relateAdapter;
    RecyclerView rvContent, rvRelate;
    ProgressBar progressBar;
    Toolbar toolbar;
    String saveDoc;


    SQLHelper sqlHelper;

    private static ArrayList<PostEntity> mListPost = new ArrayList<>();
    // private static ArrayList<ContentEntity> mListContent = new ArrayList<>();

    public static ContentEntity contentEntity = new ContentEntity();
    Element element;

    public static DetailFragment newInstance(String url, ArrayList<PostEntity> mListPost) {

        Bundle args = new Bundle();
        args.putString("url", url);
        args.putSerializable("mListPost", mListPost);
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
        //  Toast.makeText(getActivity(), "" + getArguments().getString("url"), Toast.LENGTH_SHORT).show();
        tvContent = view.findViewById(R.id.tvContent);
        imgContent = view.findViewById(R.id.imgContent);
        rvContent = view.findViewById(R.id.rvDetail);
        rvRelate = view.findViewById(R.id.rvRelate);
        progressBar = view.findViewById(R.id.process);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvShortTitle = view.findViewById(R.id.tvShortTitle);
        tvTime = view.findViewById(R.id.tvTime);
        tvRelate = view.findViewById(R.id.tvRelate);
        toolbar = view.findViewById(R.id.toolbar);
        mListPost = (ArrayList<PostEntity>) getArguments().getSerializable("mListPost");

        new HtmlReader().execute();

        // Inflate the layout for this fragment

        return view;

    }

    String s;


    public class HtmlReader extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            getHtml(getArguments().getString("url"));
            //Log.d("mll", "doInBackground: " + getArguments().getString("url"));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(View.GONE);
            tvShortTitle.setText(contentEntity.getShortTittle());
            tvTitle.setText(contentEntity.getTittle());
            tvTime.setText(contentEntity.getPubDate());
            tvRelate.setText(R.string.tv_relate);
            ContentAdapter contentAdapter = new ContentAdapter(contentEntity.getContent());
            rvContent.setAdapter(contentAdapter);
            contentAdapter.notifyDataSetChanged();

            RelateAdapter relateAdapter = new RelateAdapter(mListPost);
            rvRelate.setAdapter(relateAdapter);
            relateAdapter.notifyDataSetChanged();
            relateAdapter.setOnClickListener(new RelateAdapter.ClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String url = mListPost.get(position).getLink();
                    DetailFragment detailFragment = DetailFragment.newInstance(url, mListPost);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, detailFragment).addToBackStack("stack").commit();
                }
            });
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext(),RecyclerView.VERTICAL,false);
//            rvRelate.setAdapter(relateAdapter);
//            rvRelate.setLayoutManager(layoutManager);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
//            rvVideoCategory.setAdapter(new SubCategoryAdapter(videoListCategory));
//            rvVideoCategory.setLayoutManager(layoutManager);


//            PostAdapter postAdapter = new PostAdapter(mListPost);
//            rvRelate.setAdapter(postAdapter);
//            postAdapter.notifyDataSetChanged();
//            postAdapter.setOnItemClickListener(new PostAdapter.ClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                String url=mListPost.get(position).getLink();
//                DetailFragment detailFragment=DetailFragment.newInstance(url,mListPost);
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,detailFragment).commit();
//                }
//            });

        }
    }


    public void getHtml(String url) {

        //Document document;
        try {
            Document document = Jsoup.connect(url).get();
            saveDoc = document.html();
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
                sqlHelper = new SQLHelper(getContext());
                sqlHelper.insertContent(s, saveDoc);
            }


        } catch (
                IOException e) {
            e.printStackTrace();
        }
        Log.d("logd", "getHtml: ");
    }

    private static final String TAG = "DetailFragment";
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


    @Override
    public void onDestroy() {
        super.onDestroy();
      //  getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

        Log.d(TAG, "onDestroyFragment: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResumeDetail: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyViewDetail: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStopDetail: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttachDetail: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPauseDetail: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetachDetail: ");
//        AllPopBackStack();
    }

    public void AllPopBackStack() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }
        }
    }
}
