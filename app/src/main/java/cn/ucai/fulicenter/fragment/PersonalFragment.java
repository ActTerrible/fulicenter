package cn.ucai.fulicenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by mac-yk on 2016/10/21.
 */

public class PersonalFragment extends BaseFragment {
    Context mContext;
    @BindView(R.id.iv_personal_avatar)
    ImageView ivPersonalAvatar;
    @BindView(R.id.tv_personal_name)
    TextView tvPersonalName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        mContext = getContext();
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        UserBean user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLoginActivity(mContext);
        } else {
            ImageLoader.setAvatar(mContext, ImageLoader.getAvatarUrl(user), ivPersonalAvatar);
            tvPersonalName.setText(user.getMuserNick());
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @OnClick({R.id.tv_personal_set, R.id.iv_personal_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_personal_set:
                break;
            case R.id.iv_personal_message:
                break;
        }
    }

}
