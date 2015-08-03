package thinking.codebender.amawulire.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import thinking.codebender.amawulire.R;
import thinking.codebender.amawulire.activities.DetailView;
import thinking.codebender.amawulire.adapter.MyViewHolder;
import thinking.codebender.amawulire.adapter.RecyclerViewAdapter;
import thinking.codebender.amawulire.adapter.RecyclerViewItemClickHandler;
import thinking.codebender.amawulire.models.Articles;


public class NewsFragment extends Fragment implements RecyclerViewAdapter.OnClickListener {

    AQuery aQuery;
    RecyclerView recyclerView;
    TextView empty_label;
    List<Articles> articles = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    Articles theArticle;
    public static final String ARG_PAGE = "Page";
    // News items json url
    private String url; // = "http://api.nytimes.com/svc/news/v3/content/all/arts/24.json?limit=10&offset=1&api-key=4290b3a796179d76075e959708609a83%3A19%3A72572708";
    private static final String TAG_ITEM_TITLE = "title";
    private static final String TAG_ITEM_CONTENT = "abstract";

    public static NewsFragment create(String URL) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString("url", URL);
        fragment.setArguments(args);
        return fragment;
    }


    public NewsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        url = getArguments().getString("url");

        //instantiate the a query object
        aQuery = new AQuery(getActivity());

        //call the method to make the ajax call
        asyncJson();

    }

    //call using
    public void asyncJson() {
        //let a query check for the if the data is available in the cache before making another call

        long expire = 15 * 60 * 1000;

        aQuery.ajax(url, JSONObject.class, expire, new AjaxCallback<JSONObject>() {

            @Override

            public void callback(String url, JSONObject object, AjaxStatus status) {

                super.callback(url, object, status);


                Log.v("Json Object.....", "" + object);

                if (object != null) {


                    try {
                        JSONArray popular = object.getJSONArray("results");

                        for (int i = 0; i < popular.length(); i++) {
                            JSONObject currentArticle = popular.getJSONObject(i);
                            String title = Html.fromHtml(currentArticle.getString(TAG_ITEM_TITLE)).toString();
                            String excerpt = Html.fromHtml(currentArticle.getString(TAG_ITEM_CONTENT)).toString();
                            String imageUrl = getImageUrlFromJson(currentArticle);

                            articles.add(new Articles(title, excerpt, imageUrl, "", ""));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    recyclerViewAdapter.notifyDataSetChanged();
                    empty_label.setVisibility(View.INVISIBLE);

                } else {

                    Toast.makeText(aQuery.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    private String getImageUrlFromJson(JSONObject currentArticle) {

        //resolve the nested JSONArray under each result JSONObject
        JSONArray media = null;
        try {
            media = currentArticle.optJSONArray("media");
            if(media != null ) {
                JSONArray media_metadata = media.getJSONObject(0).getJSONArray("media-metadata");
                String imageUrl = media_metadata.getJSONObject(2).getString("url");
                Log.i("imageUrl", imageUrl);
                return imageUrl;
            }else{
                return "https://placeholdit.imgix.net/~text?txtsize=13&txt=image+no+available&w=150&h=100&txttrack=0";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_1, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        empty_label = (TextView) v.findViewById(R.id.empty_text);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        if (articles.isEmpty()) {
            empty_label.setVisibility(View.VISIBLE);
        }
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), articles);
        //recyclerViewAdapter.setOnRecyclerItemListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickHandler(getActivity(), new RecyclerViewItemClickHandler.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                // Articles art = (Articles) recyclerViewAdapter.getItemCount(position);

                Intent newActivity = new Intent(getActivity(), DetailView.class);
                newActivity.putExtra("theArticle", articles.get(position));

                startActivity(newActivity);


            }
        }));


        return v;


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View view, MyViewHolder holder) {

    }
}
