package com.jcode.tebocydelevery;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.jcode.tebocydelevery.buy.di.BuyComponent;
import com.jcode.tebocydelevery.buy.di.BuyModule;
import com.jcode.tebocydelevery.buy.di.DaggerBuyComponent;
import com.jcode.tebocydelevery.buy.ui.CarBuyView;
import com.jcode.tebocydelevery.detailsOrder.di.DaggerDetailsComponent;
import com.jcode.tebocydelevery.detailsOrder.di.DetailsComponent;
import com.jcode.tebocydelevery.detailsOrder.di.DetailsOrderModule;
import com.jcode.tebocydelevery.detailsOrder.ui.DetailsOrderView;
import com.jcode.tebocydelevery.domain.di.DomainModule;
import com.jcode.tebocydelevery.historyOrders.di.DaggerHistoryOrderComponent;
import com.jcode.tebocydelevery.historyOrders.di.HistoryOrderComponent;
import com.jcode.tebocydelevery.historyOrders.di.HistoryOrderModule;
import com.jcode.tebocydelevery.historyOrders.ui.HistoryOrderView;
import com.jcode.tebocydelevery.init.login.di.DaggerLoginComponent;
import com.jcode.tebocydelevery.init.login.di.LoginComponent;
import com.jcode.tebocydelevery.init.login.di.LoginModule;
import com.jcode.tebocydelevery.init.login.ui.LoginView;
import com.jcode.tebocydelevery.lib.di.LibModule;
import com.jcode.tebocydelevery.main.di.DaggerMainComponent;
import com.jcode.tebocydelevery.main.di.MainComponent;
import com.jcode.tebocydelevery.main.di.MainModule;
import com.jcode.tebocydelevery.main.ui.MainView;
import com.jcode.tebocydelevery.myOrders.di.DaggerMyOrdersComponent;
import com.jcode.tebocydelevery.myOrders.di.MyOrdersComponent;
import com.jcode.tebocydelevery.myOrders.di.MyOrdersModule;
import com.jcode.tebocydelevery.myOrders.ui.MyOrdersView;

public class MyApplication extends Application {

    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";

    private MyApplicationModule myApplicationModule;
    private DomainModule domainModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
    }


    private void initFirebase() {
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private void initModules() {
        myApplicationModule = new MyApplicationModule(this);
        domainModule = new DomainModule();

    }

    public String getEmailKey() {
        return EMAIL_KEY;
    }

    public String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

    public LoginComponent getLoginComponent(LoginView view) {
        return DaggerLoginComponent
                .builder()
                .myApplicationModule(myApplicationModule)
                .domainModule(domainModule)
                .libModule(new LibModule(null))
                .loginModule(new LoginModule(view))
                .build();
    }

    public MainComponent getMainComponent(MainView view) {
        return DaggerMainComponent
                .builder()
                .myApplicationModule(myApplicationModule)
                .domainModule(domainModule)
                .libModule(new LibModule(null))
                .mainModule(new MainModule(view))
                .build();
    }


    public BuyComponent getBuyComponent(CarBuyView view) {
        return DaggerBuyComponent
                .builder()
                .myApplicationModule(myApplicationModule)
                .domainModule(domainModule)
                .libModule(new LibModule(null))
                .buyModule(new BuyModule(view))
                .build();
    }


    public DetailsComponent getDetailsComponent(DetailsOrderView view) {
        return DaggerDetailsComponent
                .builder()
                .myApplicationModule(myApplicationModule)
                .domainModule(domainModule)
                .libModule(new LibModule(null))
                .detailsOrderModule(new DetailsOrderModule(view))
                .build();
    }

    public HistoryOrderComponent getHistoryOrderComponent(HistoryOrderView view) {

        return DaggerHistoryOrderComponent
                .builder()
                .myApplicationModule(myApplicationModule)
                .domainModule(domainModule)
                .libModule(new LibModule(null))
                .historyOrderModule(new HistoryOrderModule(view))
                .build();
    }

    public MyOrdersComponent getMyOrdersComponent(MyOrdersView view) {

        return DaggerMyOrdersComponent
                .builder()
                .myApplicationModule(myApplicationModule)
                .domainModule(domainModule)
                .libModule(new LibModule(null))
                .myOrdersModule(new MyOrdersModule(view))
                .build();
    }

}
