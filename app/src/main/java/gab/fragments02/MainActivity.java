package gab.fragments02;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements Headers.onHeadlineSelectedListener{
    private static final String CURRREN_POS = "current_pos";
    private int mCurrentPosition = -1;

    @Override
    public void onArticleSelected(int position){
        mCurrentPosition = position;

        //We are in a single panel mode.
        if(findViewById(R.id.articles_fragment) == null){
            Bundle bundle = new Bundle();
            bundle.putInt(Articles.ARG_POSITION, position);

            Articles  articles = new Articles();
            articles.setArguments(bundle);

            FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, articles);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        //We are in two panel mode
        else{
            Articles articles = (Articles) getFragmentManager().findFragmentById(R.id.articles_fragment);
            articles.updateArticleView(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //the fragment headers is loaded if we are in portrait -> fragment_container == null
        //and if the activity is created for the first time.
        if(findViewById(R.id.fragment_container) != null && savedInstanceState == null){
            Headers headers = new Headers();
            headers.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment_container, headers).commit();
        }

        if(savedInstanceState != null) mCurrentPosition = savedInstanceState.getInt(CURRREN_POS);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(findViewById(R.id.articles_fragment) == null){
            Headers headers = new Headers();
            headers.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment_container, headers).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if( mCurrentPosition != -1){
            if (findViewById(R.id.articles_fragment) != null) {
                Articles articles = (Articles) getFragmentManager().findFragmentById(R.id.articles_fragment);
                articles.updateArticleView(mCurrentPosition);
            }
            else{
                Bundle bundle = new Bundle();
                bundle.putInt(Articles.ARG_POSITION, mCurrentPosition);

                Articles articles = new Articles();
                articles.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,articles);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRREN_POS,mCurrentPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
