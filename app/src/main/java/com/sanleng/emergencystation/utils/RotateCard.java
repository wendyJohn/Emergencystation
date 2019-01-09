package com.sanleng.emergencystation.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;


public class RotateCard extends FrameLayout {

	private ArrayList<RotateCardViewHolder> viewHolderList;
	private ArrayList<View> viewList;
	private int ovalA, ovalB;// 椭圆轨迹的长半轴，短半轴
	private int parentWidth, parentHeight;// RotateCard的宽高
	private int viewWidth, viewHeight;// viewSet中的View的宽高
	FrameLayout.LayoutParams viewpParams;// view的LayoutParams
	private float angleOffset;// 手指每滑动一像素需要绕椭圆中心旋转的度数
	private RotateCardOnTouchListener mTouchListener;// 供RotateCard和子View同时使用,处理滑动事件

	// 增加绘制前的监听以获得RotateCard的宽高
	private ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
		@Override
		public boolean onPreDraw() {
			parentWidth = RotateCard.this.getMeasuredWidth();
			parentHeight = RotateCard.this.getMeasuredHeight();
			angleOffset = (float) ((180.0 / parentWidth) * 1.5);

			if (viewList != null && viewList.size() > 0) {
				afterGetParamsAndViews();
			}

			// 除去监听
			RotateCard.this.getViewTreeObserver().removeOnPreDrawListener(
					onPreDrawListener);
			return true;
		}
	};

	public RotateCard(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public RotateCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public RotateCard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// 增加RotateCard绘制之前的监听
		RotateCard.this.getViewTreeObserver().addOnPreDrawListener(
				onPreDrawListener);

		// 增加RotateCard的滑动监听
		mTouchListener = new RotateCardOnTouchListener();
		this.setOnTouchListener(mTouchListener);
	}

	/**
	 * 必须保证传入的View集合的每个View的大小一致，否则显示可能不准确
	 */
	public void commitViews(ArrayList<View> viewList, int viewWidth,
							int viewHeight) {
		this.removeAllViews();
		this.viewList = viewList;
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		viewpParams = new LayoutParams(viewWidth, viewHeight);
		// Log.i(INFO_TAG, "viewWidth:" + viewWidth);
		// Log.i(INFO_TAG, "viewHeight:" + viewHeight);

		if (parentWidth != 0 && parentHeight != 0) {
			afterGetParamsAndViews();
		}
	}

	/**
	 * 必须保证传入的View集合的每个View的大小一致，否则显示可能不准确
	 * 应该没有谁的view不是圆形或正方形吧？输入直径或边长就好了
	 *            View集合
	 */
	public void commitViews(ArrayList<View> viewList, int viewWidthHeight) {
		this.removeAllViews();
		this.viewList = viewList;
		this.viewWidth = viewWidthHeight;
		this.viewHeight = viewWidthHeight;
		viewpParams = new LayoutParams(viewWidth, viewHeight);

		if (parentWidth != 0 && parentHeight != 0) {
			afterGetParamsAndViews();
		}
	}

	/**
	 * 在获得RotateCard后并且调用过commitViews函数后,此函数被调用
	 */
	private void afterGetParamsAndViews() {

		ovalA = (parentWidth - viewWidth) / 2;
		ovalB = (int) ((parentHeight - viewHeight * 1.5) / 2);

		int viewNum = viewList.size();
		float anglePerView = (float) (360.0 / viewNum);

		if (viewHolderList == null) {
			viewHolderList = new ArrayList<RotateCardViewHolder>();
		} else {
			viewHolderList.clear();
		}

		float tmpAngle = 270.0f;// 第一个view在270度的位置，即最前的位置
		for (int i = 0; i < viewNum; ++i) {
			int index = i + 1;
			View view = viewList.get(i);
			// 添加子view的滑动事件监听
			view.setOnTouchListener(mTouchListener);
			// 添加子view的点击事件监听
			view.setOnClickListener(new ItemOnClickListener(index));
			// 为子view创建一个RotateCardViewHolder
			RotateCardViewHolder holder = new RotateCardViewHolder(view,
					tmpAngle, index);
			// 将holder捆绑在子view上，方面从子view直接获取holder
			view.setTag(holder);
			viewHolderList.add(holder);

			// 增加anglePerView的角度，用于确定下一个view的位置
			tmpAngle += anglePerView;
		}
		// 把子view添加到RotateCard中
		loadViewBySequence();
	}

	/**
	 * 把所有view旋转angleDiff的角度
	 *
	 * @param angleDiff
	 *            旋转的角度
	 */
	private void rotateViewsByAngle(float angleDiff) {
		// 若有动画在进行，则不进行旋转
		if (set != null && set.isRunning()) {
			return;
		}
		// 旋转每一个view
		for (RotateCardViewHolder holder : viewHolderList) {
			holder.setAngle(holder.getAngle() + angleDiff);
		}
		// 重新载入view以确保他们正确的遮挡关系
		loadViewBySequence();
	}

	/**
	 * 通过对vew排序后重新把view添加到RotateCard中来决定他们的遮挡关系 ，越靠前的view越慢添加
	 */
	private void loadViewBySequence() {
		Collections.sort(viewHolderList, new RotateCardViewHolderComparator());
		this.removeAllViews();
		for (RotateCardViewHolder holder : viewHolderList) {
			this.addView(holder.getView(), viewpParams);
		}
	}

	/**
	 * 封装对view的一些操作
	 */
	public class RotateCardViewHolder {
		private View mView;
		private float mAngle;
		private int index;

		public RotateCardViewHolder(View view, float angle, int index) {
			this.mView = view;
			this.mAngle = angle;
			this.index = index;
			setAngle(angle);
		}

		/**
		 * 设置位置和大小
		 */
		public void resetPositionAndScale() {
			// 设置大小
			float angleDiff = getAngleDiffWith90();
			float scale = (float) ((1.0 * angleDiff / 180) * 0.75 + 0.25);
			mView.setScaleX(scale);
			mView.setScaleY(scale);

			// 设置位置
			float x = (float) (ovalA * Math.cos(mAngle / 180.0 * Math.PI)
					+ parentWidth / 2 - viewWidth / 2);
			float y = (float) (parentHeight / 2 - ovalB
					* Math.sin(mAngle / 180.0 * Math.PI) - viewHeight / 2);
			mView.setX(x);
			mView.setY(y);
		}

		/**
		 * 设置view的角度，同时改变他的显示的大小和位置
		 *
		 * @param angle
		 *            view的角度
		 */
		public void setAngle(float angle) {
			// 保证mAngle在0~360之间
			mAngle = angle;
			while (mAngle < 0 || mAngle > 360) {
				if (mAngle < 0) {
					mAngle = (mAngle + 360) % 360;
				} else {
					mAngle = mAngle % 360;
				}
			}
			resetPositionAndScale();
		}

		/**
		 * 获取view的角度
		 *
		 * @return view的角度
		 */
		public float getAngle() {
			return mAngle;
		}

		/**
		 * 获取view一开始的序号
		 *
		 * @return view的序号
		 */
		public int getIndex() {
			return index;
		}

		/**
		 * 获取view的实例
		 *
		 * @return view的实例
		 */
		public View getView() {
			return mView;
		}

		/**
		 * 获取该view当前位置跟90度位置相差的度数
		 *
		 * @return 跟90度相差的度数
		 */
		public float getAngleDiffWith90() {
			float tmpAngle = mAngle > 270 ? mAngle - 360 : mAngle;
			float angleDiff = Math.abs(tmpAngle - 90);
			return angleDiff;
		}

		/**
		 * 获取该view当前位置跟270度位置相差的度数
		 *
		 * @return 跟270度相差的度数
		 */
		public float getAngleDiffWith270() {
			float tmpAngle = mAngle < 90 ? mAngle + 360 : mAngle;
			float angleDiff = Math.abs(tmpAngle - 270);
			return angleDiff;
		}

	}

	/**
	 * 比较器，用来把view按照度数越接近270度越靠后的顺序排序
	 *
	 */
	public class RotateCardViewHolderComparator implements
			Comparator<RotateCardViewHolder> {

		@Override
		public int compare(RotateCardViewHolder a, RotateCardViewHolder b) {
			// TODO Auto-generated method stub
			return a.getAngleDiffWith270() > b.getAngleDiffWith270() ? -1 : 1;

		}
	}

	private float lastX, nowX;// 上次手指的x坐标位置和当前手指的x坐标位置
	private VelocityTracker vTracker;// 监控手指滑动的使用率
	private AnimatorSet set;// 动画集
	private boolean justCancelAnimation = false;// 是否刚刚通过点击屏幕使正在进行的动画取消,用于取消动画的同事发生的点击事件

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN: {
				// 若动画正在进行，则取消动画
				if (set != null && set.isRunning()) {
					set.cancel();
					set = null;
					justCancelAnimation = true;
				}
				// 获取VelocityTracker的一个实例
				vTracker = VelocityTracker.obtain();
				// 记录lastX
				lastX = event.getX();
			}
			break;
			case MotionEvent.ACTION_MOVE: {
				justCancelAnimation = false;
				// 把动作送到vTracker
				vTracker.addMovement(event);
				// 记录nowX
				nowX = event.getX();
			}
			break;
			case MotionEvent.ACTION_UP: {
				if (justCancelAnimation) {
					justCancelAnimation = false;
					return true;// 用来取消点击事件
				}
				// 设置计算单元，为1秒
				vTracker.computeCurrentVelocity(1000);
				// 获取在x轴方向1秒内划过的像素数
				float xSpeed = vTracker.getXVelocity();
				// 活动过快，则执行动画，让RotateCard再旋转一会才停下
				if (Math.abs(xSpeed) > 800.0f) {
					// 还要旋转的角度
					float angleDiff = xSpeed / 10;
					// 还要旋转的时间
					int duration = (int) (Math.abs(angleDiff) / 180 * 1000);

					// 设置动画集
					set = new AnimatorSet();
					ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f)
							.setDuration(duration);
					animation.addUpdateListener(new AnimatorUpdateListener() {

						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							// TODO Auto-generated method stub
							loadViewBySequence();
						}

					});
					set.play(animation);
					for (RotateCardViewHolder _holder : viewHolderList) {
						ObjectAnimator oa = ObjectAnimator.ofFloat(_holder,
								"angle", _holder.getAngle(),
								_holder.getAngle() + angleDiff).setDuration(
								duration);
						oa.setInterpolator(new DecelerateInterpolator());
						set.play(oa).with(animation);
					}
					// 开始动画集合
					set.start();
				}
			}
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	private class RotateCardOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			int action = event.getAction();
			switch (action) {
				case MotionEvent.ACTION_DOWN: {

				}
				break;
				case MotionEvent.ACTION_MOVE: {
					// 此次手指滑动需要旋转的度数
					float angleDiff = (nowX - lastX) * angleOffset;
					// 旋转angleDiff的角度
					rotateViewsByAngle(angleDiff);
					// 记录lastX
					lastX = nowX;
				}
				break;
				case MotionEvent.ACTION_UP: {

				}
				break;
			}

			if (v instanceof RotateCard) {
				// 若是手指的位置是RotateCard，返回true让事件继续传递给RotateCard
				return true;
			} else {
				// 若是手指的位置是子view，返回false让子view的点击事件可以响应
				return false;
			}
		}
	}

	public class ItemOnClickListener implements OnClickListener {

		private int index;

		public ItemOnClickListener(int index) {
			this.index = index;
		}

		/**
		 * * 把被点击的view旋转的最前面
		 */
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final View tView = v;
			final RotateCardViewHolder holder = (RotateCardViewHolder) v
					.getTag();
			float angle = holder.getAngle();
			// 需要旋转的度数
			float angleDiff = angle >= 0.0f && angle <= 90.0f ? -90.0f - angle
					: 270.0f - angle;
			// 是否子执行view的点击事件(若被点击view在很靠前，则旋转后执行它的点击事件)
			final boolean performClick = Math.abs(angleDiff) < 45.0f;
			int duration = (int) (Math.abs(angleDiff) / 180 * 1000);

			// 初始化动画集
			set = new AnimatorSet();
			ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f)
					.setDuration(duration);
			animation.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					// TODO Auto-generated method stub
					loadViewBySequence();
				}

			});
			set.play(animation);
			if (performClick) {
				ObjectAnimator aa = ObjectAnimator.ofFloat(tView, "alpha",
						1.0f, 0.5f, 1.0f).setDuration(800);
				ObjectAnimator saX = ObjectAnimator.ofFloat(tView, "scaleX",
						1.0f, 1.5f, 1.0f).setDuration(800);
				ObjectAnimator saY = ObjectAnimator.ofFloat(tView, "scaleY",
						1.0f, 1.5f, 1.0f).setDuration(800);
				set.play(aa).with(animation);
				set.play(saX).with(animation);
				set.play(saY).with(animation);
			}
			for (RotateCardViewHolder _holder : viewHolderList) {
				ObjectAnimator oa = ObjectAnimator.ofFloat(_holder, "angle",
						_holder.getAngle(), _holder.getAngle() + angleDiff)
						.setDuration(duration);
				oa.setInterpolator(new DecelerateInterpolator());
				set.play(oa).with(animation);
			}
			set.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					// TODO Auto-generated method stub
					if (mOnItemClickListener != null && performClick) {
						// 执行外部设置的点击事件
						mOnItemClickListener.onItemClickListener(tView, index);
					}
				}

				@Override
				public void onAnimationCancel(Animator animation) {
					// TODO Auto-generated method stub
					tView.setAlpha(1.0f);
					holder.resetPositionAndScale();
				}
			});
			set.start();
		}
	}

	/**
	 * 子view点击事件监听器OnItemClickListener
	 *
	 * RotateCard内部需要自行处理子vew的点击事件，OnItemClickListener监听器供外部代码使用
	 */
	public interface OnItemClickListener {
		public void onItemClickListener(View view, int index);
	}

	private OnItemClickListener mOnItemClickListener;

	/**
	 * 添加子view点击事件监听
	 *
	 * @param onItemClickListener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}
}
