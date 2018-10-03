package com.my.shishir.demoapp.listener;

import android.support.annotation.NonNull;

/**
 * A parameterized task result that can be used for callbacks from any task.
 */
public interface TaskResultListener<T> {

    /**
     * Called when the task has succeeded.
     */
    void onTaskSuccess(@NonNull T result);

    /**
     * Called when the task has failed.
     * Note that the error is usually a string resource, but we cannot
     * annotate it that way because it can also be 0.
     *
     * @param error user-oriented error message, or 0 if no error should be
     *                             displayed - typically because it's already been shown.
     */
    void onTaskFailure(int error);
}
