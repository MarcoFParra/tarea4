package mp.tareas.tarea4.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

import mp.tareas.tarea4.R;
import mp.tareas.tarea4.Ticket;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public static ArrayList<Ticket> dataInitial = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        final LinearLayout parentLayout = root.findViewById(R.id.parentLayout);

        if (dataInitial.size() == 0)
            dataInitial.add(new Ticket());

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });

        homeViewModel.getList().observe(this, new Observer<ArrayList<Ticket>>() {
            @Override
            public void onChanged(ArrayList<Ticket> tickets) {
                parentLayout.removeAllViews();
                parentLayout.addView(createTableLayout(tickets));
            }
        });


        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataInitial.add(new Ticket(homeViewModel.getList().getValue().size(),
                        Calendar.getInstance().getTime()));

                homeViewModel.setList(dataInitial);

                Snackbar.make(view, "Nuevo ticket generado", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
        return root;
    }

    private TableLayout createTableLayout( ArrayList<Ticket> tickets) {
        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = new TableLayout(getContext());
        //  tableLayout.setBackgroundColor(Color.BLACK);

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.weight = 1;
        int i = 0;
        for (Ticket value: tickets) {

            // 3) create tableRow
            TableRow tableRow = new TableRow(getActivity());
            // tableRow.setBackgroundColor(Color.BLACK);
            for (int j = 0; j < 4; j++) {
                // 4) create textView
                TextView textView = new TextView(getActivity());
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(10, 10, 10, 10);
                textView.setText(value.ToArray()[j]);
                if (i == 0) {
                    textView.setText(value.getHeaders()[j]);
                    textView.setTextColor(Color.parseColor("#ffffff"));
                    textView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                }

                else {
                    if (value.getHoraFin() != null)
                        textView.setBackgroundResource(R.drawable.row_paid);
                    else if (i%2 == 0)
                        textView.setBackgroundResource(R.drawable.row_green);
                    else
                        textView.setBackgroundResource(R.drawable.row_white);
                    textView.setText(value.ToArray()[j]);
                }

                tableRow.addView(textView, tableRowParams);
            }
            tableLayout.addView(tableRow, tableLayoutParams);
            i++;
        }

        return tableLayout;
    }
}