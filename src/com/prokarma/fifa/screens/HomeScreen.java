package com.prokarma.fifa.screens;


import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.prokarma.fifa.BaseActivity;
import com.prokarma.fifa.R;
import com.prokarma.fifa.controller.components.ActionBarController;
import com.prokarma.loggers.FifaLog;
import com.prokarma.loggers.Severity;

public class HomeScreen extends BaseActivity implements ActionBarController{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mFifatitles;
	
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.home_screen);
        	
        	mTitle = mDrawerTitle = getTitle();
        	FifaLog.i(Severity.LOW, "$$$ mTitle "+mTitle);
        	
        	mFifatitles = getResources().getStringArray(R.array.menu_array);
        	
        	mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        	mDrawerList = (ListView)findViewById(R.id.left_drawer);
        	
        	// set a custom shadow that overlays the main content when the drawer opens
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
            
         // set up the drawer's list view with items and click listener
            mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                    R.layout.drawer_list_item, mFifatitles));
            
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        	
        	getActionBar().setDisplayHomeAsUpEnabled(IsDisplayHomeAsUpEnabled);
        	getActionBar().setHomeButtonEnabled(true);
        	
        	// ActionBarDrawerToggle ties together the the proper interactions
            // between the sliding drawer and the action bar app icon
            mDrawerToggle = new ActionBarDrawerToggle(
                    this,                  /* host Activity */
                    mDrawerLayout,         /* DrawerLayout object */
                    R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                    R.string.drawer_open,  /* "open drawer" description for accessibility */
                    R.string.drawer_close  /* "close drawer" description for accessibility */
                    ) {
                public void onDrawerClosed(View view) {
                    getActionBar().setTitle(mTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                public void onDrawerOpened(View drawerView) {
                    getActionBar().setTitle(mDrawerTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            
            
            if (savedInstanceState == null) {
                selectItem(0);
            }
        	
        }
        
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main, menu);
            return super.onCreateOptionsMenu(menu);
        }
        
        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();
        }
        
        /* Called whenever we call invalidateOptionsMenu() */
        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            // If the nav drawer is open, hide action items related to the content view
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
            return super.onPrepareOptionsMenu(menu);
        }
        
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
             // The action bar home/up action should open or close the drawer.
             // ActionBarDrawerToggle will take care of this.
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            // Handle action buttons
            switch(item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
//                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
            }
        }
        

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            // Pass any configuration change to the drawer toggls
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
        
        @Override
        public void setTitle(CharSequence title) {
            mTitle = title;
            getActionBar().setTitle(mTitle);
        }
        
        void selectItem(int position){
        	 Fragment fragment = new SubFragment();
             Bundle args = new Bundle();
             args.putInt(SubFragment.ARG_PLANET_NUMBER, position);
             fragment.setArguments(args);

             FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

             // update selected item and title, then close the drawer
             mDrawerList.setItemChecked(position, true);
             setTitle(mFifatitles[position]);
             mDrawerLayout.closeDrawer(mDrawerList);
        }
        
        /* The click listner for ListView in the navigation drawer */
        private class DrawerItemClickListener implements ListView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
            
        }
        
        /**
         * Fragment that appears in the "content_frame", shows a planet
         */
        public static class SubFragment extends Fragment {
            public static final String ARG_PLANET_NUMBER = "planet_number";

            public SubFragment() {
                // Empty constructor required for fragment subclasses
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
                int i = getArguments().getInt(ARG_PLANET_NUMBER);
                String planet = getResources().getStringArray(R.array.menu_array)[i];

                int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                                "drawable", getActivity().getPackageName());
                ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
                getActivity().setTitle(planet);
                return rootView;
            }
        }
        
}
