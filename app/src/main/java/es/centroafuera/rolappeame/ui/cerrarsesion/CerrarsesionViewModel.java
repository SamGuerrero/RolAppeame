package es.centroafuera.rolappeame.ui.cerrarsesion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CerrarsesionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CerrarsesionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}