package co.edu.unab.mgads.atenea.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import co.edu.unab.mgads.atenea.R;
import co.edu.unab.mgads.atenea.entity.PostNoticiaResponse;

public class DetailNewsActivity extends AppCompatActivity {

    private PostNoticiaResponse postNoticiaResponse;
    private String title;
    private TextView tviTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        tviTitle= (TextView)findViewById(R.id.tviTitle);
        validateExtras();

        if(postNoticiaResponse!=null && title!=null)
        {
            tviTitle.setText(title);
        }
    }

    private void validateExtras() {
        if(getIntent().getExtras()!=null)
        {
            title= getIntent().getExtras().getString("TITLE");
            postNoticiaResponse= (PostNoticiaResponse)getIntent().getExtras().getSerializable("ENTITY");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_news, menu);
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
