package com.example.new_project.Register;

import com.example.new_project.BaseMVP.baseView;

public interface RegisterView extends baseView {
    void Register(String res);
    void Failure(String res);

}
