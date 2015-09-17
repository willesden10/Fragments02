package gab.fragments02;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Headers extends ListFragment {
    onHeadlineSelectedListener mCallBack;

    public interface onHeadlineSelectedListener{
        public void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallBack = (onHeadlineSelectedListener) activity;
        }
        catch(ClassCastException exc){
            throw new ClassCastException(MainActivity.class.toString() + " Must implement onHeadlineSelectedListener interface");
        }
    }

    //ListFragment has a default layout that consists of a single list view.
    // However, if you desire, you can customize the fragment layout
    // by returning your own view hierarchy from onCreateView(LayoutInflater, ViewGroup, Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this shows a list in which each element has two lines
        //The data of each element is contained in a Map (HashMap)
        //Each line of the list is an element of the Lis (ArrayList)
//        HashMap<String, String> hashMap01 = new HashMap<>();
//        hashMap01.put("header", "HEADER 01");
//        hashMap01.put("subheader", "sub header 01");
//
//        HashMap<String, String> hashMap02 = new HashMap<>();
//        hashMap02.put("header", "HEADER 02");
//        hashMap02.put("subheader", "sub header 02");
//
//        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
//        arrayList.add(hashMap01);
//        arrayList.add(hashMap02);
//
//        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
//                arrayList, android.R.layout.two_line_list_item,
//                new String[]{"header", "subheader"},
//                new int[]{android.R.id.text1, android.R.id.text2});
//
//        setListAdapter(simpleAdapter);

        //Shows a list in which each item is an textView, the text of each TextView is bounded with the string array in NewsArray.Headlines.
        int layout = android.R.layout.simple_list_item_activated_1;
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, NewsArray.Headlines));
    }

    @Override
    public void onStart() {
        super.onStart();
        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.articles_fragment) != null) {
            //getListView() Get the activity's list view widget.
            //setChoiceMode(int) Defines the choice behavior for the List.
            // By default, Lists do not have any choice behavior (CHOICE_MODE_NONE).
            // By setting the choiceMode to CHOICE_MODE_SINGLE, the List allows up to one item to be in a chosen state.
            // By setting the choiceMode to CHOICE_MODE_MULTIPLE, the list allows any number of items to be chosen.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    //This method will be called when an item in the list is selected
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallBack.onArticleSelected(position);
    }
}
