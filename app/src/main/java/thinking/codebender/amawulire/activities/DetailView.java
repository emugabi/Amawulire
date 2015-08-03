package thinking.codebender.amawulire.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import thinking.codebender.amawulire.R;
import thinking.codebender.amawulire.models.Articles;


public class DetailView extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        Articles theArticle = (Articles) getIntent().getSerializableExtra("theArticle");

        if (theArticle != null) {
            theArticle.getImage();
            TextView excerptText = (TextView) findViewById(R.id.theExcerpt);
            ImageView imageView = (ImageView) findViewById(R.id.theImage);

            excerptText.setText(Html.fromHtml(theArticle.getExcerpt()));
            AQuery aQuery = new AQuery(this);

            aQuery.id(imageView).image(theArticle.getImage(), true, true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
