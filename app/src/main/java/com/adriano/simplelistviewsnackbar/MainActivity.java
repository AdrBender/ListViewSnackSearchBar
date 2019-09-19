package com.adriano.simplelistviewsnackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;

import android.app.SearchManager;
import android.os.Bundle;
import android.graphics.Color;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;

import android.content.Context;
import android.content.res.Resources;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	
	ListView lv;
	SearchView searchView;
	CustomAdapter adapter;
	CoordinatorLayout coordinatorLayout;
	
	//ArrayAdapter<String> adapter;
	ArrayList<Lista> itemLista = new ArrayList<>();;
	
	int imagemPlanetas[] = {
		R.drawable.mercurio,
		R.drawable.venus,
		R.drawable.terra,
		R.drawable.marte,
		R.drawable.jupiter,
		R.drawable.saturno,
		R.drawable.netuno,
		R.drawable.urano
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cLayout);
		lv = (ListView) findViewById(R.id.list_view);
		
		 //adapter = new ArrayAdapter<String>(this, R.layout.item_list, R.id.item_listTextView, products);
		 //lv.setAdapter(adapter);
		 		
		Resources res = getResources();
		//int[] imagemPlanetas = res.getIntArray(R.array.planetas_imagens);
		String[] planetasArray = res.getStringArray(R.array.planetas_nomes);
		
		//adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray);
		//lv.setAdapter(adapter);
		
		for (int i = 0; i < imagemPlanetas.length; i++) {
			//Lista lista = itemLista.get(i);
			Lista lista = new Lista();
			
			lista.setImagem(imagemPlanetas[i]);
            lista.setPlaneta(planetasArray[i]);

            itemLista.add(lista);
		}

        final CustomAdapter adapter = new CustomAdapter(getApplicationContext(), itemLista);
        lv.setAdapter(adapter);
	
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            	@Override
            	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                	Lista lista = itemLista.get(position);
            		itemLista.remove(lista);
					adapter.notifyDataSetChanged();
				
                	Snackbar snackbar = Snackbar.make(view, lista.getPlaneta() + " Removido", Snackbar.LENGTH_LONG)
                       //.setAction("No action", null).show();
						.setAction("RETRY", new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								Snackbar snackBar = Snackbar.make(coordinatorLayout,"Undo successful", 
																 Snackbar.LENGTH_SHORT);
								//View v = snackBar.getView();
								view = snackBar.getView(); 
								TextView textView = view.findViewById(android.support.design.R.id.snackbar_text); 
								textView.setTextColor(Color.GREEN); 
								snackBar.show(); 
							}
						});
                	snackbar.setActionTextColor(Color.RED);
                	View v = snackbar.getView();
                	TextView textView = v.findViewById(android.support.design.R.id.snackbar_text);
                	textView.setTextColor(Color.YELLOW);
                	snackbar.show();
				}
			});
			
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab); 
			fab.setOnClickListener(new View.OnClickListener() { 
				@Override 
				public void onClick(View v) { 
					Toast.makeText(MainActivity.this,"Floating Action Button!", Toast.LENGTH_SHORT).show(); 
				} 
			});
    	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.action_bar, menu);

		SearchManager searchManager = 
			(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = 
			(SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
			searchManager.getSearchableInfo(getComponentName()));
		
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
				
            @Override
            public boolean onQueryTextChange(String newText) {
				String text = newText;
                adapter.filter(text);
 
                return false;
            }
        });
        return true;
    }
}
