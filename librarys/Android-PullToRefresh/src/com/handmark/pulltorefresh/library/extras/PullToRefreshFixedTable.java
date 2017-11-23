package com.handmark.pulltorefresh.library.extras;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.fixedheadtable.TableFixHeaders;
import com.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;

public class PullToRefreshFixedTable extends PullToRefreshBase<TableFixHeaders> {

	public PullToRefreshFixedTable(Context context) {
		super(context);
	}

	public PullToRefreshFixedTable(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshFixedTable(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode,
			com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle animStyle) {
		super(context, mode, animStyle);
	}

	public PullToRefreshFixedTable(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode) {
		super(context, mode);
	}

	@Override
	public Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected TableFixHeaders createRefreshableView(Context context, AttributeSet attrs) {
		final TableFixHeaders tfh;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			tfh = new InternalTableFixHeadersSDK9(context, attrs);
		} else {
			tfh = new InternalTableFixHeaders(context, attrs);
		}
		return tfh;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		return getRefreshableView().getActualScrollY() == getRefreshableView().getMaxScrollY();
	}

	@Override
	protected boolean isReadyForPullStart() {
		return getRefreshableView().getActualScrollY() == 0;
	}

	@TargetApi(9)
	final class InternalTableFixHeadersSDK9 extends TableFixHeaders {

		public InternalTableFixHeadersSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX,
				int maxOverScrollY, boolean isTouchEvent) {
			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
					maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToRefreshFixedTable.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);

			return returnValue;
		}

	}

	final class InternalTableFixHeaders extends TableFixHeaders implements EmptyViewMethodAccessor {

		public InternalTableFixHeaders(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
//			PullToRefreshFixedTable.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyView(View emptyView) {

		}

	}
}
