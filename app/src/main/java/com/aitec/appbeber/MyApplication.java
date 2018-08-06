package com.aitec.appbeber;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.aitec.appbeber.MyProfile.di.DaggerProfileComponent;
import com.aitec.appbeber.MyProfile.di.ProfileComponent;
import com.aitec.appbeber.MyProfile.di.ProfileModule;
import com.aitec.appbeber.MyProfile.ui.ProfileView;
import com.aitec.appbeber.RackProducts.di.DaggerRackProductsComponent;
import com.aitec.appbeber.RackProducts.di.RackProductsComponent;
import com.aitec.appbeber.RackProducts.di.RackProductsModule;
import com.aitec.appbeber.RackProducts.ui.RackProductsView;
import com.aitec.appbeber.buy.di.BuyComponent;
import com.aitec.appbeber.buy.di.BuyModule;
import com.aitec.appbeber.buy.di.DaggerBuyComponent;
import com.aitec.appbeber.buy.ui.CarBuyView;
import com.aitec.appbeber.detailsOrder.di.DaggerDetailsComponent;
import com.aitec.appbeber.detailsOrder.di.DetailsComponent;
import com.aitec.appbeber.detailsOrder.di.DetailsOrderModule;
import com.aitec.appbeber.detailsOrder.ui.DetailsOrderView;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.historyOrders.di.DaggerHistoryOrderComponent;
import com.aitec.appbeber.historyOrders.di.HistoryOrderComponent;
import com.aitec.appbeber.historyOrders.di.HistoryOrderModule;
import com.aitec.appbeber.historyOrders.ui.HistoryOrderView;
import com.aitec.appbeber.init.login.di.DaggerLoginComponent;
import com.aitec.appbeber.init.login.di.LoginComponent;
import com.aitec.appbeber.init.login.di.LoginModule;
import com.aitec.appbeber.init.login.ui.LoginView;
import com.aitec.appbeber.lib.di.LibModule;
import com.aitec.appbeber.main.di.DaggerMainComponent;
import com.aitec.appbeber.main.di.MainComponent;
import com.aitec.appbeber.main.di.MainModule;
import com.aitec.appbeber.main.ui.MainView;
import com.aitec.appbeber.myOrders.di.DaggerMyOrdersComponent;
import com.aitec.appbeber.myOrders.di.MyOrdersComponent;
import com.aitec.appbeber.myOrders.di.MyOrdersModule;
import com.aitec.appbeber.myOrders.ui.MyOrdersView;

public class MyApplication extends MultiDexApplication {

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

    public SharedPreferences getSharePreferences() {
        return getSharedPreferences("token", Context.MODE_PRIVATE);
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

    public RackProductsComponent getRackProductsComponent(RackProductsView view) {
        return DaggerRackProductsComponent
                .builder()
                .myApplicationModule(myApplicationModule)
                .domainModule(domainModule)
                .libModule(new LibModule(null))
                .rackProductsModule(new RackProductsModule(view))
                .build();
    }


    public ProfileComponent getProfileComponent(ProfileView view) {
        return DaggerProfileComponent
                .builder()
                .domainModule(domainModule)
                .libModule(new LibModule(null))
                .profileModule(new ProfileModule(view))
                .build();
    }
}
