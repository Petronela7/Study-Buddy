package com.example.studybuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchActivity2 extends Activity {

    ListView lv;
    SearchView sv;

    String[] array = {"Analiza","Algebra","Geometrie","OOP","SO","SDA","Economie","Literatura"};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        lv = (ListView) findViewById(R.id.listView1);
        sv = (SearchView) findViewById(R.id.searchView1);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        lv.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
