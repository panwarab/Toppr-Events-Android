package abhiroj95.com.topprandroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import abhiroj95.com.topprandroid.Fragments.EventDetailFragment;
import abhiroj95.com.topprandroid.Fragments.FavoriteEventDetailFragment;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i=getIntent();
        String fragtolaunch=i.getExtras().getString("FRAGMENT");
        if(fragtolaunch.equals("Category")) {
            getFragmentManager().beginTransaction().replace(R.id.Event_Detail,new EventDetailFragment()).commitAllowingStateLoss();
        }
        else
        {
            getFragmentManager().beginTransaction().replace(R.id.Event_Detail,new FavoriteEventDetailFragment()).commitAllowingStateLoss();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,"Hi, I would like to invite you at hacker earth.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
