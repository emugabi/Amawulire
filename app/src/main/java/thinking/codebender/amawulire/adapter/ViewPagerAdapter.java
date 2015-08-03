package thinking.codebender.amawulire.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Map;

import thinking.codebender.amawulire.fragments.NewsFragment;

/**
 * Created by codebender on 7/25/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    String Titles[];
    int NumberOfTabs;
    Map<String, String>  Urls;

    public ViewPagerAdapter( FragmentManager fm, String mTitles[], int mNumberOfTabsHere, Map<String, String> mUrls) {
        super(fm);
        this.NumberOfTabs = mNumberOfTabsHere;
        this.Titles = mTitles;
        this.Urls = mUrls;
    }

    @Override
    public Fragment getItem(int position) {

      /*  if (position == 0) {

            Tab1 tab1 = new Tab1();
            return tab1;
        } else if (position == 1)  {

            Tab2 tab2 = new Tab2();

            return  tab2;
        } else if(position == 2 )  {

            Tab3 tab3 = new Tab3();
            return  tab3;
        } else {

            Tab4 tab4 = new Tab4();
            return tab4;
        }

*/
        //get category
        String categoryTitle = Titles[position];

        return  NewsFragment.create(Urls.get(categoryTitle)); //Url is the HashMap and categoryTitle is the key we set for each url
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return Titles[position];

    }

    @Override
    public int getCount() {

        return NumberOfTabs;
    }


}
