package cn.ucai.fulicenter.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;

import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.controller.fragment.CartFragment;
import cn.ucai.fulicenter.controller.fragment.CategoryFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.controller.fragment.PersonalFragment;
import cn.ucai.fulicenter.model.utils.I;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rbBoutique)
    RadioButton rbBoutique;
    @BindView(R.id.rbCart)
    RadioButton rbCart;
    @BindView(R.id.rbNewGood)
    RadioButton rbNewGood;
    @BindView(R.id.rbCategory)
    RadioButton rbCategory;
    @BindView(R.id.rbPersonal)
    RadioButton rbPersonal;
    @BindView(R.id.tvCartHint)
    TextView count;
    int index;
    RadioButton[] btns;
    NewGoodsFragment newGoodsFragment;
    BoutiqueFragment boutiqueFragment;
    CategoryFragment categoryFragment;
    PersonalFragment pensonalFragment;
    CartFragment cartFragment;
    Fragment[] fragments;
    int currentIndex;
    cartCountReceiver mRecevier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setListener() {
        super.setListener();
        IntentFilter filter=new IntentFilter(I.BROADCAST_UPDATE_COUNT);
        mRecevier=new cartCountReceiver();
        this.registerReceiver(mRecevier,filter);
    }

    private void initFragment() {
        fragments = new Fragment[5];
        newGoodsFragment = new NewGoodsFragment(this);
        boutiqueFragment = new BoutiqueFragment();
        categoryFragment = new CategoryFragment();
        pensonalFragment = new PersonalFragment();
        cartFragment = new CartFragment();
        fragments[0] = newGoodsFragment;
        fragments[1] = boutiqueFragment;
        fragments[2] = categoryFragment;
        fragments[3] = cartFragment;
        fragments[4] = pensonalFragment;
        FragmentManager manger = getSupportFragmentManager();
        FragmentTransaction transaction = manger.beginTransaction();
        transaction.add(R.id.fragment_container, newGoodsFragment)
                .add(R.id.fragment_container, boutiqueFragment)
                .hide(boutiqueFragment)
                .show(newGoodsFragment)
                .commit();
        Log.i("main", "finishinitFragment");
    }

    @Override
    protected void initView() {
        btns = new RadioButton[]{rbNewGood, rbBoutique, rbCategory, rbCart, rbPersonal};
        initFragment();
    }

    private void setRadioButtonStatus() {
        L.e("index" + index);
        for (int i = 0; i < btns.length; i++) {
            if (i == index) {
                btns[i].setChecked(true);
            } else {
                btns[i].setChecked(false);
            }
        }

    }

    public void setFragment() {
        if (currentIndex != index) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                ft.add(R.id.fragment_container, fragments[index]);
            }
            ft.show(fragments[index]).commit();
            setRadioButtonStatus();
            currentIndex = index;
        }
    }

    public void onCheckedChange(View v) {
        L.i("changeclicked");
        switch (v.getId()) {
            case R.id.rbNewGood:
                index = 0;
                break;
            case R.id.rbBoutique:
                L.e("clicked");
                index = 1;
                break;
            case R.id.rbCategory:
                index = 2;
                break;
            case R.id.rbCart:
                if(FuLiCenterApplication.getUser()==null){
                    MFGT.gotoLoginActivityFromCart(this);
                }else {
                    index = 3;
                }

                break;
            case R.id.rbPersonal:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLoginActivity(this);
                } else {
                    index = 4;
                }

                break;
        }

        setFragment();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(index == 4 && FuLiCenterApplication.getUser()==null){
            index = 0;
        }
        setFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == I.REQUEST_CODE_LOGIN && FuLiCenterApplication.getUser() != null) {
            L.e("main","success get result");
            index = 4;
        }else if(requestCode==I.REQUEST_CODE_LOGIN_CART&&FuLiCenterApplication.getUser()!=null){
            index = 3;
        }
    }
    class cartCountReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int count = intent.getIntExtra("count", 0);
           // MainActivity.this.count.setText(count);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mRecevier);
    }
}
