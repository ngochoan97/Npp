package com.example.nppproject.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.solver.GoalRow;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nppproject.Adapter.PostAdapter;
import com.example.nppproject.Entity.PostEntity;
import com.example.nppproject.Globals;
import com.example.nppproject.Public.PublicMethod;
import com.example.nppproject.R;
import com.example.nppproject.SQL.SQLHelper;
import com.example.nppproject.XMLDOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    SQLHelper sqlHelper;
    Context mContext;
    private static ArrayList<PostEntity> mListPost;
    private static ArrayList<PostEntity> mListPost2;
    RecyclerView rvPost;
    PostAdapter postAdapter;
    ProgressBar progressBar;
    TextView tvTitleTop,tvTimeTop;
    ImageView imgTop;
    LinearLayout llTop;
    private static final String TAG = "HomeFragment";
    static String urlRss;
    PublicMethod publicMethod= new PublicMethod();
    public HomeFragment() {
        // Required empty public constructor

    }

    public static HomeFragment newInstance(String rssUrl) {

        Bundle args = new Bundle();
        args.putString("rssUrl", rssUrl);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            urlRss = getArguments().getString("rssUrl");
            Log.d(TAG, "onCreateView: link :  "+urlRss);
        }catch (Exception e){
            urlRss = Globals.URL_HOME;
        }
        try {
            AllPopBackStack();
        } catch (Exception e) {
            e.printStackTrace();
        }
        llTop=view.findViewById(R.id.llTop);
        tvTimeTop=view.findViewById(R.id.tvTimeTop);
        tvTitleTop=view.findViewById(R.id.tvTitleTop);
        imgTop=view.findViewById(R.id.imgTop);
      //  Toast.makeText(getActivity(), "" + urlRss, Toast.LENGTH_SHORT).show();
        rvPost = view.findViewById(R.id.rvHomePost);
       llTop.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DetailFragment detailFragment=DetailFragment.newInstance(mListPost.get(0).getLink(),mListPost);
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,detailFragment).addToBackStack("stack").commit();
           }
       });
        progressBar = view.findViewById(R.id.process);
        if (publicMethod.checkConnectInternet(getContext())==false)
        {Toast.makeText(getContext(), "Mất mạng rồi", Toast.LENGTH_SHORT).show();
//            Glide.with(mContext).load(R.mipmap.crop).into(imgNetwork);
            NetWorkFragment netWorkFragment=NetWorkFragment.newInstance(urlRss);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,netWorkFragment).addToBackStack("stack").commit();
        }
        else
        {
            //imgNetwork.setVisibility(View.GONE);
//            new RSSReader().execute();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (publicMethod.checkConnectInternet(getContext())==false)
        {Toast.makeText(getContext(), "Mất mạng rồi", Toast.LENGTH_SHORT).show();
//            Glide.with(mContext).load(R.mipmap.crop).into(imgNetwork);
            NetWorkFragment netWorkFragment=NetWorkFragment.newInstance(urlRss);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,netWorkFragment).addToBackStack("stack").commit();
        }
        else
        {
            //imgNetwork.setVisibility(View.GONE);
            new RSSReader().execute();
        }

    }

    public class RSSReader extends AsyncTask<Void, Void, Void> {

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            progressBar.setVisibility(View.VISIBLE);
            parserXML();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(aVoid);

//            Log.d(TAG, "onPostExecute: " + mListPost.get(0).getTitle());
            postAdapter = new PostAdapter(mListPost2);

            rvPost.setAdapter(postAdapter);
            postAdapter.notifyDataSetChanged();
           // Toast.makeText(getActivity(), "" + mListPost.size(), Toast.LENGTH_SHORT).show();
            postAdapter.setOnItemClickListener(new PostAdapter.ClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String url = mListPost.get(position).getLink();
                    String title= mListPost.get(position).getTitle();
                    //Toast.makeText(getActivity(), "đã click", Toast.LENGTH_SHORT).show();
                    DetailFragment detailFragment = DetailFragment.newInstance(url,mListPost);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, detailFragment).addToBackStack("stack").commit();

                  //  Toast.makeText(getActivity(), "" + url, Toast.LENGTH_SHORT).show();

                }
            });
            try {
                 tvTimeTop.setText(mListPost.get(0).getTime());
                tvTitleTop.setText(mListPost.get(0).getTitle());
                Glide.with(getActivity().getBaseContext()).load(mListPost.get(0).getUrlImg()).into(imgTop);
            }catch (Exception e){
                e.printStackTrace();
            }

//            back();

        }
    }


    public String getRSSFromURL(String urlRss) {
        String rss = "";
        try {
            URL urlHome = new URL(urlRss);
            URLConnection urlConnection = urlHome.openConnection();
            InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            int bytechar;
            while ((bytechar = bufferedReader.read()) != -1) {
                rss += (char) bytechar;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rss;
    }


    public void parserXML() {
        mListPost = new ArrayList<>();
        mListPost2 = new ArrayList<>();
        XMLDOMParser xmldomParser = new XMLDOMParser();
        Document document = xmldomParser.getDocument(getRSSFromURL(urlRss));

        NodeList nodeList = document.getElementsByTagName("item");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            PostEntity postEntity = new PostEntity();
            postEntity.setTitle(xmldomParser.getValue(element, "title"));
            postEntity.setTime(xmldomParser.getValue(element, "pubDate"));
            postEntity.setLink(xmldomParser.getValue(element, "link"));

            if(mListPost.size()>=1){

                mListPost2.add(postEntity);
            }
            mListPost.add(postEntity);
        }
        NodeList nodeDes = document.getElementsByTagName("description");
        int count = 0;
        for (int i = 0; i < nodeDes.getLength(); i++) {
            if (i < 1) {
            } else {
                Node node = nodeDes.item(i);
                if (node.getTextContent().trim().equals("No Description")) {
                    continue;
                } else {
                    String des = node.getTextContent();
                    mListPost.get(count).setDescription(des);
                    mListPost.get(count).setUrlImg(getUrlImgFromDes(des));
                    Log.d(TAG, "parserXML: "+mListPost.get(count).getUrlImg());
                    count++;
                }
            }
        }
    }

    public String getUrlImgFromDes(String des) {
        try{
            int start = des.lastIndexOf("https:");
            int end = des.lastIndexOf("\" >");
            String url = des.substring(start, end);
            return url;
        }catch (Exception e){
            return "http://leeford.in/wp-content/uploads/2017/09/image-not-found.jpg";
        }

    }
    public void back(){getActivity().getSupportFragmentManager().popBackStack();}

    public void AllPopBackStack() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //  getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

        Log.d(TAG, "onDestroyFragment: ");
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


}
