package com.example.new_project.Login;

import com.example.new_project.BaseMVP.baseView;

public interface LoginView extends baseView {
    void Login(String res);
    void Failure(String res);
}
