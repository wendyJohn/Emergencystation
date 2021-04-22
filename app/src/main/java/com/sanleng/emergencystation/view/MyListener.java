package com.sanleng.emergencystation.view;

import android.os.Handler;
import android.os.Message;

import com.sanleng.emergencystation.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;


public class MyListener implements PullToRefreshLayout.OnRefreshListener
{

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 下拉刷新操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{// 千万别忘了告诉控件刷新完毕了哦！
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 2000);
		EventBus.getDefault().post(new MessageEvent(MessageEvent.EXAM_Refreshs));
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 加载操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件加载完毕了哦！
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 2000);
		EventBus.getDefault().post(new MessageEvent(MessageEvent.EXAM_load));
	}

}
