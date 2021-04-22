package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.Articles;
import com.sanleng.emergencystation.utils.AsyncImageloader;

import java.util.List;

/**适配器
 * @author QiaoShi
 */
public class ArticlesAdapter extends BaseAdapter {
    private List<Articles.DataBean.ContentBean> mList;
    private Context mContext;
    private ListView listView;
    private AsyncImageloader asyncImageLoader;

    public ArticlesAdapter(Context mContext, List<Articles.DataBean.ContentBean> mList, ListView listView) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.mList = mList;
        this.listView = listView;
        asyncImageLoader = new AsyncImageloader();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.articles_item, null);
            holder = new Holder();
            holder.cst_infos = convertView.findViewById(R.id.cst_infos);
            holder.times = convertView.findViewById(R.id.times);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        try {
            holder.cst_infos.setText(mList.get(position).getArticleTitle());
            holder.times.setText(mList.get(position).getPublishTime());
            ImageView iv =  convertView.findViewById(R.id.image_article);

            // 接口回调的方法，完成图片的读取;
            String imageUrl = mList.get(position).getArticleImages();
            iv.setTag(imageUrl);
            Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new AsyncImageloader.ImageCallback() {
                public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                    ImageView imageViewByTag = listView.findViewWithTag(imageUrl);
                    if (imageViewByTag != null) {
                        imageViewByTag.setImageDrawable(imageDrawable);
                    }
                }
            });
            if (cachedImage == null) {
                iv.setImageResource(R.mipmap.nosdata_icon);
            } else {
                iv.setImageDrawable(cachedImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class Holder {
        TextView cst_infos;
        TextView times;
    }
}
