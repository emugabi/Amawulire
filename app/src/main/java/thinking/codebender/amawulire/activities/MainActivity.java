package thinking.codebender.amawulire.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import thinking.codebender.amawulire.R;
import thinking.codebender.amawulire.ui.SlidingTabLayout;
import thinking.codebender.amawulire.adapter.ViewPagerAdapter;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    SlidingTabLayout tabs;
    String TAG = MainActivity.class.getSimpleName();
    static Map<String, String> categoryUrl  = new HashMap<>();

    static { //a record of the category urls to be passed to the fragment according to user selection

        categoryUrl.put("Trending", "http://api.nytimes.com/svc/news/v3/content/all/arts/24.json?limit=10&offset=1&api-key=4290b3a796179d76075e959708609a83%3A19%3A72572708");
        categoryUrl.put("Entertainment", "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/world/7.json?offset=20&api-key=0c929dc714c1cc67da39189be1b8ec9b%3A15%3A72572708");
        categoryUrl.put("Business", "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/world/7.json?offset=20&api-key=0c929dc714c1cc67da39189be1b8ec9b%3A15%3A72572708");
        categoryUrl.put("Fashion", "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/world/7.json?offset=20&api-key=0c929dc714c1cc67da39189be1b8ec9b%3A15%3A72572708");
        categoryUrl.put("Travel", "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/world/7.json?offset=20&api-key=0c929dc714c1cc67da39189be1b8ec9b%3A15%3A72572708");
        categoryUrl.put("LifeStyle", "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/world/7.json?offset=20&api-key=0c929dc714c1cc67da39189be1b8ec9b%3A15%3A72572708");

    }
    String Titles[] = null;

  //  String[] Urls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getUserPrefs();


    }

    private void loadFragments(int NumberOfTabs) {

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, NumberOfTabs, categoryUrl);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(viewPagerAdapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(android.R.color.white);
            }
        });

        tabs.setViewPager(viewPager);
    }

    private void getUserPrefs() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String categories = sharedPref.getString("categories", null);
        if (categories != null) {

            Gson gson = new Gson();
            Titles = gson.fromJson(categories, String[].class);

            loadFragments(Titles.length);
        } else {
            Log.d(TAG, "json is null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentToNewsCategorySelection = new Intent(MainActivity.this, NewsCategorySelection.class);
            intentToNewsCategorySelection.putExtra("fromMainActivity", true);
            startActivity(intentToNewsCategorySelection);
            return true;
        }

        else if (id == R.id.action_signin){

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
