package com.swing.githubloginsdk.src.executor;

import android.os.Handler;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestExecutor {

    private static final int POOL_SIZE = 2;

    private final ExecutorService mExecutorService;
    private final Handler mUiHandler;

    public RequestExecutor(Handler uiHandler) {
        mExecutorService = Executors.newFixedThreadPool(POOL_SIZE);
        mUiHandler = uiHandler;
    }

    public <E> void execute(final IRequest<E> request, final ResultListener<E> listener) {
        // manage future entry later, if need
        Future<?> future = mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request requestHttp = new Request.Builder().url(request.getUrl()).build();

                try {
                    Response response = client.newCall(requestHttp).execute();
                    String responseString = Objects.requireNonNull(response.body()).string();
                    E resp = request.parse(responseString);
                    notifySuccess(listener, resp);
                } catch (Throwable e) {
                    notifyFailure(listener, e);
                    e.printStackTrace();
                }
            }
        });
    }

    protected void notifyFailure(final ResultListener<?> listener, Throwable e) {
        // handle exceptions here
        final int errorCode = 401;
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onError(errorCode);
            }
        });
    }

    protected <E> void notifySuccess(final ResultListener<E> listener, final E resp) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(resp);
            }
        });
    }

    public interface IRequest<E> {
        String getUrl();

        E parse(String resp);
    }

    public interface ResultListener<E> {
        void onSuccess(E result);

        void onError(int errorCode);
    }
}
