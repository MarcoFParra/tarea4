package mp.tareas.tarea4.ui.home;

import android.widget.Adapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mp.tareas.tarea4.Ticket;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private MutableLiveData< ArrayList<Ticket>> data ;



    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        data = new MutableLiveData<>();
        data.setValue(HomeFragment.dataInitial);

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<Ticket>> getList () {return data;}

    public void setList(ArrayList<Ticket> dats)
    {

        data.setValue(dats);
    }
}