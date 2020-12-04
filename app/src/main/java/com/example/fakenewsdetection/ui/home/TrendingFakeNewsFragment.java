package com.example.fakenewsdetection.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakenewsdetection.R;
import com.example.fakenewsdetection.db.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrendingFakeNewsFragment extends Fragment implements TrendingFakeNewsViewAdapter.ItemListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView recyclerView;
    private ArrayList<ModelNews> itemArrayList;
    private DBHelper db;


    public static TrendingFakeNewsFragment newInstance() {
        TrendingFakeNewsFragment fragment = new TrendingFakeNewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        db = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_trending, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.trendingRecyclerView);
        initRecyclerView();
        return root;
    }
    private void initRecyclerView(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        itemArrayList = new ArrayList<>();
        createListData();
        recyclerView.setAdapter(new TrendingFakeNewsViewAdapter(itemArrayList,this));

    }
    private void createListData(){
        ModelNews item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: No, former PM Manmohan Singh is not a chief guest at Joe Biden's swearing-in ceremony",
                "https://www.altnews.in/fact-check-did-former-pm-manmohan-singh-get-invitation-of-joe-biden-oath-ceremony/");
        itemArrayList.add(item);
         item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_opindia,null),
                "Nikkei Asian Review writes a strange, ill-informed article on PM Modi: Here are some things they got wildly wrong",
                "https://www.opindia.com/2020/03/nikkei-asian-review-article-lie-pm-modi-fact-check/");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: Pakistan's lovable mascot 'Chacha Cricket' hits out of boundary his death rumours ",
                "https://www.indiatoday.in/fact-check/story/pakistan-mascot-chacha-cricket-death-rumours-1742746-2020-11-21");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: This is not a video of White House welcoming Joe Biden with Vedic chants",
                "https://www.indiatoday.in/fact-check/story/fact-check-this-is-not-a-video-of-white-house-welcoming-joe-biden-with-vedic-chants-1742419-2020-11-20");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_opindia,null),
                "Pakistani propaganda media outlet spreads fake news of Muslim girl being raped by Hindus in New Delhi’s Jafrabad",
                "https://www.opindia.com/2020/03/pakistani-propaganda-media-outlet-spreads-fake-news-of-muslim-girl-being-raped-by-hindus-in-new-delhis-jafrabad/");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_fact_checker,null),
                "Shabby Homes, Rising Debts: The Trouble With PM�s Rural Housing Fund",
                "https://www.factchecker.in/shabby-homes-rising-debts-the-trouble-with-pms-rural-housing-fund/");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: Viral video of Yogi Adityanath burning crackers is not from Diwali",
                "https://www.indiatoday.in/fact-check/story/fact-check-viral-video-of-yogi-adityanath-burning-crackers-is-not-from-diwali-1742418-2020-11-20");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: Picture of burqa-clad students passed off as Kerala’s all-women police force",
                "https://www.indiatoday.in/fact-check/story/fact-check-picture-of-burqa-clad-students-passed-off-as-kerala-s-all-women-police-force-1742047-2020-11-18");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_fact_checker,null),
                "Yogi Adityanath Claims No Riots In UP In 2 Years Of BJP Govt. Fact: False",
                "https://www.factchecker.in/yogi-adityanath-claims-no-riots-in-up-in-2-years-of-bjp-govt-fact-false/");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: Joe Biden is not apologising to the daughter of a race victim here",
                "https://www.indiatoday.in/fact-check/story/fact-check-joe-biden-is-not-apologising-to-the-daughter-of-a-race-victim-here-1741959-2020-11-18");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: Video of Kejriwal alleging BJP-AIMIM deal in Bihar is four years old",
                "https://www.indiatoday.in/fact-check/story/fact-check-video-of-kejriwal-alleging-bjp-aimim-deal-in-bihar-is-four-years-old-1741337-2020-11-16");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: Joe Biden is not apologising to the daughter of a race victim here",
                "https://www.indiatoday.in/fact-check/story/fact-check-joe-biden-is-not-apologising-to-the-daughter-of-a-race-victim-here-1741959-2020-11-18");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: Picture of burqa-clad students passed off as Kerala’s all-women police force",
                "https://www.indiatoday.in/fact-check/story/fact-check-picture-of-burqa-clad-students-passed-off-as-kerala-s-all-women-police-force-1742047-2020-11-18");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_opindia,null),
                "Pakistani propaganda media outlet spreads fake news of Muslim girl being raped by Hindus in New Delhi’s Jafrabad",
                "https://www.opindia.com/2020/03/pakistani-propaganda-media-outlet-spreads-fake-news-of-muslim-girl-being-raped-by-hindus-in-new-delhis-jafrabad/");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: Picture of burqa-clad students passed off as Kerala’s all-women police force",
                "https://www.indiatoday.in/fact-check/story/fact-check-picture-of-burqa-clad-students-passed-off-as-kerala-s-all-women-police-force-1742047-2020-11-18");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: Viral video of Yogi Adityanath burning crackers is not from Diwali",
                "https://www.indiatoday.in/fact-check/story/fact-check-viral-video-of-yogi-adityanath-burning-crackers-is-not-from-diwali-1742418-2020-11-20");
        itemArrayList.add(item);
        item = new ModelNews(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_indiatoday,null),
                "Fact Check: This video of illegal voting is not from Bihar",
                "https://www.indiatoday.in/fact-check/story/video-of-illegal-voting-not-from-bihar-fact-check-1741308-2020-11-16");
        itemArrayList.add(item);

    }

    @Override
    public void onItemClick(String link) {

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        String date = sdf.format(new Date());
        Log.d("date",date);

        db.insertHistory(link,date);


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);

    }


}