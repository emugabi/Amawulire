package thinking.codebender.amawulire.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import thinking.codebender.amawulire.R;
import thinking.codebender.amawulire.ui.TouchCheckBox;

public class NewsCategorySelection extends ActionBarActivity {
    ListView category_list;
    TextView category_count;
    String[] category_names = {"Trending", "Entertainment", "Business", "Fashion", "Lifestyle", "Travel"};
    List<View> buttonViewList = new ArrayList();
    List<String> categoryList = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_category_selection);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        if(intent != null){
            Boolean fromMainActivity = intent.getBooleanExtra("fromMainActivity", false);
            if(!fromMainActivity){
                checkIfFirstRun();
            }
        }




        category_list = (ListView) findViewById(R.id.listview_category);
        category_count = (TextView) findViewById(R.id.textview_category_count);
        category_count.setText("0");
        category_list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return category_names.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    v = View.inflate(NewsCategorySelection.this, R.layout.category_single_item, null);
                }
                TextView category_name = (TextView) v.findViewById(R.id.category_name);
                category_name.setText(category_names[position]);
                TouchCheckBox touchCheckBox = (TouchCheckBox) v.findViewById(R.id.category_checkbox);
                touchCheckBox.setOnCheckedChangeListener(new TouchCheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(View buttonView, boolean isChecked) {
                        categoryCheckCouner(buttonView, isChecked, position);
                    }
                });
                return v;
            }
        });
    }

    private void checkIfFirstRun() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Boolean isFirstRun = sharedPref.getBoolean("firstRun", true);

        if(isFirstRun == false){
            startActivity(new Intent(NewsCategorySelection.this, MainActivity.class));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_category_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {

            savePreferences(categoryList);
            startActivity(new Intent(NewsCategorySelection.this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void savePreferences(List<String> categoryList) {
        //save preferences.
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();

        //convert category list into json array string for easy saving.
        String json = new Gson().toJson(categoryList);

        edit.putString("categories", json);
        edit.putBoolean("firstRun", false); //set firstRun to false to skip this screen at startup
        Log.d("SavePrefs", json);
        edit.commit();
    }

    /**
     * This method tracks the unique button id's and computes the checked categories
     */
    public void categoryCheckCouner(View buttonView, boolean isChecked, int position) {


        if (buttonViewList.contains(buttonView)) { //button is in already in list
            if (isChecked) {
                //do nothing
            } else {
                //button has been unchecked
                categoryList.remove(buttonViewList.indexOf(buttonView));
                buttonViewList.remove(buttonView);
            }
        } else {  //button is new in List
            if (isChecked) {
                buttonViewList.add(buttonView);
                categoryList.add(category_names[position]);
            }
        }

        category_count.setText("" + categoryList.size());
    }
}
