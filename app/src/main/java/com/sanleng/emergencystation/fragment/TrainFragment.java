package com.sanleng.emergencystation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.ArticleInfosActivity;
import com.sanleng.emergencystation.activity.LoginActivity;
import com.sanleng.emergencystation.adapter.ArticlesAdapter;
import com.sanleng.emergencystation.bean.Articles;
import com.sanleng.emergencystation.model.ArticlesContract;
import com.sanleng.emergencystation.presenter.Requests;
import com.sanleng.emergencystation.utils.MessageEvent;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.utils.Utils;
import com.sanleng.emergencystation.view.MyListener;
import com.sanleng.emergencystation.view.PullToRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 教育培训
 */
public class TrainFragment extends BaseFragment implements ArticlesContract {

    private View view;
    private ListView listView;
    private PullToRefreshLayout ptrl;
    private ArticlesAdapter articlesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_train, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        view = getView();
        initView();
    }

    public void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ptrl = (view.findViewById(R.id.refresh_view));
        ptrl.setOnRefreshListener(new MyListener());
        listView = view.findViewById(R.id.content_view);
    }

    public void refresh() {
        Requests.getArticleCall(TrainFragment.this, getActivity(), "0", "50");//加载安全咨询
    }

    /**
     * 接收EventBus返回数据
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backData(MessageEvent messageEvent) {
        switch (messageEvent.getTAG()) {
            case MessageEvent.EXAM_Refreshs:
                refresh();
                break;
            case MessageEvent.EXAM_load:
                refresh();
                break;
            default:
                break;
        }
    }


    // 教育培训加载数据
    private void addData(List<Articles.DataBean.ContentBean> list) {
        articlesAdapter = new ArticlesAdapter(getActivity(), list, listView);
        // 没有数据就提示暂无数据。
        listView.setEmptyView(view.findViewById(R.id.nodata));
        listView.setAdapter(articlesAdapter);
        articlesAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String articletitle = list.get(position).getArticleTitle();
                    String article = list.get(position).getArticle();
                    Intent articles = new Intent(getActivity(), ArticleInfosActivity.class);
                    articles.putExtra("articletitle", articletitle);
                    articles.putExtra("article", article);
                    startActivity(articles);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void ArSuccess(List<Articles.DataBean.ContentBean> mList) {
        try {
            addData(mList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Failed() {

    }

    @Override
    public void TimeOut() {
        try {
            // 清空sharepre中的用户名和密码
            new SVProgressHUD(getActivity()).showInfoWithStatus("登录超时，请重新登录");
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    PreferenceUtils.setString(getActivity(), "EmergencyStation_usernames", "");
                    Intent loginOutIntent = new Intent(getActivity(), LoginActivity.class);
                    loginOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginOutIntent);
                    getActivity().finish();
                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

}
