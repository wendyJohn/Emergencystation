package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.data.VideoConstant;
import com.squareup.picasso.Picasso;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author QiaoShi
 */
public class VideoListAdapter extends BaseAdapter {
    int[] mVideoIndexs = {0, 1};
    Context mContext;
    int mPager = -1;

    public VideoListAdapter(Context context) {
        this.mContext = context;
    }

    public VideoListAdapter(Context context, int pager) {
        this.mContext = context;
        this.mPager = pager;
    }

    @Override
    public int getCount() {
        return mPager == -1 ? mVideoIndexs.length : 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.videoview_item, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mJCVideoPlayerStandard = (JCVideoPlayerStandard) convertView.findViewById(R.id.videoplayer);
        if (mPager == -1) {
            holder.mJCVideoPlayerStandard.setUp(VideoConstant.mVideoUrls[0][position], JCVideoPlayer.SCREEN_LAYOUT_LIST, VideoConstant.mVideoTitles[0][position]);
            Picasso.with(convertView.getContext()).load(VideoConstant.mVideoThumbs[0][position]).into(holder.mJCVideoPlayerStandard.thumbImageView);
        } else {
            holder.mJCVideoPlayerStandard.setUp(VideoConstant.mVideoUrls[mPager][position], JCVideoPlayer.SCREEN_LAYOUT_LIST, VideoConstant.mVideoTitles[mPager][position]);
            Picasso.with(convertView.getContext()).load(VideoConstant.mVideoThumbs[mPager][position]).into(holder.mJCVideoPlayerStandard.thumbImageView);
        }
        return convertView;
    }

    class ViewHolder {
        JCVideoPlayerStandard mJCVideoPlayerStandard;
    }
}
