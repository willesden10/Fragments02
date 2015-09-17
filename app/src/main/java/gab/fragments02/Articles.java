package gab.fragments02;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Articles extends Fragment {
    public static final String ARG_POSITION = "position";
    private int mCurrentPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if(savedInstanceState != null)
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);

        View rootView = inflater.inflate(R.layout.articles_layout,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        //If there are arguments from the MainActivity the corresponding article is shown
        if(args != null){
            updateArticleView(args.getInt(ARG_POSITION));
        }

        //If there are not arguments (activity is recreated) the article shown is the one saved in save instance state
        else if(mCurrentPosition != -1){
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position){

        TextView textView = (TextView) getView();

        textView.setText(NewsArray.Articles[position]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION,mCurrentPosition);
    }
}
