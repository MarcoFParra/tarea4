package mp.tareas.tarea4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout bottomSheet;
    boolean pagado = false;

    ArrayList<Integer> outValues = new ArrayList<>();
    ArrayList<Integer> outOptions = new ArrayList<>();
    int CAMBIO = 0,DEPOSITADO =0, TOTAL = 0;
    LinearLayout layout;
    BottomSheetBehavior bsb;

    CheckBox chk_20, chk_10, chk_5, chk_2;

    TextView tv_total,tv_depositado;
    private TextView tv_cambio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        final AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        bottomSheet = (LinearLayout)findViewById(R.id.bottomSheet);
        layout = (LinearLayout)findViewById(R.id.ll_loot);


        bsb = BottomSheetBehavior.from(bottomSheet);

        bsb.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_DRAGGING) {
                    if (!pagado)
                    bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        tv_total = findViewById(R.id.tv_total);
        tv_depositado = findViewById(R.id.tv_depositado);
        tv_cambio = findViewById(R.id.tv_cambio);


        chk_2 = findViewById(R.id.chk_2);
        chk_5 = findViewById(R.id.chk_5);
        chk_10 = findViewById(R.id.chk_10);
        chk_20 = findViewById(R.id.chk_20);

        checkArray();


        chk_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkArray();
            }
        });

        chk_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkArray();

            }
        });

        chk_10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkArray();

            }
        });

        chk_20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkArray();

            }
        });

    }

    public void showPay(int total)
    {
        pagado = false;
        CAMBIO = 0;
        DEPOSITADO =0;
        TOTAL = total;
        layout.removeAllViews();
        tv_total.setText("$" + TOTAL);
        tv_depositado.setText("$" + "0.00");
        tv_cambio.setText("$" + "0.00");

        bsb.setState(BottomSheetBehavior.STATE_EXPANDED);

    }
    public void givemeTheLoot()
    {
        outValues.clear();
        int index =0;
        while (CAMBIO != 0)
        {
            if (outOptions.get(index)<= CAMBIO)
            {
                CAMBIO-= outOptions.get(index);
                outValues.add(outOptions.get(index));
            }
            else index++;
        }
        showTheLoot();
    }

    ImageView imageview;
    private void showTheLoot() {

        layout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(100, 100);
        params.setMargins(15,0,15,0);
        for (int i =0; i<outValues.size(); i++) {
            imageview = new ImageView(MainActivity.this);
            imageview.setImageBitmap(textAsBitmap(
                    outValues.get(i)<10 ? "  " + outValues.get(i).toString() +"  ": " " +outValues.get(i).toString() +" "
                    , 80, Color.WHITE ,getResources().getColor(R.color.colorPrimary)));
            imageview.setLayoutParams(params);
            //imageview.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            layout.addView(imageview);
        }

    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor, int colorback) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);

        Paint paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setTextSize(textSize);
        paint3.setColor(colorback);
        paint3.setTextAlign(Paint.Align.LEFT);

        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawCircle(width/2 , height/2,height/2 ,paint3);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    void checkArray() {
        outOptions.clear();
        if (chk_20.isChecked())
            outOptions.add(20);
        if (chk_10.isChecked())
            outOptions.add(10);
        if (chk_5.isChecked())
            outOptions.add(5);
        if (chk_2.isChecked())
            outOptions.add(2);
        outOptions.add(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_1:
                DEPOSITADO = DEPOSITADO + 1;
                break;
            case R.id.btn_2:
                DEPOSITADO = DEPOSITADO + 2;
                break;
            case R.id.btn_5:
                DEPOSITADO = DEPOSITADO + 5;
                break;
            case R.id.btn_10:
                DEPOSITADO = DEPOSITADO + 10;
                break;
            case R.id.btn_20:
                DEPOSITADO = DEPOSITADO + 20;
                break;
            case R.id.btn_50:
                DEPOSITADO = DEPOSITADO + 50;
                break;
            case R.id.btn_100:
                DEPOSITADO = DEPOSITADO + 100;
                break;
            case R.id.btn_200:
                DEPOSITADO = DEPOSITADO + 200;
                break;
        }
        if (DEPOSITADO >= TOTAL)
        {
            CAMBIO = DEPOSITADO - TOTAL;
            tv_cambio.setText("$"+CAMBIO);
            givemeTheLoot();
            pagado = true;
        }
        tv_depositado.setText("$" + DEPOSITADO);

    }

}
