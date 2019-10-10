package mp.tareas.tarea4.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import mp.tareas.tarea4.ui.home.HomeFragment;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<String>> tickets;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Seleccione el Ticket a pagar");

        tickets = new MutableLiveData<>();
        loadTickets();

    }

    public void loadTickets()
    {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Seleccione un ticket");
        for (int i = 1; i<HomeFragment.dataInitial.size();i++) {
            if (HomeFragment.dataInitial.get(i).getTotal() == 0)
                strings.add("Ticket " + HomeFragment.dataInitial.get(i).getId());
        }

        tickets.setValue(strings);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<ArrayList<String>> getTickets(){
        return  tickets;}
}