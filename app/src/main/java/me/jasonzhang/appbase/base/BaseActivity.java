package me.jasonzhang.appbase.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import org.reactivestreams.Subscription;

import butterknife.ButterKnife;

/**
 * Created by JifengZhang on 2017/4/12.
 */

public abstract class BaseActivity<T extends BasePresenter> extends Activity{
    protected T presenter;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayoutId());
        ButterKnife.bind(this);
        presenter = createPresenter();
        if (presenter!=null) {
            //noinspection unchecked
            presenter.attachView((BaseView) this);
        }
    }

    protected abstract @LayoutRes int getContentLayoutId();

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter!=null) {
            presenter.unSubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null) {
            presenter.detachView();
        }
    }

    protected abstract T createPresenter();
}
