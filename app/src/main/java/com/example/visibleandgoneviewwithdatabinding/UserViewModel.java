package com.example.visibleandgoneviewwithdatabinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableBoolean;

public class UserViewModel extends BaseObservable{
    public final ObservableBoolean isShow = new ObservableBoolean();

    public UserViewModel() {
        isShow.set(false);
    }

    public ObservableBoolean getIsShow() {
        return isShow;
    }

    public void changeValue() {
        getIsShow().set(!isShow.get());
    }

//    private boolean isShow = false;
//
//    public UserViewModel(boolean isShow) {
//        this.isShow = isShow;
//    }
//
//    @Bindable
//    public boolean isShow() {
//        return isShow;
//    }
//
//    public void setShow(boolean show) {
//        isShow = show;
//        notifyPropertyChanged(BR.isShow);
//    }
//
//    public void changeValue() {
//        setShow(!isShow());
//    }
}
