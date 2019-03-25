package co.mike.zemoga.base;

import androidx.annotation.StringRes;

public interface AndroidProvider {

    void showError(String message);

    void showError(@StringRes int messageRes);

    void showMessage(String message);

    void showMessage(@StringRes int messageRes);

    void showLoading(boolean show);

    void showProgressDialog();

    void showProgressDialog(String message);

    void showProgressDialog(@StringRes int messageRes);

    void dismissProgressDialog();


}