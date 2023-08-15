# VISIBLE & GONE VIEW WITH DATA BINDING

- có những trường hợp, ta muốn 1 View nào đó sẽ ẩn, hiện, hoặc tạm thời rời khỏi layout
- ví dụ, có 1 CheckBox và 1 TextView, giả sử khi ta tick vào CheckBox thì TextView sẽ hiện lên, hoặc khi ta bỏ tick thì TextView sẽ biến mất

## XÂY DỰNG CHƯƠNG TRÌNH

- khai báo DataBinding trong Gradle module app
```js
plugins {
    id 'com.android.application'
}

android {
    // 

    buildFeatures {
        dataBinding true
    }
}

// 
```
- dựng class ViewModel cho Architecture Pattern MVVM (ví dụ UserViewModel)
```java
public class UserViewModel {
    //
}
```
- khai báo data trong layout của control class (ví dụ MainActivity)
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="UserViewModel"
            type="com.example.visibleandgoneviewwithdatabinding.UserViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="10dp">

    </LinearLayout>
</layout>
```
- khai báo bind data trong control class (ví dụ MainActivity) để liên kết giữa layout và class ViewModel
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(mActivityMainBinding.getRoot());
    }
}
```

## XỬ LÝ VIEW NGAY TRONG LAYOUT DATA BINDING

- để có thể thực hiện được việc ẩn hiện liên quan đến TextView ngay trong file layout, thì ta phải ``import`` library liên quan đến TextView (View library) để có thể sử dụng những chức năng liên quan của TextView
- trong thẻ ``data`` của file layout, khai báo thẻ ``import`` ngang cấp với thẻ ``variable``
- và giá trị ta import chính là library liên quan đến tác vụ ta cần xử lý
- ví dụ ở đây ta cần tác động đến TextView ta sẽ import library View
```xml
    <data>
        <import type="android.view.View"/>
        <variable
            name="UserViewModel"
            type="com.example.visibleandgoneviewwithdatabinding.UserViewModel" />
    </data>
```

- ở phần layout chính là ``LinearLayout`` ta dựng 1 CheckBox và 1 TextView
    - CheckBox ta sẽ khai báo thuộc tính ``id`` cho nó
    - TextView ta sẽ khai báo thêm thuộc tính ``visibility``
- logic ở đây là khi CheckBox chưa được ``checked`` thì TextView sẽ biến mất, khi CheckBox được ``checked`` thì TextView sẽ hiển thị
- ta sẽ dùng toán tử 3 ngôi ``? :`` để thay thế cho câu lệnh điều kiện ``if``
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <import type="android.view.View"/>
        <variable
            name="UserViewModel"
            type="com.example.visibleandgoneviewwithdatabinding.UserViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="10dp">

        <CheckBox
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/checkbox"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/app_name"
            android:textSize="20sp"
            android:textColor="@color/black"
            android:visibility="@{checkbox.checked ? View.VISIBLE : View.GONE}"/>
    </LinearLayout>
</layout>
```

- có thể hiểu như sau, ở thuộc tính ``visibility`` của TextView, nếu CheckBox kiểm tra ``checked`` mà trả về ``true`` thì TextView sẽ sử dụng thuộc tính ``View.VISIBLE`` (hiển thị), ngược lại sẽ là ``View.GONE`` (biến mất)

## XỬ LÝ VIEW TRONG FILE CHƯƠNG TRÌNH

- đối với trường hợp, mà 1 view nào đó bị tác động phụ thuộc vào giá trị nào đó trong chương trình, thì ta sẽ xử lý view đó trong file control chương trình
- ví dụ, ta có 1 Button, khi click vào Button thì TextView sẽ hiển thị, và khi click lần nữa TextView sẽ biến mất
- ở UserViewModel để sử dụng ràng buộc dữ liệu phải ``extends BaseObservable`` sau đó thực hiện ràng buộc bằng 2 cách
    - sử dụng Observable Field
    - sử dụng ``@Bindable`` cho Getter, và ``notifyPropertyChanged(BR.THUỘC_TÍNH)`` cho Setter
- sau đó, trong UserViewModel khai báo 1 method dùng để cho Button khi click vào sẽ gọi đến và thay đổi giá trị field của UserViewModel, mà giá trị này sẽ được dùng để xác định TextView sẽ hiển thị hay không
```java
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
```

- ở MainActivity ta vẫn khai báo binding layout và ViewModel bình thường tùy vào trường hợp sử dụng ràng buộc ở ViewModel
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        // trường hợp không dùng Observable Field
//        UserViewModel userViewModel = new UserViewModel(false);
        // trường hợp dùng Observable Field
        UserViewModel userViewModel = new UserViewModel();

        mActivityMainBinding.setUserViewModel(userViewModel);

        setContentView(mActivityMainBinding.getRoot());
    }
}
```

- ở layout binding thì
    - TextView sẽ lấy giá trị thuộc tính boolean ở UserViewModel mà quyết định hiển hay ẩn
    - khai báo Button kèm thuộc tính ``onClick`` với giá trị là biểu thức lambda Listener Binding, để gọi đến method trong UserViewModel thay đổi giá trị thuộc tính boolean ở ViewModel
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <import type="android.view.View"/>
        <variable
            name="UserViewModel"
            type="com.example.visibleandgoneviewwithdatabinding.UserViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="10dp">

        <CheckBox
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/checkbox"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/app_name"
            android:textSize="20sp"
            android:textColor="@color/black"
            android:visibility="@{UserViewModel.isShow ? View.VISIBLE : View.GONE}"/>

        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="CHANGE STATUS"
            android:onClick="@{() -> UserViewModel.changeValue()}"/>
    </LinearLayout>
</layout>
```