package mp.tareas.tarea4.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Calendar;

import mp.tareas.tarea4.MainActivity;
import mp.tareas.tarea4.R;
import mp.tareas.tarea4.ui.home.HomeFragment;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    ArrayAdapter<String> spinnerArrayAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        final Spinner spinner = root.findViewById(R.id.spinner);


        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        dashboardViewModel.getTickets().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {

                spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.spinner_item, strings);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                spinner.setAdapter(spinnerArrayAdapter);

            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    int idTicket = Integer.valueOf(spinnerArrayAdapter.getItem(position).split(" ")[1]);
                    Calendar inicio = Calendar.getInstance();
                    inicio.setTime(HomeFragment.dataInitial.get(idTicket).getHoraInicio());
                    Calendar fin = Calendar.getInstance();
                    long diff = fin.getTimeInMillis() - inicio.getTimeInMillis();
                    fin.setTimeInMillis(fin.getTimeInMillis() + diff * 60);
                    Toast.makeText(getActivity(), "Tiempo Transcurrido: " + String.valueOf((diff * 60) / 60000), Toast.LENGTH_SHORT).show();
                    HomeFragment.dataInitial.get(idTicket).setFechaFin(fin.getTime(), diff * 60);
                    ((MainActivity) getActivity()).showPay((int) HomeFragment.dataInitial.get(idTicket).getTotal());
                    dashboardViewModel.loadTickets();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }


}