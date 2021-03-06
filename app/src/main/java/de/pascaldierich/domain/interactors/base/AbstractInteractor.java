package de.pascaldierich.domain.interactors.base;

import android.support.annotation.NonNull;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;

public abstract class AbstractInteractor implements BaseInteractor {
    private static final String LOG_TAG = AbstractInteractor.class.getSimpleName();
    
    private Executor mThreadExecutor;
    protected MainThread mMainThread;
    
    private volatile boolean mIsCanceled;
    private volatile boolean mIsRunning;
    
    public AbstractInteractor(@NonNull Executor threadExecutor, @NonNull MainThread mainThread) {
        mThreadExecutor = threadExecutor;
        mMainThread = mainThread;
    }
    
    /**
     * This method contains the actual business logic of the interactor. It SHOULD NOT BE USED DIRECTLY but, instead, a
     * developer should call the execute() method of an interactor to make sure the operation is done on a background thread.
     * <p/>
     * This method should only be called directly while doing unit/integration tests. That is the only reason it is declared
     * public as to help with easier testing.
     */
    public abstract void run();
    
    public void cancel() {
        mIsCanceled = true;
        mIsRunning = false;
    }
    
    public boolean isRunning() {
        return mIsRunning;
    }
    
    public void onFinished() {
        mIsRunning = false;
        mIsCanceled = false;
    }
    
    public void execute() {
        
        // mark this interactor as running
        this.mIsRunning = true;
        
        // start running this interactor in a background thread
        mThreadExecutor.execute(this);
    }
}
