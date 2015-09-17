package co.edu.unab.mgads.atenea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.edu.unab.mgads.atenea.RecyclerView.Adapters.DrawerAdapter;
import co.edu.unab.mgads.atenea.RecyclerView.Classes.DrawerItem;
import co.edu.unab.mgads.atenea.RecyclerView.Utils.ItemClickSupport;
import co.edu.unab.mgads.atenea.Utils.CircleTransform;
import co.edu.unab.mgads.atenea.entity.LoginResponse;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    Button buttonRedLight, buttonRedDark, buttonIndigoLight, buttonIndigoDark;
    ToggleButton toggleButtonDrawer;
    FrameLayout statusBar;
    LinearLayout linearLayoutDrawerAccount, linearLayoutDrawerMain;
    RelativeLayout relativeLayoutScrollViewChild;
    ImageView imageViewDrawerArrowUpDown;
    ScrollView scrollViewNavigationDrawerContent;
    ViewTreeObserver viewTreeObserverNavigationDrawerScrollView;
    ViewTreeObserver.OnScrollChangedListener onScrollChangedListener;
    RecyclerView recyclerViewDrawer1, recyclerViewDrawerSettings;
    RecyclerView.Adapter drawerAdapter1, drawerAdapterSettings;
    ArrayList<DrawerItem> drawerItems1, drawerItemsSettings;
    float drawerHeight, scrollViewHeight;
    LinearLayoutManager linearLayoutManager,  linearLayoutManagerSettings;
    ItemClickSupport itemClickSupport1,  itemClickSupportSettings;
    TypedValue typedValueColorPrimary, typedValueTextColorPrimary, typedValueTextColorControlHighlight, typedValueColorBackground;
    int colorPrimary, textColorPrimary, colorControlHighlight, colorBackground;
    int recyclerViewHeight;

    private LoginResponse loginResponse;
    private static final String TAG = "MainActivity";
    private String noss;
    // Atributos
    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setTheme(R.style.AppThemeOrangeLight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar and statusBar (really FrameLayout)
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Noticias");
        statusBar = (FrameLayout) findViewById(R.id.statusBar);



        // Setup navigation drawer
        setupNavigationDrawer();



        if(getIntent().getExtras()!=null)
        {
            loginResponse= (LoginResponse)getIntent().getExtras().getSerializable("ENTITY");
            Log.i(TAG, " loginResponse " + loginResponse);

            //  Superior Drawer
            TextView NombreUsuario = (TextView)findViewById(R.id.textViewName);
            NombreUsuario.setText(loginResponse.user.name.toString());

            TextView CorreoUsuario = (TextView)findViewById(R.id.textViewUsername);
            CorreoUsuario.setText(loginResponse.user.mail.toString());

            String urlPictureMain;
            urlPictureMain = loginResponse.user.picture.url.toString();

            ImageView imageViewPictureMain;
            imageViewPictureMain = (ImageView) findViewById(R.id.imageViewPictureMain);

            Picasso.with(getApplicationContext()).load(urlPictureMain).transform(new CircleTransform()).into(imageViewPictureMain);

        }

        Fragment fragment = null;


        fragment = new NoticiasFragment();



        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
/*
// Obtener instancia de la lista
        listView= (ListView) findViewById(R.id.listView);

        // Crear adaptador y setear
        adapter = new PostNoticiasAdapter(this);
        listView.setAdapter(adapter);
*/
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



    public void setupNavigationDrawer() {

        // Setup Navigation drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Setup Drawer Icon
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();

        String urlPictureMain, urlCoverMain, urlPictureSecond;
        urlPictureMain = "https://lh5.googleusercontent.com/-NZySyUIyLeA/VWuTZdbE9zI/AAAAAAAAAEU/xpSc2dWVkTo/w325-h577/2014-12-19%2B20.58.25.jpg";
        urlCoverMain = "http://ateneaprueba.soydetic.com/modules/image/Login.jpg";
        urlPictureSecond = "https://lh3.googleusercontent.com/-fIIhUhsMF3k/AAAAAAAAAAI/AAAAAAAAAp4/RPUESUibS6U/s120-c/photo.jpg";

        ImageView imageViewPictureMain, imageViewCoverMain, imageViewPictureSecond;
        imageViewPictureMain = (ImageView) findViewById(R.id.imageViewPictureMain);
        imageViewCoverMain = (ImageView) findViewById(R.id.imageViewCover);
        imageViewPictureSecond = (ImageView) findViewById(R.id.imageViewPictureSecond);

        Picasso.with(getApplicationContext()).load(urlPictureMain).transform(new CircleTransform()).into(imageViewPictureMain);
        Picasso.with(getApplicationContext()).load(urlCoverMain).into(imageViewCoverMain);
        Picasso.with(getApplicationContext()).load(urlPictureSecond).transform(new CircleTransform()).into(imageViewPictureSecond);

        TypedValue typedValue = new TypedValue();
        MainActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        final int color = typedValue.data;
        drawerLayout.setStatusBarBackgroundColor(color);

        toggleButtonDrawer = (ToggleButton) findViewById(R.id.toggleButtonDrawer);
        toggleButtonDrawer.setOnClickListener(this);

        // Hide Settings and Feedback buttons when navigation drawer is scrolled
        hideNavigationDrawerSettingsAndFeedbackOnScroll();

        // Setup RecyclerViews inside drawer
        setupNavigationDrawerRecyclerViews();
    }



    private void hideNavigationDrawerSettingsAndFeedbackOnScroll() {

        scrollViewNavigationDrawerContent = (ScrollView) findViewById(R.id.scrollViewNavigationDrawerContent);
        relativeLayoutScrollViewChild = (RelativeLayout) findViewById(R.id.relativeLayoutScrollViewChild);


        viewTreeObserverNavigationDrawerScrollView = relativeLayoutScrollViewChild.getViewTreeObserver();

        if (viewTreeObserverNavigationDrawerScrollView.isAlive()) {
            viewTreeObserverNavigationDrawerScrollView.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    if (Build.VERSION.SDK_INT > 16) {
                        relativeLayoutScrollViewChild.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        relativeLayoutScrollViewChild.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    drawerHeight = relativeLayoutScrollViewChild.getHeight();
                    scrollViewHeight = scrollViewNavigationDrawerContent.getHeight();


                }
            });
        }



        scrollViewNavigationDrawerContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ViewTreeObserver observer;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        observer = scrollViewNavigationDrawerContent.getViewTreeObserver();
                        observer.addOnScrollChangedListener(onScrollChangedListener);
                        break;

                    case MotionEvent.ACTION_UP:
                        observer = scrollViewNavigationDrawerContent.getViewTreeObserver();
                        observer.addOnScrollChangedListener(onScrollChangedListener);
                        break;
                }

                return false;
            }
        });
    }

    private void setupNavigationDrawerRecyclerViews() {

        // RecyclerView 1
        recyclerViewDrawer1 = (RecyclerView) findViewById(R.id.recyclerViewDrawer1);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewDrawer1.setLayoutManager(linearLayoutManager);

        drawerItems1 = new ArrayList<>();
        drawerItems1.add(new DrawerItem(getResources().getDrawable(R.drawable.ic_action_content_inbox), "Noticias"));
        drawerItems1.add(new DrawerItem(getResources().getDrawable(R.drawable.ic_action_device_access_time), "Materias"));
        drawerItems1.add(new DrawerItem(getResources().getDrawable(R.drawable.ic_action_navigation_check), "Sobre ATeneA"));

        drawerAdapter1 = new DrawerAdapter(drawerItems1);
        recyclerViewDrawer1.setAdapter(drawerAdapter1);

        recyclerViewDrawer1.setMinimumHeight(convertDpToPx(144));
        recyclerViewDrawer1.setHasFixedSize(true);





        // Why have I to calc recyclerView height?
        // Because recyclerView at this moment doesn't support wrap_content, this cause an height of 0 px

        // Get colorPrimary, textColorPrimary, colorControlHighlight and background to apply to selected items
        typedValueColorPrimary = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValueColorPrimary, true);
        colorPrimary = typedValueColorPrimary.data;

        typedValueTextColorPrimary = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.textColorPrimary, typedValueTextColorPrimary, true);
        textColorPrimary = typedValueTextColorPrimary.data;

        typedValueTextColorControlHighlight = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorControlHighlight, typedValueTextColorControlHighlight, true);
        colorControlHighlight = typedValueTextColorControlHighlight.data;

        typedValueColorBackground = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.colorBackground, typedValueColorBackground, true);
        colorBackground = typedValueColorBackground.data;

        // Set icons alpha at start
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after some time
                for (int i = 0; i < recyclerViewDrawer1.getChildCount(); i++) {
                    ImageView imageViewDrawerItemIcon = (ImageView) recyclerViewDrawer1.getChildAt(i).findViewById(R.id.imageViewDrawerItemIcon);
                    TextView textViewDrawerItemTitle = (TextView) recyclerViewDrawer1.getChildAt(i).findViewById(R.id.textViewDrawerItemTitle);
                    LinearLayout linearLayoutItem = (LinearLayout) recyclerViewDrawer1.getChildAt(i).findViewById(R.id.linearLayoutItem);
                    if (i == 0) {
                        imageViewDrawerItemIcon.setColorFilter(colorPrimary);
                        if (Build.VERSION.SDK_INT > 15) {
                            imageViewDrawerItemIcon.setImageAlpha(255);
                        } else {
                            imageViewDrawerItemIcon.setAlpha(255);
                        }
                        textViewDrawerItemTitle.setTextColor(colorPrimary);
                        linearLayoutItem.setBackgroundColor(colorControlHighlight);
                    } else {
                        imageViewDrawerItemIcon.setColorFilter(textColorPrimary);
                        if (Build.VERSION.SDK_INT > 15) {
                            imageViewDrawerItemIcon.setImageAlpha(138);
                        } else {
                            imageViewDrawerItemIcon.setAlpha(138);
                        }
                        textViewDrawerItemTitle.setTextColor(textColorPrimary);
                        linearLayoutItem.setBackgroundColor(colorBackground);
                    }
                }


            }
        }, 250 );

        itemClickSupport1 = ItemClickSupport.addTo(recyclerViewDrawer1);
        itemClickSupport1.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                for (int i = 0; i < recyclerViewDrawer1.getChildCount(); i++) {
                    ImageView imageViewDrawerItemIcon = (ImageView) recyclerViewDrawer1.getChildAt(i).findViewById(R.id.imageViewDrawerItemIcon);
                    TextView textViewDrawerItemTitle = (TextView) recyclerViewDrawer1.getChildAt(i).findViewById(R.id.textViewDrawerItemTitle);
                    LinearLayout linearLayoutItem = (LinearLayout) recyclerViewDrawer1.getChildAt(i).findViewById(R.id.linearLayoutItem);
                    if (i == position) {
                        imageViewDrawerItemIcon.setColorFilter(colorPrimary);
                        if (Build.VERSION.SDK_INT > 15) {
                            imageViewDrawerItemIcon.setImageAlpha(255);
                        } else {
                            imageViewDrawerItemIcon.setAlpha(255);
                        }
                        textViewDrawerItemTitle.setTextColor(colorPrimary);
                        linearLayoutItem.setBackgroundColor(colorControlHighlight);
                    } else {
                        imageViewDrawerItemIcon.setColorFilter(textColorPrimary);
                        if (Build.VERSION.SDK_INT > 15) {
                            imageViewDrawerItemIcon.setImageAlpha(138);
                        } else {
                            imageViewDrawerItemIcon.setAlpha(138);
                        }
                        textViewDrawerItemTitle.setTextColor(textColorPrimary);
                        linearLayoutItem.setBackgroundColor(colorBackground);
                    }
                }

                Fragment fragment = null;

                switch (position) {
                    case 0:
                        fragment = new NoticiasFragment();
                                              break;
                    case 1:
                        fragment = new Fragment2();
                        break;
                    case 2:
                        fragment = new Fragment3();
                        break;

                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

              //  ndList.setItemChecked(pos, true);

                //getSupportActionBar().setTitle(opciones[pos]);

                drawerLayout.closeDrawers();
            }
        });



    }


    // All onClick for all views
    @Override
    public void onClick(View v) {

        sharedPreferences = getSharedPreferences("VALUES", MODE_PRIVATE);
        Intent intent = new Intent(MainActivity.this, MainActivity.class);

        switch (v.getId()) {

            case R.id.toggleButtonDrawer:
                linearLayoutDrawerAccount = (LinearLayout) findViewById(R.id.linearLayoutDrawerAccounts);
                linearLayoutDrawerMain = (LinearLayout) findViewById(R.id.linearLayoutDrawerMain);
                imageViewDrawerArrowUpDown = (ImageView) findViewById(R.id.imageViewDrawerArrowUpDown);

                if (linearLayoutDrawerAccount.getVisibility() == View.VISIBLE) {
                    linearLayoutDrawerAccount.setVisibility(View.GONE);
                    linearLayoutDrawerMain.setVisibility(View.VISIBLE);

                    Animation animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setFillAfter(true);
                    animation.setDuration(500);
                    imageViewDrawerArrowUpDown.startAnimation(animation);
                    imageViewDrawerArrowUpDown.setBackgroundResource(R.drawable.ic_navigation_arrow_drop_up);
                } else {
                    linearLayoutDrawerAccount.setVisibility(View.VISIBLE);
                    linearLayoutDrawerMain.setVisibility(View.GONE);
                    Animation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setFillAfter(true);
                    animation.setDuration(500);
                    imageViewDrawerArrowUpDown.startAnimation(animation);
                    imageViewDrawerArrowUpDown.setBackgroundResource(R.drawable.ic_navigation_arrow_drop_down);
                }

                break;
        }
    }

    public int convertDpToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }


}
